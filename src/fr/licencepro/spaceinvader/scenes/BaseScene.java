package fr.licencepro.spaceinvader.scenes;


import org.andengine.entity.scene.Scene;

public abstract class BaseScene extends Scene{
	
	public BaseScene(){
	}
	
	public abstract void createScene();
	public abstract void createRessources();
}
