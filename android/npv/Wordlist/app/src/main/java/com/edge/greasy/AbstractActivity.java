package com.edge.greasy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.dbo.Word;
import com.edge.greasy.utility.Constants;
import com.edge.greasy.utility.DataHolder;
import com.edge.greasy.utility.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Patil on 7/7/2016.
 */
public abstract class AbstractActivity extends Activity implements
        CompoundButton.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, TextWatcher,
        TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener, View.OnTouchListener {

    public List<Word> allWordsList = new ArrayList<>();
    public int allWordsCount = 0;

    public List<Word> currPageWordsList = new ArrayList<>();
    public int currPageWordsCount = 0;

    public int allPagesCount = 0;
    public int currPageCounter = 0;

    protected TextView title;
    protected EditText inputSearch;
    protected ToggleButton toggleButton;
    protected Button btn_prev;
    protected Button btn_next;
    protected LinkedHashMap<String, Integer> mapIndex;
    protected GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        gestureDetector = new GestureDetector(getApplicationContext(), new SwipeGestureListener(){

            @Override
            public void onSwipeLeft() {
                goToNextPage();
            }

            @Override
            public void onSwipeRight() {
                goToPreviousPage();  }
        });
        //check for the presence of the TTS resources with the corresponding intent
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, 1);

        //requestWindowFeature(Window.FEATURE_ACTION_BAR);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        DataHolder.mHelper = new SqlLiteDbHelper(this);
        DataHolder.mHelper.openDataBase();
        DataHolder.listView = (ListView) findViewById(R.id.listview);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(this);

        btn_prev = (Button) findViewById(R.id.prev);
        btn_next = (Button) findViewById(R.id.next);
        title = (TextView) findViewById(R.id.title);

        toggleButton = (ToggleButton) findViewById(R.id.toggle_button);
        toggleButton.setChecked(true);
        toggleButton.setOnCheckedChangeListener(this);

        DataHolder.searchButton = (Button) findViewById(R.id.searchButton);
        DataHolder.searchButton.setOnClickListener(this);

        DataHolder.readButton = (Button) findViewById(R.id.readButton);
        DataHolder.readButton.setOnClickListener(this);

        DataHolder.stopButton = (Button) findViewById(R.id.stopButton);
        DataHolder.stopButton.setOnClickListener(this);

        btn_prev.setEnabled(false);
        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);

        displayData();

    }

    protected String getCategory() {
        return "Hard";
    }

    protected void displayData() {

        allWordsList = DataHolder.getWordsList(getCategory());

        if (allWordsList == null || allWordsList.size() == 0) {
            Utility.showToast(getApplicationContext(), "No words");
            return;
        }

        allWordsCount = allWordsList.size();

        if (allWordsCount <= 50) {
            btn_prev.setEnabled(false);
            btn_next.setEnabled(false);

            currPageWordsList = allWordsList;
            currPageWordsCount = allWordsCount;

            allPagesCount = 1;
        } else {

            btn_prev.setEnabled(false);
            btn_next.setEnabled(true);

            currPageWordsCount = 50;
            for(int i=0;i<50;i++){
                currPageWordsList.add(allWordsList.get(i));
            }

            int lastPageReqd = allWordsCount % currPageWordsCount;
            lastPageReqd = lastPageReqd == 0 ? 0 : 1;
            allPagesCount = allWordsCount / currPageWordsCount + lastPageReqd;
        }

        currPageCounter = 0;
        title.setText("Page 1" + " of " + allPagesCount);

        if (allWordsCount == 0) {
            Utility.showToast(getApplicationContext(), "No words");
        } else {
            Utility.updateListview(getApplicationContext(), currPageWordsList);
            DataHolder.listView.setOnItemClickListener(this);
            DataHolder.listView.setOnTouchListener(this);

            //getIndexList(currPageWordsList);
            //displayIndex();

            LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
            indexLayout.setVisibility(View.GONE);
        }
    }

    public void goToNextPage(){
        if (currPageCounter + 1 < allPagesCount) {
            currPageCounter++;
            loadList(currPageCounter);
            CheckEnable();
        } else {
           Utility.showToast(getApplicationContext(), "No next page.");
        }
    }

    public void goToPreviousPage(){
        if (currPageCounter > 0) {
            currPageCounter--;
            loadList(currPageCounter);
            CheckEnable();
        } else {
            Utility.showToast(getApplicationContext(), "No previous page.");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                currPageCounter++;
                loadList(currPageCounter);
                CheckEnable();
                break;

            case R.id.prev:
                currPageCounter--;
                loadList(currPageCounter);
                CheckEnable();
                break;

            case R.id.searchButton:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.readButton:
                Utility.readPage(getApplicationContext(),currPageWordsList, DataHolder.readingIndex);
                break;

            case R.id.stopButton:
                Utility.stopReading();
                break;

            default:
                break;
        }
    }

    protected void getIndexList(List<Word> tempList) {
        mapIndex = new LinkedHashMap<String, Integer>();

        for (int i = 0; i < tempList.size(); i++) {
            String fruit = tempList.get(i).get_word();
            String index = fruit.substring(0, 1);
            index = index.toUpperCase();

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    protected void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
        indexLayout.removeAllViews();
        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());

        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(R.layout.side_index_item, null);
            textView.setText(index);
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    TextView selectedIndex = (TextView) v;
                    DataHolder.listView.setSelection(mapIndex.get(selectedIndex.getText()));
                }
            });

            indexLayout.addView(textView);
        }
    }

    protected void loadList(int number) {

        if (allPagesCount >= 2) {

            int start = number * currPageWordsCount;
            int end = start + currPageWordsCount;

            title.setText("P: " + (number + 1) + "/" + allPagesCount + "; W: " + (start + 1) + "-" + end + "/" + allWordsCount + ".");

            currPageWordsList.clear();

            //currPageWordsList = allWordsList.subList(start, end);
            for(int i=start;i<end;i++){
                //System.out.println(allWordsList.size() + " : " + i + " : " + allWordsList.get(i).get_word());
                if(i<allWordsCount){
                    currPageWordsList.add(allWordsList.get(i));
                }else{
                    // Nothing to do
                }

            }
        }

        Utility.updateListview(getApplicationContext(), currPageWordsList);
        DataHolder.listView.setOnItemClickListener(this);
        DataHolder.listView.setOnTouchListener(this);

        //getIndexList(currPageWordsList);
        // displayIndex();
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
        indexLayout.setVisibility(View.GONE);
    }

    protected void CheckEnable() {
        if (currPageCounter + 1 == allPagesCount) {
            btn_next.setEnabled(false);
        } else if (currPageCounter == 0) {
            btn_prev.setEnabled(false);
        } else {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (!DataHolder.SHOW_MEANING) {
            Utility.showDetailDialog(this, currPageWordsList.get(position));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        if (isChecked) {
            DataHolder.SHOW_MEANING = true;
        } else {
            DataHolder.SHOW_MEANING = false;
        }
        Utility.updateListview(getApplicationContext(), currPageWordsList);
        DataHolder.adapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
        DataHolder.adapter.filter(text);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // Success, create the t1 instance
                // Toast.makeText(this,"All cool", Toast.LENGTH_SHORT).show();
                if (DataHolder.tts == null) {
                    DataHolder.tts = new TextToSpeech(this, this);
                }

            } else {
                // TextToSpeech data is missing, install it
                Toast.makeText(this, "Need to install Text-to-Speech engine.", Toast.LENGTH_SHORT).show();
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            //Toast.makeText(this,"Text-To-Speech engine is Ready", Toast.LENGTH_SHORT).show();
            //Set language
            DataHolder.tts.setLanguage(Locale.US);
            // Set listener to know when a particular utterance is done playing
            int result = DataHolder.tts.setOnUtteranceCompletedListener(this);

        } else if (status == TextToSpeech.ERROR) {
            Toast.makeText(this, "Error initializing Text-To-Speech engine", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (DataHolder.tts != null) {
            DataHolder.tts.stop();
        }
    }

    /*speak() calls are asynchronous, so they will return well before the text is done
     * being synthesized and played by Android, regardless of the use of QUEUE_FLUSH or QUEUE_ADD.
     * But you might need to know when a particular utterance is done playing. We also need to make
     * sure our activity implements the TextToSpeech.OnUtteranceCompletedListener interface
     */
    @Override
    public void onUtteranceCompleted(String uttId) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //Utility.showToast(getApplicationContext(), "Did You Touch?");
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //Utility.showToast(getApplicationContext(), "Did You Touch?");
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}

