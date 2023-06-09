package com.gameproject.framework.impl;

import com.gameproject.framework.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import androidx.preference.PreferenceManager;

public class GameFileIO implements FileIO {

    Context context;
    AssetManager assets;
    String externalStoragePath;

    public GameFileIO(Context context){

        this.context = context;
        this.assets = context.getAssets();
        this.externalStoragePath = context.getExternalFilesDir(null).getAbsolutePath() + File.separator;
    }

    public InputStream readAsset(String fileName) throws IOException {

        return assets.open(fileName);
    }

    public InputStream readFile(String fileName) throws IOException {

        return new FileInputStream(externalStoragePath + fileName);
    }


    public OutputStream writeFile(String fileName) throws IOException {

        return new FileOutputStream(externalStoragePath + fileName);
    }

    public SharedPreferences getPreferences() {

        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
