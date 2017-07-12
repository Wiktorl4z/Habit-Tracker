package com.example.l4z.habittracker.date;

import android.provider.BaseColumns;

/**
 * Created by l4z on 11.07.2017.
 */

public class ParticipantsContract implements BaseColumns {

    public ParticipantsContract() {
    }

    public static final class ParticipantsEntry implements BaseColumns {

        public final static String TABLE_NAME = "participants";
        public final static String COLUMN_ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_AGE = "age";
        public final static String COLUMN_RANK = "rank";
        public final static String COLUMN_RANK_500 = "500";
        public final static String COLUMN_RANK_1000 = "1000";
        public final static String COLUMN_RANK_1500 = "1500";
        public final static String COLUMN_RANK_2000 = "2000";
    }
}
