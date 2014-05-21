package fr.licencepro.spaceinvader.object;


/**
 * Objey à afficher dans le jeu (joueurs, unites).
 * 
 * 
 * @author Sulivan
 *
 */
public abstract class GameObject {
	protected int x = 0;
	protected int y = 0;

	public GameObject(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	//-------------GETTERS----------------
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
