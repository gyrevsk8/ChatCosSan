public class Configs {
    protected String dbHost = "localhost";
    protected String dbPort = "3306";
    protected String dbUser = "root";
    protected String dbPass = "root";
    String dbName = "chat";

    // Поля и методы, обозначенные модификатором доступа protected, будут видны:
    //в пределах всех классов, находящихся в том же пакете, что и наш;
    // в пределах всех классов-наследников нашего класса.
}
