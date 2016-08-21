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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioFocusListener listener;
    final int MAX_VOLUME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> phrases = new ArrayList<Word>();
        phrases.add(new Word("Where are you going?","minto wuksus",R.color.category_phrases,R.raw.phrase_where_are_you_going));
        phrases.add(new Word("What is your name?","tinnә oyaase'nә",R.color.category_phrases,R.raw.phrase_what_is_your_name));
        phrases.add(new Word("My name is...","oyaaset...",R.color.category_phrases,R.raw.phrase_my_name_is));
        phrases.add(new Word("How are you feeling?","michәksәs?",R.color.category_phrases,R.raw.phrase_how_are_you_feeling));
        phrases.add(new Word("I’m feeling good.","kuchi achit",R.color.category_phrases,R.raw.phrase_im_feeling_good));
        phrases.add(new Word("Are you coming?r","әәnәs'aa?",R.color.category_phrases,R.raw.phrase_are_you_coming));
        phrases.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.color.category_phrases,R.raw.phrase_yes_im_coming));
        phrases.add(new Word("I’m coming.","әәnәm",R.color.category_phrases,R.raw.phrase_im_coming));
        phrases.add(new Word("Let’s go.","yoowutis",R.color.category_phrases,R.raw.phrase_lets_go));
        phrases.add(new Word("Come here.","әnni'nem",R.color.category_phrases,R.raw.phrase_come_here));

        ListView listView = (ListView)findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(this,phrases);
        listView.setAdapter(wordAdapter);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                mediaPlayer = MediaPlayer.create(getApplicationContext(),phrases.get(position).getSong());
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
    }

    private float adjustVolume(int currentVol)
    {
        return (float) (1 - (Math.log(MAX_VOLUME - currentVol) / Math.log(MAX_VOLUME)));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(""+NumbersActivity.class,"Stopping..."+listener+", "+audioManager);
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
