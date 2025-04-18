package service;

import model.field.PropertyField;
import model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.contains;

@ExtendWith(MockitoExtension.class)
class TaxServiceTest {

    @Mock
    PropertyTransactionService pts;

    @Mock
    UserInteractionService ui;

    @InjectMocks
    TaxService ts;

    @Test
    void testPayTax() {
        Player player = new Player("jaca");
        PropertyField pf1 = new PropertyField("f1", 1, null, 200000, 100000);
        PropertyField pf2 = new PropertyField("f2", 19, null, 300000, 120000);
        PropertyField pf3 = new PropertyField("f3", 33, null, 330000, 130000);
        player.addProperty(pf1);
        player.addProperty(pf2);
        player.addProperty(pf3);
        pf1.setHousesCount(1);
        pf2.setHotel(true);
        int expectedTax = (int)(0.1 * (pf1.calculateValue() + pf2.calculateValue() + pf3.calculateValue()));

        when(pts.payToBank(player, expectedTax)).thenReturn(true);

        ts.payTax(player);

        verify(pts).payToBank(player, expectedTax);
        verify(ui).displayMessage(contains("zapłacił"));
    }

    @Test
    void testPayTaxWithNoProperties_doesNothing() {
        Player empty = new Player("pustak");

        ts.payTax(empty);

        verifyNoInteractions(pts, ui);
    }

    @Test
    void testPayTaxNoDisplayMessageWhenNotPaid() {
        Player player = new Player("goha");
        PropertyField pf1 = new PropertyField("f1", 1, null, 2000000, 100000);
        player.addProperty(pf1);
        pf1.setHotel(true);
        player.subtractBalance(5000000);
        int expectedTax = (int)(0.1 * pf1.calculateValue());

        ts.payTax(player);

        verify(pts).payToBank(player, expectedTax);
        verify(ui, never()).displayMessage(anyString());
    }
}
