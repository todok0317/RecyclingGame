import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
	// ***************************
	// GameFrame 클래스
	// 게임의 Frame, JPanel을 상속 받아 구현
	// 게임의 각 화면에 해당하는 JPanel들을 통합하고 CardLayout을 이용하여 화면 전환을 관리
	// ***************************

	private CardLayout cardLayout;
	private GameModel gameModel;
	private GameView gameView;
	private MainMenu mainMenu;
	private LevelSelectMenu levelSelectMenu;
	private LevelManager levelManager;
	private TutorialDialogManager tutorialDialogManager;
	private ScoreManager scoreManager;
	private final int WIDTH = 1400;
	private final int HEIGHT = 800;

	public GameFrame() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		scoreManager = new ScoreManager();
		gameModel = new GameModel(scoreManager);
		gameView = new GameView(gameModel);
		levelManager = new LevelManager();
		tutorialDialogManager = new TutorialDialogManager(this); // 생성자에서 초기화

		// showLevelSelectMenu 메소드를 호출하는 리스너를 메인 메뉴에 전달
		mainMenu = new MainMenu(e -> showLevelSelectMenu());

		// startGameWithLevel 메소드를 호출하는 리스너를 레벨 선택 메뉴에 전달
		levelSelectMenu = new LevelSelectMenu(e -> startGameWithLevel(e.getActionCommand()));

		// 이전 버튼 클릭 시 메인 메뉴로 돌아가는 리스너 추가(지후)
		levelSelectMenu.setBackButtonListener(e -> showMainMenu());

		add(mainMenu, "MainMenu"); // 메인메뉴 화면
		add(levelSelectMenu, "LevelSelect"); // 레벨 선택 화면
		add(gameView, "Game"); // 게임 화면

		setTitle("Recycling Simulation Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 프레임을 중앙에 표시

	}

	// 레벨 선택 화면으로 전환
	public void showLevelSelectMenu() {
		cardLayout.show(getContentPane(), "LevelSelect");
	}

	// 메인 메뉴 화면으로 전환
	private void showMainMenu() {
		cardLayout.show(getContentPane(), "MainMenu");
	}

	// 선택한 레벨에 따른 튜토리얼을 표시
	public void showTutorialDialog(String level) {
		// 선택한 레벨에 따라 LevelData
		LevelData levelData = levelManager.getLevelData(level);
		// 예외 처리
		if (levelData == null || levelData.getItemTemplates().isEmpty()) {
			System.out.println("선택한 레벨에 대한 사진이 없습니다.");
			return;
		}

		// 튜토리얼 다이얼로그 처리를 다른데서 처리
		tutorialDialogManager.showItemDialogs(levelData, 0, level);
	}

	// 게임 시작 메소드, 선택한 레벨에 맞는 데이터를 불러와 게임을 시작
	private void startGameWithLevel(String level) {
		// 해금된 레벨만 플레이 가능
		if (Integer.parseInt(level) > scoreManager.getUnlockedLevel()) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> parent of b15d252 (1.3)
			SoundManager.playSound("click");
			showLevelLockedDialog();
=======
			// 다이얼로그에 이미지와 텍스트 추가
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 수직으로 정렬
			panel.setBackground(StyleManager.tutorialBackgroudColor); // 전체 배경색 설정

			// 이미지 로드 및 크기 조정
			ImageIcon originalIcon = new ImageIcon("images/levelup.png");
			Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 크기 조정
			JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
			imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬

			// "아직 플레이할 수 없습니다" 텍스트 라벨
			JLabel textLabel = new JLabel("레벨업을 해야 합니다.");
			textLabel.setFont(StyleManager.fontLargeBold); // 텍스트 스타일 설정
			textLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬

			// 추가 텍스트: "전 레벨에서 100점을 돌파해야 합니다."
			JLabel extraTextLabel = new JLabel("전 레벨에서 100점을 돌파해야 합니다.");
			extraTextLabel.setFont(StyleManager.fontMidiumBold); // 작은 폰트 설정
			extraTextLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬
			//extraTextLabel.setForeground(Color.GRAY); // 보조 텍스트 색상 설정

			// "확인" 버튼 추가
			JButton confirmButton = new JButton("확인");
			confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // 가운데 정렬
			confirmButton.setFont(StyleManager.fontSmallBold);
			confirmButton.setFocusPainted(false);
			confirmButton.setPreferredSize(new Dimension(100, 50)); // 버튼 크기 유지
			confirmButton.setForeground(Color.WHITE); // 텍스트 색상
			confirmButton.setBackground(StyleManager.buttonColor); // 버튼 배경색
			confirmButton.setOpaque(true); // 불투명 설정 (배경색 보이게)
			confirmButton.setContentAreaFilled(true); // 버튼 배경 렌더링 활성화
			confirmButton.addActionListener(e -> {
				((JDialog) SwingUtilities.getWindowAncestor(confirmButton)).dispose(); // 다이얼로그 닫기
			});


			// 패널에 컴포넌트 추가
			panel.add(imageLabel);
			panel.add(Box.createVerticalStrut(10)); // 이미지와 텍스트 사이에 여백 추가
			panel.add(textLabel);
			panel.add(Box.createVerticalStrut(5)); // 두 텍스트 라벨 사이에 여백 추가
			panel.add(extraTextLabel);
			panel.add(Box.createVerticalStrut(20)); // 텍스트와 버튼 사이 여백 추가
			panel.add(confirmButton);

			// JDialog 생성
			JDialog dialog = new JDialog();
			dialog.setUndecorated(true); // 창 장식 제거
			dialog.setModal(true); // 모달 설정
			dialog.getContentPane().add(panel); // 다이얼로그에 패널 추가
			dialog.setSize(400, 400); // 크기 설정
			dialog.setLocationRelativeTo(levelSelectMenu); // 화면 중앙에 위치
			dialog.setVisible(true); // 다이얼로그 표시
>>>>>>> parent of b284cf5 (Merge branch 'feature' into main)
<<<<<<< HEAD
=======
>>>>>>> parent of 091706a (적용되지 않은 효과음 및 디자인 적용, 코드 정리, 이미지 폴더 정리)
=======
			JOptionPane.showMessageDialog(levelSelectMenu, "아직 플레이할 수 없습니다");
>>>>>>> parent of 079ed8a (튜토리얼 디자인 및 메세지 수정 & 레벨업 필요 튜토리얼 디자인 수정 (#6))
=======
>>>>>>> parent of b15d252 (1.3)
		} else {
			LevelData levelData = levelManager.getLevelData(level); // 레벨 데이터 받기
			gameModel.setLevelData(levelData);	// 해당 레벨로 게임 데이터 설정
			
			cardLayout.show(getContentPane(), "Game"); // 게임 화면으로 전환

			showTutorialDialog(level); // 튜로리얼 표시
			
			new GameController(gameModel, gameView, this).startGame(); // 컨트롤러 생성 및 게임 시작
		}
	}

	private void showLevelLockedDialog() {
		// 다이얼로그에 이미지와 텍스트 추가
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 수직으로 정렬
		panel.setBackground(StyleManager.tutorialBackgroudColor); // 전체 배경색 설정

		// 이미지 로드 및 크기 조정
		ImageIcon originalIcon = new ImageIcon("images/deco/lock.png");
		Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // 크기 조정
		JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
		imageLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬

		// "아직 플레이할 수 없습니다" 텍스트 라벨
		JLabel textLabel = new JLabel("아직 도전할 수 없어요");
		textLabel.setFont(StyleManager.fontLargeBold); // 텍스트 스타일 설정
		textLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬

		// 추가 텍스트: "전 레벨에서 100점을 돌파해야 합니다."
		JLabel extraTextLabel = new JLabel("이전 레벨에서 100점을 돌파 후 도전해보세요!");
		extraTextLabel.setFont(StyleManager.fontMidiumBold); // 작은 폰트 설정
		extraTextLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // 가운데 정렬
		// extraTextLabel.setForeground(Color.GRAY); // 보조 텍스트 색상 설정

		// "확인" 버튼 추가
		JButton confirmButton = new JButton("확인");
		confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT); // 가운데 정렬
		confirmButton.setFont(StyleManager.fontMidiumBold);
		confirmButton.setFocusPainted(false);
		confirmButton.setPreferredSize(new Dimension(100, 50)); // 버튼 크기 유지
		confirmButton.setForeground(Color.WHITE); // 텍스트 색상
		confirmButton.setBackground(StyleManager.buttonColor); // 버튼 배경색
		confirmButton.setOpaque(true); // 불투명 설정 (배경색 보이게)
		confirmButton.setContentAreaFilled(true); // 버튼 배경 렌더링 활성화
		confirmButton.addActionListener(e -> {
			SoundManager.playSound("click");
			((JDialog) SwingUtilities.getWindowAncestor(confirmButton)).dispose(); // 다이얼로그 닫기
		});

		// 패널에 컴포넌트 추가
		panel.add(Box.createVerticalStrut(50));
		panel.add(imageLabel);
		panel.add(Box.createVerticalStrut(20)); // 이미지와 텍스트 사이에 여백 추가
		panel.add(textLabel);
		panel.add(Box.createVerticalStrut(5)); // 두 텍스트 라벨 사이에 여백 추가
		panel.add(extraTextLabel);
		panel.add(Box.createVerticalStrut(20)); // 텍스트와 버튼 사이 여백 추가
		panel.add(confirmButton);

		// JDialog 생성
		JDialog dialog = new JDialog();
		dialog.setUndecorated(true); // 창 장식 제거
		dialog.setModal(true); // 모달 설정
		dialog.getContentPane().add(panel); // 다이얼로그에 패널 추가
		dialog.setSize(400, 400); // 크기 설정
		dialog.setLocationRelativeTo(levelSelectMenu); // 화면 중앙에 위치
		dialog.setVisible(true); // 다이얼로그 표시
	}

}
