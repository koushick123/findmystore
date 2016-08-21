package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

/**
 * Created by Koushick on 20-08-2016.
 */

public class AudioFocusListener implements AudioManager.OnAudioFocusChangeListener {

    MediaPlayer mediaPlayer;
    final int MAX_VOLUME = 200;
    int currVolume=10;
    AudioManager audioManager;

    public AudioFocusListener(MediaPlayer mPlayer,AudioManager audioM) {
        mediaPlayer = mPlayer;
        audioManager = audioM;
    }

    @Override
    public void onAudioFocusChange(int focus) {

        if(focus == AudioManager.AUDIOFOCUS_GAIN)
        {
            Log.d(""+AudioFocusListener.class,"Gained it back..");
            mediaPlayer.start();
            mediaPlayer.setVolume(adjustVolume(currVolume), adjustVolume(currVolume));
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    releaseMediaPlayer();
                }
            });
        }
        else if(focus == AudioManager.AUDIOFOCUS_LOSS || focus == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
        {
            Log.d(""+AudioFocusListener.class,"Losing focus...");
            releaseMediaPlayer();
            audioManager.abandonAudioFocus(this);
        }
        else if(focus == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
        {
            currVolume+=currVolume*.2;
            mediaPlayer.setVolume(adjustVolume(currVolume), adjustVolume(currVolume));
        }
    }

    private void releaseMediaPlayer()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private float adjustVolume(int currentVol)
    {
        return (float) (1 - (Math.log(MAX_VOLUME - currentVol) / Math.log(MAX_VOLUME)));
    }
}