package view;

public interface iView {
    /**
     * запросить данные у пользователя
     * @return - введенные данные
     */
    String getUserInput(String message);

    /**
     * отобразить сообщение об ошибке
     * @param message - сообщение пользователю
     */
    void  showErrorMessage(String message);
}
