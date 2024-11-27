import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FeedbackDialog extends JDialog {
	// ***************************
	// FeedbackDialog 클래스
	// 게임 종료 후 게임 결과와 잘못된 분리배출들에 대한 피드백을 표시하는 다이얼로그
	// 잘못 분리배출된 아이템들의 리스트를 받아와 표시
	// JDialog를 상속 받아 구현
	// ***************************

	public FeedbackDialog(JFrame parent, int finalScore, boolean isHighScore, List<Item> incorrectItems) {
		super(parent, "게임 종료 - 피드백", true);

		// incorrectItems를 Set으로 설정해 중복 아이템 제거
		Set<Item> incorrectItemSet = incorrectItems.stream()
				.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Item::getImagePath))));

		// 다이얼로그 레이아웃 설정
		setLayout(new BorderLayout(20, 20));

		// 최종 점수 표시
		JLabel scoreLabel = new JLabel("최종 점수: " + finalScore);
		scoreLabel.setFont(StyleManager.fontMidiumBold);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel headerPanel = new JPanel(); // 점수와 메시지를 표시하는 패널
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
		headerPanel.add(Box.createVerticalStrut(20));
		headerPanel.add(scoreLabel);

		// 최고 점수를 갱신했다면 알림 표시
		if (isHighScore) {
			JLabel highScoreLabel = new JLabel("축하합니다! 최고 점수를 갱신했습니다!");
			highScoreLabel.setFont(StyleManager.fontMidiumBold);
			highScoreLabel.setForeground(Color.RED);
			highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			headerPanel.add(highScoreLabel);
		}

		if (!incorrectItemSet.isEmpty()) {
			JLabel feedbackLabel = new JLabel("올바른 분리 수거 방법을 알아보아요! ");
			feedbackLabel.setFont(StyleManager.fontSmallBold);
			feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			headerPanel.add(feedbackLabel);

			// 잘못된 분리수거 리스트를 담을 패널
			JPanel contentPanel = new JPanel();
			contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
			contentPanel.add(Box.createVerticalStrut(10)); // 점수와 피드백 간격

			// 스크롤 가능하도록 JScrollPane 추가
			JScrollPane scrollPane = new JScrollPane(contentPanel);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().setUnitIncrement(16);

			// 잘못된 항목 리스트 표시
			for (Item item : incorrectItemSet) {
				JPanel itemPanel = new JPanel();
				itemPanel.setLayout(new BorderLayout(10, 0));
				itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 패널 여백
				itemPanel.setBackground(Color.WHITE);

				// 아이템 이미지
				JLabel imageLabel = new JLabel();
				imageLabel.setIcon(getResizedIcon(item.getImagePath(), 100, 100)); // 이미지 크기 고정

				// 잘못된 도구 사용 피드백 추가
				String feedbackText = item instanceof ComplexItem ? ((ComplexItem) item).getTutorialMessage()
						: item.getTutorialMessage();

				// 아이템 설명
				JLabel feedbackTextLabel = new JLabel(feedbackText);
				feedbackTextLabel.setFont(StyleManager.fontSmallRegular);

				JPanel feedbackTextPanel = new JPanel();
				feedbackTextPanel.setLayout(new BorderLayout());
				feedbackTextPanel.setBackground(Color.WHITE);
				feedbackTextPanel.add(feedbackTextLabel);

				itemPanel.add(imageLabel, BorderLayout.WEST);
				itemPanel.add(feedbackTextPanel, BorderLayout.CENTER);

				contentPanel.add(itemPanel);
				contentPanel.add(Box.createVerticalStrut(10)); // 항목 간 간격
			}

			add(scrollPane, BorderLayout.CENTER);

		} else {
			JLabel successLabel = new JLabel("모든 분리수거를 올바르게 수행했습니다! 잘하셨습니다!");
			successLabel.setFont(StyleManager.fontSmallRegular);
			successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			headerPanel.add(successLabel);
		}

		add(headerPanel, BorderLayout.NORTH);

		// 닫기 버튼 추가
		JPanel buttonPanel = new JPanel();
		JButton closeButton = new JButton("닫기");
		closeButton.setPreferredSize(new Dimension(100, 40));
		closeButton.setBackground(StyleManager.buttonColor);
		closeButton.setFocusPainted(false);
		closeButton.setForeground(Color.WHITE);
		closeButton.setFont(StyleManager.fontMidiumBold);
		closeButton.addActionListener(e -> setVisible(false));
		buttonPanel.add(closeButton);
		add(buttonPanel, BorderLayout.SOUTH);

		// 다이얼로그 크기 조정
		if (incorrectItemSet.size() < 3) {
			pack();
			setSize(500, this.getPreferredSize().height);
		} else {
			setSize(500, 600);
		}
		setLocationRelativeTo(parent);
	}

	private ImageIcon getResizedIcon(String imagePath, int width, int height) {
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}
}
