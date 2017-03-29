package com.edge.greasy.utility;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.edge.greasy.adapter.ListViewAdpter;
import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.dbo.Word;

public class Constants {
	
	public static String DATABASE_NAME = "WordsLists.sqlite";

    public static int DATABASE_VERSION = 5;
	public static final String SP_KEY_DB_VER = "db_ver";
	public static final String DB_PATH_SUFFIX = "/databases/";

    public static String DATABASE_TABLE_WORD = "grewords_db";
	public static final String WORD_ID = "Id";
	public static final String WORD_TEXT = "Word";
	public static final String WORD_ENGLISH = "EnglishMeaning";
	public static final String WORD_MARATHI = "MarathiMeaning";
	public static final String WORD_HINDI = "HindiMeaning";
	public static final String WORD_SENTENCE = "Sentence";

    public static String DATABASE_TABLE_USER = "grewords_user";
    public static final String USER_ID = "WordId";
    public static final String USER_CATEGORY = "Category";
    public static final String USER_UPD_TIME = "UpdateTimestamp";

    public static String DATABASE_TABLE_SETTINGS = "grewords_settings";
    public static final String SETTINGS_KEY = "Key";
    public static final String SETTINGS_VALUE = "Value";

    public static final String APP_KEY = "";

}
