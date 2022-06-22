package model;

public class Portfolio {
    private int id;
    private  String Name;
    private int amountFond;
    private float summaFond;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAmountFond() {
        return amountFond;
    }

    public int setAmountFond(int amountFond) {
        this.amountFond = amountFond;
        return amountFond;
    }

    public float getSummaFond() {
        return summaFond;
    }

    public float setSummaFond(float summaFond) {
        this.summaFond = summaFond;
        return summaFond;
    }
}
