package com.nurolopher.currency;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import adapter.TabsPagerAdapter;
import parser.BankURL;
import parser.Currency;
import parser.CurrencyParser;
import helpers.DateHelper;
import helpers.StringHelper;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private static final String TAG = "MainActivity";
    public static final String SHARED_PREFS_CURRENCY = "SHARED_PREFS_CURRENCY";
    public static final String currencyPrefTag = "currency_table";
    public static final String datePrefTag = "date_updated";

    public static ViewPager viewPager;
    private android.app.ActionBar actionBar;
    public static TabsPagerAdapter tabsPagerAdapter;

    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        getOverflowMenu();

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();

        String[] tabs = getResources().getStringArray(R.array.currency_titles);
        for (String tabText : tabs) {
            ActionBar.Tab tab = actionBar.newTab().setText(tabText).setTabListener(this);
            actionBar.addTab(tab);
        }

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setHomeButtonEnabled(false);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        String currencyInString = sharedPreferences.getString(currencyPrefTag, "");

        Currency.currencyTable = StringHelper.unMergeString(currencyInString);

        CurrencyParser currencyParser = new CurrencyParser(this);
        currencyParser.execute(BankURL.getArrayURL());
    }

    @Override
    protected void onStop() {
        super.onStop();
        MainActivity.progressDialog.dismiss();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_CURRENCY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(currencyPrefTag, StringHelper.mergeDoubleStringArray(Currency.currencyTable));
        editor.commit();
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
                Currency.currencyTable = new String[][]{};
                CurrencyParser currencyParser = new CurrencyParser(this);
                currencyParser.execute(BankURL.getArrayURL());
                break;
            case R.id.action_last_updated:
                MainActivity.showUpdateToast(getApplicationContext());
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String dateInString = dateFormat.format(calendar.getTime());

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.SHARED_PREFS_CURRENCY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.datePrefTag, dateInString);
        editor.commit();

    }

    public static void showUpdateToast(Context context) {

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
            toastMessage.append(days + " " + context.getString(R.string.days));
        }
        if (hours > 0) {
            toastMessage.append(hours + " " + context.getString(R.string.hours) + " ");
        }
        if (minutes > 0) {
            toastMessage.append(minutes + " " + context.getString(R.string.minutes) + " ");
        }
        if (seconds > 0) {
            toastMessage.append(seconds + " " + context.getString(R.string.seconds_ago));

        }
        if (timeDiff / 1000 > 0) {
            defaultText = context.getString(R.string.last_updated) + " ";
        }
        toastMessage.insert(0, defaultText);
        Toast.makeText(context.getApplicationContext(), toastMessage.toString(), Toast.LENGTH_LONG).show();

    }
}
