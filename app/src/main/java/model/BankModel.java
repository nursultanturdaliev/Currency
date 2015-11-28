package model;

/**
 * Created by nurolopher on 11/28/2015.
 */
public class BankModel {

    private String name;
    private CurrencyModel currencyModel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyModel getCurrencyModel() {
        return currencyModel;
    }

    public void setCurrencyModel(CurrencyModel currencyModel) {
        this.currencyModel = currencyModel;
    }
}
