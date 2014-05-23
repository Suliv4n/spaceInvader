package fr.licencepro.spaceinvader.object;


/**
 * Objey � afficher dans le jeu (joueurs, unites).
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
	 * D�placer le MOB de dx sur l'axe des abscisces et dy sur l'axe des ordonn�es.
	 * 
	 * @param dx
	 * 	D�placement horizontal.
	 * @param dy
	 * 	D�placement vertical.
	 */
	public void move(int dx, int dy){
		//TODO gestion des collisions ?
		x += dx;
		y += dy;
	}
}
