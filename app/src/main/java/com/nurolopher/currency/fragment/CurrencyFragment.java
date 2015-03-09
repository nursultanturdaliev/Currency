package com.nurolopher.currency.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nurolopher.currency.R;

import adapter.CurrencyAdapter;
import parser.Currency;


public class CurrencyFragment extends ListFragment {


    private static final String TAG = "CurrencyFragment";
    private static final String ARG_CURRENCY_TYPE = "currency_type";
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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        int columnIndex = Currency.getCurrencyCell(position, currencyType);
        if (columnIndex > -1) {
            String[] bankParams = Currency.currencyTable[position][columnIndex].split(";");
            final double buy = Double.parseDouble(bankParams[2].replace(',', '.'));
            final double sell = Double.parseDouble(bankParams[3].replace(',', '.'));
            final String fromCurrency = bankParams[1];
            String toCurrency = Currency.SOM;


            //dialog
            final Dialog dialog = new Dialog(new ContextThemeWrapper(getActivity(), R.style.AlertDialogStyle));
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
                        if (txtCurrencyLeft.getText().toString() == Currency.SOM) {
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

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            // Image currency toggle
            ImageButton imgBtnToggle = (ImageButton) dialog.findViewById(R.id.imgBtnToggle);
            imgBtnToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CharSequence tmpText = txtCurrencyLeft.getText();
                    txtCurrencyLeft.setText(txtCurrencyRight.getText());
                    txtCurrencyRight.setText(tmpText);
                    if (edtTxtFrom.getText().length() > 0) {
                        if (txtCurrencyLeft.getText().toString() == Currency.SOM) {
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
            dialog.show();

            int alertTitleId = getResources().getIdentifier("android:id/alertTitle", null, null);
            TextView alertTitle = (TextView) dialog.findViewById(alertTitleId);
            if (alertTitle != null)
                alertTitle.setTextColor(getResources().getColor(R.color.application_color)); // change title text color

            int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
            View divider = dialog.findViewById(dividerId);
            if (divider != null)
                divider.setBackgroundColor(getActivity().getResources().getColor(R.color.application_color));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currencyType = getArguments().getString(ARG_CURRENCY_TYPE);
        }
        CurrencyAdapter currencyAdapter = new CurrencyAdapter(getActivity(), R.layout.fragment_currency, currencyType);
        setListAdapter(currencyAdapter);
    }
}
