package parser;

import java.util.Arrays;

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
        if (row < Currency.currencyTable.length)
            for (int i = 0; i < 4; i++) {
                if (currencyTable[row][i].indexOf(currency) > -1) {
                    return i;
                }
            }
        return -1;
    }

    public static void normalizeCurrencyTable() {
        int count = 0;
        for (int i = 0; i < currencyTable.length; i++) {
            if (currencyTable[i][3] != null) {
                count++;
            }
        }
        currencyTable = Arrays.copyOfRange(currencyTable, 0, count);
    }
}
