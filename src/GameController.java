import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameController implements MouseListener, MouseMotionListener {
	// ***************************
	// GameController 클래스
	// MouseListener, MouseMotionListener를 구현하여 게임의 진행 관리
	// JPanel을 상속 받아 구현
	// ***************************

	private GameModel gameModel;
	private GameView gameView;
	private Timer gameTimer;
	private Item draggedItem;	// 드래그된 아이템 (드래그 할 수 있게 설정된 아이템)

	public GameController(GameModel model, GameView view) {
		this.gameModel = model;
		this.gameView = view;

		gameView.addMouseListener(this);
		gameView.addMouseMotionListener(this);
	}

	public void startGame() {
		// 게임을 시작하는 메소드
		// 1초마다 시간을 감소시키고 남은 시간이 0이 되면 게임을 종료시키는 타이머 생성
		gameTimer = new Timer(1000, e -> {
			gameModel.decrementTime();	// 시간 감소
			gameView.repaint();	// 화면 다시 그리기
			if (gameModel.getTimeLeft() <= 0) {
				endGame();	// 게임 종료
			}
		});
		gameTimer.start();	// 타이머 시작
	}

	private void endGame() {
		// 게임 종료 메소드
		gameTimer.stop();	// 타이머 중지
		// 메시지 다이얼로그로 최종 점수 표시
		JOptionPane.showMessageDialog(gameView, "게임 종료! 점수: " + gameModel.getScore());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 마우스를 누를 시
		Point clickPoint = e.getPoint();	// 마우스를 누른 좌표
		Item currentItem = gameModel.getCurrentItem();	// 현재 제공된 아이템
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
			Point dropPoint = e.getPoint();	// 마우스를 뗀 좌표
			// 마우스를 뗀 좌표에 위치한 분리수거 통을 알아냄
			for (Bin bin : gameModel.getBins()) {
				if (bin.getBounds().contains(dropPoint)) {
					// 올바른 분리수거인지 알아냄
					boolean isCorrect = gameModel.isCorrectBin(bin);
					gameModel.updateScore(isCorrect);	// 점수 업데이트
					gameView.remove(draggedItem);	// 기존 아이템 제거
					gameModel.provideNewItem();		// 새 아이템 제공
					gameView.displayNewItem();		// 새 아이템 배치
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
