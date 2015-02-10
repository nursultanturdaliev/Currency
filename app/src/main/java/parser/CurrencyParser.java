package parser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

import com.nurolopher.currency.MainActivity;
import com.nurolopher.currency.R;

import adapter.TabsPagerAdapter;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class CurrencyParser extends AsyncTask<String, Integer, String[][]> {
    private static final String TAG = "CurrencyParser";
    private Context context;
    private Document document;
    private Elements rows;
    public static String[][] currencyTable;

    public CurrencyParser(Context context) {
        this.context = context;
    }

    @Override
    protected String[][] doInBackground(String... urls) {

        currencyTable = new String[urls.length][4];
        int actual_index = 0;
        int count;
        for (int index = 0; index < urls.length; index++) {
            count = 0;
            try {
                switch (urls[index]) {
                    case BankURL.ECO:
                        count = parseEco(actual_index, count, BankURL.ECO);
                        break;
                    case BankURL.NBKR:
                        count = parseNBKR(actual_index, count, BankURL.NBKR);
                        break;
                    case BankURL.DEMIR:
                        count = parseDEMIR(actual_index, count, BankURL.DEMIR);
                        break;
                    case BankURL.OPTIMA:
                        count = parseOPTIMA(actual_index, count, BankURL.OPTIMA);
                        break;
                    case BankURL.ROSIN:
                        count = parseROSIN(actual_index, count, BankURL.ROSIN);
                        break;
                    case BankURL.KICB:
                        count = parseKICB(actual_index, count, BankURL.KICB);
                        break;
                    case BankURL.BTA:
                        count = parseBTA(actual_index, count, BankURL.BTA);
                        break;
                    case BankURL.AYIL:
                        count = parseAYIL(actual_index, count, BankURL.AYIL);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                Log.e(TAG, "Cant' connect to: " + urls[index]);
            }
            actual_index += count;
            publishProgress(index);
        }

        return currencyTable;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.progressDialog.setMessage("Processing...");
        MainActivity.progressDialog.setMax(4);
        MainActivity.progressDialog.show();

    }

    @Override
    protected void onPostExecute(String[][] currencies) {
        super.onPostExecute(currencies);

        currencyTable = currencies;
        MainActivity.progressDialog.cancel();


        MainActivity.tabsPagerAdapter = new TabsPagerAdapter(((MainActivity) context).getSupportFragmentManager());
        MainActivity.viewPager.setAdapter(MainActivity.tabsPagerAdapter);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    private int parseDEMIR(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        rows = document.select("#moneytable tr");
        for (int i = 1; i < 5; i++) {
            Elements rowCells = rows.get(i).select("td");
            if (rowCells.size() > 0) {
                currencyTable[actual_index][i - 1] = context.getResources().getString(R.string.demir) + ";" + rowCells.get(0).select("strong").get(0).text().trim() + ";" + rowCells.get(1).text() + ";" + rowCells.get(2).text();
                count = 1;
            }
        }
        return count;
    }

    private int parseNBKR(int actual_index, int count, String url) throws IOException {
        document = Jsoup.parse(Jsoup.connect(url).get().body().toString(), url, Parser.xmlParser());
        rows = document.select("Currency");
        for (int i = 0; i < rows.size(); i++) {
            // TODO: Not buy and sell but. on two dates should be displayed
            currencyTable[actual_index][i] = context.getResources().getString(R.string.nbkr) + ";" + rows.get(i).attr("isocode").trim() + ";" + rows.get(i).select("Value").get(0).text() + ";" + rows.get(i).select("Value").get(0).text();
            count = 1;
        }
        return count;
    }

    private int parseEco(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        rows = document.getElementsByClass("row");
        for (int i = 1; i < rows.size(); i++) {
            Elements rowCells = rows.get(i).getElementsByClass("cell");
            if (rowCells.size() > 0) {
                currencyTable[actual_index][i - 1] = context.getResources().getString(R.string.eco) + ";" + rowCells.get(0).text().trim() + ";" + rowCells.get(1).text() + ";" + rowCells.get(2).text();
                count = 1;
            }
        }
        return count;
    }

    private int parseOPTIMA(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        Element table = document.select(".currency_table").first();
        Elements rows = table.select("tr");
        for (int i = 2; i < 6; i++) {
            Elements cell = rows.get(i).select("td span");
            currencyTable[actual_index][i - 2] = context.getResources().getString(R.string.optima) + ";" + Currency.getCurrencyArrayAt(i - 2) + ";" + cell.get(0).text() + ";" + cell.get(1).text();
            count = 1;
        }
        return count;
    }

    private int parseROSIN(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        Element table = document.getElementsByClass("currency").first().select("table").first();
        Elements rows = table.select("tbody tr");
        for (int i = 0; i < 4; i++) {
            Elements cells = rows.get(i).select("td");
            currencyTable[actual_index][i] = context.getResources().getString(R.string.rosin) + ";" + cells.get(0).select("div").first().text().trim() + ";" + cells.get(1).text() + ";" + cells.get(2).text();
            count = 1;
        }
        return count;
    }

    private int parseKICB(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        Element table = document.getElementsByClass("con").first();
        Elements rows = table.getElementsByClass("cur_line");
        rows.remove(rows.select("img"));
        for (int i = 1; i < 5; i++) {
            Elements cells = rows.get(i).select("span");
            currencyTable[actual_index][i - 1] = context.getResources().getString(R.string.kicb) + ";" + cells.get(0).text().trim() + ";" + cells.get(1).text() + ";" + cells.get(2).text();
            count = 1;
        }
        return count;
    }

    private int parseBTA(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        Elements rows = document.select("table.currency tbody tr");
        for (int i = 2; i < 6; i++) {
            Elements cells = rows.get(i).select("td");
            currencyTable[actual_index][i - 2] = context.getResources().getString(R.string.bta) + ";" + cells.get(0).text().trim() + ";" + cells.get(1).text() + ";" + cells.get(2).text();
            count = 1;
        }
        return count;
    }

    private int parseAYIL(int actual_index, int count, String url) throws IOException {
        document = Jsoup.connect(url).get();
        Elements rows = document.select("#ja-col1 table tbody tr");
        for (int i = 1; i < rows.size(); i++) {
            Elements cells = rows.get(i).select("td");
            if (i == 4)
                currencyTable[actual_index][i - 1] = context.getResources().getString(R.string.ayil) + ";USD;" + cells.get(1).text() + ";" + cells.get(2).text();
            else
                currencyTable[actual_index][i - 1] = context.getResources().getString(R.string.ayil) + ";" + cells.get(0).text() + ";" + cells.get(1).text() + ";" + cells.get(2).text();
            count = 1;
        }
        return count;
    }
}
