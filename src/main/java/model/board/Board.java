package model.board;

import model.field.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final List<Field> fields;

    public Board() {
        fields = new ArrayList<>();
        initializeFields();
    }

    public Field getField(int idx) {
        return fields.get(idx);
    }

    public List<Field> getAllFields() {
        return fields;
    }

    private void initializeFields() {
        fields.add(new StartField("START", 0));
        fields.add(new PropertyField("Teligi", 1, 240000, 80000));
        fields.add(new PropertyField("Bieżanowska", 2, 260000, 90000));
        fields.add(new PropertyField("Aleksandry", 3, 280000, 110000));
        fields.add(new TaxField("Podatek", 4));
        fields.add(new ChanceField("Szansa", 5));
        fields.add(new PropertyField("Igołomska", 6, 300000, 140000));
        fields.add(new ResortField("Przylasek Rusiecki", 7, 600000, 100000));
        fields.add(new PropertyField("Kocmyrzowska", 8, 320000, 150000));
        fields.add(new PropertyField("Plac Centralny im. R.Reagana", 9, 340000, 160000));
        fields.add(new JailField("WIĘZIENIE (Montelupich)", 10));
        fields.add(new PropertyField("Witosa", 11, 400000, 170000));
        fields.add(new ChanceField("Szansa", 12));
        fields.add(new PropertyField("Nowohucka", 13, 420000, 180000));
        fields.add(new PropertyField("Wielicka", 14, 440000, 190000));
        fields.add(new ResortField("Bagry", 15, 600000, 100000));
        fields.add(new PropertyField("Stella‑Sawickiego", 16, 500000, 200000));
        fields.add(new SpecialField("Tauron Arena", 17, 750000, 80000));
        fields.add(new PropertyField("Opolska", 18, 520000, 220000));
        fields.add(new PropertyField("Tetmajera", 19, 530000, 230000));
        fields.add(new FestivalField("FESTIWAL", 20));
        fields.add(new PropertyField("Kobierzyńska", 21, 550000, 240000));
        fields.add(new ChanceField("Szansa", 22));
        fields.add(new PropertyField("Tyniecka", 23, 560000, 250000));
        fields.add(new PropertyField("Kapelanka", 24, 570000, 260000));
        fields.add(new SpecialField("Szpital psychiatryczny – Kobierzyn", 25, 750000, 80000));
        fields.add(new PropertyField("Czarnowiejska", 26, 580000, 270000));
        fields.add(new ResortField("Zakrzówek", 27, 600000, 100000));
        fields.add(new PropertyField("al. Juliusza Słowackiego", 28, 590000, 280000));
        fields.add(new PropertyField("Karmelicka", 29, 630000, 300000));
        fields.add(new TravelField("PODRÓŻ", 30));
        fields.add(new PropertyField("Grzegórzecka", 31, 800000, 320000));
        fields.add(new PropertyField("Nadwiślańska", 32, 840000, 340000));
        fields.add(new ChanceField("Szansa", 33));
        fields.add(new PropertyField("Miodowa", 34, 850000, 350000));
        fields.add(new ResortField("Kopiec Krakusa", 35, 600000, 100000));
        fields.add(new PropertyField("Plac Nowy", 36, 950000, 400000));
        fields.add(new SpecialField("Okrąglak", 37, 750000, 80000));
        fields.add(new PropertyField("Grodzka", 38, 900000, 450000));
        fields.add(new PropertyField("Floriańska", 39, 1000000, 500000));
    }
}
