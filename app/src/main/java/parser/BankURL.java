package parser;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class BankURL {

    public static final String ECO = "http://ecoislamicbank.kg/";
    public static final String DEMIR = "http://www.demirbank.kg/en.html";
    public static final String NBKR = "http://www.nbkr.kg/XML/daily.xml";
    public static final String OPTIMA = "http://www.optimabank.kg/en/exchange-rates-2.html";
    public static final String ROSIN = "http://www.rib.kg/";
    public static final String KICB = "http://www.kicb.net/welcome/";
    public static final String BTA = "http://www.btabank.kg/en/";

    public static String[] getArrayURL() {
        return new String[]{NBKR, DEMIR, ECO, OPTIMA, ROSIN, KICB, BTA};
    }
}
