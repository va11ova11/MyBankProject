package mainmain.Dao;

import model.Person;

import java.sql.SQLException;

public interface CashDAO {
    Person getBalance() throws SQLException;
    Person getPrintBalance() throws SQLException;
    void updateBalance(float NewBalance) throws SQLException;
    Person cashInBalance() throws SQLException;
    Person cashOutBalance() throws SQLException;

}
