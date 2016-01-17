package cf.bcmbx.learnemall.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cf.bcmbx.learnemall.R;
import cf.bcmbx.learnemall.db.WordsManager;
import cf.bcmbx.learnemall.model.Word;
import cf.bcmbx.learnemall.translator.TranslationModuleAsyncTask;

public class AddSingleWordFragment extends Fragment {
    private EditText mWord = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_single_word_fragment, container, false);

        Button addWord = (Button) v.findViewById(R.id.add_it);
        Button getTranslation = (Button) v.findViewById(R.id.get_translation);

        mWord = (EditText) v.findViewById(R.id.original_word);
        final EditText mTranslation = (EditText) v.findViewById(R.id.translation_word);

        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTranslation.toString().isEmpty() | mWord.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.enter_word_or_translation), Toast.LENGTH_SHORT).show();
                } else {
                    WordsManager manager = new WordsManager(getActivity().getApplicationContext());
                    boolean isSuccessful = manager.addWord(mWord.getText().toString(), mTranslation.getText().toString());
                    if (isSuccessful) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.added), Toast.LENGTH_SHORT).show();
                        mWord.setText("");
                        mTranslation.setText("");
                    } else {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.somethings_wrong), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        getTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (isNetworkAvailable()) {
                if (mWord.getText().toString().isEmpty() | mTranslation.toString().isEmpty()) {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.enter_word), Toast.LENGTH_SHORT).show();
                } else {
                    Word word = new Word(mWord.getText().toString());
                    TranslationModuleAsyncTask translator = new TranslationModuleAsyncTask(getActivity(), word, mTranslation);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    translator.execute(prefs.getString(getActivity().getString(R.string.pref_mylang_key), "en"),
                            prefs.getString(getActivity().getString(R.string.pref_foreignlang_key), "ru"));
                }
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.network_not_available), Toast.LENGTH_SHORT).show();
            }
          }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWord.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWord.setFocusableInTouchMode(true);
                mWord.requestFocus();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(mWord, 0);
            }
        }, 300);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
