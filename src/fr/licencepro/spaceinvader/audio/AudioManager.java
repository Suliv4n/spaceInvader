package fr.licencepro.spaceinvader.audio;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.ui.activity.BaseGameActivity;

import fr.licencepro.spaceinvader.MainActivity;

public class AudioManager {
	public static Music current;
	
	public static void loadMusic(String path)
	{
		BaseGameActivity activity = MainActivity.getInstance();
    	try {
			current = MusicFactory.createMusicFromAsset(activity.getEngine().getMusicManager(), activity,"musics/music.ogg");
    	} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void playLoop(){
		current.setLooping(true);
		current.play();
	}
	
	public static void pause(){
		current.pause();
	}
	
	public static void resume(){
		current.resume();
	}

	public static boolean isMusicLoaded() {
		return current != null;
	}
}
