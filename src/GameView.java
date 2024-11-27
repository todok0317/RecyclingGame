import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameView extends JPanel {
	// ***************************
	// GameView 클래스
	// 아이템의 배치, 점수, 시간 표시 등 화면에 보여지는 것을 관리
	// JPanel을 상속 받아 구현
	// 주요 로직을 포함하지 않음
	// ***************************

	private GameModel gameModel;
	private JLabel incorrectMarkLabel; // X 표시용 JLabel
	private Timer clearMarkTimer; // X 표시 제거 타이머
	public JButton levelSelectButton;

	public GameView(GameModel model) {
		this.gameModel = model;
		setLayout(null); // 절대 레이아웃 사용
		setBackground(StyleManager.backgroundColor);
<<<<<<< HEAD
		
		//타이머, 스코어 이미지 로드
		try {
<<<<<<< HEAD
			timerIcon = ImageIO.read(new File("images/deco/stopwatch.png"));
			scoreIcon = ImageIO.read(new File("images/deco/star.png"));
=======
			timerIcon = ImageIO.read(new File("images/stopwatch.png"));
			scoreIcon = ImageIO.read(new File("images/star.png"));
			
>>>>>>> parent of b284cf5 (Merge branch 'feature' into main)
		} catch (Exception e) {
			e.printStackTrace();
		}
=======
>>>>>>> parent of bceb675 (아이템, 쓰레기통 이미지 적용, 게임 화면 디자인 (#7))

		// X 표시용 JLabel 초기화
		incorrectMarkLabel = new JLabel();
		incorrectMarkLabel.setIcon(getResizedIcon("images/deco/incorrect_mark.png", 200, 200)); // 크기 조정된 아이콘
		incorrectMarkLabel.setSize(200, 200); // 크기 설정
		incorrectMarkLabel.setVisible(false); // 초기에는 보이지 않음
		add(incorrectMarkLabel); // GameView에 추가

		initializeLevelSelectButton();
		setComponentZOrder(levelSelectButton, 0); // 버튼을 최상위로 설정
		revalidate();
		repaint();

	}

	// 레벨 선택 화면으로 돌아가는 버튼 생성
	private void initializeLevelSelectButton() {
<<<<<<< HEAD
		levelSelectButton = new JButton("이전");
		
		levelSelectButton.setFont(StyleManager.buttonFont);
		levelSelectButton.setFocusPainted(false);
		levelSelectButton.setOpaque(true);
		levelSelectButton.setBackground(StyleManager.buttonColor);
=======
		levelSelectButton = new JButton("Back");
>>>>>>> parent of bceb675 (아이템, 쓰레기통 이미지 적용, 게임 화면 디자인 (#7))

		int buttonWidth = 110;
		int buttonHeight = 50;

		int buttonX = 5;
		int buttonY = 5;

		levelSelectButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
	}

	// 게임 화면 초기화
	public void resetView() {
		removeAll(); // 컴포넌트 모두 제거 (아이템, 분리수거 통들)

		add(levelSelectButton);

		displayBins(); // 분리수거 통 배치
		displayNewItem(); // 아이템 배치
		displayTools();	// 도구 배치
		add(incorrectMarkLabel); // X 표시 JLabel 다시 추가
	}

	private ImageIcon getResizedIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	// X 이미지 표시
	public void showIncorrectMark(Point location) {
		// 기존 타이머가 실행 중이면 중단
		if (clearMarkTimer != null && clearMarkTimer.isRunning()) {
			clearMarkTimer.stop();
		}
		// X 표시 위치 설정 및 표시
		incorrectMarkLabel.setLocation(location.x - 50, location.y - 50); // X 표시를 중심으로 배치
		incorrectMarkLabel.setVisible(true);

		// Z-인덱스를 최상단으로 설정
		setComponentZOrder(incorrectMarkLabel, 0);

		// 1초 뒤에 X 표시 제거
		clearMarkTimer = new Timer(1000, e -> {
			incorrectMarkLabel.setVisible(false);
		});
		clearMarkTimer.setRepeats(false); // 한 번만 실행되도록 설정
		clearMarkTimer.start();
	}

	// 분리수거 통들을 배치하는 메소드
	private void displayBins() {
		// 중앙 상단에 나란히 배치
		int panelWidth = getWidth();
		int binWidth = gameModel.getBins().get(0).getWidth();
		int binHeight = gameModel.getBins().get(0).getHeight();
		int spacing = 60;

		int totalWidth = (binWidth * gameModel.getBins().size()) + (spacing * (gameModel.getBins().size() - 1));
		int startX = (panelWidth - totalWidth) / 2; // 중앙 정렬을 위한 시작 X 좌표
		int yPosition = 150; // 화면 상단에서 20px 아래

		for (int i = 0; i < gameModel.getBins().size(); i++) {
			Bin bin = gameModel.getBins().get(i);
			bin.setBounds(startX + i * (binWidth + spacing), yPosition, binWidth, binHeight);
			add(bin);
		}
	}

	// 새 아이템을 배치하는 메소드
	public void displayNewItem() {
		// 새 아이템을 추가
		// 아이템이 분리되어 현재 아이템이 여러개라면 전부 배치
		List<Item> items = gameModel.getCurrentItem();

		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int itemWidth = items.get(0).getWidth();
		int itemHeight = items.get(0).getHeight();
		int spacing = 40;
		
		int totalWidth = (itemWidth * items.size()) + (spacing * (items.size() - 1));
		int startX = (panelWidth - totalWidth) / 2; // 중앙 정렬을 위한 X 좌표
		int yPosition = panelHeight - itemHeight - 40; // 하단에서 20px 위로 위치

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setBounds(startX + i * (itemWidth + spacing), yPosition, itemWidth, itemHeight);
			add(items.get(i)); // 패널에 추가
			setComponentZOrder(items.get(i), 0); // Item의 z-인덱스를 가장 위로 설정
		}

		repaint(); // 화면 업데이트
	}
	
	// 도구들을 배치하는 메소드
	private void displayTools() {
		// 우측 중앙에 세로로 배치
		if (gameModel.getTools() != null) {
			int panelHeight = getHeight();
			int panelWidth = getWidth();
			int toolWidth = gameModel.getBins().get(0).getWidth(); // 도구의 폭
			int toolHeight = gameModel.getBins().get(0).getHeight();
			int spacing = 10; // tool 간격
	
			List<Tool> tools = gameModel.getTools();
			int totalHeight = (toolHeight * tools.size()) + (spacing * (tools.size() - 1));
	
			int startY = (panelHeight - totalHeight) / 2; // 중앙 정렬을 위한 시작 y 좌표
			int xPosition = panelWidth - toolWidth;
	
			for (int i = 0; i < tools.size(); i++) {
				tools.get(i).setBounds(xPosition, startY + i * (toolWidth + spacing), toolWidth, toolHeight);
				add(tools.get(i)); // 패널에 추가
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 타이머 및 점수 표시
<<<<<<< HEAD
		g.setFont(StyleManager.fontExtraLargeBold);
		g.drawString("  " + remainingTime, 800, 100);
		g.drawString("  " + gameModel.getScore(), 510, 100);
=======
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("time left: " + gameModel.getTimeLeft(), 650, 100);
		g.drawString("score: " + gameModel.getScore(), 200, 650);
>>>>>>> parent of bceb675 (아이템, 쓰레기통 이미지 적용, 게임 화면 디자인 (#7))
	}

}