package com.nurolopher.currency.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;

import com.nurolopher.currency.MainActivity;
import com.nurolopher.currency.R;

import adapter.CurrencyAdapter;

public class CurrencyFragment extends ListFragment {


    private static final String TAG = "CurrencyFragment";
    private static final String ARG_CURRENCY_TYPE = "currencyType";
    private String currencyType;

    public static CurrencyFragment newInstance(String currencyType) {

        CurrencyFragment fragment = new CurrencyFragment();

        Bundle args = new Bundle();
        args.putString(ARG_CURRENCY_TYPE, currencyType);
        fragment.setArguments(args);

        return fragment;
    }

    public CurrencyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyType = getArguments().getString(ARG_CURRENCY_TYPE);
        }
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(getActivity(), R.layout.fragment_currency, MainActivity.currencyTable, currencyType);
        setListAdapter(currencyAdapter);
        

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity.fragments.add(this);
        Log.i(TAG, "Fragment added:" + this.currencyType);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
