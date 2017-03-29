package com.edge.greasy.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.edge.greasy.R;
import com.edge.greasy.adapter.ListViewAdpter;
import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.dbo.Word;

public class Utility {

	private static String text;
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");

	public static void showDetailDialog(Context context, Word word) {

		final Dialog dialog = new Dialog(context);

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.custom_dialog, null, false);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

		dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

		// Retrieve views from the inflated dialog layout and update their
		// values
		TextView txtTitle = (TextView) dialog.findViewById(R.id.word_title);
		txtTitle.setText(word.get_word());

		TextView txtMessage = (TextView) dialog.findViewById(R.id.txt_meaning);
		if (word.get_meaning() != null) {
			txtMessage.setText(word.get_meaning());
		} else {
			txtMessage.setTextColor(Color.RED);
			txtMessage.setText("[ No meaning available ]");
		}

		TextView txtmeaning = (TextView) dialog.findViewById(R.id.txtmeaning);
		text = "<font color=#16a085>" + word.get_marathimeaning() + "</font><font color=#000> / </font>" + "<font color=#e67e22>" + word.get_hindiMeaning() + "</font>";

		//System.out.println("hindi = " + word.get_hindiMeaning());
		txtmeaning.setText(Html.fromHtml(text));

		TextView txtSentence = (TextView) dialog.findViewById(R.id.word_sentence);
		if (word.get_sentence() != null) {
			txtSentence.setText(word.get_sentence());
		} else {
			txtSentence.setTextColor(Color.RED);
			txtSentence.setText("[ No sentence available ]");
		}

		// Display the dialog
		dialog.show();
	}

	public static void showToast(Context context, String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void updateListview(Context context, List<Word> tempList) {
		DataHolder.adapter = new ListViewAdpter(context, tempList);
		DataHolder.listView.setAdapter(DataHolder.adapter);
		DataHolder.adapter.notifyDataSetChanged();
	}

	public static void saveEasyWordsCount(Context context, int value) {
		SharedPreferences pref = context.getSharedPreferences("MyPref",
				Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt("key_easyWordsCount", value);
		editor.commit();
	}

	public static void retriveEasyWordsCount(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("MyPref",
				context.MODE_PRIVATE);
		int count = prefs.getInt("key_easyWordsCount", 0); // 0 is the default
		// value.
		//System.out.println("count = " + count);
	}

    public static void refreshData(Context context, SqlLiteDbHelper mHelper) {
        DataHolder.wordsListMap.clear();
        refreshAllList(context, mHelper);
        refreshCategoryList(context, mHelper);
    }

	public static void refreshCategoryList(Context context, SqlLiteDbHelper mHelper) {

		DataHolder.dataBase = mHelper.getWritableDatabase();

		Cursor mCursor = DataHolder.dataBase.rawQuery(
				  "SELECT * FROM "
				+  Constants.DATABASE_TABLE_WORD + " , " + Constants.DATABASE_TABLE_USER
                + " WHERE " + Constants.WORD_ID + " = " + Constants.USER_ID
                + " ORDER BY " + Constants.USER_UPD_TIME + " DESC "
                , null);

		if (mCursor.moveToFirst()) {
			do {
				String category = mCursor.getString(mCursor
						.getColumnIndex(Constants.USER_CATEGORY));
				Word word = new Word(
						mCursor.getInt(mCursor
								.getColumnIndex(Constants.WORD_ID)),
						mCursor.getString(mCursor
								.getColumnIndex(Constants.WORD_TEXT)),
						mCursor.getString(mCursor
										.getColumnIndex(Constants.WORD_ENGLISH)),
						mCursor.getString(mCursor
						    .getColumnIndex(Constants.WORD_SENTENCE)),
				        category,
                        null,
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_MARATHI)),
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_HINDI))
                        );
                DataHolder.addWordToList(category, word);
			} while (mCursor.moveToNext());
		}

		mCursor.close();
	}

    public static void refreshAllList(Context context, SqlLiteDbHelper mHelper) {

        DataHolder.dataBase = mHelper.getWritableDatabase();

        Cursor mCursor = DataHolder.dataBase.rawQuery(
                "SELECT * FROM "
                        +  Constants.DATABASE_TABLE_WORD + " , " + Constants.DATABASE_TABLE_USER
                        + " WHERE " + Constants.WORD_ID + " = " + Constants.USER_ID
                , null);

        if (mCursor.moveToFirst()) {
            do {
                String category = mCursor.getString(mCursor
                        .getColumnIndex(Constants.USER_CATEGORY));
                Word word = new Word(
                        mCursor.getInt(mCursor
                                .getColumnIndex(Constants.WORD_ID)),
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_TEXT)),
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_ENGLISH)),
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_SENTENCE)),
                        category,
                        null,
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_MARATHI)),
                        mCursor.getString(mCursor
                                .getColumnIndex(Constants.WORD_HINDI))
                );
                DataHolder.addWordToList("All", word);

            } while (mCursor.moveToNext());
        }

        mCursor.close();
    }

    public static void stopReading(){
        DataHolder.stopButton.setVisibility(View.GONE);
        DataHolder.readButton.setVisibility(View.VISIBLE);
        DataHolder.stopReading = true;
        if (DataHolder.tts != null) {
            DataHolder.tts.stop();
        }
    }

    public static void readPage(Context context, List<Word> currPageWordsList, int startPosition){

        /*// Set utteranceId to know when a particular utterance is done playing
        HashMap<String, String> myHash = new HashMap<String, String>();
        myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "done");
        DataHolder.tts.speak("Page Reading Complete.", TextToSpeech.QUEUE_ADD, myHash);*/

        DataHolder.stopReading = false;
        DataHolder.readButton.setVisibility(View.GONE);
        DataHolder.stopButton.setVisibility(View.VISIBLE);

        //DataHolder.tts.speak("Reading Page", TextToSpeech.QUEUE_ADD, null);
        int size = currPageWordsList.size();
        //for(int i = startPosition;i<size;i++){
            DataHolder.readingIndex = startPosition;
            Word w = (Word) DataHolder.adapter.getItem(DataHolder.readingIndex);
            System.out.println(w._word);
			//showDetailDialog(context,w);
            // DataHolder.listView.setSelection(i);
            HashMap<String, String> myHash = new HashMap<String, String>();
            myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(DataHolder.readingIndex));

            DataHolder.tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                @Override
                public void onUtteranceCompleted(String s) {
                    if(!DataHolder.stopReading){
                        System.out.println(s);
                        DataHolder.readingIndex++;
                        try{
                            //DataHolder.listView.setSelection(DataHolder.readingIndex);
                            Word w = (Word) DataHolder.adapter.getItem(DataHolder.readingIndex);
                            if(w!=null){
                                System.out.println(w._word);
                                HashMap<String, String> myHash = new HashMap<String, String>();
                                myHash.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(DataHolder.readingIndex));
                                DataHolder.tts.speak(w.toRead(), TextToSpeech.QUEUE_ADD, myHash);
                            }
                        }catch(Exception e){
                            //DataHolder.tts.speak("Reading Page Complete.", TextToSpeech.QUEUE_ADD, null);
                            DataHolder.readingIndex = 0;
                            DataHolder.readButton.setVisibility(View.VISIBLE);
                            DataHolder.stopButton.setVisibility(View.GONE);
                        }
                    }else{

                    }

                }
            });
            DataHolder.tts.speak(w.toRead(), TextToSpeech.QUEUE_ADD, myHash);

			//System.out.println(w._word);
		//}


	}

    public static void updateCategoryOfWord(String category, int  wordId ){
        long timestamp = getTimestampAsLong();
        //System.out.println("Update Timestamp : " + timestamp);
        DataHolder.dataBase.execSQL("UPDATE "
                + Constants.DATABASE_TABLE_USER + " SET "
                + Constants.USER_CATEGORY + " = " + "'"+ category +"', "
                + Constants.USER_UPD_TIME + " = " + timestamp + " "
                + " WHERE " + Constants.USER_ID + " = " + wordId);
    }

    public static long getTimestampAsLong(){
        Date date = new Date();
        return Long.valueOf(dateFormatter.format(date));
    }

    public static void main(String args[]){
        //System.out.println(getTimestampAsLong());
    }
}
