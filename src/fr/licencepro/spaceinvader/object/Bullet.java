package fr.licencepro.spaceinvader.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;

public class Bullet extends GameObject
{
	private int update = 0;
	
	private int power;
	private int speed;
	
	private Sprite sprite;
	private TextureRegion texture;
	
	private MOB parent;
	
	public Bullet(int x, int y, int power, int speed, TextureRegion texture, MOB parent)
	{
		super(x, y);
		
		this.power = power;
		this.speed = speed;
		
		this.texture = texture;
		this.parent = parent;
		
	}
	
	public void update()
	{
		update++;
		
		//Comment se déplace la munition
		y += speed;
		
		sprite.setX(x);
		sprite.setY(y);
	}
	
	public TextureRegion getTexture()
	{
		return texture;
	}
	
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
		//centrer la bullet
		x -= (int) sprite.getWidth() / 2;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}

	public int getPower(){
		return power;
	}
	
	public MOB getParent(){
		return parent;
	}
	
	
	
}
