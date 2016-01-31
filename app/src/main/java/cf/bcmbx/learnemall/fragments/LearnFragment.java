package cf.bcmbx.learnemall.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cf.bcmbx.learnemall.R;
import cf.bcmbx.learnemall.adapter.WordTouchHelper;
import cf.bcmbx.learnemall.adapter.WordsAdapter;
import cf.bcmbx.learnemall.db.WordsManager;

public class LearnFragment extends Fragment {
    private WordsAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        if (new WordsManager(getActivity().getApplicationContext()).getNotLearntWords().size() == 0) {
            v = inflater.inflate(R.layout.empty_db, container, false);
        } else {
            v = inflater.inflate(R.layout.all_words_fragment, container, false);
            RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.list_words_container);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            mAdapter = new WordsAdapter(new WordsManager(getActivity().getApplicationContext()).getNotLearntWords(), true, getActivity());
            mRecyclerView.setAdapter(mAdapter);
            ItemTouchHelper.Callback callback = new WordTouchHelper(mAdapter, getActivity().getApplicationContext(), true);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(mRecyclerView);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
