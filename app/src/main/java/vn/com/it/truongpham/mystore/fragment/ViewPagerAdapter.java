package vn.com.it.truongpham.mystore.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment=null;
        switch (i){
            case 0 : fragment=new FragmentThu();
            break;
            case 1 : fragment=new FragmentChi();
            break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
