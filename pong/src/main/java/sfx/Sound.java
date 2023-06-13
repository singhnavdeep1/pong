package sfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public abstract class Sound {
	
	private final static String SYS_SEP = System.getProperty("file.separator");
	
	protected String path;
	protected File file;
	protected Media media;
	protected MediaPlayer player;
	
	public Sound(String path) {
		this.path = "src" + SYS_SEP + "main" + SYS_SEP + "java" + SYS_SEP + path;
		this.file = new File(this.path);
		this.media = new Media(this.file.toURI().toString());
		this.player = new MediaPlayer(this.media);
	}
	
	public void play() {
		this.player.play();
	}
	
	public void pause() {
		this.player.pause();
	}
	
	public void stop() {
		this.player.stop();
	}
	
	public String getPath() {return this.path;}
	
	public File getFile() {return this.file;}
	
	public Media getMedia() {return this.media;}
	
	public MediaPlayer getPlayer() {return this.player;}
	
	public String getInfo() {return this.path.substring(13) + " ; " + this.player.getCurrentTime().toString() + " ; " + this.player.getStatus().toString();}
	
	public MediaPlayer.Status getStatus() {return this.player.getStatus();}

}
