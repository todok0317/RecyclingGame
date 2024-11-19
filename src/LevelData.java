import java.util.List;

public class LevelData {
	// ***************************
	// LevelData 클래스
	// 레벨에 필요한 아이템과 분리수거 통들의 데이터
	// ***************************

	private int level;
	private List<Item> itemTemplates; // 아이템 템플릿 목록
	private List<Bin> bins; // 분리수거 통 목록
	private List<Tool> tools; // 도구 목록

	public LevelData(int level, List<Item> items, List<Bin> bins) {
		this.level = level;
		this.itemTemplates = items;
		this.bins = bins;
	}
	
	public LevelData(int level, List<Item> items, List<Bin> bins, List<Tool> tools) {
		this.level = level;
		this.itemTemplates = items;
		this.bins = bins;
		this.tools = tools;
	}
	
	public int getLevel() {
		return level;
	}

	public List<Item> getItemTemplates() {
		return itemTemplates;
	}

	public List<Bin> getBins() {
		return bins;
	}
	
	public List<Tool> getTools() {
		return tools;
	}
}
