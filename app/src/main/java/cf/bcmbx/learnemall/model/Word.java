package cf.bcmbx.learnemall.model;

public class Word {
    private String mWord;
    private String mTranslation;

    public Word() {
    }

    public Word(String word) {
        mWord = word;
    }

    public Word(String word, String translation) {
        mWord = word;
        mTranslation = translation;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        this.mWord = word;
    }

    public String getTranslation() {
        return mTranslation;
    }

    public void setTranslation(String translation) {
        this.mTranslation = translation;
    }
}
