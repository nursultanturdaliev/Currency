package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.nurolopher.currency.R;

/**
 * Created by nursultan on 5-Feb 15.
 */
public class CurrencyAdapter extends ArrayAdapter {

    private final String[][] currencies;
    private final String currencyType;

    public CurrencyAdapter(Context context, int resource, String[][] currencies, String currencyType) {
        super(context, resource, currencies);
        this.currencies = currencies;
        this.currencyType = currencyType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_currency, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView buy = (TextView) rowView.findViewById(R.id.textViewBuy);
        TextView sell = (TextView) rowView.findViewById(R.id.textViewSell);

        for (int index = 0; index < currencies[position].length; index++) {
            String[] str = currencies[position][index].split(";");
            if (str[1].equals(currencyType)) {
                title.setText(str[0]);
                buy.setText(str[2]);
                sell.setText(str[3]);
            }

        }


        return rowView;
    }
}
