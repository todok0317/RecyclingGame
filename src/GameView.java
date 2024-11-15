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

	public GameView(GameModel model) {
		this.gameModel = model;
		setLayout(null); // 절대 레이아웃으로 아이템 위치를 수동으로 설정
		setBackground(StyleManager.backgroundColor);
	}
	
	public void resetView() {
		removeAll(); // 컴포넌트 모두 제거 (아이템, 분리수거 통들)
		
		displayBins();	// 분리수거 통 배치
		displayNewItem();	// 아이템 배치
		displayTools();	// 도구 배치
	}

	private void displayBins() {
		// 분리수거 통들을 배치하는 메소드
		// 중앙 상단에 나란히 배치
		int panelWidth = getWidth();
		int binWidth = gameModel.getBins().get(0).getWidth(); // 분리수거 통의 폭 (모든 통이 동일한 크기라고 가정)
		int binHeight = gameModel.getBins().get(0).getHeight();
		int spacing = 60; // Bin 간격

		List<Bin> bins = gameModel.getBins();
		int totalWidth = (binWidth * bins.size()) + (spacing * (bins.size() - 1));

		int startX = (panelWidth - totalWidth) / 2; // 중앙 정렬을 위한 시작 X 좌표
		int yPosition = 20; // 화면 상단에서 20px 아래

		for (int i = 0; i < bins.size(); i++) {
			bins.get(i).setBounds(startX + i * (binWidth + spacing), yPosition, binWidth, binHeight);
			add(bins.get(i)); // 패널에 추가
		}
	}

	public void displayNewItem() {
		// 새 아이템을 배치하는 메소드
		// 새 아이템을 추가
		// 아이템이 분리되어 현재 아이템이 여러개라면 전부 배치
		List<Item> items = gameModel.getCurrentItem();

		// 아이템의 위치 계산
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
	
	private void displayTools() {
		// 도구들을 배치하는 메소드
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
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("time left: " + gameModel.getTimeLeft(), 900, 600);
		g.drawString("score: " + gameModel.getScore(), 900, 650);
	}
}
