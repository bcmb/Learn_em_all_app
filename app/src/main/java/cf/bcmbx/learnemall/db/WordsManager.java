package cf.bcmbx.learnemall.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cf.bcmbx.learnemall.model.Word;

import static cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable;

public class WordsManager {
    private SQLiteDatabase mDatabase;
    private String[] mAllColumns = { NewWordsTable.Cols.ORIGINAL_WORD,
            NewWordsTable.Cols.TRANSLATION };

    public WordsManager (Context c) {
        mDatabase = new cf.bcmbx.learnemall.db.WordsDBHelper(c).getWritableDatabase();
    }

    public boolean markAsLearnt(String origWord) {
        ContentValues values = new ContentValues();
        values.put(NewWordsTable.Cols.LEARNT, "1");

        return mDatabase.update(NewWordsTable.NAME, values, NewWordsTable.Cols.ORIGINAL_WORD + "=?", new String[] {origWord}) == -1;
    }

    public boolean addWord(String origWord, String translation) {
        ContentValues values = new ContentValues();
        values.put(NewWordsTable.Cols.ORIGINAL_WORD, origWord);
        values.put(NewWordsTable.Cols.TRANSLATION, translation);
        values.put(NewWordsTable.Cols.LEARNT, "0");

        return mDatabase.insert(NewWordsTable.NAME, null, values) != -1;
    }

    public Word getWord(String origWord) {
        Cursor cursor = mDatabase.query(NewWordsTable.NAME,
                mAllColumns, NewWordsTable.Cols.ORIGINAL_WORD + "=?", new String[] {origWord}, null, null, null);
        cursor.moveToFirst();
        Word word = cursorToWord(cursor);
        cursor.close();

        return word;
    }

    public boolean removeWord(String origWord) {
        ContentValues values = new ContentValues();
        values.put(NewWordsTable.Cols.ORIGINAL_WORD, origWord);

        return mDatabase.delete(NewWordsTable.NAME, NewWordsTable.Cols.ORIGINAL_WORD + "=?", new String[] {origWord}) != -1;
    }

    public List<Word> getAllWords() {
        ArrayList<Word> words = new ArrayList<Word>();
        Cursor cursor = mDatabase.query(NewWordsTable.NAME,
                mAllColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();

        return words;
    }

    public List<Word> getNotLearntWords() {
        ArrayList<Word> words = new ArrayList<Word>();
        Cursor cursor = mDatabase.query(NewWordsTable.NAME,
                mAllColumns, NewWordsTable.Cols.LEARNT + "=?", new String[]{"0"}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Word word = cursorToWord(cursor);
            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();

        return words;
    }

    private Word cursorToWord(Cursor cursor) {
        Word word = new Word();
        word.setWord(cursor.getString(cursor.getColumnIndex(NewWordsTable.Cols.ORIGINAL_WORD)));
        word.setTranslation(cursor.getString(cursor.getColumnIndex(NewWordsTable.Cols.TRANSLATION)));

        return word;
    }
}
