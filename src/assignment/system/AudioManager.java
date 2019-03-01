package src.assignment.system;

import java.awt.*;
import java.util.*;
import java.lang.*;
import src.assignment.state.*;

public class AudioManager implements InterfaceAudioManager {
	
	/***Properties***/
	
	protected Map<String, AudioSource> audioList;
	protected ArrayList<String> playList;
	protected String currentlyPlaying;
	
	protected InterfaceGame game;
	
	/***Constructor***/
	
	public AudioManager(InterfaceGame game) {
		this.game = game;
		audioList = new HashMap<String, AudioSource> ();
		playList = new ArrayList<String>();
		currentlyPlaying = "";
	}
	
	public void registerAudio(AudioSource audio) {
		audioList.put(audio.name(), audio);
	}
	
	public void addToPlayList(String name) {
		AudioSource audio = audioList.get(name);
		if (audio == null) {
			throw new NullPointerException(String.format("Audio %s has not been registered.", name));
		} 
		playList.add(name);
	}
	
	public void shufflePlayList() {
		Collections.shuffle(playList);
	}
	
	public String getNameFromPlayList(int index) {
		return playList.get(index);
	}
		
	public void playFromPlayList(int index) {
		String name = playList.get(index);
		playAudio(name);
	}
	
	public void loopFromPlayList(int index) {
		String name = playList.get(index);
		startAudioLoop(name);
	}
	
	public void playAudio(String name) {
		AudioSource audio = audioList.get(name);
		if (audio == null) {
			throw new NullPointerException(String.format("Audio %s has not been registered.", name));
		} 
		currentlyPlaying = audio.name();
		game.playAudio(audio);
	}
	
	public void startAudioLoop(String name) {
		AudioSource audio = audioList.get(name);
		if (audio == null) {
			throw new NullPointerException(String.format("Audio %s has not been registered.", name));
		} 
		currentlyPlaying = audio.name();
		game.startAudioLoop(audio);
	}
	
	public void stopAudioLoop(String name) {
		AudioSource audio = audioList.get(name);
		if (audio == null) {
			throw new NullPointerException(String.format("Audio %s has not been registered.", name));
		} 
		game.stopAudioLoop(audio);
	}
	
	public void stopCurrentAudioLoop() {
		AudioSource audio = audioList.get(currentlyPlaying);
		if (audio != null) { game.stopAudioLoop(audio); } 
		
	}
}