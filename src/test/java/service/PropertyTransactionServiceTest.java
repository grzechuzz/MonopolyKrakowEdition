package service;

import model.field.Ownable;
import model.field.PropertyField;
import model.field.ResortField;
import model.field.SpecialField;
import model.player.Player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PropertyTransactionServiceTest {

    @Mock
    private UserInteractionService ui;

    @InjectMocks
    private PropertyTransactionService pts;

    private Player player1;
    private Player player2;
    private PropertyField field;

    @BeforeEach
    void setUp() {
        player1 = new Player("grzechu");
        player2 = new Player("krzychu");
        field = new PropertyField("Opolska", 18, null, 520000, 220000);
    }

    @Test
    void testBankruptPlayer() {
        player1.addProperty(field);
        pts.bankruptPlayer(player1);

        assertAll(
                () -> assertTrue(player1.getStatus().isEliminated()),
                () -> assertEquals(0, player1.getProperties().size())
        );
    }

    @Test
    void testForceSellWithVariousFieldsSelected() {
        PropertyField f1 = new PropertyField("Grodzka", 38, null, 900000, 450000);
        ResortField f2 = new ResortField("Zakrzówek", 27, null, 600000, 100000);
        SpecialField f3 = new SpecialField("Tauron Arena", 17, null, 750000, 80000);
        f1.setHousesCount(2);
        player1.addProperty(f1);
        player1.addProperty(f2);
        player1.addProperty(f3);

        List<Ownable> selected = List.of(f1, f3);

        when(ui.propertiesToSell(player1)).thenReturn(selected);

        pts.forceSell(player1);

        assertAll(
                () -> assertEquals(7550000, player1.getBalance()),
                () -> assertNull(f1.getOwner()),
                () -> assertEquals(player1, f2.getOwner()),
                () -> assertNull(f3.getOwner()),
                () -> assertEquals(1, player1.getProperties().size())
        );
    }

    @Test
    void testForceSellWithNoFieldsSelected() {
        PropertyField f1 = new PropertyField("Grodzka", 38, null, 900000, 450000);
        ResortField f2 = new ResortField("Zakrzówek", 27, null, 600000, 100000);
        SpecialField f3 = new SpecialField("Tauron Arena", 17, null, 750000, 80000);
        f1.setHousesCount(2);
        player1.addProperty(f1);
        player1.addProperty(f2);
        player1.addProperty(f3);

        when(ui.propertiesToSell(player1)).thenReturn(List.of());

        pts.forceSell(player1);

        assertAll(
                () -> assertEquals(5000000, player1.getBalance()),
                () -> assertEquals(player1, f1.getOwner()),
                () -> assertEquals(player1, f2.getOwner()),
                () -> assertEquals(player1, f3.getOwner()),
                () -> assertEquals(3, player1.getProperties().size())
        );
    }

    @Test
    void testPayPlayerHasEnoughMoney() {
        pts.pay(player1, player2, 900000);
        assertAll(
                () -> assertEquals(4100000, player1.getBalance()),
                () -> assertEquals(5900000, player2.getBalance())
        );
    }

    @Test
    void testPayPlayerHasEnoughMoneyWithForceSell() {
        PropertyField f = new PropertyField("Grodzka", 38, null, 900000, 450000);
        f.setHotel(true);
        player1.addProperty(f);
        when(ui.propertiesToSell(player1)).thenReturn(List.of(f));

        pts.pay(player1, player2, 7000000);

        assertAll(
                () -> assertEquals(700000, player1.getBalance()),
                () -> assertEquals(12000000, player2.getBalance())
        );
    }

    @Test
    void testPayPlayerHasNotEnoughMoney() {
        when(ui.propertiesToSell(player1)).thenReturn(List.of());

        pts.pay(player1, player2, 7000000);

        assertAll(
                () -> assertTrue(player1.getStatus().isEliminated()),
                () -> assertEquals(10000000, player2.getBalance())
        );
    }

    @Test
    void testPayToBankPlayerHasMoney() {
        pts.payToBank(player1, 300000);

        assertEquals(4700000, player1.getBalance());
    }

    @Test
    void testPayToBankPlayerHasNotEnoughMoney() {
        SpecialField f = new SpecialField("Tauron Arena", 17, null, 750000, 80000);
        player1.addProperty(f);
        when(ui.propertiesToSell(player1)).thenReturn(List.of(f));

        pts.payToBank(player1, 6000000);

        assertTrue(player1.getStatus().isEliminated());
    }

    @Test
    void testBuyFieldWithEnoughMoneyToBuy() {
        when(ui.confirmPurchase(field.getName(), field.getPrice())).thenReturn(true);

        assertAll(
                () -> assertTrue(pts.buyField(player1, field)),
                () -> assertEquals(1, player1.getProperties().size()),
                () -> assertEquals(4480000, player1.getBalance()),
                () -> assertTrue(player1.getProperties().containsAll(List.of(field)))
        );
    }

    @Test
    void testBuyFieldWithNotEnoughMoneyToBuy() {
        player1.subtractBalance(4800000);

        assertFalse(pts.buyField(player1, field));
    }

    @Test
    void testBuyFieldWithNegativeAnswer() {
        when(ui.confirmPurchase(field.getName(), field.getPrice())).thenReturn(false);

        assertFalse(pts.buyField(player1, field));
    }

    @Test
    void testBuyFieldsWithEnoughMoneyToBuy() {
        PropertyField f1 = new PropertyField("Grodzka", 38, null, 900000, 450000);
        ResortField f2 = new ResortField("Zakrzówek", 27, null, 600000, 100000);
        SpecialField f3 = new SpecialField("Tauron Arena", 17, null, 750000, 80000);

        when(ui.confirmPurchase(f1.getName(), f1.getPrice())).thenReturn(true);
        when(ui.confirmPurchase(f2.getName(), f2.getPrice())).thenReturn(false);
        when(ui.confirmPurchase(f3.getName(), f3.getPrice())).thenReturn(true);

        assertTrue(pts.buyField(player1, f1));
        assertFalse(pts.buyField(player1, f2));
        assertTrue(pts.buyField(player1, f3));

        assertAll(
                () -> assertEquals(2, player1.getProperties().size()),
                () -> assertEquals(3350000, player1.getBalance()),
                () -> assertTrue(player1.getProperties().containsAll(List.of(f1, f3)))
        );
    }

    @Test
    void testBuildHouses() {
        when(ui.promptHouseCount(field, 2)).thenReturn(2);
        field.setOwner(player1);

        assertAll(
                () -> assertTrue(pts.buildHouses(player1, field)),
                () -> assertEquals(4480000, player1.getBalance()),
                () -> assertEquals(2, field.getHousesCount())
        );
    }

    @Test
    void testBuildHousesWhenFieldNotOwned() {
        assertFalse(pts.buildHouses(player1, field));
    }

    @Test
    void testBuildHousesWhenLimitSatisfied() {
        field.setHousesCount(3);
        field.setOwner(player1);

        assertFalse(pts.buildHouses(player1, field));
    }

    @Test
    void testBuildHousesWithNotEnoughMoney() {
        player1.subtractBalance(5000000);
        field.setOwner(player1);
        when(ui.promptHouseCount(field, 2)).thenReturn(1);

        assertFalse(pts.buildHouses(player1, field));
    }

    @Test
    void testUpgradeToHotel() {
        field.setHousesCount(3);
        field.setOwner(player1);

        assertTrue(pts.upgradeToHotel(player1, field));
    }

    @Test
    void testUpgradeToHotelWhenFieldNotOwned() {
        assertFalse(pts.upgradeToHotel(player1, field));
    }

    @Test
    void testUpgradeToHotelWhenNotEnoughHouses() {
        field.setHousesCount(2);
        field.setOwner(player1);

        assertFalse(pts.upgradeToHotel(player1, field));
    }

    @Test
    void testUpgradeToHotelWithNotEnoughMoney() {
        field.setHousesCount(3);
        player1.subtractBalance(4500000);
        field.setOwner(player1);

        assertFalse(pts.upgradeToHotel(player1, field));
    }

    @Test
    void testBuyOpponentField() {
        player1.addProperty(field);
        when(ui.confirmPurchase(field.getName(), 780000)).thenReturn(true);

        assertAll(
                () -> assertTrue(pts.buyOpponentField(player2, player1, field)),
                () -> assertEquals(player2, field.getOwner()),
                () -> assertEquals(5780000, player1.getBalance()),
                () -> assertEquals(4220000, player2.getBalance()),
                () -> assertFalse(player1.getProperties().contains(field)),
                () -> assertTrue(player2.getProperties().contains(field))
        );
    }

    @Test
    void testBuyOponentFieldWithNoConfirmation() {
        field.setOwner(player1);
        when(ui.confirmPurchase(field.getName(), 780000)).thenReturn(false);

        assertFalse(pts.buyOpponentField(player2, player1, field));
    }

    @Test
    void buyOpponentFieldWithNotEnoughMoney() {
        player1.subtractBalance(4000000);
        assertFalse(pts.buyOpponentField(player1, player2, field));
    }
}

