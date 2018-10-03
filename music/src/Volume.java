
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Volume extends Thread {

   public FileInputStream FIS;
   public BufferedInputStream BIS;
   public String song;
   public String singer;
   public int findslash;
   public int findminus;
   public int finddot;
   public Player player;
   private int index;

   public long pauseLocation; 
   public long songTotalLength;

   public String fileLocation;
   

   // initialize pauseLocation and songTotalLength >> song become reset
   public void Stop() {
      if (player != null) {
         player.close();

         pauseLocation = 0;
         songTotalLength = 0;

      }
   }

   //song is stopped and check pauseLocation
   public void Pause() {
      if (player != null) {
         try {
            pauseLocation = FIS.available();
            player.close();
         } catch (IOException ex) {
         }
      }
   }

   // song restart from pauseLocation
   public void Resume() {
      try {

         if (Music.lblSong.getText().equals("")) {
            File f = new File(fileLocation);
            Music.lblSong.setText(f.getName());
         }
         FIS = new FileInputStream(fileLocation);
         BIS = new BufferedInputStream(FIS);

         player = new Player(BIS);

         FIS.skip(songTotalLength - pauseLocation);

      } catch (FileNotFoundException | JavaLayerException ex) {
      } catch (IOException ex) {
      }

      new Thread() {
         @Override
         public void run() {
            try {
               player.play();
            } catch (JavaLayerException ex) {
            }
         }
      }.start();
   }

   // this function used when Clicked progressbar, song jump to there
   public void Resume2() {
      try {
         player.close();
         if (Music.lblSong.getText().equals("")) {
            File f = new File(fileLocation);
            Music.lblSong.setText(f.getName());
         }
         FIS = new FileInputStream(fileLocation);
         BIS = new BufferedInputStream(FIS);

         player = new Player(BIS);

         FIS.skip(Music.currentLocation);

      } catch (FileNotFoundException | JavaLayerException ex) {
      } catch (IOException ex) {
      }

      new Thread() {
         @Override
         public void run() {
            try {
               player.play();
            } catch (JavaLayerException ex) {
            }
         }
      }.start();
   }

   // song start
   public void Play(final String path,final int index) {

      try {
         this.index=index;
         FIS = new FileInputStream(path);
         BIS = new BufferedInputStream(FIS);

         player = new Player(BIS);

         songTotalLength = FIS.available();

         fileLocation = path + "";

      } catch (FileNotFoundException | JavaLayerException ex) {
      } catch (IOException ex) {
      }

      new Thread() {
         @Override
         public void run() {
            try {
               findslash = path.lastIndexOf("\\");
               findminus = path.lastIndexOf("-");
               finddot = path.lastIndexOf(".");
               singer = path.substring(findslash + 1, findminus);
               song = path.substring(findminus + 1, finddot);

               Music.lblSong.setText(song);
               Music.lblSinger.setText(singer);
               Music.lblSong.setVisible(true);
               player.play();

               if (player.isComplete()) {
                  Play(fileLocation,index);
               }
               if (player.isComplete()) {
                  Music.lblSong.setText(song);
                  Music.lblSong.setVisible(true);
               }
            } catch (JavaLayerException ex) {
            }
         }
      }.start();
   }

   // when it get the filepath it analyze songname, singer, path
   public void setVolume(String path) {
      this.fileLocation = path;
      findslash = path.lastIndexOf("\\");
      findminus = path.lastIndexOf("-");
      finddot = path.lastIndexOf(".");
      singer = path.substring(findslash + 1, findminus);
      song = path.substring(findminus + 1, finddot);
   }

   public String getSong() {
      return song;
   }

   public String getSinger() {
      return singer;
   }
   public int getIndex(){
      return index;
   }

   public void setIndex(int in){
      this.index=in;
   }
}