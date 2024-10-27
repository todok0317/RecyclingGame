import javax.swing.*;
import java.awt.*;

public class Bin extends JLabel {
	// ***************************
	// Bin 클래스
	// 분리수거 통
	// JLabel을 상속 받아 구현
	// ***************************

	private String type; // 분리수거 될 수 있는 아이템의 유형

	public Bin(String type, String imagePath) {
		this.type = type;

		// 이미지 아이콘을 설정하여 JLabel로 표시
		setIcon(new ImageIcon(imagePath));
		setSize(getPreferredSize()); // 이미지 크기로 사이즈 설정
	}

	public boolean isCorrectItem(Item item) {
		// 올바른 분리수거인지 리턴
		return item.getType().equals(type);
	}
}
