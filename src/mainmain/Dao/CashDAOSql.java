package mainmain.Dao;

import model.Person;

import java.sql.*;
import java.util.Scanner;

public class CashDAOSql implements CashDAO {
    final String URL = "jdbc:postgresql://localhost:5432/portfolio";
    final String NAME = "postgres";
    final String PASSWORD = "123";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Scanner scanner = new Scanner(System.in);

    @Override
    public Person getBalance() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank");

        resultSet.next();
        Person person = new Person();
        person.setBalance(resultSet.getFloat("balance"));
        person.getBalance();

        return person;
    }

    //Показать баланс
    @Override
    public Person getPrintBalance() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM bank");

        resultSet.next();
        Person person = new Person();
        person.setBalance(resultSet.getFloat("balance"));

        System.out.println("----------------------");
        System.out.println("Ваш баланс: " + person.getBalance());
        System.out.println("----------------------");

        return person;
    }


    //Обновить баланс при покупке фонда
    public void updateBalance(float NewBalance) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bank SET balance=? WHERE id =1");
        preparedStatement.setFloat(1, NewBalance);
        preparedStatement.execute();
    }

    //Пополнить баланс
    @Override
    public Person cashInBalance() throws SQLException {
        Person person = new Person();
        getPrintBalance();
        System.out.println("Введите сумму пополнения: ");
        float SummaPopolnenia = scanner.nextFloat();
        if (SummaPopolnenia>0) {
            float NewBalance = person.setBalance(getBalance().getBalance()) + SummaPopolnenia;

            System.out.println("----------------------");
            System.out.println("Ваш баланс пополнен на: " + SummaPopolnenia);
            System.out.println("Теперь он составляет " + NewBalance);
            System.out.println("----------------------");

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bank SET balance = ? WHERE id = 1");
            preparedStatement.setFloat(1, NewBalance);
            preparedStatement.execute();
        }
        return person;
    }

    //Снять с баланса
    @Override
    public Person cashOutBalance() throws SQLException {
        Person person = new Person();
        System.out.println("----------------------");
        System.out.println("Ваш баланс: " + person.setBalance(getBalance().getBalance()));
        System.out.println("----------------------");
        System.out.println("Укажите сумму снятия: ");


        float SummaSnyatia = scanner.nextFloat();

        //Проверка на превышение баланса
            if (SummaSnyatia < person.getBalance() || SummaSnyatia == person.getBalance()) {
                float NewBalance = person.getBalance() - SummaSnyatia;

                System.out.println("----------------------");
                System.out.println("Вы сняля с баланса: " + SummaSnyatia);
                System.out.println("Теперь он составляет: " + NewBalance);
                System.out.println("----------------------");

                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bank set balance = ? where id = 1;");
                preparedStatement.setFloat(1, NewBalance);
                preparedStatement.execute();

        }else {
                System.out.println("Сумма снятия превышает баланс");
            }
            return person;
    }
}
