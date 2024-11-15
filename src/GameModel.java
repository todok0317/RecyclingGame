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
	private List<Bin> bins; // 분리수거 통 목록
	private List<Tool> tools; // 도구 목록
	private List<Item> currentItem; // 현재 제공 중인 아이템
	private int score; // 점수
	private int timeLeft; // 남은 시간
	private Random random;

	public GameModel() {
	}

	public void resetStates() {
		// 게임 상태 초기화
		score = 0;
		timeLeft = 30;
		random = new Random();
	}

	public void setLevelData(LevelData levelData) {
		// 선택된 레벨의 데이터를 받아 초기화하는 메소드
		resetStates();
		this.itemTemplates = new ArrayList<>(levelData.getItemTemplates());
		this.bins = new ArrayList<>(levelData.getBins());
		// 해당 레벨에 도구가 있다면 도구 목록 받기
		if (levelData.getTools() != null)
			this.tools = new ArrayList<>(levelData.getTools());
		else
			this.tools = null;
	}

	public void provideNewItem() {
		// 새로운 아이템을 제공하는 메소드
		// 아이템 템플릿에서 랜덤으로 1개의 아이템 제공
		int index = random.nextInt(itemTemplates.size());
		Item item = itemTemplates.get(index);
		
		currentItem = new ArrayList<>();
		// 아이템을 복사하여 현재 아이템으로 설정
		if (item instanceof ComplexItem) {
			ComplexItem complexItem = (ComplexItem) item;
			currentItem.add(new ComplexItem(complexItem.getName(), complexItem.getType(), complexItem.getImagePath(), complexItem.getMainItem(), complexItem.getSubItem(), complexItem.getNecessaryTool()));
		} else {
			currentItem.add(new Item(item.getName(), item.getType(), item.getImagePath()));
		}
	}

	public List<Item> getCurrentItem() {
		return currentItem;
	}

	public List<Bin> getBins() {
		return bins;
	}
	
	public List<Tool> getTools() {
		return tools;
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

	public boolean isCorrectBin(Bin bin, Item item) {
		// 올바른 분리수거인지 리턴
		return bin.isCorrectItem(item);
	}
	
	public boolean isCorrectTool(Tool tool, ComplexItem item) {
		return tool.isCorrectItem(item);
	}
	
	public void changeCurrentItem(List<Item> items) {
		// 현재 제공된 아이템 변경
		currentItem = items;
	}
}
