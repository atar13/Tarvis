import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Preferences extends Application {

    @Override
    public void start(Stage prefStage) throws Exception {
        prefStage.setTitle("Preferences");
        Scene scene = new Scene(new StackPane(),800, 640);
        prefStage.setScene(scene);

    }

    public void startPrefs(){
        launch();
    }



    
}