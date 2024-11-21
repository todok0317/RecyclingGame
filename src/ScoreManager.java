import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ScoreManager {
	// ***************************
	// ScoreManager 클래스
	// 레벨 별 최고 점수와 해금된 레벨 정보를 관리하는 클래스
	// scores.dat 파일을 생성해 정보 저장
	// ***************************
	
	private Map<Integer, Integer> highScores; // 레벨별 최고 점수
	private int unlockedLevel; // 해금된 레벨
	private final int unlockScore = 100; // 다음 레벨 해금을 위한 점수

	public ScoreManager() {
		highScores = new HashMap<>();
		loadScores();
	}

	// 파일에서 점수와 해금된 레벨을 불러옴
	private void loadScores() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("scores.dat"))) {
			highScores = (Map<Integer, Integer>) in.readObject();
			unlockedLevel = in.readInt();
		} catch (IOException | ClassNotFoundException e) {
			unlockedLevel = 1; // 기본값으로 레벨 1만 잠금 해제
		}
	}

	// 점수와 해금된 레벨을 파일에 저장
	private void saveScores() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("scores.dat"))) {
			out.writeObject(highScores);
			out.writeInt(unlockedLevel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 레벨별 최고 점수를 반환
	public int getHighScore(int level) {
		return highScores.getOrDefault(level, 0);
	}

	// 최고 점수 갱신 및 갱신 여부 반환
	public boolean updateHighScore(int level, int score) {
		int currentHighScore = getHighScore(level);
		if (score > currentHighScore) {
			highScores.put(level, score);
			
			// 일정 점수를 넘으면 다음 레벨 해금
			if (score >= unlockScore && level == unlockedLevel)
				unlockedLevel++;
			
			saveScores();
			return true;
		}
		return false;
	}

	// 현재 해금된 레벨을 반환
	public int getUnlockedLevel() {
		return unlockedLevel;
	}

}
