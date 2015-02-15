package parser;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class Currency {

    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String RUB = "RUB";
    public static final String KZT = "KZT";
    public static final String SOM = "SOM";
    public static String[][] currencyTable;

    public static String[] getCurrencyArray() {
        return new String[]{USD, EUR, RUB, KZT};
    }


    public static String getCurrencyArrayAt(int index) {
        return getCurrencyArray()[index];
    }

    public static int getCurrencyCell(int row, String currency) {
        for (int i = 0; i < 4; i++) {
            if (currencyTable[row][i].indexOf(currency) > -1) {
                return i;
            }
        }
        return -1;
    }
}
