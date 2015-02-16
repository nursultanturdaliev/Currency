package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nurolopher.currency.R;

import parser.Currency;


/**
 * Created by nursultan on 5-Feb 15.
 */
public class CurrencyAdapter extends ArrayAdapter {

    private final String currencyType;

    public CurrencyAdapter(Context context, int resource, String currencyType) {
        super(context, resource, Currency.currencyTable);
        this.currencyType = currencyType;
    }

    @Override
    public int getCount() {
        return Currency.currencyTable.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.fragment_currency, parent, false);
        if (position < Currency.currencyTable.length) {


            TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
            TextView buy = (TextView) rowView.findViewById(R.id.textViewBuy);
            TextView sell = (TextView) rowView.findViewById(R.id.textViewSell);

            for (int index = 0; index < Currency.currencyTable[position].length; index++) {
                if (Currency.currencyTable[position][index] != null) {
                    String[] str = Currency.currencyTable[position][index].split(";");
                    if (str[1].equals(currencyType)) {
                        title.setText(str[0]);
                        buy.setText(str[2]);
                        sell.setText(str[3]);
                    }

                }
            }


            return rowView;
        } else {
            return rowView;
        }
    }
}
