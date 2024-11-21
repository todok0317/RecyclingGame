import javax.swing.*;
import java.awt.*;

public class Item extends JLabel {
	// ***************************
	// Item 클래스
	// 분리수거하기 위해 제공될 아이템
	// JLabel을 상속 받아 구현
	// ***************************

	private String name;
	private String type; // 아이템의 재질 유형
	private String imagePath; // 이미지 경로를 저장

	public Item(String name, String type, String imagePath) {
		this.name = name;
		this.type = type;
		this.imagePath = imagePath;

		// 이미지 아이콘을 설정하여 JLabel로 표시
		setIcon(new ImageIcon(imagePath));
		setSize(getPreferredSize()); // 이미지 크기로 사이즈 설정
	}

	// imagePath를 반환하는 메서드
    public String getImagePath() {
        return imagePath;
    }
    
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	
}
