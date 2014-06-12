package fr.licencepro.spaceinvader;

public class Config {
	
	private Config(){}
	
	//Dimension de la camera
	public static final int CAMERA_WIDTH = 480;
	public static final int CAMERA_HEIGHT = 720;
	
	public static final int FPS = 60;
	
	//Dossier où se trouvent les fichiers XML de données (à partir du dossier assets).
	public static final String DATA = "data/";
	
	//Grille pour placer les Groupes d'AI.
	public static final int ORIGIN_GRID_X = 70;
	public static final int ORIGIN_GRID_Y = 150;
	public static final int GRID_HORIZONTAL = 10;
	public static final int GRID_VERTICAL = 5;
	
	//Area Game
	public static final int MIN_GAME_AREA_X = 40;
	public static final int MAX_GAME_AREA_X = 390;
	public static final int MIN_GAME_AREA_Y = 125;
	public static final int MAX_GAME_AREA_Y = 600;
	
	
	
	public static final int DEFAULT_AI_HEALTH = 3;
	public static final int DEFAULT_AI_SPEED = 10;
	
	
}