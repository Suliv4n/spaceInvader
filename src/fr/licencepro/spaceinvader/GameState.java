package fr.licencepro.spaceinvader;

public enum GameState {
	RUNNING(1),
	LOSE(2),
	WIN(3),
	TIMER(4),
	PAUSE(5);
	
	int i;
	GameState(int i){this.i = i;}
	
}
