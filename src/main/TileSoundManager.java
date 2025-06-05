package main;

import javax.sound.sampled.FloatControl;

public class TileSoundManager {
    Screen screen;
    Sound tileSound = new Sound();
    
    private final int noSoundIndex = -1;
    
    private int lastTileCol = -1;
	private int lastTileRow = -1;
	private int lastTileSoundIndex = -1;
	
	FloatControl fc;
	int volumeScale = 3;
	float volume;

	private boolean wasMoving = false;
	
	public TileSoundManager(Screen screen) {
		this.screen = screen;
	}

	public void playTileSound() {
	    int footX = screen.player.worldX + screen.player.collisionArea.x + screen.player.collisionArea.width / 2;
	    int footY = screen.player.worldY + screen.player.collisionArea.y + screen.player.collisionArea.height;

	    int col = footX / Screen.tileSize;
	    int row = footY / Screen.tileSize;

	    boolean movedTile = col != lastTileCol || row != lastTileRow;

	    if (screen.player.isMoving == false) {
	        if (tileSound.isPlaying()) {
	            tileSound.stop();
	        }
	        wasMoving = false;
	        return;
	    }

	    if (wasMoving == false) {
	        wasMoving = true;

	        if (col >= 0 && col < Screen.maxWorldCol && row >= 0 && row < Screen.maxWorldRow) {
	            int tileNum = screen.tileM.mapTileNum[col][row];
	            int soundIndex = screen.tileM.tile[tileNum].soundIndex;

	            if (soundIndex != noSoundIndex) {
	                if (!tileSound.isPlaying()) {
	                    tileSound.setFile(soundIndex);
	                    tileSound.loop();
	                }
	                lastTileSoundIndex = soundIndex;
	            }
	            lastTileCol = col;
	            lastTileRow = row;
	        }
	        return;
	    }

	    if (movedTile) {
	        if (col >= 0 && col < Screen.maxWorldCol && row >= 0 && row < Screen.maxWorldRow) {
	            int tileNum = screen.tileM.mapTileNum[col][row];
	            int soundIndex = screen.tileM.tile[tileNum].soundIndex;

	            if (soundIndex != lastTileSoundIndex) {
	                if (tileSound.isPlaying()) {
	                    tileSound.stop();
	                }
	                if (soundIndex != noSoundIndex) {
	                    tileSound.setFile(soundIndex);
	                    tileSound.loop();
	                }
	                lastTileSoundIndex = soundIndex;
	            }
	            lastTileCol = col;
	            lastTileRow = row;
	        }
	    }
	}
	
	public void stopTileSound() {
		tileSound.stop();
	}
	
	public void updateVolume(Sound sfx) {
	    this.tileSound.volumeScale = sfx.volumeScale;
	}
}