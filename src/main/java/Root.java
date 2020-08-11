import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Action;
import javax.swing.border.StrokeBorder;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

public class Root extends Application {

    MusicPlayer musicPlayer = new MusicPlayer();
    Preferences prefs = new Preferences();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tarvis");

        Menu fileMenu = new Menu("File");
        MenuItem addFileMenuItem = new MenuItem("Add new file to Library");
        MenuItem addFolderMenuItem = new MenuItem("Add new folder to Library");
        fileMenu.getItems().addAll(addFileMenuItem, addFolderMenuItem);

        Menu editMenu = new Menu("Edit");

        Menu optionsMenu = new Menu("Options");
        MenuItem prefsMenuItem = new MenuItem("Preferences");
        optionsMenu.getItems().addAll(prefsMenuItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutMenuItem = new MenuItem("About");
        helpMenu.getItems().addAll(aboutMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu, optionsMenu, helpMenu);

        EventHandler<ActionEvent> prefsItemEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Scene prefsScene = new Scene(new StackPane(), 400, 400);
                Stage prefsStage = new Stage();
                prefsStage.setTitle("Preferences");
                prefsStage.setScene(prefsScene);
                prefsStage.show();
            }
        };
        EventHandler<ActionEvent> aboutItemEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Scene aboutScene = new Scene(new StackPane(), 400, 400);
                Stage aboutStage = new Stage();
                //add icon and java and javafx versions here
                
                // String javaVersion = System.getProperty("java.version");
                // String javafxVersion = System.getProperty("javafx.version"); 
                aboutStage.setTitle("About");
                aboutStage.setScene(aboutScene);
                aboutStage.show();
            }
        };
        prefsMenuItem.setOnAction(prefsItemEvent);
        aboutMenuItem.setOnAction(aboutItemEvent);

        BorderPane rootBorderPane = new BorderPane();
        VBox leftSidebar = new VBox(8);
        leftSidebar.setPadding(new Insets(5, 0, 0, 5));
        Label songsLabel = new Label("Songs");
        Label albumLabel = new Label("Albums");
        Label artistsLabel = new Label("Artists");
        Label playlistsLabel = new Label("Playlists");
        leftSidebar.getChildren().addAll(songsLabel, albumLabel, artistsLabel, playlistsLabel);

        ObservableList<String> sidebarList = FXCollections.observableArrayList(
            "Songs",
            "Albums",
            "Artists",
            "Playlists"
        );
        ListView<String> lefSidebarListView = new ListView<>(sidebarList);

        Button playPauseButton = new Button("Play", new ImageView(musicPlayer.darkPlay));
        playPauseButton.setStyle(TarvisStyles.mediaButtonStyle);


        // if(getClass().getResource("mediaButtons.css").toString() ==null){
        //     System.out.println("css is null");
        // }else{
        // }

        EventHandler<ActionEvent> playPauseEvent = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                try{
                // musicPlayer.playMusic();
                playPauseButton.graphicProperty().set(musicPlayer.playMusic());
                }catch(IOException error){
                    error.printStackTrace();
                }
            }
        };

        playPauseButton.setOnAction(playPauseEvent);

        //For right side:
        //VBox (imageview at top, some labels with current song info, media controls, and list view under with array of queued up songs)
        VBox nowPlayingVBox = new VBox();
        nowPlayingVBox.getChildren().addAll(playPauseButton);


        rootBorderPane.setLeft(lefSidebarListView);
        rootBorderPane.setRight(nowPlayingVBox);
        rootBorderPane.setTop(menuBar);
        Scene scene = new Scene(new StackPane(rootBorderPane), 800, 640);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        musicPlayer.quitPlayer();
        super.stop();
    }

    public void startProgram(){
        launch();
    }
    
}