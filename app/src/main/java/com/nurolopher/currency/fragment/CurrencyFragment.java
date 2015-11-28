package com.nurolopher.currency.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nurolopher.currency.R;

import java.util.ArrayList;
import java.util.List;

import adapter.CurrencyAdapter;
import model.CurrencyModel;
import parser.Currency;


public class CurrencyFragment extends Fragment implements ListView.OnItemClickListener {


    private static final String ARG_CURRENCY_TYPE = "currency_type";
    private String currencyType;

    public static CurrencyFragment newInstance(String currencyType) {

        CurrencyFragment fragment = new CurrencyFragment();

        Bundle args = new Bundle();
        args.putString(ARG_CURRENCY_TYPE, currencyType);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<CurrencyModel> list = new ArrayList<>();
        if (Currency.hashtable != null) {
            list = Currency.getHastTable().get(currencyType);
        }
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(getActivity(), list);
        listView.setOnItemClickListener(this);
        listView.setAdapter(currencyAdapter);

        return view;
    }

    public CurrencyFragment() {
    }

    private void imageCurrencyToggle(final double buy, final double sell, Dialog dialog, final TextView txtCurrencyLeft, final TextView txtCurrencyRight, final EditText edtTxtFrom, final TextView edtTxtTo) {
        ImageButton imgBtnToggle = (ImageButton) dialog.findViewById(R.id.imgBtnToggle);
        imgBtnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tmpText = txtCurrencyLeft.getText();
                txtCurrencyLeft.setText(txtCurrencyRight.getText());
                txtCurrencyRight.setText(tmpText);
                if (edtTxtFrom.getText().length() > 0) {
                    if (txtCurrencyLeft.getText().toString().equals(Currency.SOM)) {
                        double result = Double.parseDouble(edtTxtFrom.getText().toString()) / sell;
                        edtTxtTo.setText(String.format("%.2f", result));
                    } else {
                        double result = Double.parseDouble(edtTxtFrom.getText().toString()) * buy;
                        edtTxtTo.setText(String.format("%.2f", result));
                    }
                } else {
                    edtTxtTo.setText("");
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyType = getArguments().getString(ARG_CURRENCY_TYPE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int columnIndex = Currency.getCurrencyCell(position, currencyType);
        if (columnIndex > -1) {
            String[] bankParams = Currency.currencyTable[position][columnIndex].split(";");
            final double buy = Double.parseDouble(bankParams[2].replace(',', '.'));
            final double sell = Double.parseDouble(bankParams[3].replace(',', '.'));
            final String fromCurrency = bankParams[1];
            String toCurrency = Currency.SOM;


            //dialog
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.converter_dialog);
            dialog.setTitle(getActivity().getString(R.string.converter_title));

            //currency titles
            final TextView txtCurrencyLeft = (TextView) dialog.findViewById(R.id.txtCurrencyLeft);
            final TextView txtCurrencyRight = (TextView) dialog.findViewById(R.id.txtCurrencyRight);
            txtCurrencyLeft.setText(fromCurrency);
            txtCurrencyRight.setText(toCurrency);

            //currencies
            final EditText edtTxtFrom = (EditText) dialog.findViewById(R.id.edtTxtFrom);
            final TextView edtTxtTo = (TextView) dialog.findViewById(R.id.edtTxtTo);

            edtTxtFrom.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (edtTxtFrom.getText().length() > 0) {
                        try {
                            if (txtCurrencyLeft.getText().toString().equals(Currency.SOM)) {
                                double result = Double.parseDouble(edtTxtFrom.getText().toString()) / sell;
                                edtTxtTo.setText(String.format("%.2f", result));
                            } else {
                                double result = Double.parseDouble(edtTxtFrom.getText().toString()) * buy;
                                edtTxtTo.setText(String.format("%.2f", result));
                            }
                        } catch (Exception ignore) {
                        }
                    } else {
                        edtTxtTo.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            // Image currency toggle
            imageCurrencyToggle(buy, sell, dialog, txtCurrencyLeft, txtCurrencyRight, edtTxtFrom, edtTxtTo);
            dialog.show();

            edtTxtFrom.post(new Runnable() {
                public void run() {
                    edtTxtFrom.requestFocusFromTouch();
                    InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    lManager.showSoftInput(edtTxtFrom, 0);
                }
            });
        }
    }
}
