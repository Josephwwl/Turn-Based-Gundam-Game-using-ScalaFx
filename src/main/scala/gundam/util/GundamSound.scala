package gundam.util

import gundam.model.SoundEffectPlayer
import scalafx.scene.media.{Media, MediaPlayer}

trait GundamSound extends SoundEffectPlayer {
  def playSelectedSound(filename: String, loop: Boolean = false): Unit ={
    val media = new Media(getClass.getResource("../sound/" + filename).toURI().toString())
    val player = new MediaPlayer(media = media)
    if (loop) player.setCycleCount(MediaPlayer.Indefinite)
    player.stop()
    player.play()
  }

  override def playAttackSound(): Unit = {
    playSelectedSound("attack.mp3")
  }

  override def playDeathSound(): Unit = {
    playSelectedSound("death_sound.mp3")
  }

  override def playEnhanceSound(): Unit = {
    playSelectedSound("enhancement.mp3")
  }
}
