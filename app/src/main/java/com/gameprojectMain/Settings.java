package com.gameprojectMain;

import android.util.Log;

import com.gameproject.framework.Audio;
import com.gameproject.framework.FileIO;
import com.gameproject.framework.Music;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class Settings {

    public static boolean soundEnabled = false;
    public static int savedScore = 0;
    public static Items savedItems[];

    public static boolean firstTime = true;
    public static boolean gameOver = false;

    private static String check;
    public static ArrayList<Music> musicList = new ArrayList<>();
    private static int count = 0;

    public static void load(FileIO files){

        BufferedReader in = null;

        try{
            in = new BufferedReader(new InputStreamReader(files.readFile("data.island")));
            savedItems = new Items[7];
            soundEnabled = Boolean.parseBoolean(in.readLine());
            savedScore = Integer.parseInt(in.readLine());
            check = in.readLine();
            if(check != null || check.equals("0")) {
                firstTime = false;
                String temp = check;
                for (int i = 0; i < savedItems.length; i++) {
                    int num = Integer.parseInt(temp);
                    savedItems[i] = new Items(num, i);
                    temp = in.readLine();
                }
            }
        }catch (IOException e) {
            // Use default location
        }catch (NumberFormatException e) {
            // Use default
        }catch (Exception e) {
            Log.d("Error", "load: " + e.toString());
        }finally {
            try{
                if(in != null)
                    in.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void save(FileIO files) {

        BufferedWriter out = null;

        try{
            out = new BufferedWriter(new OutputStreamWriter(files.writeFile("data.island")));
            out.write(Boolean.toString(soundEnabled));
            out.newLine();
            out.write(Integer.toString(savedScore));
            out.newLine();
            if(savedItems != null) {
                for (Items i : savedItems) {
                    out.write(Integer.toString(i.quantity));
                    out.newLine();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (NullPointerException npe) {
            Log.d("Notices", "save: savedItems is null.");
        }
        finally {
            try{
                if(out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveData(Items items[]) {

            savedItems = items;
    }

    public static void addScore(int score) {

        if(savedScore < score)
            savedScore = score;
    }

    public static void initialMusic() {

        musicList.add(Assets.track1);
        musicList.add(Assets.track2);
        musicList.add(Assets.track3);
        musicList.add(Assets.track4);

        for(Music m : musicList) {
            m.setVolume(1);
        }
    }

    public static void playMusic() {

        if(!soundEnabled) {

            if(musicList.get(0).isPlaying())
                musicList.get(0).pause();

            if(musicList.get(1).isPlaying()) {
                musicList.get(1).pause();
            }

            if(musicList.get(2).isPlaying()) {
                musicList.get(2).pause();
            }

            if(musicList.get(3).isPlaying()) {
                musicList.get(3).isPlaying();
            }
        }
        else {
            musicList.get(count).play();

            if(musicList.get(count).isStopped())
                musicList.get(count++).play();

            if(count >= musicList.size()) {
                count = 0;
                musicList.get(count).play();
            }

        }
    }
}
