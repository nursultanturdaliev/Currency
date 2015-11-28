package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nurolopher.currency.CompleteListViewHolder;
import com.nurolopher.currency.R;

import java.util.List;

import model.CurrencyModel;


/**
 * Created by nursultan on 5-Feb 15.
 */
public class CurrencyAdapter extends ArrayAdapter {

    private final Context context;
    private List<CurrencyModel> mCurrencyModels;

    public CurrencyAdapter(Context context, List<CurrencyModel> mCurrencyModels) {
        super(context, R.layout.row, mCurrencyModels);
        this.mCurrencyModels = mCurrencyModels;
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
            CurrencyModel currencyModel = mCurrencyModels.get(position);
            if (currencyModel == null) {
                return convertView;
            }
            viewHolder.title.setText(currencyModel.getName());
            viewHolder.buy.setText(currencyModel.getBuy());
            viewHolder.sell.setText(currencyModel.getSell());
            convertView.setTag(viewHolder);
        }

        return convertView;
    }
}
