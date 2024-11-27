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

	private ActionListener startGameListener;

	public MainMenu(ActionListener startGameListener) {
		this.startGameListener = startGameListener;

		setLayout(null);

		CenterPanel centerPanel = new CenterPanel();
		WestPanel westPanel = new WestPanel();

		centerPanel.setBounds(450, 0, 500, 800);
		westPanel.setBounds(0, 0, 1400, 800);
		setComponentZOrder(westPanel.popupImageScrollPane, 0);

		add(centerPanel);
		add(westPanel);

		setBackground(StyleManager.backgroundColor); // 메인 색 조정
	}

	// 레이아웃 센터에 들어갈 패널, 게임 시작 버튼 포함
	private class CenterPanel extends JPanel {
		CenterPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 세로 방향으로 정렬

			ImageIcon guideIcon = new ImageIcon("images/deco/trashEarth3.png");
			JLabel imageLabel = new JLabel(guideIcon);
			imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
			add(Box.createVerticalStrut(50));
			add(imageLabel); // 이미지 추가

			add(Box.createVerticalStrut(10)); // 이미지, 타이틀 사이의 간격 조정

			JLabel title = new JLabel("분리배출의 달인", SwingConstants.CENTER);
			title.setFont(StyleManager.fontExtraLargeBold);
			title.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
			add(title);

			JLabel subtitle = new JLabel("환경을 지키는 분리배출 미션, 시작해볼까요?", SwingConstants.CENTER);
			subtitle.setFont(StyleManager.fontMidiumBold);
			subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // X축으로 중앙 정렬
			add(subtitle); // 부제목 추가
			add(Box.createVerticalStrut(20)); // 제목, 버튼 사이 간격 추가

			JButton startButton = new JButton("게임 시작");
			startButton.setFont(StyleManager.fontLargeBold);
			startButton.setPreferredSize(new Dimension(150, 50)); // 기본 크기 설정
			startButton.setFocusPainted(false); // 버튼 포커스 테두리 제거
			startButton.setOpaque(true); // 배경을 불투명하게 설정
			startButton.setBackground(StyleManager.buttonColor); // 배경색 설정

			// 게임 시작 버튼 클릭 시 전달 받은 startGameListener의 메소드 실행
			startButton.addActionListener(startGameListener);
			startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // X축 중앙 정렬로

			add(startButton);
			add(Box.createVerticalStrut(10));

			setOpaque(false);
		}
	}

	// 레이아웃 왼쪽에 표시할 패널, 기타 정보 포함
	private class WestPanel extends JPanel {
		public JScrollPane popupImageScrollPane;

		WestPanel() {
			setLayout(null);

			ImageIcon icon = new ImageIcon("images/deco/click.png");

			JLabel label1 = new JLabel(" 자세한 분리배출 방법을 알고 싶다면?", icon, JLabel.LEFT);
			label1.setVerticalTextPosition(JLabel.CENTER);
			label1.setHorizontalTextPosition(JLabel.RIGHT);
			label1.setFont(StyleManager.buttonFont);
			label1.setBounds(15, 15, 500, 50);
			label1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					openWebPage("https://www.junggu.seoul.kr/content.do?cmsid=14189");
				}
			});

			JLabel label2 = new JLabel(" 왜 분리배출을 해야 할까요?", icon, JLabel.LEFT);
			label2.setVerticalTextPosition(JLabel.CENTER);
			label2.setHorizontalTextPosition(JLabel.RIGHT);
			label2.setFont(StyleManager.buttonFont);
			label2.setBounds(15, 70, 500, 50);

			// 레이블을 클릭하면 아래에 이미지 띄우기
			ImageIcon image = new ImageIcon("images/deco/why.png");
			JLabel imageLabel = new JLabel(image);
			JLabel textLabel = new JLabel("출처 - 환경부 분리배출 가이드라인");
			textLabel.setFont(StyleManager.fontMidiumBold);

			JPanel scrollPanel = new JPanel(new BorderLayout());
			scrollPanel.add(imageLabel, BorderLayout.CENTER);
			scrollPanel.add(textLabel, BorderLayout.SOUTH);

			// JScrollPane으로 감싸기
			popupImageScrollPane = new JScrollPane(scrollPanel);
			popupImageScrollPane.setBounds(15, 130, image.getIconWidth() + 10, 600); // 레이블 바로 아래 위치
			popupImageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			popupImageScrollPane.getVerticalScrollBar().setUnitIncrement(16);
			popupImageScrollPane.setVisible(false);

			// 클릭하면 분리배출 가이드라인 웹 페이지로 이동
			label2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					popupImageScrollPane.setVisible(!popupImageScrollPane.isVisible());
				}
			});

			add(label1);
			add(label2);
			add(popupImageScrollPane);

			setOpaque(false);
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

}
