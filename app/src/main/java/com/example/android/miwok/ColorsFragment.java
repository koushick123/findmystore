package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Koushick on 21-08-2016.
 */

public class ColorsFragment extends Fragment {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioFocusListener listener;
    final int MAX_VOLUME = 500;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.word_list,container, false);

        final ArrayList<Word> colors = new ArrayList<Word>();
        colors.add(new Word("red","weṭeṭṭi",R.drawable.color_red,R.color.category_colors,R.raw.color_red));
        colors.add(new Word("green","chokokki",R.drawable.color_green,R.color.category_colors,R.raw.color_green));
        colors.add(new Word("brown","ṭakaakki",R.drawable.color_brown,R.color.category_colors,R.raw.color_brown));
        colors.add(new Word("gray","ṭopoppi",R.drawable.color_gray,R.color.category_colors,R.raw.color_gray));
        colors.add(new Word("black","kululli",R.drawable.color_black,R.color.category_colors,R.raw.color_black));
        colors.add(new Word("white","kelelli",R.drawable.color_white,R.color.category_colors,R.raw.color_white));
        colors.add(new Word("dusty yellow","ṭopiisә",R.drawable.color_dusty_yellow,R.color.category_colors,R.raw.color_dusty_yellow));
        colors.add(new Word("mustard yellow","chiwiiṭә",R.drawable.color_mustard_yellow,R.color.category_colors,R.raw.color_mustard_yellow));

        ListView listView = (ListView)rootView.findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(getContext(),colors);
        listView.setAdapter(wordAdapter);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                mediaPlayer = MediaPlayer.create(getContext(),colors.get(position).getSong());
                listener = new AudioFocusListener(mediaPlayer,audioManager);
                int result = audioManager.requestAudioFocus(listener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer.start();
                    int currVolume = 150;
                    mediaPlayer.setVolume(adjustVolume(currVolume), adjustVolume(currVolume));
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
        return rootView;
    }

    private float adjustVolume(int currentVol)
    {
        return (float) (1 - (Math.log(MAX_VOLUME - currentVol) / Math.log(MAX_VOLUME)));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(""+ColorsFragment.class,"Stopping..."+listener+", "+audioManager);
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
            if(audioManager != null && listener != null) {
                audioManager.abandonAudioFocus(listener);
            }
        }
    }
}
