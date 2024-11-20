import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GameController implements MouseListener, MouseMotionListener {
	// ***************************
	// GameController 클래스
	// MouseListener, MouseMotionListener를 구현하여 게임의 진행 관리
	// JPanel을 상속 받아 구현
	// ***************************

	private GameModel gameModel;
	private GameView gameView;
	private GameFrame gameFrame;
	private Timer gameTimer;
	private Item draggedItem; // 드래그된 아이템 (드래그 할 수 있게 설정된 아이템)
	private List<Item> incorrectItems; // 잘못된 분리수거 항목을 저장할 리스트

	public GameController(GameModel model, GameView view, GameFrame gameFrame) {
		this.gameModel = model;
		this.gameView = view;
		this.gameFrame = gameFrame;
		this.incorrectItems = new ArrayList<>();

		// gameView에 마우스리스너 부착
		gameView.addMouseListener(this);
		gameView.addMouseMotionListener(this);
		gameView.levelSelectButton.addActionListener(e -> {
			stopGame();
			gameFrame.showLevelSelectMenu();
		});
	}

	public void startGame() {
		// 게임을 시작하는 메소드
		gameModel.provideNewItem(); // 첫 아이템 제공
		gameView.resetView(); // 게임 화면 초기화
		incorrectItems.clear();

		// 1초마다 시간을 감소시키고 남은 시간이 0이 되면 게임을 종료시키는 타이머 생성
		gameTimer = new Timer(1000, e -> {
			gameModel.decrementTime();
			gameView.repaint();
			if (gameModel.getTimeLeft() <= 0) {
				endGame();
			}
		});
		gameTimer.start(); // 타이머 시작
	}

	private void endGame() {
		// 게임 종료 메소드
		stopGame(); // 게임 중지

		// 게임이 종료된 시점에 FeedbackDialog로 최종 점수와 잘못된 항목을 출력
		FeedbackDialog feedbackDialog = new FeedbackDialog(gameFrame, gameModel.getScore(), incorrectItems);
		feedbackDialog.setVisible(true);

		gameFrame.showLevelSelectMenu();
	}

	// 게임 중지 메소드
	private void stopGame() {
		gameTimer.stop(); // 타이머 중지

		// gameView에서 마우스리스너 제거 (제거 안 해주었더니 게임 재시작 횟수만큼 이벤트 중복 생성)
		gameView.removeMouseListener(this);
		gameView.removeMouseMotionListener(this);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 마우스를 누를 시
		Point clickPoint = e.getPoint(); // 마우스를 누른 좌표
		Item currentItem = gameModel.getCurrentItem(); // 현재 제공된 아이템
		// 누른 좌표가 현재 제공된 아이템 위라면 아이템을 드래그할 수 있게 설정
		if (currentItem != null && currentItem.getBounds().contains(clickPoint)) {
			draggedItem = currentItem;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// 마우스를 누른 후 드래그 시
		// 누른 아이템도 드래그
		if (draggedItem != null) {
			draggedItem.setLocation(e.getX() - draggedItem.getWidth() / 2, e.getY() - draggedItem.getHeight() / 2);
			gameView.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 마우스를 뗄 시
		if (draggedItem != null) {
			Point dropPoint = e.getPoint(); // 마우스를 뗀 좌표
			// 마우스를 뗀 좌표에 위치한 분리수거 통을 알아냄
			for (Bin bin : gameModel.getBins()) {
				if (bin.getBounds().contains(dropPoint)) {
					// 올바른 분리수거인지 알아냄
					boolean isCorrect = gameModel.isCorrectBin(bin);

					gameModel.updateScore(isCorrect); // 점수 업데이트

					if (!isCorrect) {
						// 실패한 항목 리스트에 추가
						incorrectItems.add(draggedItem);
						// X 표시를 드롭 위치에 표시
						gameView.showIncorrectMark(dropPoint);
					}

					gameView.removeItem(); // 기존 아이템 제거
					gameModel.provideNewItem(); // 새 아이템 제공
					gameView.displayNewItem(); // 새 아이템 배치

					break;
				}
			}
			draggedItem = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
