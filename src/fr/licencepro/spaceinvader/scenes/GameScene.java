package fr.licencepro.spaceinvader.scenes;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import android.util.Log;
import fr.licencepro.spaceinvader.Config;
import fr.licencepro.spaceinvader.GameState;
import fr.licencepro.spaceinvader.MainActivity;
import fr.licencepro.spaceinvader.audio.AudioManager;
import fr.licencepro.spaceinvader.data.DataManager;
import fr.licencepro.spaceinvader.object.AI;
import fr.licencepro.spaceinvader.object.AIGroup;
import fr.licencepro.spaceinvader.object.Bullet;
import fr.licencepro.spaceinvader.object.Direction;
import fr.licencepro.spaceinvader.object.Player;
import fr.licencepro.spaceinvader.object.Weapon;

public class GameScene extends BaseScene
{
	
	private Player joueur;

	//private TextureRegion regionImage;
	private AIGroup aiGroup;
	private ArrayList<Bullet> bullets;
	
	private GameState gameState;
	
	private Sprite spriteGagne;
	private Sprite spritePerdu;
	
	private Music music;
	
	private float timer = 4;
	
	private ButtonSprite buttonPause;
	private ButtonSprite buttonResume;
	private ButtonSprite buttonMenu;
	
	private ArrayList<Sprite> timerSprites;
	
	public Rectangle transparentBackground;
	
	public Sprite pause;
	
	public GameScene(){
		super();
	}

	
	
	@Override
	public void createScene() {
		final BaseGameActivity activity = MainActivity.getInstance();
		
    	activity.getEngine().registerUpdateHandler(new FPSLogger());
    	
    	gameState = GameState.TIMER;
    	
    	createBackground();
    	
    	
    	
    	//-----------------------TIMER------------------------------------------------------
    	registerUpdateHandler(new TimerHandler(0.1f, true, new ITimerCallback(){

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				int oldSecond = (int)timer + 1;
				timer -= pTimerHandler.getTimerSeconds();
				int second = (int)timer + 1;
				// float scale = timer - second + 1;
				float scale = second - timer;
				Log.wtf("TIMER", "TIMER " + "| " + second + " | " + oldSecond +" | "+timer + " | "+scale);
				
				if(timer <= 0){
					detachChild(timerSprites.get(second-1));
					gameState = GameState.RUNNING;
					Log.wtf("TIMER", "TIMER STOP... HAMER TIME");
					unregisterUpdateHandler(pTimerHandler);
				}
				else{
					if(oldSecond > second && oldSecond <= timerSprites.size() )
					{
						detachChild(timerSprites.get(oldSecond-1));
					}
					if(!timerSprites.get(second-1).hasParent())
					{
						attachChild(timerSprites.get(second-1));
					}
					timerSprites.get(second-1);
					
					timerSprites.get(second-1);
					
					timerSprites.get(second-1).setScale(scale);
					timerSprites.get(second-1).setX( Config.CAMERA_WIDTH/2 - timerSprites.get(second-1).getWidth()/2);
					timerSprites.get(second-1).setY( Config.CAMERA_HEIGHT/2 - timerSprites.get(second-1).getHeight()/2);
				}
			}
    		
    	}));
    	//-----------------------------------------------------------------------------------
    	
        registerUpdateHandler(new TimerHandler(1f/Config.FPS, true, new ITimerCallback() {
        	
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	//scene.attachChild(joueur.getSprite());
            	switch(gameState){
            	case RUNNING://-------------------------------RUNNING---------------------------------
                	joueur.update();
                	ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
                	ArrayList<AI> aiToRemove = new ArrayList<AI>();
                	
                	for(Bullet b : bullets){
                		b.update();
                		if(!b.getSprite().hasParent())
                		{
                			attachChild(b.getSprite());
                		}
                		
            			if(b.getY() < Config.MIN_GAME_AREA_Y || b.getY() > Config.MAX_GAME_AREA_Y)
            			{
            				bulletsToRemove.add(b);
            				detachChild(b.getSprite());
            				b.getSprite().dispose();
            			}
                		
                		for(AI ai : aiGroup)
                		{
                			if(ai.getSprite().collidesWith(b.getSprite()))
                			{
                				ai.updateHealth(-b.getPower());
                				bulletsToRemove.add(b);
                				detachChild(b.getSprite());
                				b.getSprite();
                				
            					updateSprite(ai);
            					attachChild(ai.getSprite());
            					
                				if(ai.isDestroyed())
                				{
                					aiToRemove.add(ai);
                					detachChild(ai.getSprite());
                				}
                			}			
                		}
                		if(joueur.getSprite().collidesWith(b.getSprite()) && !joueur.isDestroyed() && !(b.getParent() == joueur) )
                		{
                			joueur.updateHealth(-b.getPower());
                			Log.d("destroyed", "Joueur touché " + joueur.getHealth());
                			if(joueur.isDestroyed()){
                				detachChild(joueur.getSprite());
                				joueur.getSprite().dispose();
                				Log.d("destroyed", "Joueur destroyed " + joueur.isDestroyed());
                			}
                			else{
                				updateSprite(joueur);
                				attachChild(joueur.getSprite());
                			}
            				bulletsToRemove.add(b);
            				detachChild(b.getSprite());
            				b.getSprite().dispose();
                		}
                		
                	}
                	for(AI ai : aiGroup){
    	    			if(ai.isFiring()){
    			        	Bullet aib = ai.fire(Direction.DOWN);
    			        	bullets.add(aib);
    		        		Sprite sprite = new Sprite(aib.getX(), aib.getY(), aib.getTexture(), activity.getVertexBufferObjectManager());
    		        		aib.setSprite(sprite);
        			}
        			}
                	
                	if(joueur.isDestroyed())
                	{
                		gameState = GameState.LOSE;
                	}
                	else if(aiGroup.isAllDestroyed())
                	{
                		gameState = GameState.WIN;
                	}
                	
                	aiGroup.removeAll(aiToRemove);
                	bullets.removeAll(bulletsToRemove);
            	
            	break;
            	//------------------------------------------------------------------------------
            	case LOSE: //----------------------LOSE-----------------------------------------
            		if(!spritePerdu.hasParent())
            		{
            			attachChild(spritePerdu);
            		}
            		break;
            	case WIN: //-------------------------------WIN----------------------------------
            		if(!spriteGagne.hasParent())
            		{
            			attachChild(spriteGagne);
            		}
            		break;
				default:
					break;
            	}	

            }
            }));
        
        
        registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	if(gameState == GameState.RUNNING){
            		aiGroup.doAI();
            	}
            	Log.wtf("lol","BOUCLE 2 AI");
            }
            })); 
        
        

    	for(AI i : aiGroup)
    	{
    		attachChild(i.getSprite());
    	}
    	
    	createControllers();
    	attachChild(joueur.getSprite());
    	attachChild(buttonPause);
	}
	
	
    private void createControllers()
	{
	
    	final BaseGameActivity activity = MainActivity.getInstance();
		final BitmapTextureAtlas textureBarre = new BitmapTextureAtlas(activity.getTextureManager(), 512, 128);
		final BitmapTextureAtlas textureLeft = new BitmapTextureAtlas(activity.getTextureManager(), 128, 128);
		final BitmapTextureAtlas textureRight = new BitmapTextureAtlas(activity.getTextureManager(), 128, 128);
		final BitmapTextureAtlas textureFire = new BitmapTextureAtlas(activity.getTextureManager(), 128, 128);
		
		//Barre du bas
		TextureRegion ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureBarre, activity, "images/barre.png", 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureBarre);
		final Sprite barre = new Sprite(0, Config.CAMERA_HEIGHT - 90, ri, activity.getVertexBufferObjectManager());
		attachChild(barre);
		
		//Bouton gauche
		ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureLeft, activity, "images/direction_button.png", 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureLeft);
		final Sprite left = new Sprite(20, Config.CAMERA_HEIGHT - 85 , ri, activity.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if(touchEvent.isActionDown())
	            {
	                joueur.setMovingX(-1);
	            }
	            else if(touchEvent.isActionUp()  || (touchEvent.isActionMove() && touchEvent.isActionOutside()))
	            {
	            	joueur.stopMoving();
	            }
	            return true;
	        };
	    };
	    
		
		//Bouton droit
		ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureRight, activity, "images/direction_button.png", 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureRight);
		final Sprite right = new Sprite(110 , Config.CAMERA_HEIGHT - 85 , ri, activity.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if(touchEvent.isActionDown())
	            {
	            	joueur.setMovingX(1);
	            }
	            else if(touchEvent.isActionUp() || (touchEvent.isActionMove() && touchEvent.isActionOutside()) )
	            {
	            	joueur.stopMoving();
	            }
	            return true;
	        };
	    };
	    right.setRotation(180);
	    
	    //Bouton fire
		ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureFire, activity, "images/fire_button.png", 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureFire);
		final Sprite fire = new Sprite(Config.CAMERA_WIDTH - 105, Config.CAMERA_HEIGHT - 85 , ri, activity.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	        	if(!joueur.isDestroyed())
	        	{
		        	 if(touchEvent.isActionDown()){
			        	Bullet b = joueur.fire(Direction.UP);
			        	bullets.add(b);
		        		Sprite sprite = new Sprite(b.getX(), b.getY(), b.getTexture(), getVertexBufferObjectManager());
		        		b.setSprite(sprite);
		        		b.getSprite().setRotation(180);
		        	}
	        	}
	        	return true;
	        };
	    };

	    registerTouchArea(left);
	    registerTouchArea(right);
	    registerTouchArea(fire);
	    attachChild(left);
	    attachChild(right);
	    attachChild(fire);
	}

	private void createBackground()
	{
		final BaseGameActivity activity = MainActivity.getInstance();
		final BitmapTextureAtlas textureFond = new BitmapTextureAtlas(activity.getTextureManager(), 1024, 1024);
		TextureRegion region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureFond, activity, "images/en_jeu.png", 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureFond);
		Sprite sprite = new Sprite(-30, 0, region, activity.getVertexBufferObjectManager());
		attachChild(sprite);
	}
	
	/**
	 * Met à jour le sprite d'une IA.
	 * @param ai
	 */
	private void updateSprite(AI ai){
		final BaseGameActivity activity = MainActivity.getInstance();
		
		if(ai.getSprite() != null && ai.getSprite().hasParent()){
			detachChild(ai.getSprite());
		}
		
		String pathSprite = "images/ennemi2.png";
		if(ai.getHealth() == 1){
			pathSprite = "images/ennemi1.png";
		}
		else if(ai.getHealth() == 2){
			pathSprite = "images/ennemi3.png";
		}
		
		final BitmapTextureAtlas textureImageAI = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64);
		TextureRegion ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureImageAI, activity , pathSprite, 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureImageAI);
		final Sprite sprite = new Sprite(ai.getX(), ai.getY(), ri, activity.getVertexBufferObjectManager());
		ai.setSprite(sprite);
	}
	
	
	/**
	 * Met à jour le sprite du joueur.
	 * @param ai
	 */
	private void updateSprite(Player joueur){
		final BaseGameActivity activity = MainActivity.getInstance();
		
		AudioManager.playLoop();
		
		if(joueur.getSprite() != null && joueur.getSprite().hasParent()){
			detachChild(joueur.getSprite());
		}
		
		String pathSprite = "images/ships/vaisseau2.png";
		if(joueur.getHealth() == 1){
			pathSprite = "images/ships/vaisseau1.png";
		}
		else if(joueur.getHealth() == 2){
			pathSprite = "images/ships/vaisseau3.png";
		}
		
		final BitmapTextureAtlas textureImageAI = new BitmapTextureAtlas(activity.getEngine().getTextureManager(), 64, 64);
		TextureRegion ri = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureImageAI, activity, pathSprite, 0, 0);
		activity.getEngine().getTextureManager().loadTexture(textureImageAI);
		final Sprite sprite = new Sprite(joueur.getX(), joueur.getY(), ri, activity.getEngine().getVertexBufferObjectManager());
		joueur.setSprite(sprite);
	}



	@Override
	public void createRessources() {
		final BaseGameActivity activity = MainActivity.getInstance();
		
		AudioManager.loadMusic("musics/music.ogg");
		
    	bullets = new ArrayList<Bullet>();
    	//image vaisseau
    	final BitmapTextureAtlas textureWeapon = new BitmapTextureAtlas(activity.getTextureManager(), 16, 16);
    	final BitmapTextureAtlas textureWeaponAI = new BitmapTextureAtlas(activity.getTextureManager(), 16, 16);
    	final BitmapTextureAtlas texturePerdu = new BitmapTextureAtlas(activity.getTextureManager(), 512, 1024);
    	final BitmapTextureAtlas textureGagne = new BitmapTextureAtlas(activity.getTextureManager(), 512, 1024);
    	final BitmapTextureAtlas texturePauseBtn = new BitmapTextureAtlas(activity.getTextureManager(), 64, 64);
    	final BitmapTextureAtlas texturePause = new BitmapTextureAtlas(activity.getTextureManager(), 512, 1024);
    	final BitmapTextureAtlas textureResume = new BitmapTextureAtlas(activity.getTextureManager(), 128, 64);
    	final BitmapTextureAtlas textureMenu = new BitmapTextureAtlas(activity.getTextureManager(), 128, 64);
    	
    	TextureRegion regionWeapon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureWeapon, activity, "images/bullet1.png", 0, 0);
    	TextureRegion regionWeaponAI = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureWeaponAI, activity, "images/bullet2.png", 0, 0);
    	TextureRegion regionGagne = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureGagne, activity, "images/gagne.png", 0, 0);
    	TextureRegion regionPerdu = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturePerdu, activity, "images/perdu.png", 0, 0);
    	TextureRegion regionPauseBtn = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturePauseBtn, activity, "images/btn_pause.png", 0, 0);
    	TextureRegion regionPause = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texturePause, activity, "images/pause.png", 0, 0);
    	TextureRegion regionResume = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureResume, activity, "images/resume.png", 0, 0);
    	TextureRegion regionMenu = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureMenu, activity, "images/back_to_menu.png", 0, 0);
    	
    	Weapon weapon = new Weapon(1, 5, regionWeapon);
    	joueur = new Player(215, Config.CAMERA_HEIGHT - 150 , 3, 3 ,  Direction.UP, 1f);
    	joueur.setWeapon(weapon);
    	
    	activity.getEngine().getTextureManager().loadTexture(textureWeapon);
    	activity.getEngine().getTextureManager().loadTexture(textureWeaponAI);
    	activity.getEngine().getTextureManager().loadTexture(textureGagne);
    	activity.getEngine().getTextureManager().loadTexture(texturePerdu);
    	activity.getEngine().getTextureManager().loadTexture(texturePauseBtn);
    	activity.getEngine().getTextureManager().loadTexture(texturePause);
    	activity.getEngine().getTextureManager().loadTexture(textureResume);
    	activity.getEngine().getTextureManager().loadTexture(textureMenu);
    	
    	spriteGagne = new Sprite(0,0,regionGagne, activity.getVertexBufferObjectManager());
    	spritePerdu = new Sprite(0,0,regionPerdu, activity.getVertexBufferObjectManager());
    	aiGroup = DataManager.generateAIs("test",false, activity.getResources());
    	buttonPause = new ButtonSprite(20, 20, regionPauseBtn, activity.getVertexBufferObjectManager());
    	pause = new Sprite(0, 0, regionPause, activity.getVertexBufferObjectManager());
    	buttonResume = new ButtonSprite(0, 400, regionResume, activity.getVertexBufferObjectManager());
    	buttonMenu = new ButtonSprite(0, 460, regionMenu, activity.getVertexBufferObjectManager());
    	
    	buttonResume.setX(Config.CAMERA_WIDTH/2 - buttonResume.getWidth()/2);
    	buttonMenu.setX(Config.CAMERA_WIDTH/2 - buttonMenu.getWidth()/2);
    	
    	buttonResume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				resume();
				
			}
		});
    	
    	buttonMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				MainActivity.getInstance().setScene(ScenesId.MENU_SCENE);
			}
		});
    	
    	buttonPause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				pause();
			}
		});
    	
    	for(AI i : aiGroup){
    		updateSprite(i);
    		Weapon weaponAI = new Weapon(1, 2, regionWeaponAI);
    		i.setWeapon(weaponAI);
    	}
    	
    	updateSprite(joueur);
    	
    	
    	timerSprites = new ArrayList<Sprite>();
    	for(int i=0; i<4; i++)
    	{
    		BitmapTextureAtlas bitmap = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256);
    		TextureRegion texture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmap, activity, "images/timer/"+i+".png", 0, 0);
    		MainActivity.getInstance().getEngine().getTextureManager().loadTexture(bitmap);
    		Sprite sprite = new Sprite(0, 0, texture, MainActivity.getInstance().getVertexBufferObjectManager());
    		timerSprites.add(sprite);
    	}
    	
    	transparentBackground = new Rectangle(0, 0, 480, 720, activity.getVertexBufferObjectManager());
    	transparentBackground.setColor(Color.BLACK);
    	transparentBackground.setAlpha(0.5f);
    	registerTouchArea(buttonPause);
    	registerTouchArea(buttonResume);
    	registerTouchArea(buttonMenu);
    	
	}
	
	/**
	 * Pause the game
	 */
	public void pause(){
		if(gameState == GameState.RUNNING){
			gameState = GameState.PAUSE;
			/*
			if(AudioManager.isMusicLoaded()){
				AudioManager.pause();
			}
			*/
			
			attachChild(transparentBackground);
			attachChild(pause);
			attachChild(buttonResume);
			attachChild(buttonMenu);
		}
	}
	
	/**
	 * Resume the game
	 */
	private void resume(){
		if(gameState == GameState.PAUSE)
		{
			gameState = GameState.RUNNING;
			if(AudioManager.isMusicLoaded()){
				AudioManager.resume();
			}
			
			detachChild(transparentBackground);
			detachChild(pause);
			detachChild(buttonResume);
			detachChild(buttonMenu);
		}
	}
}
