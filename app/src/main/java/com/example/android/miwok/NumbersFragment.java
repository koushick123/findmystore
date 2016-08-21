/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersFragment extends Fragment{

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioFocusListener listener;
    final int MAX_VOLUME = 500;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.word_list,container, false);

        final ArrayList<Word> numbers = new ArrayList<Word>();
        numbers.add(new Word("one","lutti",R.drawable.number_one,R.color.category_numbers,R.raw.number_one));
        numbers.add(new Word("two","otiiko",R.drawable.number_two,R.color.category_numbers,R.raw.number_two));
        numbers.add(new Word("three","tolookosu",R.drawable.number_three,R.color.category_numbers,R.raw.number_three));
        numbers.add(new Word("four","oyyiisa",R.drawable.number_four,R.color.category_numbers,R.raw.number_four));
        numbers.add(new Word("five","massokka",R.drawable.number_five,R.color.category_numbers,R.raw.number_five));
        numbers.add(new Word("six","temmokka",R.drawable.number_six,R.color.category_numbers,R.raw.number_six));
        numbers.add(new Word("seven","kenekaku",R.drawable.number_seven,R.color.category_numbers,R.raw.number_seven));
        numbers.add(new Word("eight","kawinta",R.drawable.number_eight,R.color.category_numbers,R.raw.number_eight));
        numbers.add(new Word("nine","wo'e",R.drawable.number_nine,R.color.category_numbers,R.raw.number_nine));
        numbers.add(new Word("ten","na'aacha",R.drawable.number_ten,R.color.category_numbers,R.raw.number_ten));

        final ListView listView = (ListView)rootView.findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(getContext(),numbers);
        listView.setAdapter(wordAdapter);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                mediaPlayer = MediaPlayer.create(getContext(),numbers.get(position).getSong());
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
        Log.d(""+NumbersFragment.class,"Stopping..."+listener+", "+audioManager);
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