package fr.licencepro.spaceinvader;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;
import android.os.Bundle;
import fr.licencepro.spaceinvader.audio.AudioManager;
import fr.licencepro.spaceinvader.scenes.GameScene;
import fr.licencepro.spaceinvader.scenes.MenuScene;
import fr.licencepro.spaceinvader.scenes.ScenesId;
import fr.licenpro.spaceinvader.ressources.RessourcesManager;

public class MainActivity extends BaseGameActivity {

	
	
	
	private static MainActivity INSTANCE;
	private Camera camera;
	private GameScene game;
	private MenuScene menu;
	
	private ScenesId currentScene;
	
    @Override
    public EngineOptions onCreateEngineOptions() {
        this.camera = new Camera(0,0, Config.CAMERA_WIDTH, Config.CAMERA_HEIGHT);
        EngineOptions options = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
        options.getTouchOptions().setNeedsMultiTouch(true);
        options.getAudioOptions().setNeedsMusic(true);
        return options;
    }
 
    @Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) {
    	
    	RessourcesManager.prepare(this);
    	
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
    	
    }
 


    @Override
    public Engine onCreateEngine(final EngineOptions pEngineOptions) {
        Engine e = new LimitedFPSEngine(pEngineOptions, Config.FPS);
        return e;
    }
    

	/*
	@Override
	public void onPause(){
		super.onPause();
		music.pause();
	}

	@Override
	public void onResume(){
		super.onResume();
		if(music != null && !music.isPlaying())
		{
			music.resume();
		}
	}
	 */


	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		game = new GameScene();
		menu = new MenuScene();
		
		//game.createRessources();
		//game.createScene();
		menu.createRessources();
		menu.createScene();
		
		this.getEngine().setScene(menu);
		
		pOnCreateSceneCallback.onCreateSceneFinished(menu);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		INSTANCE = this;
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		INSTANCE = this;

		if(AudioManager.isMusicLoaded())
		{
			AudioManager.pause();
		}
		
		if(currentScene == ScenesId.GAME_SCENE && game != null)
		{
			game.pause();
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		INSTANCE = this;

		if(AudioManager.isMusicLoaded())
		{
			AudioManager.resume();
		}
		
	}
	
	public static MainActivity getInstance(){
		return INSTANCE;
	}
	
	public void setScene(ScenesId id){
		if(id == ScenesId.GAME_SCENE){
			game = new GameScene();
			game.createRessources();
			game.createScene();
			this.getEngine().setScene(game);
		}
		else if(id == ScenesId.MENU_SCENE){
			menu = new MenuScene();
			menu.createRessources();
			menu.createScene();
			this.getEngine().setScene(menu);
		}
		
		currentScene = id;
	}

}
