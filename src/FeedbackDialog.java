import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

	public FeedbackDialog(JFrame parent, int finalScore, List<Item> incorrectItems) {
		super(parent, "게임 종료 - 피드백", true);

		// incorrectItems를 Set으로 설정해 중복 아이템 제거
		Set<Item> incorrectItemSet = incorrectItems.stream()
				.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Item::getImagePath))));

		// 다이얼로그 레이아웃 설정
		setLayout(new BorderLayout());

		// 최종 점수 표시
		JLabel scoreLabel = new JLabel("최종 점수: " + finalScore);
		scoreLabel.setFont(StyleManager.fontMidiumBold);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(scoreLabel);
		contentPanel.add(Box.createVerticalStrut(10)); // 점수와 피드백 간격

		if (!incorrectItemSet.isEmpty()) {
			JLabel feedbackLabel = new JLabel("올바른 분리 수거 방법을 알아보아요! ");
			feedbackLabel.setFont(StyleManager.fontSmallBold);
			feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(feedbackLabel);

			contentPanel.add(Box.createVerticalStrut(20));

			// 잘못된 항목 리스트 표시
			for (Item item : incorrectItemSet) {
				JPanel itemPanel = new JPanel();
				GroupLayout layout = new GroupLayout(itemPanel);
				itemPanel.setLayout(layout);
				itemPanel.setBackground(Color.WHITE);

				// 아이템 이미지
				JLabel imageLabel = new JLabel();
				imageLabel.setIcon(getResizedIcon(item.getImage(), 100, 100)); // 이미지 크기 고정

				// 아이템 설명
				JLabel textLabel = new JLabel(item.getName() + " 용기는 " + item.getType() + " 수거함에 배출해야 합니다.");
				textLabel.setFont(StyleManager.fontSmallRegular);

				// GroupLayout 설정
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);

				layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(imageLabel) // 이미지 왼쪽
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // 간격
						.addComponent(textLabel, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE) // 텍스트 너비
																												// 고정
				);

				layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(imageLabel).addComponent(textLabel));

				contentPanel.add(itemPanel);
				contentPanel.add(Box.createVerticalStrut(10)); // 항목 간 간격
			}
		} else {
			JLabel successLabel = new JLabel("모든 분리수거를 올바르게 수행했습니다! 잘하셨습니다!");
			successLabel.setFont(StyleManager.fontSmallRegular);
			successLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(successLabel);
		}

		// 스크롤 가능하도록 JScrollPane 추가
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setPreferredSize(new Dimension(500, 600)); // 다이얼로그 크기 설정
		add(scrollPane, BorderLayout.CENTER);

		// 닫기 버튼 추가
		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(e -> setVisible(false));
		add(closeButton, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(parent);
	}

	private ImageIcon getResizedIcon(String imagePath, int width, int height) {
		ImageIcon icon = new ImageIcon(imagePath);
		Image img = icon.getImage();
		Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImg);
	}
}
