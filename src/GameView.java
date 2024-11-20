import javax.swing.*;
import java.awt.*;

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

		// X 표시용 JLabel 초기화
		incorrectMarkLabel = new JLabel();
		incorrectMarkLabel.setIcon(getResizedIcon("images/incorrect_mark.png", 200, 200)); // 크기 조정된 아이콘
		incorrectMarkLabel.setSize(200, 200); // 크기 설정
		incorrectMarkLabel.setVisible(false); // 초기에는 보이지 않음
		add(incorrectMarkLabel); // GameView에 추가

		initializeLevelSelectButton();
		setComponentZOrder(levelSelectButton, 0); // 버튼을 최상위로 설정
		revalidate();
		repaint();

	}

	private void initializeLevelSelectButton() {
		levelSelectButton = new JButton("Back");

		int buttonWidth = 100;
		int buttonHeight = 40;

		int buttonX = 30;
		int buttonY = 30;

		levelSelectButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
	}

	public void resetView() {
		removeAll(); // 컴포넌트 모두 제거 (아이템, 분리수거 통들)

		add(levelSelectButton);

		displayBins(); // 분리수거 통 배치
		displayNewItem(); // 아이템 배치
		add(incorrectMarkLabel); // X 표시 JLabel 다시 추가
	}

	private ImageIcon getResizedIcon(String path, int width, int height) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

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

	public void displayBins() {
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

	public void removeItem() {
		remove(gameModel.getCurrentItem());
	}

	public void displayNewItem() {
		Item newItem = gameModel.getCurrentItem();
		add(newItem);

		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int itemWidth = newItem.getWidth();
		int itemHeight = newItem.getHeight();

		int xPosition = (panelWidth - itemWidth) / 2;
		int yPosition = panelHeight - itemHeight - 40;

		newItem.setBounds(xPosition, yPosition, itemWidth, itemHeight);
		setComponentZOrder(newItem, 0); // 아이템을 최상단으로 설정
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 타이머 및 점수 표시
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("time left: " + gameModel.getTimeLeft(), 650, 100);
		g.drawString("score: " + gameModel.getScore(), 200, 650);
	}

}