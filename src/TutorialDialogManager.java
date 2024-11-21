import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TutorialDialogManager {
	// ***************************
	// TutorialDialogManager 클래스
	// 레벨 선택 후 게임 시작 전 해당 레벨의 구성과 아이템들 설명 튜토리얼을 관리
	// 다이얼로그들로 만들어 화면에 표시
	// ***************************
	
	private GameFrame gameFrame;
	private boolean isLevelDialogShown = false; // 레벨 다이얼로그가 한 번만 나타나도록 체크하는 변수

	public TutorialDialogManager(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public void showItemDialogs(LevelData levelData, int index, String level) {
		if (index >= levelData.getItemTemplates().size()) {
			return; // 모든 튜토리얼 다이얼로그를 보여준 후 게임 시작
		}

		// 처음에만 레벨 다이얼로그를 띄운다
		if (!isLevelDialogShown) {
			showLevelDialog(level); // 처음에만 레벨 다이얼로그 표시
			isLevelDialogShown = true; // 이후부터 레벨 다이얼로그는 표시되지 않도록 설정
		}

		Item item = levelData.getItemTemplates().get(index);
		ImageIcon itemIcon = new ImageIcon(item.getImagePath());

		String message = getItemMessage(level, item);

		// 제목 레이블 생성
		JLabel titleLabel = new JLabel("Tutorial", JLabel.CENTER);
		titleLabel.setFont(StyleManager.fontLargeBold);
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 아이템 레이블 생성
		JLabel itemLabel = new JLabel(item.getName(), itemIcon, JLabel.CENTER);
		itemLabel.setVerticalTextPosition(JLabel.BOTTOM);
		itemLabel.setHorizontalTextPosition(JLabel.CENTER);
		itemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 설명 레이블 생성
		JLabel instructionLabel = new JLabel(message, JLabel.CENTER);
		instructionLabel.setFont(StyleManager.fontMidiumBold);
		instructionLabel.setForeground(Color.BLACK);
		instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 다이얼로그 박스 구성
		Box dialogBox = Box.createVerticalBox();
		dialogBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // 전체 여백 - 위, 좌, 아래, 우)
		dialogBox.add(titleLabel);
		dialogBox.add(Box.createVerticalStrut(30)); // 제목과 아이템 간의 여백
		dialogBox.add(itemLabel);
		dialogBox.add(Box.createVerticalStrut(15)); // 아이템과 설명 간의 여백
		dialogBox.add(instructionLabel);

		// 아이템 다이얼로그
		JDialog dialog = new JDialog(gameFrame, true);
		// dialog.setUndecorated(true); // 타이틀바 제거
		dialog.setUndecorated(false);

		dialog.setSize(750, 450);
		dialog.setLocationRelativeTo(null); // 위치 선정
		dialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor); // 다이얼로그 배경색 설정
		dialog.getContentPane().setLayout(new BorderLayout());

		// Next 버튼 생성
		JButton nextButton = new JButton("Next");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40));
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			dialog.dispose();
			showItemDialogs(levelData, index + 1, level); // 다음 아이템 다이얼로그 호출
		});

		// 버튼 패널 구성
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(StyleManager.tutorialBackgroudColor); // 버튼 패널 배경색 설정
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30)); // 버튼 패널 여백 -위, 좌, 아래, 우)
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 버튼 중앙 정렬
		buttonPanel.add(nextButton);

		// 다이얼로그에 컴포넌트 추가
		dialog.getContentPane().add(dialogBox, BorderLayout.CENTER);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// 다이얼로그 표시
		dialog.setVisible(true);

	}

	// 아이템 메세지 메서드
	private String getItemMessage(String level, Item item) {
		Map<String, String> binMap = new HashMap<>();
		binMap.put("플라스틱", "플라스틱 수거함");
		binMap.put("유리", "유리 수거함");
		binMap.put("종이", "종이 수거함");
		binMap.put("일반", "일반 쓰레기통");

		switch (level) {
		case "1":
			return item.getName() + "은 " + binMap.getOrDefault(item.getType(), "알 수 없는 통") + "에 넣어주세요!";
		case "2":
			return item.getName() + "은 " + binMap.getOrDefault(item.getType(), "알 수 없는 통") + "에 넣어주세요!";
		case "3":
			return "정확하게 " + item.getName() + "을(를) 올바른 통에 분리수거 해주세요!";
		default:
			return "올바르게 분리수거 해주세요!";
		}
	}

	// 레벨 구성 메세지 메서드
	private void showLevelDialog(String level) {
		String title = "";
		String dialogMessage = "";

		switch (level) {
		case "1":
			title = "LEVEL 1";
			dialogMessage = "환영합니다, 분리배출 히어로!\r\n"
					+ "첫 번째 임무는 기본 분리배출입니다.\r\n 유리병, 음료수캔, 참치캔, 플라스틱 용기를\r\n 올바른 분리배출함에 넣어보세요!";
			break;
		case "2":
			title = "LEVEL 2";
			dialogMessage = "잘하고 있어요! 이제 분리배출의 달인으로 한 단계 더 나아가 볼까요?\r\n"
					+ "이번 레벨에서는 PET와 종량제봉투가 추가됩니다.\r\n 새로운 아이템을 제대로 배출해 보세요!";
			break;
		case "3":
			title = "LEVEL 3";
			dialogMessage = "당신은 이제 분리배출 마스터가 될 준비가 되었습니다!\r\n" + "커터칼과 싱크대를 이용해\r\n 더 복잡한 분리배출을 완수해야 합니다. 도전하세요!";
			break;
		default:
			title = "알 수 없는 레벨";
			dialogMessage = "레벨 정보가 잘못되었습니다.";
			break;
		}

		// 레벨 메세지 메서드 디자인
		JDialog levelDialog = new JDialog(gameFrame, title, true);
		levelDialog.setUndecorated(true); // 다이얼 로그 특유의 아이콘, 제목 없애기
		levelDialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor);
		levelDialog.setSize(750, 400);
		levelDialog.setLocationRelativeTo(gameFrame); // 화면 중앙에 표시

		// 전체 레이아웃을 GridBagLayout으로..
		levelDialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
		gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 사이 여백 추가

		// 제목 레이블
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		titleLabel.setFont(StyleManager.fontLargeBold);
		gbc.gridx = 0;
		gbc.gridy = 0;
		levelDialog.add(titleLabel, gbc);

		// 메시지 레이블
		JLabel messageLabel = new JLabel(
				"<html><div style='text-align: center;'>" + dialogMessage.replaceAll("\r\n", "<br>") + "</div></html>");
		messageLabel.setFont(StyleManager.fontMidiumBold);
		messageLabel.setForeground(Color.BLACK);
		gbc.gridx = 0;
		gbc.gridy = 1;
		levelDialog.add(messageLabel, gbc);

		// 버튼 구성
		JButton nextButton = new JButton("Next");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40));
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			levelDialog.dispose(); // 다이얼로그 닫기
		});

		gbc.gridx = 0;
		gbc.gridy = 2;
		levelDialog.add(nextButton, gbc);

		// 다이얼로그 표시
		levelDialog.setVisible(true);
	}

}
