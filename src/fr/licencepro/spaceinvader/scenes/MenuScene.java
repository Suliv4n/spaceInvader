package fr.licencepro.spaceinvader.scenes;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import fr.licencepro.spaceinvader.Config;
import fr.licencepro.spaceinvader.MainActivity;

public class MenuScene extends BaseScene{

	Sprite fond;
	Sprite titre;
	ButtonSprite score;
	ButtonSprite jouer;
	ButtonSprite quitter;
	
	public MenuScene(){
		super();
	}

	@Override
	public void createScene() {
		attachChild(fond);
		attachChild(jouer);
		attachChild(score);
		attachChild(quitter);
		attachChild(titre);
	}

	@Override
	public void createRessources() {
		BitmapTextureAtlas bmFond = new BitmapTextureAtlas(MainActivity.getInstance().getTextureManager(), 1024, 1024);
		TextureRegion trFond = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bmFond, MainActivity.getInstance(), "images/fond.png", 0, 0);
		MainActivity.getInstance().getTextureManager().loadTexture(bmFond);
		fond = new Sprite(0,0,trFond, MainActivity.getInstance().getVertexBufferObjectManager());
		
		
		BitmapTextureAtlas bmJouer = new BitmapTextureAtlas(MainActivity.getInstance().getTextureManager(), 128, 128);
		TextureRegion trJouer = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bmJouer, MainActivity.getInstance(), "images/play.png", 0, 0);
		MainActivity.getInstance().getTextureManager().loadTexture(bmJouer);
		jouer = new ButtonSprite(Config.CAMERA_WIDTH/2 , 500, trJouer, MainActivity.getInstance().getVertexBufferObjectManager()); 
		jouer.setX(Config.CAMERA_WIDTH/2 - jouer.getWidth()/2);
		this.registerTouchArea(jouer);
		jouer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
					float pTouchAreaLocalY) {
				MainActivity.getInstance().setScene(ScenesId.GAME_SCENE);
			}
		});
		
		
		
		BitmapTextureAtlas bmScore = new BitmapTextureAtlas(MainActivity.getInstance().getTextureManager(), 256, 128);
		TextureRegion  trScore = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bmScore, MainActivity.getInstance(), "images/score.png", 0, 0);
		score = new ButtonSprite(30 , 380, trScore, MainActivity.getInstance().getVertexBufferObjectManager()); 
		MainActivity.getInstance().getTextureManager().loadTexture(bmScore);
		
		
		BitmapTextureAtlas bmQuitter = new BitmapTextureAtlas(MainActivity.getInstance().getTextureManager(), 256, 128);
		TextureRegion  trQuitter = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bmQuitter, MainActivity.getInstance(), "images/quitter.png", 0, 0);
		quitter = new ButtonSprite(30 , 380, trQuitter, MainActivity.getInstance().getVertexBufferObjectManager()); 
		quitter.setX(Config.CAMERA_WIDTH - quitter.getWidth() - 30 );
		MainActivity.getInstance().getTextureManager().loadTexture(bmQuitter);
		
		
		BitmapTextureAtlas bmTitre = new BitmapTextureAtlas(MainActivity.getInstance().getTextureManager(), 1024, 1024);
		TextureRegion  trTitre = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bmTitre, MainActivity.getInstance(), "images/titre.png", 0, 0);
		titre = new Sprite(0 , 0, trTitre, MainActivity.getInstance().getVertexBufferObjectManager()); 
		titre.setX(Config.CAMERA_WIDTH/2 - titre.getWidth()/2);
		MainActivity.getInstance().getTextureManager().loadTexture(bmTitre);
	}
	
	
}
