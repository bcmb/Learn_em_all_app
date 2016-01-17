package cf.bcmbx.learnemall.db;

public class VocabDBSchema {
    public static final class NewWordsTable {
        public static final String NAME = "newWords";

        public static final class Cols {
            public static final String ORIGINAL_WORD = "origWord";
            public static final String TRANSLATION = "translation";
            public static final String LEARNT = "ifLearnt";
        }
    }

    public static final class LearntWordsTable {
        public static final String NAME = "learnt.words";

        public static final class Cols {
            public static final String ORIGINAL_WORD = "origWord";
            public static final String TRANSLATION = "translation";
        }
    }
}
