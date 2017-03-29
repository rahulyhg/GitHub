package com.edge.greasy;

import android.view.View;

import com.edge.greasy.utility.DataHolder;
import com.edge.greasy.utility.Utility;

public class AllWordsActivity extends AbstractActivity{

	@Override
	protected String getCategory() {
		return "All";
	}

	@Override
	protected void displayData() {

		allWordsList = DataHolder.getWordsList(getCategory());
		allWordsCount = allWordsList.size();

        btn_prev.setEnabled(false);
        btn_next.setEnabled(false);

		btn_prev.setVisibility(View.GONE);
		btn_next.setVisibility(View.GONE);

        currPageWordsList = allWordsList;
        currPageWordsCount = allWordsCount;

        allPagesCount = 1;

		currPageCounter = 0;
		title.setText("All Words");

		if (allWordsCount == 0) {
			Utility.showToast(getApplicationContext(), "No words");
		}else{
			Utility.updateListview(getApplicationContext(), currPageWordsList);
			DataHolder.listView.setOnItemClickListener(this);

			getIndexList(currPageWordsList);
			displayIndex();
		}
	}
}