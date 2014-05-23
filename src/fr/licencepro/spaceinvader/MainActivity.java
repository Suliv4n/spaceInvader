package fr.licencepro.spaceinvader;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.input.touch.TouchEvent;

import fr.licencepro.spaceinvader.object.Player;
import android.graphics.Typeface;
import android.util.Log;

public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	
	private Font mFont;
	private Scene scene;
	
	private Player joueur;

	private Camera mCamera;

	private TextureRegion regionImage;
	
	
    @Override
    public EngineOptions onCreateEngineOptions() {
        this.mCamera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
    }
 
    @Override
    protected void onCreateResources() {
    	mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
    	mFont.load();
    	
    	//this.mEngine = new LimitedFPSEngine(this.onCreateEngineOptions(), 60);
    	
    	//image vaisseau
    	final BitmapTextureAtlas textureImage = new BitmapTextureAtlas(this.getTextureManager(), 32, 32);
    	regionImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureImage, this, "ships/red_ship.png", 0, 0);
    	this.getEngine().getTextureManager().loadTexture(textureImage);
        
    }
 
    @Override
    protected Scene onCreateScene() {
    	this.mEngine.registerUpdateHandler(new FPSLogger());
    	
    	scene = new Scene();
    	scene.setBackground(new Background(255, 255, 255));
    	
    	final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
    	final Text centerText = new Text((CAMERA_WIDTH/2)-100, (CAMERA_HEIGHT/2)-40, this.mFont, "Spaaaaace", vertexBufferObjectManager);
    	
    	joueur = new Player(  30, CAMERA_HEIGHT - 50 , 50);
    	final Sprite sprite = new Sprite(joueur.getX(), joueur.getY(), regionImage, this.getVertexBufferObjectManager());
    	
    	joueur.setSprite(sprite);

    	
    	
    	
        this.scene.registerUpdateHandler(new TimerHandler(0.5f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	//scene.attachChild(joueur.getSprite());
            	Log.i("position : ",joueur.getX()+" , "+joueur.getY());
            }
                    
            })); 

    	
    	createControllers();
    	
    	scene.attachChild(joueur.getSprite());
        scene.attachChild(centerText);
        
        return scene;
    }

    @Override
    public Engine onCreateEngine(final EngineOptions pEngineOptions) {
        Engine e = new LimitedFPSEngine(pEngineOptions, 60);
        return e;
    }

    
    
    

private void createControllers()
	{
	
		
	    final Rectangle left = new Rectangle(20, CAMERA_HEIGHT - 70, 60, 60, this.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	                joueur.move(-1, 0);
	            }
	            return true;
	        };
	    };
	    left.setColor(0.5f,0.5f,0.5f);
	    
	    final Rectangle right = new Rectangle(CAMERA_WIDTH - 80, CAMERA_HEIGHT - 70, 60, 60, this.getVertexBufferObjectManager())
	    {
	        public boolean onAreaTouched(TouchEvent touchEvent, float X, float Y)
	        {
	            if (touchEvent.isActionUp())
	            {
	            	joueur.move(1, 0);
	            }
	            return true;
	        };
	    };
	    right.setColor(0.5f,0.5f,0.5f);
	    
	    scene.registerTouchArea(left);
	    scene.registerTouchArea(right);
	    scene.attachChild(left);
	    scene.attachChild(right);

	    
	}

}
