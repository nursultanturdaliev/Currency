package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nurolopher.currency.Currency.DollarContent;
import com.nurolopher.currency.R;

import java.util.List;

/**
 * Created by nursultan on 5-Feb 15.
 */
public class CurrencyAdapter extends ArrayAdapter {

    private List<DollarContent.DummyItem> items;

    public CurrencyAdapter(Context context, int resource, List<DollarContent.DummyItem> items) {
        //super(context, R.layout.currency_list_item,items);
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.currency_list_item, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView buy = (TextView) rowView.findViewById(R.id.textViewBuy);
        TextView sell = (TextView) rowView.findViewById(R.id.textViewSell);

        title.setText(items.get(position).title);
        buy.setText(Double.toString(items.get(position).buy));
        sell.setText(Double.toString(items.get(position).sell));
        Log.i("H", items.toString());

        return rowView;
    }
}
