package model;

public interface iModel {
    /**
     * сохранить данные пользователя в файл
     * @param family название файла
     * @param userData - строка с данными пользователя
     * @return true -> данные сохранены, false -> возникла ошибка
     */
    public boolean saveUser(String family, String userData);
}
