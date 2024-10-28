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
	private final int WIDTH = 1200;
	private final int HEIGHT = 800;

	public GameFrame() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);

		gameModel = new GameModel();
		gameView = new GameView(gameModel);
		
		// startGame 메소드를 직접 호출하는 ActionListener를 메인 메뉴에 전달
		mainMenu = new MainMenu(e -> startGame());

		add(mainMenu, "MainMenu"); // 메인메뉴 화면
		add(gameView, "Game"); // 게임 화면

		setTitle("Recycling Simulation Game");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	// 게임 시작 메소드
	private void startGame() {
		cardLayout.show(getContentPane(), "Game"); // 게임 화면으로 전환
		gameView.displayNewItem(); // 첫 아이템 표시
		new GameController(gameModel, gameView).startGame(); // 컨트롤러 생성 및 게임 시작
	}
}
