package mainmain.Dao;
import model.FoundStructure;
import model.Portfolio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FondStructureServicePostgreSql implements FondStructureDAO {
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

    //Получение фонда по Айди
    @Override
    public FoundStructure getFondById(int id) throws SQLException {
        FoundStructure foundStructure = new FoundStructure();
        String Sql = "SELECT id , fondname, fondprice, fondprofitability FROM fond WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);

        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            foundStructure.setID(resultSet.getInt("id"));
            foundStructure.setFondName(resultSet.getString("fondname"));
            foundStructure.setFondPrice(resultSet.getDouble("fondprice"));
            foundStructure.setFondProfitability(resultSet.getDouble("fondprofitability"));
            System.out.println("Фонд " + foundStructure.getId() + "." + foundStructure.getFondName() + "\nЦена фонда " +
                    foundStructure.getFondPrice() + "\nДоходность "
                    + foundStructure.getFondProfitability() + "%");
        }
        return foundStructure;
    }


    //Получение всех фондов с базы и передача их в список
    @Override
    public List<FoundStructure> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM fond ORDER BY id");
        System.out.println("№     Тикер       цена за 1 пай  годовая доходность");
        List<FoundStructure> Fonds = new ArrayList<>();
        Fonds.add(0, null);
        for (int i = 1; i < 10; i++) {
            while (resultSet.next()) {

                FoundStructure foundStructure = new FoundStructure();
                foundStructure.setID(resultSet.getInt("id"));
                foundStructure.setFondName(resultSet.getString("fondname"));
                foundStructure.setFondPrice(resultSet.getDouble("fondprice"));
                foundStructure.setFondProfitability(resultSet.getDouble("fondprofitability"));
                Fonds.add(foundStructure);
                System.out.println(foundStructure.getId() + "     " + foundStructure.getFondName() + "        " +
                        foundStructure.getFondPrice() + "          " + foundStructure.getFondProfitability() + "%");
            }
        }
        return Fonds;
    }

    //Получение всех фондов с базы данных + покупка и изменение баланса в базе данных
    @Override
    public void BuyFond() throws SQLException {
        List<FoundStructure> Fonds = new FondStructureServicePostgreSql().getAll();
        FoundStructure foundStructure;
        CashDAOSql cashDAOSql = new CashDAOSql();


        //Получаем фонд по Id
        System.out.println("Укажите Id фонда");
        int FondId = scanner.nextInt();
        foundStructure = getFondById(FondId);

        //Получение цены выбранного фонда из списка Фондов
        float FondPrice = (float) Fonds.get(FondId).getFondPrice();

        //-----Получение баланса с базы данных
        float balance = cashDAOSql.getBalance().getBalance();

        System.out.println("Укажите сколько паёв хотите купить");


        //Вычисление баланса после покупки
        int Kolichestvo = scanner.nextInt();
        float SummaPokupki = Kolichestvo * FondPrice;
        float NewBalance = balance - Kolichestvo * FondPrice;
        System.out.println("Сумма вашей покупки составляет: " + SummaPokupki);
        System.out.println("Ваш баланс: " + NewBalance);


        //Получение текущего количества фондов
        int amountfond = 0;
        float summafond = 0;
        String Sql1 = "SELECT amountfond, summafond, fondprice FROM portfolio WHERE id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(Sql1);
        preparedStatement2.setInt(1, FondId);
        ResultSet resultSet = preparedStatement2.executeQuery();
        while (resultSet.next()) {
            amountfond = (resultSet.getInt("amountfond"));
            summafond = (resultSet.getFloat("summafond"));
            resultSet.getFloat("fondprice");
        }

        //Занесение покупки в базу данных
        String Sql = "UPDATE portfolio set amountfond = ? ,summafond = ?, fondprice = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setInt(4, FondId);
        //Сложение фондов в таблице
        preparedStatement.setInt(1, amountfond + foundStructure.setAmountFond(Kolichestvo));
        preparedStatement.setFloat(2, summafond + foundStructure.setSummaFond(SummaPokupki));
        preparedStatement.setFloat(3, FondPrice);
        preparedStatement.execute();


        //Изменение баланса в базе данных
        cashDAOSql.updateBalance(NewBalance);
    }

    //Получение портфеля
    @Override
    public List<Portfolio> getPortfolio() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM portfolio ORDER BY id");
        List<Portfolio> portfolios = new ArrayList<>();
        portfolios.add(0,null);
            System.out.println("Ваш портфель на данный момент: ");
            System.out.println("Название Фонда         Количество паёв      Общая сумма");
        for(int i = 1; i <10; i++) {
            while (resultSet.next()) {
                Portfolio portfolio = new Portfolio();
                portfolio.setId(resultSet.getInt("id"));
                portfolio.setName(resultSet.getString("fondname"));
                portfolio.setAmountFond(resultSet.getInt("amountfond"));
                portfolio.setSummaFond(resultSet.getFloat("summafond"));
                portfolios.add(portfolio);
                System.out.println(portfolio.getId() + "." + portfolio.getName() + "   " +
                        portfolio.getAmountFond() + "                   " + portfolio.getSummaFond());
            }
        }
        return portfolios;
    }


    //Продажа фонда обновление нового числа фондов + изменение баланса
    @Override
    public void sellFond() throws SQLException {
        Portfolio portfolio = new Portfolio();
        //Получение баланса и портфеля
        CashDAOSql cashDAOSql = new CashDAOSql();
        float balance = cashDAOSql.getBalance().getBalance();
        getPortfolio();


        //Получение выбранного фонда по айди
        System.out.println("Введите Id фонда который хотите продать");
        int FondId = scanner.nextInt();
        getFondById(FondId);


        //Получение количества паёв
        System.out.println("Введите сколько паёв хотите продать");
        int KolichestvoPaev = scanner.nextInt();


        float fondprice = 0;
        int amountfond = 0;
        float summafond = 0;

        //Получение выбранного фонда с БД
        String Sql1 = "SELECT amountfond, summafond, fondprice FROM portfolio WHERE id = ?";
        PreparedStatement preparedStatement2 = connection.prepareStatement(Sql1);
        preparedStatement2.setInt(1, FondId);
        ResultSet resultSet = preparedStatement2.executeQuery();
        while (resultSet.next()) {
            amountfond = (resultSet.getInt("amountfond"));
            summafond = (resultSet.getFloat("summafond"));
            fondprice = (resultSet.getFloat("fondprice"));
        }


        //Обновление баланса в БД
        float NewBalance = balance + fondprice * KolichestvoPaev;
        System.out.println("Ваш баланс теперь: " + NewBalance);
        cashDAOSql.updateBalance(NewBalance);

        //Обновление суммы паёв фонда в БД
        float summafondResult = fondprice * KolichestvoPaev;
        String Sql = "UPDATE portfolio set amountfond = ? ,summafond = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);

        //Вычитание количества и суммы фонда в таблице
        preparedStatement.setInt(1, portfolio.setAmountFond(amountfond - KolichestvoPaev));
        preparedStatement.setFloat(2, portfolio.setSummaFond(summafond - summafondResult));
        preparedStatement.setInt(3, FondId);
        preparedStatement.execute();
    }
}



