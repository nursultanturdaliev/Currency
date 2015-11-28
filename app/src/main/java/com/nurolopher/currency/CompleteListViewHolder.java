package com.nurolopher.currency;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by nurolopher on 11/11/2015.
 */
public class CompleteListViewHolder extends RecyclerView.ViewHolder {

    public final TextView title;
    public final TextView buy;
    public final TextView sell;

    public CompleteListViewHolder(View base) {
        super(base);
        title = (TextView) base.findViewById(R.id.textViewTitle);
        buy = (TextView) base.findViewById(R.id.textViewBuy);
        sell = (TextView) base.findViewById(R.id.textViewSell);
    }
}
