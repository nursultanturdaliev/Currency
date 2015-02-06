package parser;

import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.nurolopher.currency.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

import adapter.CurrencyAdapter;

/**
 * Created by nursultan on 6-Feb 15.
 */
public class CurrencyParser extends AsyncTask<String, Integer, String[][]> {
    private static final String TAG = "CurrencyParser";
    public ListFragment fragment;
    private String currencyType;

    public CurrencyParser(ListFragment fragment, String currencyType) {
        this.fragment = fragment;
        this.currencyType = currencyType;
    }

    @Override
    protected String[][] doInBackground(String... urls) {
        String[][] currencyTable = new String[urls.length][4];

        for (int index = 0; index < urls.length; index++) {
            try {
                Document document;
                Elements rows;
                switch (urls[index]) {
                    case "http://ecoislamicbank.kg/":
                        document = Jsoup.connect(urls[index]).get();
                        rows = document.getElementsByClass("row");
                        for (int i = 1; i < rows.size(); i++) {
                            Elements rowCells = rows.get(i).getElementsByClass("cell");
                            currencyTable[index][i - 1] = "ЭкоИсламик;" + rowCells.get(0).text() + ";" + rowCells.get(1).text() + ";" + rowCells.get(2).text();
                        }
                        break;
                    case "http://www.nbkr.kg/XML/daily.xml":
                        document = Jsoup.parse(Jsoup.connect(urls[index]).get().body().toString(), urls[index], Parser.xmlParser());
                        rows = document.select("Currency");
                        for (int i = 0; i < rows.size(); i++) {
                            currencyTable[index][i] = "Нацбанк;" + rows.get(i).attr("isocode") + ";" + rows.get(i).select("Value").get(0).text() + ";" + rows.get(i).select("Value").get(0).text();
                        }
                    default:
                        break;
                }
            } catch (IOException e) {
                Log.e(TAG, "Cant' connect to: " + urls[index].toString());
            }
            publishProgress(index);
        }

        return currencyTable;
    }

    @Override
    protected void onPostExecute(String[][] currencies) {
        super.onPostExecute(currencies);
        Log.i(TAG, Arrays.deepToString(currencies));
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(fragment.getActivity(), R.layout.fragment_currency, currencies, currencyType);
        fragment.setListAdapter(currencyAdapter);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }
}
