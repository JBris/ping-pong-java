package src.assignment1.system;

public interface InterfaceGameOptions {
	
	public int getNumberOfRounds();
	
	public Difficulty getDifficulty();
	
	public AiPlayer getAiPlayer();

	public void setNumberOfRounds(int numberOfRounds);
	
	public void setDifficulty(Difficulty difficulty);
	
	public void setAiPlayer(AiPlayer aiPlayer);
	
}