package parser;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class BankURL {

    public static final String ECO = "http://ecoislamicbank.kg/";
    public static final String DEMIR = "http://www.demirbank.kg/en.html";
    public static final String NBKR = "http://www.nbkr.kg/XML/daily.xml";

    public static String[] getArrayURL() {
        return new String[]{NBKR, DEMIR, ECO};
    }
}
