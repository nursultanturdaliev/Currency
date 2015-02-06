package parser;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Arrays;

import com.nurolopher.currency.R;

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
        Resources resources = fragment.getActivity().getResources();
        int actual_index = 0, count = 0;
        for (int index = 0; index < urls.length; index++) {
            count = 0;
            try {
                Document document;
                Elements rows;
                switch (urls[index]) {
                    case BankURL.ECO:
                        document = Jsoup.connect(urls[index]).get();
                        rows = document.getElementsByClass("row");
                        for (int i = 1; i < rows.size(); i++) {
                            Elements rowCells = rows.get(i).getElementsByClass("cell");
                            if (rowCells.size() > 0) {
                                currencyTable[actual_index][i - 1] = resources.getString(R.string.eco) + ";" + rowCells.get(0).text() + ";" + rowCells.get(1).text() + ";" + rowCells.get(2).text();
                                count = 1;
                            }
                        }
                        break;
                    case BankURL.NBKR:
                        document = Jsoup.parse(Jsoup.connect(urls[index]).get().body().toString(), urls[index], Parser.xmlParser());
                        rows = document.select("Currency");
                        for (int i = 0; i < rows.size(); i++) {
                            currencyTable[actual_index][i] = resources.getString(R.string.nbkr) + ";" + rows.get(i).attr("isocode") + ";" + rows.get(i).select("Value").get(0).text() + ";" + rows.get(i).select("Value").get(0).text();
                            count = 1;
                        }
                        break;
                    case BankURL.DEMIR:
                        document = Jsoup.connect(urls[index]).get();
                        rows = document.select("#moneytable tr");
                        for (int i = 1; i < 5; i++) {
                            Elements rowCells = rows.get(i).select("td");
                            if (rowCells.size() > 0) {
                                currencyTable[actual_index][i - 1] = resources.getString(R.string.demir) + ";" + rowCells.get(0).select("strong").get(0).text() + ";" + rowCells.get(1).text() + ";" + rowCells.get(2).text();
                                count = 1;
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                Log.e(TAG, "Cant' connect to: " + urls[index].toString());
            }
            actual_index += count;
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
