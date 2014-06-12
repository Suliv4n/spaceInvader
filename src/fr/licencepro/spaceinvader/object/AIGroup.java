package fr.licencepro.spaceinvader.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AIGroup implements Iterable<AI>, Pattern{
	
	private ArrayList<AI> aiGroup;
	private Pattern pattern;
	
	private Direction direction;
	private ArrayList<Direction> moves;
	
	public AIGroup(ArrayList<AI> ais)
	{
		aiGroup = ais;
		direction = Direction.RIGHT;
		moves = new ArrayList<Direction>();
	}

	@Override
	public Iterator<AI> iterator() {
		return aiGroup.iterator();
	}

	/**
	 * Supprime toutes les AI passés en paramètres.
	 * 
	 * @param aiToRemove
	 * 	Les IA à supprimer du groupe.
	 * @return
	 * 	@see ArrayList.removeAll()
	 */
	public boolean removeAll(ArrayList<AI> aiToRemove) {
		return aiGroup.removeAll(aiToRemove);
	}
	
	public void doAI(){
		pattern.doAI();
		moves.add(this.direction);
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public ArrayList<Direction> getMoves(){
		return moves;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 * Des AI aléatoires parmi la prmière ligne du groupe tire.
	 * 
	 * @param nb
	 * 	Nombre d'AI qui tirent.
	 */
	public void firstLineRandomFire(int nb) 
	{
		ArrayList<AI> firstLine = new ArrayList<AI>();
		
		for(AI ai : aiGroup){
			boolean canFire = true;
			for(AI next : getAIWithOrdonateGreaterThan(ai.getY())){
				if(!ai.equals(next) && ai.getX() == next.getX())
				{
					canFire = false;
					break;
				}
			}
			if(canFire){
				firstLine.add(ai);
			}
		}
		
		
		int max = firstLine.size();
		nb = Math.min(nb, max);
		
		ArrayList<Integer> tab = new ArrayList<Integer>();
		
		for(int i = 0; i<max; i++){
			tab.add(i);
		}
		
		for(int i = 0; i<nb ;i++)
		{
			Random r = new Random();
			int valeur = tab.get(r.nextInt(max-i));
			tab.remove((Object) valeur);
			firstLine.get(valeur).commitFire();
		}

	}
	
	private ArrayList<AI> getAIWithOrdonateGreaterThan(int y){
		ArrayList<AI> res = new ArrayList<AI>();
		for(AI ai : aiGroup){
			if(ai.getY() >= y)
			{
				res.add(ai);
			}
		}
		return res;
	}
	
	public boolean isAllDestroyed(){
		boolean res = true;
		for(AI ai : aiGroup){
			if(!ai.isDestroyed()){
				res = false;
				break;
			}
		}
		return res;
	}
	
}
