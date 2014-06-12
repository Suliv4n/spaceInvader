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
	private int speed = 1;
	private float ratio;
	
	private Direction direction;
	
	private Sprite sprite;
	
	private int[] weaponPosition;
	
	public MOB(int x, int y, int max_health, int speed, Direction direction, float ratio){
		super(x,y);
		this.health = max_health;
		this.max_health = max_health;
		this.speed = speed;
		this.direction = direction;
		this.ratio = ratio;
		
		weaponPosition = new int[2];
	}
	
	
	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
		sprite.setScale(ratio);
		weaponPosition[0] = (int) sprite.getWidth()/2;
	    weaponPosition[1] = 0;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	/**
	 * Retourne vrai si le MOB est détruit. Faux sinon.
	 * 
	 * Le joueur est détruit si l'attribut health est à 0.
	 * 
	 * @return vrai si le MOB est détruit, faux sinon.
	 */
	public boolean isDestroyed(){
		return health == 0;
	}
	
	/**
	 * Rajoute delta à la valeur de l'attribut health.
	 * Si delta est négatif le MOB perd de la vie, sinon il en gagne.
	 * L'attribut health est remis à 0 s'il est plus petit que 0.
	 * L'attribut health est remis à max_health s'il est plus grand que max_health.
	 * 
	 * @param delta
	 *	La valeur à rajouter à health.
	 */
	public void updateHealth(int delta){
		health += delta;
		
		health = Math.min(health, max_health);
		health = Math.max(health, 0);
	}
	
	/**
	 * Le MOB tire.
	 */
	public Bullet fire(Direction direction)
	{
		int bx = x + weaponPosition[0];
		int by = y + weaponPosition[1];
		
		if(direction == Direction.DOWN){
			by = (int) (y + weaponPosition[1] + sprite.getHeight());
		}
		
		return weapon.fire(bx , by, direction);
	}

	@Override
	public void move(int dx, int dy)
	{
		super.move(dx,dy);
		sprite.setX(x);
		sprite.setY(y);
	}
	
	public void setWeapon(Weapon weapon)
	{
		this.weapon = weapon;
		this.weapon.setOwner(this);
	}
	
	
	public int getHealth(){
		return health;
	}
	
}
