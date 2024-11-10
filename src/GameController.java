import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public GameController(GameModel model, GameView view, GameFrame gameFrame) {
		this.gameModel = model;
		this.gameView = view;
		this.gameFrame = gameFrame;

		// gameView에 마우스리스너 부착
		gameView.addMouseListener(this);
		gameView.addMouseMotionListener(this);
	}

	public void startGame() {
		// 게임을 시작하는 메소드
		gameModel.provideNewItem(); // 첫 아이템 제공
		gameView.resetView(); // 게임 화면 초기화

		// 1초마다 시간을 감소시키고 남은 시간이 0이 되면 게임을 종료시키는 타이머 생성
		gameTimer = new Timer(1000, e -> {
			gameModel.decrementTime(); // 시간 감소
			gameView.repaint(); // 화면 다시 그리기
			if (gameModel.getTimeLeft() <= 0) {
				endGame(); // 게임 종료
			}
		});
		gameTimer.start(); // 타이머 시작
	}

	private void endGame() {
		// 게임 종료 메소드
		gameTimer.stop(); // 타이머 중지
		// 메시지 다이얼로그로 최종 점수 표시,
		JOptionPane.showMessageDialog(gameView,
				"<html>게임 종료!<br>점수: " + gameModel.getScore() + "<br>분리수거에 대해 조금 더 공부합시다</html>", "알림",
				JOptionPane.INFORMATION_MESSAGE);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 수직으로 컴포넌트를 배치

		gameFrame.showLevelSelectMenu(); // 레벨 선택 화면으로 돌아감

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
			sortWaste(dropPoint);
			useTool(dropPoint);
			draggedItem = null;
		}
	}

	private void sortWaste(Point dropPoint) {
		// 분리수거를 수행하는 메소드
		// 마우스를 뗀 좌표에 위치한 분리수거 통을 알아냄
		for (Bin bin : gameModel.getBins()) {
			if (bin.getBounds().contains(dropPoint)) {
				// 올바른 분리수거인지 알아냄
				boolean isCorrect = gameModel.isCorrectBin(bin);
				gameModel.updateScore(isCorrect); // 점수 업데이트
				gameView.removeItem(); // 기존 아이템 제거
				gameModel.provideNewItem(); // 새 아이템 제공
				gameView.displayNewItem(); // 새 아이템 배치
				break;
			}
		}
	}

	private void useTool(Point dropPoint) {
		// 도구를 사용하여 아이템을 가공하는 메소드
		if (gameModel.getTools() != null) {
			// 마우스를 뗀 좌표에 위치한 도구를 알아냄
			for (Tool tool : gameModel.getTools()) {
				if (tool.getBounds().contains(dropPoint)) {
					String toolName = tool.getName();
					String itemType = draggedItem.getType();
					String itemImagePath = draggedItem.getImagePath();
					// 정규식을 사용하여 아이템의 타입에 '#도구이름'이 있는지 알아냄
					Matcher matcher = Pattern.compile("#" + toolName).matcher(itemType);
					if (matcher.find()) { // 있다면 제거
						itemType = itemType.substring(0, matcher.start()) + itemType.substring(matcher.end());
					}
					// 정규식을 사용하여 이미지 경로에 '#도구이름'이 있는지 알아냄
					matcher = Pattern.compile("#" + toolName).matcher(itemImagePath);
					if (matcher.find()) { // 있다면 제거
						itemImagePath = itemImagePath.substring(0, matcher.start())
								+ itemImagePath.substring(matcher.end());
					}
					gameView.removeItem(); // 기존 아이템 제거
					// 현재 제공된 아이템을 도구 사용으로 가공된 아이템으로 변경
					gameModel.changeCurrentItem(draggedItem.getName(), itemType, itemImagePath);
					gameView.displayNewItem(); // 가공된 아이템 배치
					break;
				}
			}
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
