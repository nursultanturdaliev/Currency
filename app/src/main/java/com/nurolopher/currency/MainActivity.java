package com.nurolopher.currency;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nurolopher.currency.fragment.CurrencyFragment;

import java.lang.reflect.Field;

import adapter.TabsPagerAdapter;
import helpers.StringHelper;
import parser.BankURL;
import parser.Currency;
import parser.CurrencyParser;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_CURRENCY = "SHARED_PREFS_CURRENCY";
    private static final String currencyPrefTag = "currency_table";
    private static final String datePrefTag = "date_updated";

    public static TabsPagerAdapter tabsPagerAdapter;

    public ProgressDialog progressDialog;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getOverflowMenu();
        setupActionBar();
        setupViewPager();
        prepareCurrencyTable();
        populateCurrency();
        configAds();
    }

    private void populateCurrency() {
        CurrencyParser currencyParser = new CurrencyParser(this);
        currencyParser.execute(BankURL.getArrayURL());
    }

    private void prepareCurrencyTable() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        String currencyInString = sharedPreferences.getString(currencyPrefTag, "");

        Currency.currencyTable = StringHelper.unMergeString(currencyInString);
        Currency.normalizeCurrencyTable();
    }

    private void configAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2BDF6CAE6CD3E181584C6FAC61FCF7F6")
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.pager);
        String[] tabs = getResources().getStringArray(R.array.currency_titles);

        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.USD), tabs[0]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.EUR), tabs[1]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.RUB), tabs[2]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.KZT), tabs[3]);
        viewPager.setAdapter(tabsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onStop() {
        progressDialog.dismiss();
        Currency.normalizeCurrencyTable();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currencyPrefTag, StringHelper.mergeDoubleStringArray(Currency.currencyTable));
        editor.apply();

        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_update:
                if (isNetworkConnected()) {
                    Currency.currencyTable = new String[][]{};
                    populateCurrency();
                } else {
                    showNoInternetMessage();
                }
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void showNoInternetMessage() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootView), R.string.no_internet, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showUpdateMessage() {
        String message = getString(R.string.default_text);
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rootView), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setElevation(0.0f);
        }
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ignored) {
        }
    }

    public void startProgressBar() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(this.getString(R.string.progress_title));
        progressDialog.setMessage(this.getString(R.string.progress_message_even));
        progressDialog.setProgress(0);
        progressDialog.setMax(BankURL.getArrayURL().length);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    public void toggleProgressBarMessage(int value) {
        int resId = (value % 2 == 1) ? R.string.progress_message_even : R.string.progress_message_odd;
        progressDialog.setMessage(getResources().getString(resId));
    }

    boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}

