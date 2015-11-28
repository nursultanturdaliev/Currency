package com.nurolopher.currency;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nurolopher.currency.fragment.CurrencyFragment;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import adapter.TabsPagerAdapter;
import helpers.DateHelper;
import helpers.StringHelper;
import parser.BankURL;
import parser.Currency;
import parser.CurrencyParser;


public class MainActivity extends AppCompatActivity {

    private static final String SHARED_PREFS_CURRENCY = "SHARED_PREFS_CURRENCY";
    private static final String currencyPrefTag = "currency_table";
    private static final String datePrefTag = "date_updated";

    public static TabsPagerAdapter tabsPagerAdapter;

    public static ProgressDialog progressDialog;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getOverflowMenu();

        setupActionBar();

        viewPager = (ViewPager) findViewById(R.id.pager);

        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        String currencyInString = sharedPreferences.getString(currencyPrefTag, "");

        Currency.currencyTable = StringHelper.unMergeString(currencyInString);
        Currency.normalizeCurrencyTable();

        CurrencyParser currencyParser = new CurrencyParser(this);
        currencyParser.execute(BankURL.getArrayURL());

        configAds();
    }

    private void configAds() {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2BDF6CAE6CD3E181584C6FAC61FCF7F6")
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setupViewPager(ViewPager viewPager) {
        tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        Resources resources = getResources();
        String[] tabs = resources.getStringArray(R.array.currency_titles);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.USD), tabs[0]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.USD), tabs[1]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.RUB), tabs[2]);
        tabsPagerAdapter.addFragment(CurrencyFragment.newInstance(Currency.KZT), tabs[3]);
        viewPager.setAdapter(tabsPagerAdapter);
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
                    CurrencyParser currencyParser = new CurrencyParser(this);
                    currencyParser.execute(BankURL.getArrayURL());
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG);
                    View view = toast.getView();
                    view.setBackgroundColor(getResources().getColor(R.color.application_color));
                    toast.show();
                }
                break;
            case R.id.action_last_updated:
                showUpdateToast(getApplicationContext());
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
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

    public static void startProgressBar(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(context.getString(R.string.progress_title));
        progressDialog.setMessage(context.getString(R.string.progress_message_even));
        progressDialog.setProgress(0);
        progressDialog.setMax(BankURL.getArrayURL().length);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

    }

    public static void toggleProgressBarMessage(Context context, int value) {
        if (value % 2 == 1) {
            progressDialog.setMessage(context.getResources().getString(R.string.progress_message_even));
        } else {
            progressDialog.setMessage(context.getResources().getString(R.string.progress_message_odd));
        }

    }

    public static void setUpdateTime(Context context) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.UK);
        Calendar calendar = Calendar.getInstance();
        String dateInString = dateFormat.format(calendar.getTime());

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(datePrefTag, dateInString);
        editor.apply();

    }

    public static void showUpdateToast(Context context) {
        Toast toast = Toast.makeText(context.getApplicationContext(), getMessage(context), Toast.LENGTH_LONG);
        toast.show();
    }

    public static String getMessage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        String lastUpdateDateStr = sharedPreferences.getString(datePrefTag, "");
        long[] dateDiff = DateHelper.getDateDiff(lastUpdateDateStr);

        String defaultText = context.getString(R.string.default_text);
        StringBuilder toastMessage = new StringBuilder();

        long days = dateDiff[0];
        long hours = dateDiff[1];
        long minutes = dateDiff[2];
        long seconds = dateDiff[3];
        long timeDiff = dateDiff[4];

        if (days > 0) {
            toastMessage.append(days).append(" ").append(context.getString(R.string.days)).append(" ");
        }
        if (hours > 0) {
            toastMessage.append(hours).append(" ").append(context.getString(R.string.hours)).append(" ");
        }
        if (minutes > 0) {
            toastMessage.append(minutes).append(" ").append(context.getString(R.string.minutes)).append(" ");
        }
        if (seconds > 0) {
            toastMessage.append(seconds).append(" ").append(context.getString(R.string.seconds_ago));

        }
        if (timeDiff / 1000 > 0) {
            defaultText = context.getString(R.string.last_updated) + " ";
        }
        toastMessage.insert(0, defaultText);
        return toastMessage.toString();
    }

    boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }
}
