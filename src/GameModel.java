import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
	// ***************************
	// GameModel 클래스
	// 아이템, 분리수거 통, 시간, 점수 등 게임에 필요한 데이터 관리
	// 주요 로직은 포함하지 않음
	// ***************************

	private List<Item> itemTemplates; // 아이템 템플릿 목록
	private Bin[] bins; // 분리수거 통 목록
	private Item currentItem; // 현재 제공 중인 아이템
	private int score; // 점수
	private int timeLeft; // 남은 시간
	private Random random;

	public GameModel() {
		itemTemplates = new ArrayList<>();
		bins = new Bin[3]; // 3개의 분리수거 통
		score = 0;
		timeLeft = 60; // 60초 동안 게임 진행
		random = new Random();

		initializeItemsAndBins();
		provideNewItem(); // 첫 아이템 제공
	}

	private void initializeItemsAndBins() {
		itemTemplates.add(new Item("플라스틱", "플라스틱", "images/plastic.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));

		bins[0] = new Bin("플라스틱", "images/plastic_bin.png");
		bins[1] = new Bin("유리", "images/glass_bin.png");
		bins[2] = new Bin("종이", "images/paper_bin.png");
	}

	public void provideNewItem() {
		// 새로운 아이템을 제공하는 메소드
		// 아이템 템플릿에서 랜덤으로 1개의 아이템 제공
		int index = random.nextInt(itemTemplates.size());
		currentItem = new Item(itemTemplates.get(index).getName(), itemTemplates.get(index).getType(),
				itemTemplates.get(index).getIcon().toString());
	}

	public Item getCurrentItem() {
		return currentItem;
	}

	public Bin[] getBins() {
		return bins;
	}

	public int getScore() {
		return score;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void updateScore(boolean isCorrect) {
		// 점수를 업데이트하는 메소드
		// 올바르게 분리수거 했다면 +10점, 틀렸다면 -5점
		if (isCorrect) {
			score += 10;
		} else {
			score -= 5;
		}
	}

	public void decrementTime() {
		// 시간 감소
		if (timeLeft > 0) {
			timeLeft--;
		}
	}

	public boolean isCorrectBin(Bin bin) {
		// 올바른 분리수거인지 리턴
		return bin.isCorrectItem(currentItem);
	}
}
