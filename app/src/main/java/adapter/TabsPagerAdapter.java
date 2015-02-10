package adapter;

import android.support.v4.app.*;

import parser.Currency;

import com.nurolopher.currency.fragment.CurrencyFragment;

/**
 * Created by nursultan on 5-Feb 15.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {


    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return CurrencyFragment.newInstance(Currency.USD);
            case 1:
                return CurrencyFragment.newInstance(Currency.EUR);
            case 2:
                return CurrencyFragment.newInstance(Currency.RUB);
            default:
                return CurrencyFragment.newInstance(Currency.KZT);
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
