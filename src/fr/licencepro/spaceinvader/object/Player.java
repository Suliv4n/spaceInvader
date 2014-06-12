package fr.licencepro.spaceinvader.object;

import org.andengine.entity.IEntity;

/**
 * Cette classe repr�sente un joueur.
 * 
 * @author Sulivan
 *
 */
public class Player extends MOB{

	private int movingX = 0;
	private int movingY= 0;
	
	public Player(int x, int y, int max_health, int speed, Direction direction, float ratio) {
		super(x, y, max_health, speed, direction, ratio);
	}

	/**
	 * Met � jour la position du joueur.
	 */
	public void update(){
		move(movingX, movingY);
	}
	
	/**
	 * Le joueur arr�te de se d�placer.
	 */
	public void stopMoving(){
		movingX = 0;
		movingY = 0;
	}

	public void setMovingX(int multiplier){
		movingX = super.getSpeed() * multiplier;
	}
	
	public void setMovingY(int multiplier){
		movingY = super.getSpeed() * multiplier;
	}
}
