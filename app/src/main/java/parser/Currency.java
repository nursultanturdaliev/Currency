package parser;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class Currency {

    public static final String USD = "USD";
    public static final String EUR = "EUR";
    public static final String RUB = "RUB";
    public static final String KZT = "KZT";

    public static String[] getCurrencyArray() {
        return new String[]{USD, EUR, RUB, KZT};
    }


    public static String getCurrencyArrayAt(int index) {
        return getCurrencyArray()[index];
    }
}
