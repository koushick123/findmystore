package com.example.android.miwok;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;

import java.util.ArrayList;

/**
 * Created by Koushick on 08-08-2016.
 */

public class WordAdapter extends ArrayAdapter {

    public WordAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate((R.layout.list_item),parent,false);
        }

        Word currentWord = (Word)getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.text1);
        TextView textView2 = (TextView) listItemView.findViewById(R.id.text2);
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.img);
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.row);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)getContext().getResources().getDimension(R.dimen.list_item_height));
        linearLayout.setLayoutParams(params);
        linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(),currentWord.getWordType()));
        textView2.setText(currentWord.getEnglishWord());
        textView.setText(currentWord.getMiwokWord());
        textView.setTextAppearance(R.style.CategoryStyle);
        textView2.setTextColor(ContextCompat.getColor(getContext(),R.color.white_text_color));
        ImageView playimageView = (ImageView) listItemView.findViewById(R.id.play_pause);
        playimageView.setScaleType(ImageView.ScaleType.CENTER);
        if(currentWord.getWordType() == R.color.category_phrases)
        {
            params.setMargins((int)getContext().getResources().getDimension(R.dimen.play_image_left_margin),0,0,0);
        }
        playimageView.setLayoutParams(params);

        if(currentWord.getImageResourceId() > -1)
        {
            imageView.setImageResource(currentWord.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
            imageView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.tan_background));
        }
        else
        {
            imageView.setVisibility(View.INVISIBLE);
        }

        return listItemView;
    }
}
