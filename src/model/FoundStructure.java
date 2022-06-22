package model;

public class FoundStructure extends Portfolio{
    private int Id;
    private String FondName;
    private double FondPrice;
    private double FondProfitability;

    public FoundStructure(String fondName, double fondPrice, double fondProfitability, int id) {
        this.FondName = fondName;
        this.FondPrice = fondPrice;
        this.FondProfitability = fondProfitability;
        this.Id = id;
    }
    public FoundStructure(){
    }
    public int getId(){
        return Id;
    }

    public void setID(int id){
        this.Id = id;
    }

    public double getFondProfitability() {
        return FondProfitability;
    }

    public void setFondProfitability(double fondProfitability) {
        this.FondProfitability = fondProfitability;
    }

    public String getFondName() {
        return FondName;
    }

    public void setFondName(String fondName) {
        this.FondName = fondName;
    }

    public double getFondPrice() {
        return FondPrice;
    }

    public void setFondPrice(double fondPrice) {
        this.FondPrice = fondPrice;
    }
}
