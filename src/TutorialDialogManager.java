import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
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
	
	
		//다이얼로그가 안나오는 현상으로 위해 resetLevelDialogShown를 추가해 초기화해야함..
	 public void resetLevelDialogShown() {
	        isLevelDialogShown = false;
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

		    // 제목 이미지 로드 및 크기 조정
		    ImageIcon titleImageIcon = new ImageIcon("images/tutorial.png");
		    Image titleImage = titleImageIcon.getImage();
		    int titleWidth = 300;  // 원하는 너비
		    int titleHeight = 70; // 원하는 높이
		    Image resizedTitleImage = titleImage.getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH);
		    JLabel titleLabel = new JLabel(new ImageIcon(resizedTitleImage));
		    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		    // 아이템 레이블 생성
		    JLabel itemLabel = new JLabel(item.getName(), itemIcon, JLabel.CENTER);
		    itemLabel.setVerticalTextPosition(JLabel.BOTTOM);
		    itemLabel.setHorizontalTextPosition(JLabel.CENTER);

		    // 도구 레이블 생성 - 레벨 3에서만 추가되면 됨
		    JLabel toolLabel = null;
		    if ("3".equals(level) && item instanceof ComplexItem) {
		        ComplexItem complexItem = (ComplexItem) item;
		        String necessaryToolName = complexItem.getNecessaryTool();
		        String toolImagePath = null;

		        if ("커터".equals(necessaryToolName)) {
		            toolImagePath = "images/cutter.png";
		        } else if ("싱크대".equals(necessaryToolName)) {
		            toolImagePath = "images/sink.png";
		        }

		        if (toolImagePath != null) {
		            ImageIcon toolIcon = new ImageIcon(toolImagePath);
		            toolLabel = new JLabel(necessaryToolName, toolIcon, JLabel.CENTER);
		            toolLabel.setVerticalTextPosition(JLabel.BOTTOM);
		            toolLabel.setHorizontalTextPosition(JLabel.CENTER);
		        }
		    }

		    // 수평 박스 생성: 아이템과 도구를 나란히 배치
		    Box horizontalBox = Box.createHorizontalBox();
		    horizontalBox.add(Box.createHorizontalStrut(20)); // 왼쪽 여백
		    horizontalBox.add(itemLabel);
		    if (toolLabel != null) {
		        horizontalBox.add(Box.createHorizontalStrut(20)); // 아이템과 도구 간 여백
		        horizontalBox.add(toolLabel);
		    }
		    horizontalBox.add(Box.createHorizontalStrut(20)); // 오른쪽 여백
		    horizontalBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		    // 설명 레이블 생성
		    JLabel instructionLabel = new JLabel(message, JLabel.CENTER);
		    instructionLabel.setFont(StyleManager.fontMidiumBold);
		    instructionLabel.setForeground(Color.BLACK);
		    instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		    // 다이얼로그 박스 구성
		    Box dialogBox = Box.createVerticalBox();
		    dialogBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // 전체 여백 - 위, 좌, 아래, 우
		    dialogBox.add(titleLabel);
		    dialogBox.add(Box.createVerticalStrut(30)); // 제목과 수평 박스 간의 여백
		    dialogBox.add(horizontalBox); // 아이템과 도구 추가
		    dialogBox.add(Box.createVerticalStrut(15)); // 수평 박스와 설명 간의 여백
		    dialogBox.add(instructionLabel);

		    // 아이템 다이얼로그
		    JDialog dialog = new JDialog(gameFrame, true);
		    dialog.setUndecorated(false);
		    dialog.setSize(750, 500);
		    dialog.setLocationRelativeTo(null); // 위치 선정
		    dialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor); // 다이얼로그 배경색 설정
		    dialog.getContentPane().setLayout(new BorderLayout());

		    // Next 버튼 생성 (크기 고정 및 정렬 조정)
		    JButton nextButton = new JButton("Next");
		    nextButton.setFocusPainted(false);
		    nextButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 유지
		    nextButton.setBackground(StyleManager.buttonColor);
		    nextButton.setForeground(Color.WHITE);
		    nextButton.setFont(StyleManager.fontMidiumBold);
		    nextButton.addActionListener(e -> {
		        dialog.dispose();
		        showItemDialogs(levelData, index + 1, level); // 다음 아이템 다이얼로그 호출
		    });

		    // 버튼을 포함하는 패널 생성
		    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // 가운데 정렬, 여백 조정
		    buttonPanel.setBackground(StyleManager.tutorialBackgroudColor); // 배경색 다이얼로그와 동일하게 설정
		    buttonPanel.add(nextButton);

		    // 다이얼로그에 구성 추가
		    dialog.getContentPane().add(dialogBox, BorderLayout.CENTER);
		    dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널 추가
		    dialog.setVisible(true);
		}


<<<<<<< HEAD
		Item item = levelData.getItemTemplates().get(index);
		ImageIcon itemIcon = new ImageIcon(item.getImagePath());
		String message = getItemMessage(level, item);

		// 제목 이미지 로드 및 크기 조정
		ImageIcon titleImageIcon = new ImageIcon("images/tutorial.png");
		Image titleImage = titleImageIcon.getImage();
		int titleWidth = 300; // 원하는 너비
		int titleHeight = 70; // 원하는 높이
		Image resizedTitleImage = titleImage.getScaledInstance(titleWidth, titleHeight, Image.SCALE_SMOOTH);
		JLabel titleLabel = new JLabel(new ImageIcon(resizedTitleImage));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 아이템 레이블 생성
		JLabel itemLabel = new JLabel(item.getName(), itemIcon, JLabel.CENTER);
		itemLabel.setVerticalTextPosition(JLabel.BOTTOM);
		itemLabel.setHorizontalTextPosition(JLabel.CENTER);

		// 도구 레이블 생성 - 레벨 3에서만 추가되면 됨
		JLabel toolLabel = null;
		if ("3".equals(level) && item instanceof ComplexItem) {
			ComplexItem complexItem = (ComplexItem) item;
			String necessaryToolName = complexItem.getNecessaryTool();
			String toolImagePath = null;

			if ("커터".equals(necessaryToolName)) {
				toolImagePath = "images/cutter.png";
			} else if ("싱크대".equals(necessaryToolName)) {
				toolImagePath = "images/sink.png";
			}

			if (toolImagePath != null) {
				ImageIcon toolIcon = new ImageIcon(toolImagePath);
				toolLabel = new JLabel(necessaryToolName, toolIcon, JLabel.CENTER);
				toolLabel.setVerticalTextPosition(JLabel.BOTTOM);
				toolLabel.setHorizontalTextPosition(JLabel.CENTER);
			}
		}

		// 수평 박스 생성: 아이템과 도구를 나란히 배치
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(Box.createHorizontalStrut(20)); // 왼쪽 여백
		horizontalBox.add(itemLabel);
		if (toolLabel != null) {
			horizontalBox.add(Box.createHorizontalStrut(20)); // 아이템과 도구 간 여백
			horizontalBox.add(toolLabel);
		}
		horizontalBox.add(Box.createHorizontalStrut(20)); // 오른쪽 여백
		horizontalBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 설명 레이블 생성
		JLabel instructionLabel = new JLabel(message, JLabel.CENTER);
		instructionLabel.setFont(StyleManager.fontMidiumBold);
		instructionLabel.setForeground(Color.BLACK);
		instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 다이얼로그 박스 구성
		Box dialogBox = Box.createVerticalBox();
		dialogBox.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // 전체 여백 - 위, 좌, 아래, 우
		dialogBox.add(titleLabel);
		dialogBox.add(Box.createVerticalStrut(30)); // 제목과 수평 박스 간의 여백
		dialogBox.add(horizontalBox); // 아이템과 도구 추가
		dialogBox.add(Box.createVerticalStrut(15)); // 수평 박스와 설명 간의 여백
		dialogBox.add(instructionLabel);

		// 아이템 다이얼로그
		JDialog dialog = new JDialog(gameFrame, true);
		dialog.setUndecorated(false);
		dialog.setSize(750, 500);
		dialog.setLocationRelativeTo(null); // 위치 선정
		dialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor); // 다이얼로그 배경색 설정
		dialog.getContentPane().setLayout(new BorderLayout());

		// Next 버튼 생성 (크기 고정 및 정렬 조정)
		JButton nextButton = new JButton("Next");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 유지
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			SoundManager.playSound("click");
			dialog.dispose();
			showItemDialogs(levelData, index + 1, level); // 다음 아이템 다이얼로그 호출
		});

		// 버튼을 포함하는 패널 생성
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10)); // 가운데 정렬, 여백 조정
		buttonPanel.setBackground(StyleManager.tutorialBackgroudColor); // 배경색 다이얼로그와 동일하게 설정
		buttonPanel.add(nextButton);

		// 다이얼로그에 구성 추가
		dialog.getContentPane().add(dialogBox, BorderLayout.CENTER);
		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH); // 버튼 패널 추가
		dialog.setVisible(true);
	}

<<<<<<< HEAD
	// 레벨 설명 다이얼로그를 표시하는 메소드
=======
=======
	private String getItemMessage(String level, Item item) {
		// 분리수거함 이름 정의
		Map<String, String> binMap = new HashMap<>();
		binMap.put("플라스틱", "플라스틱 수거함");
		binMap.put("유리", "유리 수거함");
		binMap.put("종이", "종이 수거함");
		binMap.put("일반", "일반 쓰레기통");
		binMap.put("비닐", "비닐 수거함");
		binMap.put("페트", "페트 수거함");

		switch (level) {
		case "1":
		case "2":
			return item.getName() + "은 " + binMap.getOrDefault(item.getType(), "알 수 없는 통") + "에 넣어주세요!";
		case "3":
			if (item instanceof ComplexItem) {
				ComplexItem complexItem = (ComplexItem) item;
				if (complexItem.getSubItem() == null) {
					// 서브 아이템이 없는 경우
					return complexItem.getName() + "은 " + complexItem.getNecessaryTool() + "에 버린 후 "
							+ binMap.getOrDefault(complexItem.getMainItem().getType(), "알 수 없는 통") + "에 넣어주세요!";
				} else {
					// 서브 아이템이 있는 경우
					return "<html>" + complexItem.getName() + "은 " + complexItem.getNecessaryTool() + "를 사용 후<br>"
							+ complexItem.getMainItem().getName() + "은 "
							+ binMap.getOrDefault(complexItem.getMainItem().getType(), "알 수 없는 통") + "에, "
							+ complexItem.getSubItem().getName() + "은 "
							+ binMap.getOrDefault(complexItem.getSubItem().getType(), "알 수 없는 통") + "에 넣어주세요!"
							+ "</html>";

				}
			}
			// 일반 아이템의 경우
			return item.getName() + "을(를) 올바르게 분리수거 해주세요!";
		default:
			return "올바르게 분리수거 해주세요!";
		}
	}

	private void showLevelDialog(String level) {
		String dialogMessage = "";
		String imagePath = "";
		ImageIcon titleImageIcon = null;
>>>>>>> parent of 091706a (적용되지 않은 효과음 및 디자인 적용, 코드 정리, 이미지 폴더 정리)


	private String getItemMessage(String level, Item item) {
	    // 분리수거함 이름 정의
	    Map<String, String> binMap = new HashMap<>();
	    binMap.put("플라스틱", "플라스틱 수거함");
	    binMap.put("유리", "유리 수거함");
	    binMap.put("종이", "종이 수거함");
	    binMap.put("일반", "일반 쓰레기통");
	    binMap.put("비닐", "비닐 수거함");
	    binMap.put("페트", "페트 수거함");

	    switch (level) {
	        case "1":
	        case "2":
	            return item.getName() + "은 " + binMap.getOrDefault(item.getType(), "알 수 없는 통") + "에 넣어주세요!";
	        case "3":
	            if (item instanceof ComplexItem) {
	                ComplexItem complexItem = (ComplexItem) item;
	                if (complexItem.getSubItem() == null) {
	                    // 서브 아이템이 없는 경우
	                    return complexItem.getName() + "은 " 
	                            + complexItem.getNecessaryTool() + "에 버린 후 " 
	                            + binMap.getOrDefault(complexItem.getMainItem().getType(), "알 수 없는 통") + "에 넣어주세요!";
	                } else {
	                    // 서브 아이템이 있는 경우
	                	return "<html>" 
	                    + complexItem.getName() + "은 " 
	                    + complexItem.getNecessaryTool() + "를 사용 후<br>"
	                    + complexItem.getMainItem().getName() + "은 " 
	                    + binMap.getOrDefault(complexItem.getMainItem().getType(), "알 수 없는 통") + "에, "
	                    + complexItem.getSubItem().getName() + "은 "
	                    + binMap.getOrDefault(complexItem.getSubItem().getType(), "알 수 없는 통") + "에 넣어주세요!"
	                    + "</html>";

	                }
	            }
	            // 일반 아이템의 경우
	            return item.getName() + "을(를) 올바르게 분리수거 해주세요!";
	        default:
	            return "올바르게 분리수거 해주세요!";
	    }
	}


>>>>>>> parent of b284cf5 (Merge branch 'feature' into main)
	private void showLevelDialog(String level) {
	    String dialogMessage = "";
	    String imagePath = "";
	    ImageIcon titleImageIcon = null;

<<<<<<< HEAD
		// 레벨별 이미지 경로와 메시지 설정
		switch (level) {
		case "1":
			titleImageIcon = new ImageIcon("images/level1.png"); // 제목 이미지 경로
			dialogMessage = "환영합니다, 분리배출 히어로!\r\n" + "첫 번째 임무는 기본 분리배출입니다.\r\n 유리병, 캔, 플라스틱 용기를\r\n 올바른 분리배출함에 넣어보세요!";
			imagePath = "images/level1_image.png";
			break;
		case "2":
			titleImageIcon = new ImageIcon("images/level2.png");
			dialogMessage = "잘하고 있어요! 이제 분리배출의 달인으로 한 단계 더 나아가 볼까요?\r\n"
					+ "이번 레벨에서는 PET와 종량제봉투가 추가됩니다.\r\n 새로운 아이템을 제대로 배출해 보세요!";
			imagePath = "images/level2_image.png";
			break;
		case "3":
			titleImageIcon = new ImageIcon("images/level3.png");
			dialogMessage = "당신은 이제 분리배출 마스터가 될 준비가 되었습니다!\r\n" + "커터칼과 싱크대를 이용해\r\n 더 복잡한 분리배출을 완수해야 합니다. 도전하세요!";
			imagePath = "images/level3_image.png";
			break;
		default:
			titleImageIcon = new ImageIcon("images/level1.png"); // 기본 이미지
			dialogMessage = "레벨 정보가 잘못되었습니다.";
			break;
		}
=======
	    // 레벨별 이미지 경로와 메시지 설정
	    switch (level) {
	        case "1":
	            titleImageIcon = new ImageIcon("images/level1.png");  // 제목 이미지 경로
	            dialogMessage = "환영합니다, 분리배출 히어로!\r\n"
	                    + "첫 번째 임무는 기본 분리배출입니다.\r\n 유리병, 캔, 플라스틱 용기를\r\n 올바른 분리배출함에 넣어보세요!";
	            imagePath = "images/level1_image.png";
	            break;
	        case "2":
	            titleImageIcon = new ImageIcon("images/level2.png");
	            dialogMessage = "잘하고 있어요! 이제 분리배출의 달인으로 한 단계 더 나아가 볼까요?\r\n"
	                    + "이번 레벨에서는 PET와 종량제봉투가 추가됩니다.\r\n 새로운 아이템을 제대로 배출해 보세요!";
	            imagePath = "images/level2_image.png";
	            break;
	        case "3":
	            titleImageIcon = new ImageIcon("images/level3.png");
	            dialogMessage = "당신은 이제 분리배출 마스터가 될 준비가 되었습니다!\r\n"
	                    + "커터칼과 싱크대를 이용해\r\n 더 복잡한 분리배출을 완수해야 합니다. 도전하세요!";
	            imagePath = "images/level3_image.png";
	            break;
	        default:
	            titleImageIcon = new ImageIcon("images/level1.png");  // 기본 이미지
	            dialogMessage = "레벨 정보가 잘못되었습니다.";
	            break;
	    }
>>>>>>> parent of b284cf5 (Merge branch 'feature' into main)

	    // 레벨 메세지 메서드 디자인
	    JDialog levelDialog = new JDialog(gameFrame, true);
	    levelDialog.setUndecorated(true); // 다이얼 로그 특유의 아이콘, 제목 없애기
	    levelDialog.getContentPane().setBackground(StyleManager.tutorialBackgroudColor);
	    levelDialog.setSize(750, 600);
	    levelDialog.setLocationRelativeTo(gameFrame); // 화면 중앙에 표시

	    // 전체 레이아웃을 GridBagLayout으로..
	    levelDialog.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
	    gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 사이 여백 추가

	    // 제목 이미지를 레이블로 추가
	    JLabel titleLabel = new JLabel(titleImageIcon);
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    levelDialog.add(titleLabel, gbc);

	    // 이미지 레이블
	    if (!imagePath.isEmpty()) {
	        ImageIcon levelImageIcon = new ImageIcon(imagePath);
	        Image img = levelImageIcon.getImage();

	        // 이미지 크기 조정 (원하는 크기로)
	        int newWidth = 300; // 원하는 너비
	        int newHeight = 250; // 원하는 높이
	        Image scaledImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

	        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        levelDialog.add(imageLabel, gbc);
	    }

	    // 메시지 레이블
	    JLabel messageLabel = new JLabel(
	            "<html><div style='text-align: center;'>" + dialogMessage.replaceAll("\r\n", "<br>") + "</div></html>");
	    messageLabel.setFont(StyleManager.fontMidiumBold);
	    messageLabel.setForeground(Color.BLACK);
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    levelDialog.add(messageLabel, gbc);

<<<<<<< HEAD
		// 버튼 구성
		JButton nextButton = new JButton("Next");
		nextButton.setFocusPainted(false);
		nextButton.setPreferredSize(new Dimension(100, 40));
		nextButton.setBackground(StyleManager.buttonColor);
		nextButton.setForeground(Color.WHITE);
		nextButton.setFont(StyleManager.fontMidiumBold);
		nextButton.addActionListener(e -> {
			SoundManager.playSound("click");
			levelDialog.dispose(); // 다이얼로그 닫기
		});
=======
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
>>>>>>> parent of b284cf5 (Merge branch 'feature' into main)

	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    levelDialog.add(nextButton, gbc);

	    // 다이얼로그 표시
	    levelDialog.setVisible(true);
	}

}
