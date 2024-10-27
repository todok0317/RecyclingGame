import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("분리수거 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GameModel model = new GameModel();
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view);

        frame.add(view);
        frame.setSize(view.getSize());
        frame.setVisible(true);

        controller.startGame();
    }
}
