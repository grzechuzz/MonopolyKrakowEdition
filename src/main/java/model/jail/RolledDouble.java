package model.jail;

public final class RolledDouble implements JailOutcome {
    private final int sum;

    public RolledDouble(int sum) {
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

}
