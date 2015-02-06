package com.nurolopher.currency.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import parser.BankURL;
import parser.CurrencyParser;

public class CurrencyFragment extends ListFragment {

    private static final String ARG_CURRENCY_TYPE = "currencyType";
    private String currencyType;

    public static CurrencyFragment newInstance(String currencyType) {
        CurrencyFragment fragment = new CurrencyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CURRENCY_TYPE, currencyType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CurrencyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyType = getArguments().getString(ARG_CURRENCY_TYPE);
        }
        CurrencyParser currencyParser = new CurrencyParser(this, currencyType);
        currencyParser.execute(BankURL.getArrayURL());
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
