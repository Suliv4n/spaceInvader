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
	
	
	/**
	 * Déplacer le MOB de dx sur l'axe des abscisces et dy sur l'axe des ordonnées.
	 * 
	 * @param dx
	 * 	Déplacement horizontal.
	 * @param dy
	 * 	Déplacement vertical.
	 */
	public void move(int dx, int dy){
		//TODO gestion des collisions ?
		x += dx;
		y += dy;
	}
}
