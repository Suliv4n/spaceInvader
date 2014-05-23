package fr.licencepro.spaceinvader.object;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;

/**
 * Objet mobile du jeu
 * 
 * @author Sulivan
 *
 */
public abstract class MOB extends GameObject{
	
	private Weapon weapon;
	private int health;
	private int max_health;
	
	private Sprite sprite;
	
	public MOB(int x, int y, int max_health){
		super(x,y);
		this.health = max_health;
		this.max_health = max_health;
	}
	
	
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Retourne vrai si le MOB est d�truire. Faux sinon.
	 * 
	 * Le joueur est d�truit si l'attribut health est � 0.
	 * 
	 * @return vrai si le MOB est d�truit, faux sinon.
	 */
	public boolean isDestroyed(){
		return health == 0;
	}
	
	/**
	 * Rajoute delta � la valeur de l'attribut health.
	 * Si delta est n�gatif le MOB perd de la vie, sinon il en gagne.
	 * L'attribut health est remis � 0 s'il est plus petit que 0.
	 * L'attribut health est remis � max_health s'il est plus grand que max_health.
	 * 
	 * @param delta
	 *	La valeur � rajouter � health.
	 */
	public void updateHealth(int delta){
		health += delta;
		
		health = Math.min(health, max_health);
		health = Math.max(health, 0);
	}

	@Override
	public void move(int dx, int dy)
	{
		super.move(dx,dy);
		sprite.setX(x);
		sprite.setY(y);
	}
}
