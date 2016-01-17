package cf.bcmbx.learnemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cf.bcmbx.learnemall.R;
import cf.bcmbx.learnemall.db.WordsManager;
import cf.bcmbx.learnemall.model.Word;

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {
    private List<Word> mWordsList;
    private boolean mIfLearnMode;
    private Context mContext;
    private WordsManager wordsManager;

    public WordsAdapter (List words, boolean mIfLearnMode, Context mContext) {
        mWordsList = words;
        this.mIfLearnMode = mIfLearnMode;
        this.mContext = mContext;
        wordsManager = new WordsManager(mContext);
    }

    public static class WordsViewHolder extends RecyclerView.ViewHolder{
        public TextView mWord;
        public TextView mTranslation;

        public WordsViewHolder(View v) {
            super(v);
            this.mWord = (TextView) v.findViewById(R.id.list_item_word);
            this.mTranslation = (TextView) v.findViewById(R.id.list_item_translation);
        }
    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mIfLearnMode) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item_learn, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item_all_words, parent, false);
        }
        return new WordsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WordsViewHolder holder, final int position) {
        if (mIfLearnMode) {
            holder.mWord.setText(mWordsList.get(position).getWord());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, wordsManager.getWord(mWordsList.get(position).getWord()).getTranslation(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            holder.mWord.setText(mWordsList.get(position).getWord());
            holder.mTranslation.setText(mWordsList.get(position).getTranslation());
        }
    }

    @Override
    public int getItemCount() {
        return mWordsList.size();
    }

    public void remove(int position) {
        mWordsList.remove(position);
        notifyItemRemoved(position);
    }

    public void markAsLearnt(int position) {
        wordsManager.markAsLearnt(mWordsList.get(position).getWord());
        mWordsList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeWordFromDb(int position) {
        wordsManager.removeWord(mWordsList.get(position).getWord());
        mWordsList.remove(position);
        notifyItemRemoved(position);
    }
}
