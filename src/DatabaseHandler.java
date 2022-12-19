import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class DatabaseHandler extends Configs{ // extends это ключевое слово,
    // предназначенное для расширения реализации какого-то существующего класса
    //Создается новый класс на основе существующего, и этот новый класс расширяет (extends) возможности старого.

    Connection dbConnection; // Определяет основное поведение подключений к базе данных
    // и предоставляет базовый класс для подключений, связанных с базой данных.

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void singUpUser(String username, String phone, String password){
        String insert = "INSERT INTO " + Const.USER_TABLE +
                "(" + Const.USER_NAME + "," + Const.USER_PHONE + "," + Const.USER_PASS + ")"
                + "VALUES(?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            // PreparedStatement используется для выполнения параметризованного запроса.
            prSt.setString(1, username);
            prSt.setString(2, phone);
            prSt.setString(3, password);

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void logMessage(String username, String message){
        String insert_message = "INSERT INTO " + Const.MESSAGE_TABLE +
                "(" + Const.MESSAGE_NAME + "," + Const.MESSAGE_MESSAGE + "," + Const.MESSAGE_DATA + ")"
                + "VALUES(?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert_message);
            prSt.setString(1, username);
            prSt.setString(2, message);
            prSt.setString(3, LocalDateTime.now().toString().substring(0,19));

            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet getUsername(String username, String password){
        ResultSet resSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_NAME + "=? AND " + Const.USER_PASS + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, username);
            prSt.setString(2, password);

            resSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
    
//    public static void main(String[] args) {
//        DatabaseHandler dbHandler = new DatabaseHandler();
//
//        dbHandler.logMessage("Anton","Yo, man");
//    }
}
