package fr.licencepro.spaceinvader.object;

/**
 * Cette classe représente une intelligence artificielle.
 * 
 * @author Sulivan
 *
 */
public class AI extends MOB{

	private boolean firing = false;
	
	public AI(int x, int y, int max_health, int speed, Direction direction, float ratio) {
		super(x, y, max_health, speed, direction, ratio);
	}
	
	public void commitFire(){
		firing = true;
	}
	
	public boolean isFiring(){
		return firing;
	}
	
	@Override
	public Bullet fire(Direction direction){
		firing = false;
		return super.fire(direction);
	}

}
