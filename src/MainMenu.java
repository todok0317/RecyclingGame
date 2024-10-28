import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
	// ***************************
	// MainMenu 클래스
	// 게임의 메인메뉴 화면
	// JPanel을 상속 받아 구현
	// ***************************
	
	public MainMenu(ActionListener startGameListener) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 200));

		JLabel title = new JLabel("Recycling Simulation Game", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		add(title);

		JButton startButton = new JButton("Game Start");
		startButton.setFont(new Font("Arial", Font.PLAIN, 18));

		// 게임 시작 버튼 클릭 시 전달 받은 startGameListener의 메소드 실행
		startButton.addActionListener(startGameListener);

		add(startButton);
	}
}
