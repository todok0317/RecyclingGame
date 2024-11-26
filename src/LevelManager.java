import java.util.ArrayList;
import java.util.List;

public class LevelManager {
	// ***************************
	// LevelManager 클래스
	// 레벨에 따라 필요한 데이터를 제공
	// 데이터베이스 역할
	// ***************************
	
	public LevelData getLevelData(String level) {
		// 레벨을 받아 해당 레벨에 필요한 데이터 제공
		// 레벨이 추가되면 이곳에 case 문으로 추가
		switch (level) {
		case "1":
			return createDataLevel1();
		case "2":
			return createDataLevel2();
		case "3":
			return createDataLevel3();
		}
		return null;
	}

	private LevelData createDataLevel1() {
		// 레벨1 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("빈 페트병", "플라스틱", "images/plastic.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "images/plastic_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("종이", "images/paper_bin.png"));

		return new LevelData(1, itemTemplates, bins);
	}

	private LevelData createDataLevel2() {
		// 레벨2 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("빈 페트병", "플라스틱", "images/plastic.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));
		itemTemplates.add(new Item("일반", "일반", "images/regular.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "images/plastic_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("종이", "images/paper_bin.png"));
		bins.add(new Bin("일반", "images/regular_bin.png"));

		return new LevelData(2, itemTemplates, bins);
	}
	
	private LevelData createDataLevel3() {
		// 레벨3 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("페트병", "페트", "images/petbottle.png"));
		itemTemplates.add(new ComplexItem("라벨이 있는 페트병", "페트#비닐", "images/petbottle#cutter#label.png", new Item("페트병", "페트", "images/petbottle.png"), new Item("라벨", "비닐", "images/label.png"), "커터"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new ComplexItem("음료수가 있는 페트병", "페트#", "images/petbottle#sink#.png", new Item("페트병", "페트", "images/petbottle.png"), "싱크대"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("페트", "images/pet_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("비닐", "images/vinyl_bin.png"));
		
		List<Tool> tools = new ArrayList<>();
		tools.add(new Tool("커터", "images/cutter.png"));
		tools.add(new Tool("싱크대", "images/sink.png"));

		return new LevelData(3, itemTemplates, bins, tools);
	}
}