package fr.licencepro.spaceinvader.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

/**
 * Cette classe représente une arme.
 * 
 * @author Sulivan
 *
 */
public class Weapon {
	private int power;
	private int speed;
	
	private TextureRegion textureBullet;
	
	private MOB owner;
	
	public Weapon(int power, int speed, TextureRegion textureBullet){
		this.power = power;
		this.speed = speed;
		this.textureBullet = textureBullet;
	}
	
	public Bullet fire(int x, int y, Direction direction){
		int sp = this.speed;
		if(direction == Direction.UP){
			sp *= -1; 
		}
		else if(direction == Direction.DOWN){
			
		}
		return new Bullet(x, y, power, sp, textureBullet, owner);
	}
	
	public void setOwner(MOB owner){
		this.owner = owner;
	}
	
	

}
