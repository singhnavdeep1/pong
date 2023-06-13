package sfx;

import javafx.scene.media.MediaPlayer;

public class Music extends Sound {

	public Music(String path) {
		super(path);
	}
	
	public void loop() {
		this.player.setCycleCount(MediaPlayer.INDEFINITE);
		this.player.play();
	}
	
	public void loop(int n) throws IllegalArgumentException {
		if (n <= 0) throw new IllegalArgumentException("n must be a positive integer");
		this.player.setCycleCount(n);
		this.player.play();
	}

}
