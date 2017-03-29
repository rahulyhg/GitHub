package com.edge.greasy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.edge.greasy.database.SqlLiteDbHelper;
import com.edge.greasy.dbo.Word;
import com.edge.greasy.utility.Constants;
import com.edge.greasy.utility.Utility;

public class EasyWordsActivity extends AbstractActivity {
    @Override
    protected String getCategory() {
        return "Easy";
    }
}