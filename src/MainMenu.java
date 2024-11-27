import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainMenu extends JPanel {
	// ***************************
	// MainMenu 클래스
	// 게임의 메인메뉴 화면
	// JPanel을 상속 받아 구현
	// ***************************

	public MainMenu(ActionListener startGameListener) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 정렬

		ImageIcon guideIcon = new ImageIcon("images/trashEarth2.png");
		JLabel imageLabel = new JLabel(guideIcon);
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(imageLabel); // 이미지 추가

		add(Box.createVerticalStrut(10)); // 이미지, 타이틀 사이의 간격 조정

		JLabel title = new JLabel("Recycling Simulation Game", SwingConstants.CENTER);
		title.setFont(StyleManager.fontLargeBold);
		title.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(title);

		JLabel subtitle = new JLabel("환경을 지키는 분리수거 미션, 시작해볼까요?", SwingConstants.CENTER);
		subtitle.setFont(StyleManager.fontMidiumBold);
		subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
		add(subtitle); // 부제목 추가
		add(Box.createVerticalStrut(20)); // 제목, 버튼 사이 간격 추가
 
		JButton startButton = new JButton("Game Start");
		startButton.setFont(StyleManager.buttonFont);
		startButton.setPreferredSize(new Dimension(150, 50)); // 기본 크기 설정
		startButton.setFocusPainted(false); // 버튼 포커스 테두리 제거
		startButton.setOpaque(true); // 배경을 불투명하게 설정
		startButton.setBackground(StyleManager.buttonColor); // 배경색 설정

		JButton guideButton = new JButton("분리배출 가이드");
		guideButton.setFont(StyleManager.buttonFont);
		guideButton.setPreferredSize(new Dimension(150, 50)); // 기본 크기 설정
		guideButton.setFocusPainted(false); // 버튼 포커스 테두리 제거
		guideButton.setOpaque(true); // 배경을 불투명하게 설정
		guideButton.setBackground(StyleManager.buttonColor);

		// 게임 시작 버튼 클릭 시 전달 받은 startGameListener의 메소드 실행
		startButton.addActionListener(startGameListener);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // X축 중앙 정렬로

		// 가이드 버튼 클릭 하면 URL 열기
		guideButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openWebPage("https://www.junggu.seoul.kr/content.do?cmsid=14189");
			}
		});

		guideButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(startButton);
		add(Box.createVerticalStrut(10));
		add(guideButton);

		setBackground(StyleManager.backgroundColor); // 메인 색 조정

	}

	private void openWebPage(String urlString) {
		try {
			Desktop desktop = Desktop.getDesktop();
			URI uri = new URI(urlString);
			desktop.browse(uri);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "URL을 열 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		}
	}
}
