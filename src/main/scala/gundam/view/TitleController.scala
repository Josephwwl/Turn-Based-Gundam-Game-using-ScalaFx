package gundam.view

import gundam.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafxml.core.macros.sfxml

@sfxml
class TitleController(private val titleBg: ImageView) {
  //Play title music
  val media = new Media(getClass.getResource("../sound/titleMusic.mp3").toURI.toString)
  var mediaPlayer =  new MediaPlayer(media = media)
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  mediaPlayer.play()
  System.gc()
  titleBg.image = new Image(getClass.getResourceAsStream("../image/titleBg.jpg"))

  //Play button
  def handlePlay(action: ActionEvent): Unit = {
    MainApp.showPlayer1SelectGundam()
    mediaPlayer.stop()
  }

  //Exit button
  def handleExit(action: ActionEvent): Unit = {
    System.exit(0)
  }
}