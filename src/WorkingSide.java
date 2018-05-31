import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkingSide implements ConnectionForBD {
    private Connection connection;
    protected static List<Integer> integers = new ArrayList<>();

    public WorkingSide(){
        String userName = "root";
        String pass = "1234";
        String connectURL = "jdbc:mysql://localhost:3306/mydbtest";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connectURL, userName, pass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void insertToBDIncome(int value){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bookkeeping (Date, income) VALUES (?, ?)")) {
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setInt(2, value);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            FaceBookkeeping.swingUtil("Ошибка при введении данных");
        }
    }
    @Override
    public void insertToBDExpenses(int value){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO myexpenses (Date, expenses) VALUES (?, ?)")) {
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setInt(2, value);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            FaceBookkeeping.swingUtil("Ошибка при введении данных");
        }
    }
    @Override
    public void selectToBDIncome(String from, String to) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT income FROM bookkeeping WHERE Date BETWEEN ? AND ?")){
            preparedStatement.setDate(1, java.sql.Date.valueOf(from));
            preparedStatement.setDate(2, java.sql.Date.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                integers.add(resultSet.getInt("income"));
            }
        }catch (SQLException e){
            FaceBookkeeping.swingUtil("Ничего не найденно");
        }
    }
    @Override
    public void selectToBDExpenses(String from, String to){
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT expenses FROM myexpenses WHERE Date BETWEEN ? AND ?")){
            preparedStatement.setDate(1, java.sql.Date.valueOf(from));
            preparedStatement.setDate(2, java.sql.Date.valueOf(to));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                integers.add(resultSet.getInt("expenses"));
            }
        } catch (SQLException e) {
            FaceBookkeeping.swingUtil("Ничего не найденно");
        }
    }
    public int calculation(String from, String to){
        int sumIncome = 0;
        int sumExpenses = 0;
        try (PreparedStatement statementIncome = connection.prepareStatement("SELECT income FROM bookkeeping WHERE Date BETWEEN ? AND ?");
             PreparedStatement statementExpenses = connection.prepareStatement("SELECT expenses FROM myexpenses WHERE Date BETWEEN ? AND ?")){
            List<Integer> list = new ArrayList<>();

            statementIncome.setDate(1, java.sql.Date.valueOf(from));
            statementIncome.setDate(2, java.sql.Date.valueOf(to));
            ResultSet resultSetIncome = statementIncome.executeQuery();
            while (resultSetIncome.next()){
                list.add(resultSetIncome.getInt("income"));
            }
            for (int i = 0; i < list.size(); i++) {
                sumIncome += list.get(i);
            }
            list.clear();

            statementExpenses.setDate(1, java.sql.Date.valueOf(from));
            statementExpenses.setDate(2, java.sql.Date.valueOf(to));
            ResultSet resultSetExpenses = statementExpenses.executeQuery();
            while (resultSetExpenses.next()){
                list.add(resultSetExpenses.getInt("expenses"));
            }
            for (int i = 0; i < list.size(); i++) {
                sumExpenses += list.get(i);
            }
            list.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sumIncome - sumExpenses;
    }
}
