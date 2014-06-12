package fr.licenpro.spaceinvader.ressources;

import java.io.File;
import java.io.IOException;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.media.Image;

public class RessourcesManager {
	private BaseGameActivity activity;
	private static RessourcesManager INSTANCE;
	
	public static RessourcesManager getInstance(){
		return INSTANCE;
	}
	
	public static void prepare(BaseGameActivity activity){
		INSTANCE = new RessourcesManager();
		INSTANCE.setActivity(activity);
	}
	
	public void setActivity(BaseGameActivity activity){
		this.activity = activity;
	}
	
	/**
	 * Retourne un sprite dont le path est passé en paramètre.
	 * 
	 * @param path
	 * 	Path du sprite
	 * @param x
	 * 	Abscisse d'affichage.
	 * @param y
	 * 	Ordonnées d'affihage
	 * @return
	 * 	Le sprite généré
	 */
	public Sprite createSprite(String path,int x, int y){ //TODO non fonctionnel
		BitmapFactory.Options options = new BitmapFactory.Options();
		
		
		String fname= "file:///android_asset/"+path;
		BitmapFactory.decodeFile(fname, options);

		int width = options.outWidth;
		int height = options.outHeight;
		
		int widthBitMap = 2;
		int heightBitMap = 2;
		
		while(width > widthBitMap){
			widthBitMap *= 2;
		}
		
		while(height > widthBitMap){
			widthBitMap *= 2;
		}
		
		BitmapTextureAtlas bm = new BitmapTextureAtlas(activity.getTextureManager(), widthBitMap, heightBitMap);
		TextureRegion tr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bm, activity, path, 0, 0);
		Sprite sprite = new Sprite(0,0,tr, activity.getVertexBufferObjectManager());
		
		return sprite;
	}
}
