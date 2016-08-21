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

public class FamilyFragment extends Fragment {

    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioFocusListener listener;
    final int MAX_VOLUME = 500;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.word_list,container, false);

        final ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("father", "әpә", R.drawable.family_father, R.color.category_family, R.raw.family_father));
        family.add(new Word("mother", "әṭa", R.drawable.family_mother, R.color.category_family, R.raw.family_mother));
        family.add(new Word("son", "angsi", R.drawable.family_son, R.color.category_family, R.raw.family_son));
        family.add(new Word("daughter", "tune", R.drawable.family_daughter, R.color.category_family, R.raw.family_daughter));
        family.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.color.category_family, R.raw.family_older_brother));
        family.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.color.category_family, R.raw.family_younger_brother));
        family.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.color.category_family, R.raw.family_older_sister));
        family.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.color.category_family, R.raw.family_younger_sister));
        family.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.color.category_family, R.raw.family_grandmother));
        family.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.color.category_family, R.raw.family_grandfather));

        ListView listView = (ListView) rootView.findViewById(R.id.list);
        WordAdapter wordAdapter = new WordAdapter(getContext(), family);
        listView.setAdapter(wordAdapter);
        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                mediaPlayer = MediaPlayer.create(getContext(),family.get(position).getSong());
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
        Log.d(""+FamilyFragment.class,"Stopping..."+listener+", "+audioManager);
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
