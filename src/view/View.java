package view;

import java.util.Scanner;

public class View implements iView {

    /**
     * пользователь вводит данные
     *
     * @return
     */
    @Override
    public String getUserInput(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine().trim();
        return userInput.replaceAll("\s+", " ");
    }

    /**
     * отобразить сообщение об ошибке
     *
     * @param message - сообщение пользователю
     */
    @Override
    public void showErrorMessage(String message) {
        System.out.println();
        for (int i = 0; i < message.length() + "Ошибка!!! ".length(); i++) {
            System.out.print("*");
        }
        System.out.println("\nОшибка!!! " + message);
        for (int i = 0; i < message.length() + "Ошибка!!! ".length(); i++) {
            System.out.print("*");
        }
        System.out.println("\n");
    }
}
