package adapter;

import android.support.v4.app.*;

import com.nurolopher.currency.Currency;
import com.nurolopher.currency.DollarFragment;

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
                return DollarFragment.newInstance(Currency.USD);
            case 1:
                return DollarFragment.newInstance(Currency.EUR);
            case 2:
                return DollarFragment.newInstance(Currency.RUB);
            default:
                return DollarFragment.newInstance(Currency.KZT);
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
