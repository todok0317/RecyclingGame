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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 정렬
		JButton backButton = new JButton("이전");

		 add(Box.createVerticalStrut(100));
		 JLabel title = new JLabel("레벨은 총 두개로 구성되어 있어요.", SwingConstants.CENTER);
		 title.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 34));
		 title.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		 add(title); 
		 
		 JLabel subtitle = new JLabel("단계별로 난이도가 달라져요.", SwingConstants.CENTER);
		 subtitle.setFont(new Font("한컴 말랑말랑 Regular", Font.PLAIN, 20));
		 subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		 add(subtitle);
		 
		 JPanel buttonPanel = new JPanel();
	     buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20)); // 버튼 중앙 정렬
	     buttonPanel.setBackground(new Color(228, 216, 216));
		 
		JButton level1Button = new JButton("Level 1");
		JButton level2Button = new JButton("Level 2");
		
		level1Button.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 24));
		level2Button.setFont(new Font("한컴 말랑말랑 Bold", Font.BOLD, 24));
		
		level1Button.setPreferredSize(new Dimension(130, 130)); // 기본 크기 설정
		level2Button.setPreferredSize(new Dimension(130, 130)); // 기본 크기 설정
		
		level1Button.setBackground(new Color(203, 126, 126));
		level2Button.setBackground(new Color(203, 126, 126));

		level1Button.setActionCommand("level1");
		level2Button.setActionCommand("level2");

		// 각 버튼에 레벨 선택 리스너를 추가
		level1Button.addActionListener(levelSelectListener);
		level2Button.addActionListener(levelSelectListener);
		
		buttonPanel.add(level1Button);
		add(Box.createVerticalStrut(200));
		buttonPanel.add(level2Button);
		add(buttonPanel);
		
		setBackground(new Color(228, 216, 216));

	}
}
