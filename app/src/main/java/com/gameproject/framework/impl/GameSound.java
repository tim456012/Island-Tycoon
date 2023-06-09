package com.gameproject.framework.impl;

import android.media.SoundPool;

import com.gameproject.framework.Sound;

public class GameSound implements Sound {

    int soundId;
    SoundPool soundPool;

    public GameSound(SoundPool soundPool, int soundId) {

        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume){

        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose(){

        soundPool.unload(soundId);
    }
}
