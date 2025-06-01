package io;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

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

    @Override
    public List<Integer> readInts() {
        String line = sc.nextLine().trim();

        if (line.isEmpty())
            return List.of();

        String[] tokens = line.split("\\s+");
        List<Integer> result = new ArrayList<>() ;

        for (String token : tokens) {
            try {
                int idx = Integer.parseInt(token);
                result.add(idx);
            } catch (NumberFormatException e) {
                //
            }
        }

        return result;
    }

    @Override
    public String readString() {
        return sc.nextLine();
    }

    @Override
    public void waitForEnter(String message) {
        System.out.println(message);
        sc.nextLine();
    }
}
