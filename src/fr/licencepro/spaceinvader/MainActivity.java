package fr.licencepro.spaceinvader;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.graphics.Typeface;

public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 720;
	
	private Font mFont;
	private Scene scene;
	
    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0,0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
    }
 
    @Override
    protected void onCreateResources() {
    	mFont = FontFactory.create(this.getFontManager(), this.getTextureManager(), 256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 32);
    	mFont.load();
    }
 
    @Override
    protected Scene onCreateScene() {
    	this.mEngine.registerUpdateHandler(new FPSLogger());
    	
    	scene = new Scene();
    	scene.setBackground(new Background(255, 255, 255));
    	
    	final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
    	final Text centerText = new Text((CAMERA_WIDTH/2)-100, (CAMERA_HEIGHT/2)-40, this.mFont, "Helloworld !", vertexBufferObjectManager);
    	
        scene.attachChild(centerText);
        return scene;
    }

}
