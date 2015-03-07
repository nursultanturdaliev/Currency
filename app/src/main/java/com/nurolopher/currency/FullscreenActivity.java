package com.nurolopher.currency;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;


public class FullscreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);
        SharedPreferences sharedPreferences = getSharedPreferences(Currency.SHARED_PREFS_CURRENCY, 0);
        String currency = sharedPreferences.getString(Currency.currencyPrefTag, "");
        if (currency.equals("")) {
            Currency.currencyTable = new String[][]{};
            CurrencyParser currencyParser = new CurrencyParser(this);
            currencyParser.execute(BankURL.getArrayURL());
        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Currency.normalizeCurrencyTable();

        SharedPreferences sharedPreferences = getSharedPreferences(Currency.SHARED_PREFS_CURRENCY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Currency.currencyPrefTag, StringHelper.mergeDoubleStringArray(Currency.currencyTable));
        editor.apply();
    }

    private void pauseFor(long millis) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(intent);
            }
        }, millis);

    }
}
