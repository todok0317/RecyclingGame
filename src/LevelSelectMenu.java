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
	ActionListener backButtonListener; //이전버튼 리스너
	JButton backButton = new JButton("이전");  //이전 버튼

	public LevelSelectMenu(ActionListener levelSelectListener) {
		this.levelSelectListener = levelSelectListener;
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 정렬
		
		JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		backButtonPanel.setOpaque(false); // 배경색이 투명하도록 설정
		// "이전" 버튼 추가
        backButton.setFont(StyleManager.buttonFont);
        backButton.setPreferredSize(new Dimension(110, 50)); // 버튼 크기 설정
        backButton.setFocusPainted(false);
        backButton.setOpaque(true);
        backButton.setBackground(StyleManager.buttonColor);
        
        // backButton을 backButtonPanel에 추가하고, 그 패널을 상단에 추가
        backButtonPanel.add(backButton);
        add(backButtonPanel);
        add(Box.createVerticalStrut(5)); //5 간격
	
		JLabel title = new JLabel("레벨은 총 세 개로 구성되어 있어요.", SwingConstants.CENTER);

		title.setFont(StyleManager.fontLargeBold);
		title.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(title);
		
		add(Box.createVerticalStrut(20)); //타이틀과 서브타이틀 간격 추가
		
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
		level1Button.setFocusPainted(false);
		level2Button.setFocusPainted(false);
		level3Button.setFocusPainted(false);

		buttonPanel.add(level1Button);
		buttonPanel.add(level2Button);
		buttonPanel.add(level3Button);
		add(Box.createVerticalStrut(20));
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

	 // "이전" 버튼에 대한 리스너 메소드 추가
	public void setBackButtonListener(ActionListener listener) {
		this.backButtonListener = listener;
		backButton.addActionListener(listener);
	}
}
