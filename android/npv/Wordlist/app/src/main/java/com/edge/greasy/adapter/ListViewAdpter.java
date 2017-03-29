package com.edge.greasy.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edge.greasy.R;
import com.edge.greasy.dbo.Word;
import com.edge.greasy.utility.Constants;
import com.edge.greasy.utility.DataHolder;
import com.edge.greasy.utility.Utility;

public class ListViewAdpter extends BaseAdapter {

	private LayoutInflater inflater;
	private Context context;
	private ViewHolder holder;
	private List<Word> list;
	private String text;
	private ArrayList<Word> arraylist;

	public ListViewAdpter(Context c, List<Word> contactPojo) {

		context = c;
		list = contactPojo;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Word>();
		this.arraylist.addAll(list);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View contentView, ViewGroup parent) {

		Word pojo = list.get(position);

		holder = new ViewHolder();
		if (contentView == null) {

			//			inflater = (LayoutInflater) context
			//					.getSystemService(context.LAYOUT_INFLATER_SERVICE);

			contentView = inflater.inflate(R.layout.row_layout, null);

			holder.txt_word = (TextView) contentView
					.findViewById(R.id.txt_word);

			holder.txt_category = (TextView) contentView
					.findViewById(R.id.txt_category);

			holder.txt_meaning = (TextView) contentView
					.findViewById(R.id.txt_meaning);

			holder.txt_sentence = (TextView) contentView
					.findViewById(R.id.txt_sentence);

			holder.txt_marathimeaning = (TextView) contentView
					.findViewById(R.id.txt_marathimeaning);


			if (DataHolder.SHOW_MEANING) {
				holder.txt_word.setTypeface(null, Typeface.BOLD);
				holder.txt_meaning.setVisibility(View.VISIBLE);
				holder.txt_sentence.setVisibility(View.VISIBLE);
				holder.txt_marathimeaning.setVisibility(View.VISIBLE);
			} else {
				holder.txt_word.setTypeface(null, Typeface.NORMAL);
				holder.txt_meaning.setVisibility(View.GONE);
				holder.txt_sentence.setVisibility(View.GONE);
				holder.txt_marathimeaning.setVisibility(View.GONE);
			}

			contentView.setTag(holder);
		}

		else {

			holder = (ViewHolder) contentView.getTag();
		}

		if (pojo != null) {

			//holder.txt_word.setText(pojo.get_word() + " ( " + pojo.get_id() + ") - (" + position + ")");
			holder.txt_word.setText(pojo.get_word());
			holder.txt_meaning.setText(pojo.get_meaning());
			holder.txt_category.setText(pojo.get_category());

			if(DataHolder.SHOW_MEANING){
				if (pojo.get_sentence() != null) {
					holder.txt_sentence.setText(pojo.get_sentence());
					holder.txt_sentence.setVisibility(View.VISIBLE);
				} else {
					holder.txt_sentence.setVisibility(View.GONE);
				}

				if (pojo.get_marathimeaning() != null || pojo.get_hindiMeaning() != null) {
					text = "<font color=#16a085>" + pojo.get_marathimeaning() + "</font><font color=#000> / </font>" + "<font color=#e67e22>" + pojo.get_hindiMeaning() + "</font>";
					holder.txt_marathimeaning.setText(Html.fromHtml(text));
					holder.txt_marathimeaning.setVisibility(View.VISIBLE);
				} else {
					holder.txt_marathimeaning.setVisibility(View.GONE);
				}
			}

			if (pojo.get_category().equalsIgnoreCase("Hard")) {
				holder.txt_category
				.setBackgroundResource(R.drawable.round_corner_orange);
			} else if (pojo.get_category().equalsIgnoreCase("Easy")) {
				holder.txt_category
				.setBackgroundResource(R.drawable.round_corner_blue);
			} else if (pojo.get_category().equalsIgnoreCase("Skip")) {
				holder.txt_category
				.setBackgroundResource(R.drawable.round_corner_red);
			}
		}

		final Word newObj = list.get(position);
        TextView readWordTextView = (TextView) contentView.findViewById(R.id.readWordTextView);
        readWordTextView.setOnClickListener(
            new OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Utility.readPage(context, list, position);
                      //System.out.print(newObj.toRead());
                      //DataHolder.tts.speak(newObj.toRead(), TextToSpeech.QUEUE_ADD, null);
                  }
              }
        );

		holder.txt_category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int Id = newObj.get_id();
				//System.out.println(" old value - " + newObj.get_category());

				if (newObj.get_category().equalsIgnoreCase("Hard")) {
                    Utility.updateCategoryOfWord("Easy", Id);
					newObj.set_category("Easy");
					v.setBackgroundResource(0);
					v.setBackgroundResource(R.drawable.round_corner_blue);
				} else if (newObj.get_category().equalsIgnoreCase("Easy")) {
                    Utility.updateCategoryOfWord("Skip", Id);
                    newObj.set_category("Skip");
                    v.setBackgroundResource(0);
                    v.setBackgroundResource(R.drawable.round_corner_red);
				} else if (newObj.get_category().equalsIgnoreCase("Skip")) {
                    Utility.updateCategoryOfWord("Hard", Id);
					newObj.set_category("Hard");
					v.setBackgroundResource(0);
					v.setBackgroundResource(R.drawable.round_corner_orange);
				}

				notifyDataSetChanged();
				DataHolder.listView.invalidate();
			}
		});

		return contentView;
	}

	public class ViewHolder {

		TextView txt_word, txt_meaning, txt_sentence, txt_marathimeaning;
		TextView txt_category;

	}

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		list.clear();
		if (charText.length() == 0) {
			list.addAll(arraylist);
		} else {
			for (Word wp : arraylist) {
				if (wp.get_word().toLowerCase(Locale.getDefault())
						.contains(charText)
					|| wp.get_meaning().toLowerCase(Locale.getDefault())
                        .contains(charText)
						) {
					list.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}

}
