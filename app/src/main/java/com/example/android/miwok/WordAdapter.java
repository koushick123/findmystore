package com.example.android.miwok;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import com.example.android.miwok.R;
import com.example.android.miwok.Word;

import java.util.ArrayList;

/**
 * Created by Koushick on 08-08-2016.
 */

public class WordAdapter extends ArrayAdapter {

    boolean play=false;
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

        final Word currentWord = (Word)getItem(position);

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
        final ImageView playimageView = (ImageView) listItemView.findViewById(R.id.play_pause);
        playimageView.setScaleType(ImageView.ScaleType.CENTER);
        if(currentWord.getWordType() == R.color.category_phrases)
        {
            params.setMargins((int)getContext().getResources().getDimension(R.dimen.play_image_left_margin),0,0,0);
        }
        playimageView.setLayoutParams(params);
        final MediaPlayer mediaPlayer = MediaPlayer.create(getContext(),currentWord.getSong());
        playimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Play == ",play+"");
                if(!play)
                {
                    playimageView.setImageResource(R.drawable.ic_pause_black_24dp);
                    Log.d("State == ",mediaPlayer.isPlaying()+"");
                    Log.d("loop State == ",mediaPlayer.isLooping()+"");
                    mediaPlayer.start();
                    play = true;
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer)
                        {
                            playimageView.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            play = false;
                        }
                    });
                }
                else
                {
                    playimageView.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    Log.d("State 22 == ",mediaPlayer.isPlaying()+"");
                    Log.d("loop State 22== ",mediaPlayer.isLooping()+"");
                    mediaPlayer.pause();
                    play = false;
                }
            }
        });

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
