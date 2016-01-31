package cf.bcmbx.learnemall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordsDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "vocabulary.db";

    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable.NAME + " (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable.Cols.ORIGINAL_WORD + " TEXT, " +
                        cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable.Cols.TRANSLATION + " TEXT," +
                        cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable.Cols.LEARNT + " TEXT" +
                        ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + cf.bcmbx.learnemall.db.VocabDBSchema.NewWordsTable.NAME + ");");
        onCreate(db);
    }
}
