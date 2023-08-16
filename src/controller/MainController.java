package controller;

import Exceptions.InValidNameException;
import Exceptions.InValidPhoneNumber;
import Exceptions.NotEnoughDataException;
import Exceptions.WrongSexException;
import model.iModel;
import view.iView;

import javax.swing.text.DateFormatter;
import java.sql.SQLOutput;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {
    private iView view;
    private iModel model;

    public MainController(iView view, iModel model) {
        this.view = view;
        this.model = model;
    }

    public void run() {
        boolean flag = true;

        while (flag) {

            System.out.println("чтобы завершить работу введите \"q\".");
            String userInput = view.getUserInput(
                    "Введите свои данные( фамилия, имя, отчество, дата рождения, телефон, пол(f/m))\n:");


            if (userInput.toLowerCase().equals("q")) {
                return;
            }

            boolean inputCheckFlag = true;
            while (inputCheckFlag) {
                inputCheckFlag = false;
                try {
                    inputCheck(userInput);

                } catch (NotEnoughDataException e) {
                    view.showErrorMessage(e.getMessage());

                } catch (InValidNameException e) {
                    view.showErrorMessage(e.getMessage());

                    if (userWantsChangeInput()) {
                        String[] userInputSplit = userInput.split(" ");
                        String[] newName = changeName(new String[]{userInputSplit[0], userInputSplit[1], userInputSplit[2]});
                        for (int i = 0; i < newName.length; i++) {
                            userInputSplit[i] = newName[i];
                        }
                        userInput = String.join(" ", userInputSplit);
                    }else {
                        continue;
                    }
                    inputCheckFlag = true;

                } catch (DateTimeException e) {
                    view.showErrorMessage(e.getMessage());
                    if (userWantsChangeInput()) {
                        String[] userInputSplit = userInput.split(" ");
                        String newDate = changeDate(userInputSplit[3]);
                        userInputSplit[3] = newDate;
                        userInput = String.join(" ", userInputSplit);
                    }else {
                        continue;
                    }
                    inputCheckFlag = true;

                } catch (InValidPhoneNumber e) {
                    view.showErrorMessage(e.getMessage());
                    if (userWantsChangeInput()) {
                        String[] userInputSplit = userInput.split(" ");
                        String newPhoneNumber = changePhone(userInputSplit[4]);
                        userInputSplit[4] = newPhoneNumber;
                        userInput = String.join(" ", userInputSplit);
                    }else {
                        continue;
                    }
                    inputCheckFlag = true;

                } catch (WrongSexException e) {
                    view.showErrorMessage(e.getMessage());
                    if (userWantsChangeInput()) {
                        String[] userInputSplit = userInput.split(" ");
                        String changeSex = "";
                        boolean flagSexIsOk = true;
                        while (flagSexIsOk) {
                            flagSexIsOk = false;
                            changeSex = view.getUserInput("Введите правильный пол: ").toLowerCase();
                            if (!changeSex.equals("f") && !changeSex.equals("m")) {
                                flagSexIsOk = true;
                                view.showErrorMessage("вы снова ввели неправильный пол");
                            }
                        }
                        userInputSplit[5] = changeSex;
                        userInput = String.join(" ", userInputSplit);
                        inputCheckFlag = true;
                    }
                }
            }
            model.saveUser();

        }
        System.out.println("До свидания");
    }


    /**
     * запрос пользователю: исправлять ошибку или нет
     *
     * @return true -> исправить ошибку, false -> выйти без изменений
     */
    public boolean userWantsChangeInput() {
        boolean inputEditFlag = true;
        while (inputEditFlag) {
            inputEditFlag = false;
            String goEdit = view.getUserInput(
                    "Введите\n 1 - чтобы исправить ошибку.\n 2 - ввести строку заново\n:");
            if (goEdit.equals("1")) {
                return true;
            } else if (!goEdit.equals("2")) {
                inputEditFlag = true;
            }
        }
        return false;
    }

    /**
     * изменения ФИО
     *
     * @param name массив вида {имя, фамилия, отчество}
     * @return - отредактированный массив с ФИО
     */
    private String[] changeName(String[] name) {
        name[0] = editName(name[0], "Фамилию");
        name[1] = editName(name[1], "Имя");
        name[2] = editName(name[2], "Отчество");

        return name;
    }


    /**
     * изменить часть ФИО
     *
     * @param name    - имя, отчество или фамилия
     * @param partFIO - конкретное описание того что изменяется ("имя"/"Фамилию"/"Отчество")
     *                которое будет отображаться в сообщении пользователю.
     * @return -  измененное слово, если требовалось изменение
     */

    private String editName(String name, String partFIO) {
        boolean flag = true;
        while (flag) {
            flag = false;
            if (nameIsCheckFalse(name)) {
                String message = String.format("Было введено не верное имя \"%s\". изменить %s: ", name, partFIO);
                name = view.getUserInput(message);
                flag = true;
            }
        }
        return name;
    }

    /**
     * изменение неправильной даты
     *
     * @param date - неправильная дата
     * @return - правильная дата
     */
    private String changeDate(String date) {
        boolean flag = true;
        while (flag) {
            flag = false;
            String newDate = view.getUserInput("Введите правильную дату рождения(дд.мм.гггг): ");
            if (dateCheckFalse(newDate)) {
                System.out.println("Вы снова ввели неправильную дату!");
                flag = true;
                continue;
            }
            date = newDate;

        }
        return date;
    }

    /**
     * изменение номера телефона
     *
     * @param phone - неверный номер
     * @return - правильный номер
     */
    private String changePhone(String phone) {
        boolean flag = true;
        while (flag) {
            flag = false;
            String newPhone = view.getUserInput("Введите номер телефона: ");
            if (newPhone.length() < 10) {
                view.showErrorMessage("номер слишком короткий(длина номера не может быть < 10 символов)");
                flag = true;
                continue;

            } else if (newPhone.length() > 11) {
                view.showErrorMessage("номер слишком длинный(длина номера не может быть > 11 символов)");
                flag = true;
                continue;
            }

            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(newPhone);
            if (matcher.find()) {
                view.showErrorMessage("Номер не должен содержать специальных символов");
                flag = true;
                continue;
            }
            phone = newPhone;

        }
        return phone;
    }

    /**
     * парсинг и проверка строки
     *
     * @param userInput - строка для проверки
     */

    private void inputCheck(String userInput) {
        // проверка количества введенных данных
        String[] userInputSplit = userInput.split(" ");
        if (userInputSplit.length < 6) {
            throw new NotEnoughDataException(
                    String.format("Не достаточно данных. Заполнено %d %s из 6",
                            userInputSplit.length,
                            userInputSplit.length == 5 || userInputSplit.length == 0 ? "пунктов" : "пункта"));
        } else if (userInputSplit.length > 6) {
            throw new NotEnoughDataException("Введено слишком много данных");
        }

        // проверка ФИО
        for (int i = 0; i < 3; i++) {
            if (nameIsCheckFalse(userInputSplit[i])) {
                throw new InValidNameException("ФИО не должно содержать специальных символов");
            }
        }

        //проверка даты
        String date = userInputSplit[3];
        if (dateCheckFalse(date)) {
            throw new DateTimeException("Неверный формат даты");
        }


        // проверка телефона
        if (phoneCheckFalse(userInputSplit[4])) {
            throw new InValidPhoneNumber("Неверный номер телефона");
        }

        // проверка пола
        String sex = userInputSplit[5].toLowerCase();
        if (!sex.equals("f") && !sex.equals("m")) {
            throw new WrongSexException("Неправильный пол(f - девочка, m-мальчик)");
        }
    }

    /**
     * проверка ФИО
     *
     * @param name - имя, фамилия или отчество
     * @return -  true -> была найдена ошибка, false -> проверка прошла успешно
     */
    private boolean nameIsCheckFalse(String name) {
        if (name.equals("")) {
            return true;
        }
        Pattern pattern = Pattern.compile("[^a-zа-я]");
        Matcher matcher = pattern.matcher(name.toLowerCase());
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    /**
     * проверка даты
     *
     * @param date - дата для проверки
     * @return true -> была найдена ошибка, false -> проверка прошла успешно
     */
    private boolean dateCheckFalse(String date) {

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeException e) {
            return true;
        }
        return false;
    }

    private boolean phoneCheckFalse(String phoneNumber) {
        if (phoneNumber.equals("")) {
            return true;
        }

        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            return true;
        }


        if (phoneNumber.length() < 10 || phoneNumber.length() > 11) {
            return true;
        }
        return false;
    }
}
