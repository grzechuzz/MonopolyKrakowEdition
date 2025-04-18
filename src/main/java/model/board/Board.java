package model.board;

import model.field.Field;
import model.field.PropertyField;
import model.field.ResortField;
import model.field.SpecialField;

import java.util.List;

public class Board {
    private final List<Field> fields;

    public Board(List<Field> fields) {
        this.fields = fields;
        initializeFields();
    }

    public Field getField(int idx) {
        return fields.get(idx);
    }

    private void initializeFields() {
        fields.add(new Field("START", 0, null));
        fields.add(new PropertyField("Teligi", 1, null, 240_000, 80_000));
        fields.add(new PropertyField("Bieżanowska", 2, null, 260_000, 90_000));
        fields.add(new PropertyField("Aleksandry", 3, null, 280_000, 110_000));
        fields.add(new Field("Podatek", 4, null));
        fields.add(new Field("Szansa", 5, null));
        fields.add(new PropertyField("Igołomska", 6, null, 300_000, 140_000));
        fields.add(new ResortField("Przylasek Rusiecki", 7, null, 600_000, 100_000));
        fields.add(new PropertyField("Kocmyrzowska", 8, null, 320_000, 150_000));
        fields.add(new PropertyField("Plac Centralny im. R.Reagana", 9, null, 340_000, 160_000));
        fields.add(new Field("WIĘZIENIE (Montelupich)", 10, null));
        fields.add(new PropertyField("Witosa", 11, null, 400_000, 170_000));
        fields.add(new Field("Szansa", 12, null));
        fields.add(new PropertyField("Nowohucka", 13, null, 420_000, 180_000));
        fields.add(new PropertyField("Wielicka", 14, null, 440_000, 190_000));
        fields.add(new ResortField("Bagry", 15, null, 600_000, 100_000));
        fields.add(new PropertyField("Stella-Sawickiego", 16, null, 500_000, 200_000));
        fields.add(new SpecialField("Tauron Arena", 17, null, 750_000, 80_000));
        fields.add(new PropertyField("Opolska", 18, null, 520_000, 220_000));
        fields.add(new PropertyField("Tetmajera", 19, null, 530_000, 230_000));
        fields.add(new Field("FESTIWAL", 20, null));
        fields.add(new PropertyField("Kobierzyńska", 21, null, 550_000, 240_000));
        fields.add(new Field("Szansa", 22, null));
        fields.add(new PropertyField("Tyniecka", 23, null, 560_000, 250_000));
        fields.add(new PropertyField("Kapelanka", 24, null, 570_000, 260_000));
        fields.add(new SpecialField("Szpital psychiatryczny – Kobierzyn", 25, null, 750_000, 80_000));
        fields.add(new PropertyField("Czarnowiejska", 26, null, 580_000, 270_000));
        fields.add(new ResortField("Zakrzówek", 27, null, 600_000, 100_000));
        fields.add(new PropertyField("al. Juliusza Słowackiego", 28, null, 590_000, 280_000));
        fields.add(new PropertyField("Karmelicka", 29, null, 630_000, 300_000));
        fields.add(new Field("PODRÓŻ", 30, null));
        fields.add(new PropertyField("Grzegórzecka", 31, null, 800_000, 320_000));
        fields.add(new PropertyField("Nadwiślańska", 32, null, 840_000, 340_000));
        fields.add(new Field("Szansa", 33, null));
        fields.add(new PropertyField("Miodowa", 34, null, 850_000, 350_000));
        fields.add(new ResortField("Kopiec Krakusa", 35, null, 600_000, 100_000));
        fields.add(new PropertyField("Plac Nowy", 36, null, 950_000, 400_000));
        fields.add(new SpecialField("Okrąglak", 37, null, 750_000, 80_000));
        fields.add(new PropertyField("Grodzka", 38, null, 900_000, 450_000));
        fields.add(new PropertyField("Floriańska", 39, null, 1_000_000, 500_000));
    }

}
