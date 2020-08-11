import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;



public class MusicPlayer {

    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    Player player;
    long totalLength;
    long pausePosition= 0;
    PlayingStates playingStates = PlayingStates.NOT_PLAYING;
    

    File musicFile;

    Image darkPause;
    Image darkPlay;


    public MusicPlayer(){
        try {
            darkPause = new Image(new FileInputStream(new File("assets/images/glyphs/darkPause30px.png")));
            darkPlay = new Image(new FileInputStream(new File("assets/images/glyphs/darkPlay30px.png")));
    
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    Runnable runnablePlay = new Runnable(){
        @Override
        public void run(){
            try {
                fileInputStream=new FileInputStream(musicFile);
                totalLength=fileInputStream.available();
                bufferedInputStream=new BufferedInputStream(fileInputStream);
                player=new Player(bufferedInputStream);
                System.out.println(playingStates);
                player.play();
                System.out.println("STOPPED");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnablePause = new Runnable(){
        @Override
        public void run(){

        }
    };

    Runnable runnableResume = new Runnable(){
        @Override
        public void run(){
            try {
                fileInputStream=new FileInputStream(musicFile);
                totalLength=fileInputStream.available();
                fileInputStream.skip(totalLength-pausePosition);
                bufferedInputStream=new BufferedInputStream(fileInputStream);
                player=new Player(bufferedInputStream);
                System.out.println(playingStates);
                player.play();
                System.out.println("STOPPED");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };



    public Node playMusic() throws IOException{
        musicFile = new File("example.mp3");
        switch (playingStates) {
            case NOT_PLAYING:
                Thread playThread = new Thread(runnablePlay);
                fileInputStream = new FileInputStream(musicFile);
                System.out.println("There are "+(fileInputStream.available())+" seconds left in this song");
                playThread.start();
                playingStates = PlayingStates.PLAYING;
                return new ImageView(darkPause);    

            case PLAYING:
                pausePosition = fileInputStream.available();
                System.out.println(pausePosition);
                player.close();
                fileInputStream.close();
                playingStates = PlayingStates.PAUSED;
                return new ImageView(darkPlay);

            case PAUSED:
                playingStates = PlayingStates.RESUME;
                Thread resumeThread = new Thread(runnableResume);
                resumeThread.start();
                return new ImageView(darkPause);
        
            case RESUME:
                pausePosition = fileInputStream.available();
                System.out.println(pausePosition);
                player.close();
                fileInputStream.close();
                playingStates = PlayingStates.PAUSED;
                return new ImageView(darkPlay);

            default:
                return new ImageView(darkPlay);
        }

    }

    public void quitPlayer(){
        if(player == null){

        }else{
            player.close();
        }
    }


}