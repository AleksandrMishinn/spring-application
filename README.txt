Для поднятия локального стенда требуется:
1. Создать базу данных MySQL с именем "universal" (charset - utf8 general_ci);
2. Выполнить Reimport Maven-зависимостей;
3. В Execute Maven Goal выполнить команду "Liquibase:update";
4. Запустить проект (com.andersen.AppLauncher).