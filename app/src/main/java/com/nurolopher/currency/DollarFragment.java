package com.nurolopher.currency;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import parser.CurrencyParser;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class DollarFragment extends ListFragment {

    private static final String ARG_CURRENCY_TYPE = "currencyType";

    // TODO: Rename and change types of parameters
    private String currencyType;

    // TODO: Rename and change types of parameters
    public static DollarFragment newInstance(String currencyType) {
        DollarFragment fragment = new DollarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CURRENCY_TYPE, currencyType);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DollarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyType = getArguments().getString(ARG_CURRENCY_TYPE);
        }
        CurrencyParser currencyParser = new CurrencyParser(this, currencyType);
        String[] urls = new String[]{"http://ecoislamicbank.kg/", "http://www.nbkr.kg/XML/daily.xml"};
        currencyParser.execute(urls);
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
