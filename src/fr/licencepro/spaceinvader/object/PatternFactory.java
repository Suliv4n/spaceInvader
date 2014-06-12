package fr.licencepro.spaceinvader.object;

import fr.licencepro.spaceinvader.Config;

public class PatternFactory {
	public static final int DEFAULT = 0;
	
	/**
	 * Créer logique du pattern en fonction du type de pattern passé en paramètre.
	 * 
	 * @param type
	 * @return
	 */
	public static void createPatternForAIGroup(int type, final AIGroup aiGroup){
		Pattern p = null;
		switch(type){
		case(DEFAULT): //-----------------------DEFAULT---------------------------
			p = new Pattern() {
				@Override
				public void doAI(){
					
					if(aiGroup.getDirection() == Direction.RIGHT || aiGroup.getDirection() == Direction.LEFT){
						boolean out = false;
						for(AI ai : aiGroup){
							if((ai.getX() + ai.getSpeed() > Config.MAX_GAME_AREA_X && aiGroup.getDirection() == Direction.RIGHT)
									|| (ai.getX() - ai.getSpeed() < Config.MIN_GAME_AREA_X && aiGroup.getDirection() == Direction.LEFT) ){
								out = true;
								break;
							}
						}
						
						if(out){
							aiGroup.setDirection(Direction.DOWN);
						}
					}
					
					if(aiGroup.getDirection() == Direction.DOWN){
						int last = aiGroup.getMoves().size() - 1;
						if(aiGroup.getMoves().size() >= 2 && aiGroup.getMoves().get(last) == Direction.DOWN && aiGroup.getMoves().get(last - 1) == Direction.DOWN){
							if(last - 2 > 0 && aiGroup.getMoves().get(last-2) == Direction.LEFT){
								aiGroup.setDirection(Direction.RIGHT);
							}
							if(last - 2 > 0 && aiGroup.getMoves().get(last-2) == Direction.RIGHT){
								aiGroup.setDirection(Direction.LEFT);
							}
							
						}
					}
					
					for(AI ai : aiGroup){
						if(aiGroup.getDirection() == Direction.RIGHT){
							ai.move(ai.getSpeed(),0);
						}
						if(aiGroup.getDirection() == Direction.DOWN){
							ai.move(0, ai.getSpeed());
						}
						if(aiGroup.getDirection() == Direction.LEFT){
							ai.move(-ai.getSpeed(), 0);
						}
					}
					
					aiGroup.firstLineRandomFire(2);
				}
				
			};
		}
		
		aiGroup.setPattern(p);
	}
}
