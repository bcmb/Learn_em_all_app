package cf.bcmbx.learnemall.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cf.bcmbx.learnemall.R;

public class AboutFragment extends Fragment {

    private static final int NUM_PAGES = 5;
    public final static Integer[] imageResIds = new Integer[] {
            R.drawable.about_slide_1, R.drawable.about_slide_2, R.drawable.about_slide_3,
            R.drawable.about_slide_4, R.drawable.about_slide_5};

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_fragment, container, false);
        mPager = (ViewPager) v.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        return v;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
              return ScreenSlidePageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
