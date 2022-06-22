package mainmain;

import mainmain.Dao.CashDAOSql;
import mainmain.Dao.FondStructureServicePostgreSql;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        //Менюшка
        while (true) {
            System.out.println("Что вы хотите?");
            System.out.println("1. Пополнение счёта нажмите");
            System.out.println("2. Посмтореть баланс");
            System.out.println("3. Снять с баланса");
            System.out.println("4. Купить фонд");
            System.out.println("5. Продать фонд");
            System.out.println("6. Посмотреть портфель");
            Scanner scanner = new Scanner(System.in);
            int command1 = scanner.nextInt();


            //Пополнение баланса
            if (command1 == 1) {
                CashDAOSql cashDAOSql = new CashDAOSql();
                cashDAOSql.cashInBalance();
            }

            //Показать баланс
            if (command1 == 2) {
                CashDAOSql cashDAO = new CashDAOSql();
                cashDAO.getPrintBalance();
            }

            //Снять с баланса
            if (command1 == 3) {
                CashDAOSql cashDAOSql = new CashDAOSql();
                cashDAOSql.cashOutBalance();
            }

            //Покупка фонда + изменение баланса в базе данных
            if (command1 == 4) {
                FondStructureServicePostgreSql fondStructureServicePostgreSql = new FondStructureServicePostgreSql();
                fondStructureServicePostgreSql.BuyFond();
            }

            //Продать фонд + изменение баланса в базе данных
            if (command1 == 5) {
                FondStructureServicePostgreSql fondStructureServicePostgreSql = new FondStructureServicePostgreSql();
                fondStructureServicePostgreSql.sellFond();
            }

            //Вывод портфеля с фондамии
            if (command1 == 6) {
                FondStructureServicePostgreSql fondStructureServicePostgreSql = new FondStructureServicePostgreSql();
                fondStructureServicePostgreSql.getPortfolio();
            }
        }
    }
}







