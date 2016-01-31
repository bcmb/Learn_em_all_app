package cf.bcmbx.learnemall.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cf.bcmbx.learnemall.R;

/**
 * Created by bcmb on 2/1/2016.
 */
public class AllDoneFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.empty_db, container, false);
         return v;
    }
}
