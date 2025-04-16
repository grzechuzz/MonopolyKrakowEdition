package io;

import java.util.Scanner;

public class ConsoleIO implements GameIO {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public boolean askYesNo(String question) {
        System.out.println(question + " (y/n)");
        String line = sc.nextLine().toLowerCase().trim();
        return line.equals("y");
    }

    @Override
    public int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Podano bledny argument, sprobuj ponownie!");
            }
        }
    }
}
