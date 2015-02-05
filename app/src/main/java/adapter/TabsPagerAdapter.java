package adapter;

import android.support.v4.app.*;

import com.nurolopher.currency.DollarFragment;
import com.nurolopher.currency.EuroFragment;
import com.nurolopher.currency.RubleFragment;
import com.nurolopher.currency.SomFragment;

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
                return new DollarFragment();
            case 1:
                return new DollarFragment();
            case 2:
                return new DollarFragment();
            default:
                return new DollarFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }
}
