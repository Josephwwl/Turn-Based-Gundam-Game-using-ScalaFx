package gundam.view

import gundam.MainApp
import gundam.model.{Game, Player}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, ProgressIndicator}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class GameController(
                      private var gundamImageLeft: ImageView,
                      private val gundamImageRight: ImageView,
                      private val battlegroundImage: ImageView,
                      private var hpLeft: ProgressIndicator,
                      private var hpRight: ProgressIndicator,
                      private var hpLeftText: Label,
                      private var hpRightText: Label,
                      private var gundamNameLeft: Label,
                      private var gundamNameRight: Label,
                      private var gridPaneLeft: GridPane,
                      private var gridPaneRight: GridPane,
                      private var move1LeftButton: Button,
                      private var move2LeftButton: Button,
                      private var move3LeftButton: Button,
                      private var move1RightButton: Button,
                      private var move2RightButton: Button,
                      private var move3RightButton: Button,
                      private var consoleText: Text,
                      private var returnButton: Button
                    ) {

  //Play battle music
  val media = new Media(getClass.getResource("../sound/battleMusic.mp3").toURI.toString)
  val mediaPlayer = new MediaPlayer(media = media)
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  mediaPlayer.play()

  //Victory sound
  val media2 = new Media(getClass.getResource("../sound/victory.mp3").toURI.toString)
  val mediaPlayer2 = new MediaPlayer(media = media2)

  //Background image and dead gundam image
  battlegroundImage.image = new Image(getClass.getResourceAsStream("../image/battlegroundBg.jpg"))
  val deadPNG = "../image/deadGundam.jpg"

  //Initialize game
  var game = new Game(MainApp.player1, MainApp.player2)
  consoleText.text <== game.gameStatus
  gundamNameLeft.text = game.playerLeft.currentGundam.name
  gundamNameRight.text = game.playerRight.currentGundam.name
  updateHPBar(game.playerLeft.currentGundam.hp, game.playerLeft.currentGundam.maxHP, hpLeft, hpLeftText)
  updateHPBar(game.playerRight.currentGundam.hp, game.playerRight.currentGundam.maxHP, hpRight, hpRightText)
  move1LeftButton.text = game.playerLeft.currentGundam.move1.getClass.getSimpleName.dropRight(1)
  move2LeftButton.text = game.playerLeft.currentGundam.move2.getClass.getSimpleName.dropRight(1)
  move3LeftButton.text = game.playerLeft.currentGundam.move3.getClass.getSimpleName.dropRight(1)
  move1RightButton.text = game.playerRight.currentGundam.move1.getClass.getSimpleName.dropRight(1)
  move2RightButton.text = game.playerRight.currentGundam.move2.getClass.getSimpleName.dropRight(1)
  move3RightButton.text = game.playerRight.currentGundam.move3.getClass.getSimpleName.dropRight(1)
  gundamImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.currentGundam.name + ".png"))
  gundamImageLeft.scaleX = -1
  gundamImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.currentGundam.name + ".png"))

  //Handle when game is over
  game.endGame.onChange((_, old, newV) => {
    mediaPlayer.stop()
    mediaPlayer2.setCycleCount(1)
    mediaPlayer2.play()
    gridPaneLeft.disable.value = true
    gridPaneRight.disable.value = true
    returnButton.setVisible(true)
  })

  //Enable moves when its player turn
  game.turn.onChange((_, old, newV) => {
    gridPaneLeft.disable.value = gridPaneRight.disable.value
    gridPaneRight.disable.value = !gridPaneRight.disable.value
  })

  //Player1 hp
  game.playerLeft.currentGundamHP.onChange((_, oldHP, newHP) => {
    updateHPBar(newHP.doubleValue(), game.playerLeft.currentGundam.maxHP, hpLeft, hpLeftText)
    if (game.playerLeft.currentGundam.hp <= 0) {
      gundamImageLeft.image = new Image(getClass.getResourceAsStream(deadPNG))
      gundamImageLeft.scaleX = 1
      game.playerLeft.checkGundam()
    }
  })

  //Player2 hp
  game.playerRight.currentGundamHP.onChange((_, oldHP, newHP) => {
    updateHPBar(newHP.doubleValue(), game.playerRight.currentGundam.maxHP, hpRight, hpRightText)
    if (game.playerRight.currentGundam.hp <= 0) {
      gundamImageRight.image = new Image(getClass.getResourceAsStream(deadPNG))
      game.playerRight.checkGundam()
    }
  })

  //Update hp bar
  def updateHPBar(hp: Double, maxHP: Double, hpBar: ProgressIndicator, hpText: Label): Unit = {
    val hpValue = hp / maxHP
    hpBar.progress = hpValue

    val style =
      if (hpValue >= 0.8) "-fx-accent: YellowGreen;"
      else if (hpValue >= 0.6) "-fx-accent: Gold;"
      else if (hpValue >= 0.4) "-fx-accent: Orange;"
      else "-fx-accent: Tomato;"
    hpBar.setStyle(style)
    hpText.text = f"$hp%.1f" + "/" + maxHP
  }

  //Gundam moves
  def handleMove1Left(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentGundam.attack(game.playerLeft.currentGundam.move1, game.playerRight)
    }
  }

  def handleMove2Left(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentGundam.attack(game.playerLeft.currentGundam.move2, game.playerRight)
    }
  }

  def handleMove3Left(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentGundam.enhancement(game.playerLeft.currentGundam.move3, game.playerRight)
    }
  }

  def handleMove1Right(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentGundam.attack(game.playerRight.currentGundam.move1, game.playerLeft)
    }
  }

  def handleMove2Right(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentGundam.attack(game.playerRight.currentGundam.move2, game.playerLeft)
    }
  }

  def handleMove3Right(action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentGundam.enhancement(game.playerRight.currentGundam.move3, game.playerLeft)
    }
  }

  //Return to main menu button
  def handleReturn(action: ActionEvent): Unit = {
    mediaPlayer.stop()
    mediaPlayer2.stop()
    MainApp.player1.reset
    MainApp.player2.reset
    MainApp.showTitle()
  }
}