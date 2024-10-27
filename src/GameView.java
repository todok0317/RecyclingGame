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

	public GameView(GameModel model) {
		this.gameModel = model;
		setLayout(null); // 절대 레이아웃으로 아이템 위치를 수동으로 설정
		setSize(1200, 800);

		displayBins(); 		// 분리수거 통 배치
		displayNewItem();	// 아이템 배치
	}

	private void displayBins() {
		// 분리수거 통들을 배치하는 메소드
		// 중앙 상단에 나란히 배치
		int panelWidth = getWidth();
		int binWidth = gameModel.getBins()[0].getWidth(); // 분리수거 통의 폭 (모든 통이 동일한 크기라고 가정)
		int binHeight = gameModel.getBins()[0].getHeight();
		int spacing = 100; // Bin 간격

		Bin[] bins = gameModel.getBins();
		int totalWidth = (binWidth * bins.length) + (spacing * (bins.length - 1));

		int startX = (panelWidth - totalWidth) / 2; // 중앙 정렬을 위한 시작 X 좌표
		int yPosition = 20; // 화면 상단에서 20px 아래

		for (int i = 0; i < bins.length; i++) {
			bins[i].setBounds(startX + i * (binWidth + spacing), yPosition, binWidth, binHeight);
			add(bins[i]); // 패널에 추가
		}
	}
	
	public void removeItem() {
		// 기존 아이템을 화면에서 제거하는 메소드
		remove(gameModel.getCurrentItem());
	}

	public void displayNewItem() {
		// 새 아이템을 배치하는 메소드
		// 새 아이템을 추가
		Item newItem = gameModel.getCurrentItem();
		add(newItem);

		// 아이템의 위치 계산
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int itemWidth = newItem.getWidth();
		int itemHeight = newItem.getHeight();

		int xPosition = (panelWidth - itemWidth) / 2; // 중앙 정렬을 위한 X 좌표
		int yPosition = panelHeight - itemHeight - 40; // 하단에서 20px 위로 위치

		// 아이템 위치 설정
		newItem.setBounds(xPosition, yPosition, itemWidth, itemHeight);
		setComponentZOrder(newItem, 0); // Item의 z-인덱스를 가장 위로 설정

		repaint(); // 화면 업데이트
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 타이머 및 점수 표시
		g.drawString("남은 시간: " + gameModel.getTimeLeft(), 10, 20);
		g.drawString("점수: " + gameModel.getScore(), 10, 40);
	}
}
