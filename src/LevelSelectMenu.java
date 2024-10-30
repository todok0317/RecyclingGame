import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LevelSelectMenu extends JPanel {
	// ***************************
	// LevelSelectMenu 클래스
	// 게임의 레벨 선택 화면
	// JPanel을 상속 받아 구현
	// ***************************

	public LevelSelectMenu(ActionListener levelSelectListener) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 200));

		JButton level1Button = new JButton("Level 1");
		JButton level2Button = new JButton("Level 2");

		level1Button.setActionCommand("level1");
		level2Button.setActionCommand("level2");

		// 각 버튼에 레벨 선택 리스너를 추가
		level1Button.addActionListener(levelSelectListener);
		level2Button.addActionListener(levelSelectListener);

		add(level1Button);
		add(level2Button);
	}
}
