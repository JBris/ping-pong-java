package src.assignment1.system;

public class GameOptions implements InterfaceGameOptions {
	
	/***Properties***/
	
	protected int numberOfRounds;
	
	protected Difficulty difficulty;
	
	protected AiPlayer aiPlayer;
	
	/***Constructor***/
	
	public GameOptions() {
		numberOfRounds = 5;
		difficulty = Difficulty.Medium;
		aiPlayer = AiPlayer.Two;
	}
	
	/***Getters and Setters***/
	
	public int getNumberOfRounds() { return numberOfRounds; }
	
	public Difficulty getDifficulty() { return difficulty; }
	
	public AiPlayer getAiPlayer() {return aiPlayer; }
	
	public void setNumberOfRounds(int numberOfRounds) { this.numberOfRounds = numberOfRounds; }
	
	public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }
	
	public void setAiPlayer(AiPlayer aiPlayer) { this.aiPlayer = aiPlayer; }
}