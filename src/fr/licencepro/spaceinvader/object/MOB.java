package fr.licencepro.spaceinvader.object;

/**
 * Objet mobile du jeu
 * 
 * @author Sulivan
 *
 */
public class MOB extends GameObject{
	
	private Weapon weapon;
	int health;
	int health_max;
	
	public MOB(int x, int y, int healt_max){
		super(x,y);
		this.health = max_health;
	}
}
