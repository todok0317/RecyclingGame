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
	private final int WIDTH = 1400;
	private final int HEIGHT = 800;

	public GameFrame() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		gameModel = new GameModel();
		gameView = new GameView(gameModel, e -> showLevelSelectMenu());
		levelManager = new LevelManager();

		// showLevelSelectMenu 메소드를 호출하는 리스너를 메인 메뉴에 전달
		mainMenu = new MainMenu(e -> showLevelSelectMenu());
		// startGameWithLevel 메소드를 호출하는 리스너를 레벨 선택 메뉴에 전달
		levelSelectMenu = new LevelSelectMenu(e -> startGameWithLevel(e.getActionCommand()));
		
		
		

		add(mainMenu, "MainMenu"); // 메인메뉴 화면
		add(levelSelectMenu, "LevelSelect"); // 레벨 선택 화면
		add(gameView, "Game"); // 게임 화면

		setTitle("Recycling Simulation Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); // 프레임을 중앙에 표시
	
	
	}

	public void showLevelSelectMenu() {
		// 레벨 선택 화면으로 전환
		cardLayout.show(getContentPane(), "LevelSelect");
	}

	private void startGameWithLevel(String level) {
		// 게임 시작 메소드, 선택한 레벨에 맞는 데이터를 불러와 게임을 시작
		LevelData levelData = levelManager.getLevelData(level); // 레벨 데이터 받기
		gameModel.setLevelData(levelData);	// 해당 레벨로 게임 데이터 설정
		
		cardLayout.show(getContentPane(), "Game"); // 게임 화면으로 전환
		
		new GameController(gameModel, gameView, this).startGame(); // 컨트롤러 생성 및 게임 시작
	}
}
