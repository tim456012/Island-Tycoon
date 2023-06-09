package com.gameproject.framework.impl;

import com.gameproject.framework.Audio;

import java.io.IOException;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import com.gameproject.framework.Audio;
import com.gameproject.framework.Music;
import com.gameproject.framework.Sound;

public class GameAudio implements Audio {

    AssetManager assets;
    SoundPool soundPool;

    public GameAudio(Activity activity) {

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(10,3,0);
    }

    public Music newMusic(String filename){

        try{
            AssetFileDescriptor assetFileDescriptor = assets.openFd(filename);
            return new GameMusic(assetFileDescriptor);
        }catch (IOException ioe){
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }

    public Sound newSound(String filename){

        try{
            AssetFileDescriptor assetFileDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetFileDescriptor, 0);
            return new GameSound(soundPool, soundId);
        }catch (IOException ioe){
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }
}
