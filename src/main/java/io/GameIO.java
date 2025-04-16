package io;

public interface GameIO {
    void displayMessage(String message);
    boolean askYesNo(String question);
    int readInt();
}
