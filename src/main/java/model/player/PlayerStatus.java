package model.player;

public class PlayerStatus {
    private boolean jailExitCard;
    private int jailRounds;
    private int consecutiveDoubles;
    private int laps;
    private boolean eliminated;

    public PlayerStatus() {
        this.jailExitCard = false;
        this.jailRounds = 0;
        this.consecutiveDoubles = 0;
        this.laps = 1;
        this.eliminated = false;
    }

    public boolean hasJailExitCard() {
        return jailExitCard;
    }

    public void setJailExitCard(boolean jailExitCard) {
        this.jailExitCard = jailExitCard;
    }

    public int getJailRounds() {
        return jailRounds;
    }

    public void setJailRounds(int jailRounds) {
        this.jailRounds = jailRounds;
    }

    public int getConsecutiveDoubles() {
        return consecutiveDoubles;
    }

    public void setConsecutiveDoubles(int doubles) {
        consecutiveDoubles = doubles;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }
}
