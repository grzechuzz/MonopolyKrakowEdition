package io;

import java.util.List;

public interface GameIO {
    void displayMessage(String message);
    boolean askYesNo(String question);
    int readInt();
    List<Integer> readInts();
    String readString();
    void waitForEnter(String message);
}
