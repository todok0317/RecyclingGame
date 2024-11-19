import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LevelSelectMenu extends JPanel {
	// ***************************
	// LevelSelectMenu 클래스
	// 게임의 레벨 선택 화면
	// JPanel을 상속 받아 구현
	// ***************************
	
	ActionListener levelSelectListener;

	public LevelSelectMenu(ActionListener levelSelectListener) {
		this.levelSelectListener = levelSelectListener;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 정렬
		JButton backButton = new JButton("이전");

		add(Box.createVerticalStrut(100));
		JLabel title = new JLabel("레벨은 총 두개로 구성되어 있어요.", SwingConstants.CENTER);
		title.setFont(StyleManager.fontLargeBold);
		title.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(title);

		JLabel subtitle = new JLabel("단계별로 난이도가 달라져요.", SwingConstants.CENTER);
		subtitle.setFont(StyleManager.fontMidiumRegular);
		subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(subtitle);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20)); // 버튼 중앙 정렬
		buttonPanel.setBackground(StyleManager.backgroundColor);

		LevelButton level1Button = new LevelButton(1);
		LevelButton level2Button = new LevelButton(2);
		LevelButton level3Button = new LevelButton(3);

		buttonPanel.add(level1Button);
		buttonPanel.add(level2Button);
		buttonPanel.add(level3Button);
		add(Box.createVerticalStrut(200));
		add(buttonPanel);

		setBackground(StyleManager.backgroundColor);

	}
	
	class LevelButton extends JButton {
		// 레벨 선택 버튼 정의
		public LevelButton(int level) {
			setText("Level " + level);
			setActionCommand(Integer.toString(level));
			
			setFont(StyleManager.buttonFont);
			setBackground(StyleManager.buttonColor);
			setPreferredSize(new Dimension(130, 130));
			
			addActionListener(levelSelectListener);
		}
	}
}
