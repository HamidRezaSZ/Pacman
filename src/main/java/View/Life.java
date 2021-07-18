package View;

import Controller.GameProgramController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class Life extends Application {
    public static Life life;
    public Button muteButton;
    private static Stage stage;
    public static int lifeCounter = 2;
    public Button downButton;
    public Button upButton;
    public Label lifeFixedLabel;
    public Button confirmButton;
    public Label lifeLabel;

    public static Life getInstance() {
        if (life == null)
            life = new Life();
        return life;
    }

    public void increaseLife(MouseEvent mouseEvent) {
        if (lifeCounter < 5) {
            ++lifeCounter;
            lifeLabel.setText(String.valueOf(lifeCounter));
        }
    }

    public void decreaseLife(MouseEvent mouseEvent) {
        if (lifeCounter > 2) {
            --lifeCounter;
            lifeLabel.setText(String.valueOf(lifeCounter));
        }
    }

    public void confirm(MouseEvent mouseEvent) throws Exception {
        User.getUserByUsername(GameProgramController.getLoginUser().getUsername()).setHeart(lifeCounter);
        GameView.getInstance().start(stage);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL lifeUrl = getClass().getResource("/fxml/life.fxml");
        Parent root = FXMLLoader.load(lifeUrl);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        Life.stage = stage;
        root.setId("life");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
    }

    public void mute(MouseEvent mouseEvent) {
        WelcomeMenu.mediaPlayer.setMute(!WelcomeMenu.isMute);
        if (!WelcomeMenu.isMute)
            muteButton.setText("UnMute");
        else
            muteButton.setText("Mute");
        WelcomeMenu.isMute = !WelcomeMenu.isMute;
    }
}
