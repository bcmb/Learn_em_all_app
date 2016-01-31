package cf.bcmbx.learnemall.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cf.bcmbx.learnemall.R;

/**
 * Created by bcmb on 1/31/2016.
 */

public class ScreenSlidePageFragment extends Fragment {
    private static final String IMAGE_DATA_EXTRA = "resId";
    private int mImageNum;
    private ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int page = getArguments().getInt("someInt");

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        mImageView = (ImageView) rootView.findViewById(R.id.slide);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final int resId = AboutFragment.imageResIds[mImageNum];
        mImageView.setImageResource(resId); // Load image into ImageView
    }

    public static ScreenSlidePageFragment newInstance(int imageNum) {
        ScreenSlidePageFragment f = new ScreenSlidePageFragment();

        final Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, imageNum);
        f.setArguments(args);

        return f;
    }
}