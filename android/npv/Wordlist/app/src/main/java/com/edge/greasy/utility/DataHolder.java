package com.edge.greasy.utility;

import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.ListView;

import com.edge.greasy.adapter.ListViewAdpter;
import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.dbo.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Patil on 7/7/2016.
 */
public class DataHolder {

    public static Map<String, List<Word>> wordsListMap = new HashMap<>();

    public static ListView listView;
    public static ListViewAdpter adapter;
    public static SQLiteDatabase dataBase;
    public static SqlLiteDbHelper mHelper;
    public static Button searchButton, readButton, stopButton;
    public static TextToSpeech tts;
    public static Boolean SHOW_MEANING = true;
    public static int readingIndex = 0;
    public static boolean stopReading = false;

    public static List<Word> getWordsList(String category){
        return wordsListMap.get(category);
    }

    public static void addWordToList(String category, Word word){
        List<Word> list = wordsListMap.get(category);
        if(list == null){
            list = new ArrayList<>();
            wordsListMap.put(category, list);
        }
        list.add(word);
    }

    public static int getCountForCategory(String category){
        List<Word> words = wordsListMap.get(category);
        if(words == null){
            return 0;
        }
        return wordsListMap.get(category).size();
    }
}
