import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class SoundManager {

	// 효과음 재생
	public static void playSound(String name) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("sounds/" + name +".wav")));
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
