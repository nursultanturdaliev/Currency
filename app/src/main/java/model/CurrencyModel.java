package model;

/**
 * Created by nurolopher on 11/28/2015.
 */
public class CurrencyModel {
    private String name;
    private String buy;
    private String sell;

    public CurrencyModel(String name, String buy, String sell) {
        this.name = name;
        this.buy = buy;
        this.sell = sell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }
}
