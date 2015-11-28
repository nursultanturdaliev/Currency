package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nurolopher.currency.CompleteListViewHolder;
import com.nurolopher.currency.R;

import parser.Currency;


/**
 * Created by nursultan on 5-Feb 15.
 */
public class CurrencyAdapter extends ArrayAdapter {

    private final String currencyType;
    private final Context context;

    public CurrencyAdapter(Context context, String currencyType) {
        super(context, R.layout.row, Currency.currencyTable);
        this.currencyType = currencyType;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompleteListViewHolder viewHolder;

        if (convertView == null) {

            // inflate the layout
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.row, parent, false);

            // well set up the ViewHolder
            viewHolder = new CompleteListViewHolder(convertView);

            for (int index = 0; index < Currency.currencyTable[position].length; index++) {
                if (Currency.currencyTable[position][index] == null) {
                    continue;
                }
                String[] str = Currency.currencyTable[position][index].split(";");
                if (str[1].equals(currencyType)) {
                    viewHolder.title.setText(str[0]);
                    viewHolder.buy.setText(str[2]);
                    viewHolder.sell.setText(str[3]);
                }
            }
            convertView.setTag(viewHolder);
        }

        return convertView;
    }
}
