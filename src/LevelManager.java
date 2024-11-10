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
		case "level1":
			return createDataLevel1();
		case "level2":
			return createDataLevel2();
		case "level3":
			return createDataLevel3();
		}
		return null;
	}

	private LevelData createDataLevel1() {
		// 레벨1 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("플라스틱", "플라스틱", "images/plastic.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "images/plastic_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("종이", "images/paper_bin.png"));

		return new LevelData(itemTemplates, bins);
	}

	private LevelData createDataLevel2() {
		// 레벨2 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("플라스틱", "플라스틱", "images/plastic.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));
		itemTemplates.add(new Item("일반", "일반", "images/regular.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("플라스틱", "images/plastic_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("종이", "images/paper_bin.png"));
		bins.add(new Bin("일반", "images/regular_bin.png"));

		return new LevelData(itemTemplates, bins);
	}
	
	private LevelData createDataLevel3() {
		// 레벨3 데이터
		List<Item> itemTemplates = new ArrayList<>();
		itemTemplates.add(new Item("페트병", "페트", "images/petbottle.png"));
		itemTemplates.add(new Item("라벨이 있는 페트병", "페트#cutter", "images/petbottle#cutter.png"));
		itemTemplates.add(new Item("유리", "유리", "images/glass.png"));
		itemTemplates.add(new Item("종이", "종이", "images/paper.png"));

		List<Bin> bins = new ArrayList<>();
		bins.add(new Bin("페트", "images/pet_bin.png"));
		bins.add(new Bin("유리", "images/glass_bin.png"));
		bins.add(new Bin("종이", "images/paper_bin.png"));
		
		List<Tool> tools = new ArrayList<>();
		tools.add(new Tool("cutter", "images/cutter.png"));

		return new LevelData(itemTemplates, bins, tools);
	}
}
