package cf.bcmbx.learnemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import cf.bcmbx.learnemall.R;

public class WordTouchHelper extends ItemTouchHelper.SimpleCallback {
   private WordsAdapter mWordsAdapter;
   private Context mContext;
    private boolean mIfLearnMode;

    public WordTouchHelper(WordsAdapter wordsAdapter, Context mContext, boolean mIfLearnMode){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mWordsAdapter = wordsAdapter;
        this.mContext = mContext;
        this.mIfLearnMode = mIfLearnMode;
        }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
    return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mIfLearnMode) {
            if (direction == 8) {
                mWordsAdapter.markAsLearnt(viewHolder.getAdapterPosition());
                Toast.makeText(mContext, mContext.getText(R.string.learnt), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, mContext.getText(R.string.next_time), Toast.LENGTH_SHORT).show();
                mWordsAdapter.remove(viewHolder.getAdapterPosition());
            }
        } else {
            Toast.makeText(mContext, mContext.getText(R.string.word_removed), Toast.LENGTH_SHORT).show();
            mWordsAdapter.removeWordFromDb(viewHolder.getAdapterPosition());
        }

    }
}
