import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ComplexItem extends Item {
	// ***************************
	// ComplexItem 클래스
	// 두 가지의 재질이 섞여 있는 아이템
	// Item 클래스를 상속받아 구현
	// ***************************
	
	private Item mainItem;	// 분리수거 할 주된 아이템
	private Item subItem;	// 분리해야하는 서브아이템
	private String necessaryTool;	// 필요한 도구의 이름

	public ComplexItem(String name, String type, String imagePath, Item mainItem, String necessaryTool) {
		// 분리수거해야 하는 아이템이 한 가지인 경우 생성자 ex) 음료수가 들어있는 페트병
		super(name, type, imagePath);
		this.mainItem = mainItem;
		this.necessaryTool = necessaryTool;
	}
	
	public ComplexItem(String name, String type, String imagePath, Item mainItem, Item subItem, String necessaryTool) {
		// 분리수거해야 하는 아이템이 두 가지인 경우 생성자 ex) 페트병, 라벨
		super(name, type, imagePath);
		this.mainItem = mainItem;
		this.subItem = subItem;
		this.necessaryTool = necessaryTool;
	}
	
	public Item getMainItem() {
		return mainItem;
	}
	
	public Item getSubItem() {
		return subItem;
	}
	
	public String getNecessaryTool() {
		return necessaryTool;
	}
	
	public List<Item> seperateItem() {
		// 메인 아이템과 서브 아이템을 분리하여 리스트로 리턴하는 메소드
		List<Item> seperatedItems = new LinkedList<>();
		seperatedItems.add(mainItem);
		if (subItem != null) {
			seperatedItems.add(subItem);
		}
		return seperatedItems;
	}
	
}
