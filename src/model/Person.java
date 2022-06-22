package model;

public class Person {
    private float balance;
    private Portfolio portfolio;

    public float getBalance() {
        return balance;
    }

    public float setBalance(float balance) {
        this.balance = balance;
        return balance;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
