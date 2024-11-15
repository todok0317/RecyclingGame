import javax.swing.*;
import java.awt.*;

public class Tool extends JLabel {
	// ***************************
	// Tool 클래스
	// 가위, 싱크대 등 아이템을 가공할 도구
	// JLabel을 상속 받아 구현
	// ***************************
	
	private String name;
	
	public Tool(String name, String imagePath) {
		this.name = name;
		setIcon(new ImageIcon(imagePath));
		setSize(getPreferredSize());
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isCorrectItem(ComplexItem item) {
		// 올바른 도구를 사용했는지 리턴
		return item.getNecessaryTool().equals(name);
	}
}
