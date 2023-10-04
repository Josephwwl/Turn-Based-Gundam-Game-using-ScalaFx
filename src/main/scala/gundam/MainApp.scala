package gundam

import MainApp.stage
import gundam.model.Player
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.{Stage, Modality}
import scalafx.scene.Scene
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.Includes._
import javafx.scene.image.Image

object MainApp extends JFXApp {
  val player1 = new Player("Player1")
  val player2 = new Player("Player2")

  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Gundam: Fighting Game"
    width = 916
    height = 664
    icons += new Image(getClass.getResource("image/titleLogo.jpg").toURI.toString)
    scene = new Scene {
      root = roots
    }
  }

  def showTitle(): Unit = {
    val resource = getClass.getResource("view/Title.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showGame(): Unit = {
    val resource = getClass.getResource("view/Game.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showPlayer1SelectGundam(): Unit = {
    val resource = getClass.getResource("view/SelectPlayer1Gundam.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showPlayer2SelectGundam(): Unit = {
    val resource = getClass.getResource("view/SelectPlayer2Gundam.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showTutorial(): Unit = {
    val resource = getClass.getResource("view/Tutorial.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]

    val tutorialStage = new Stage {
      title = "Tutorial"
      width = 900
      height = 600
      icons += new Image(getClass.getResource("image/titleLogo.jpg").toURI.toString)
      scene = new Scene {
        root = roots
      }
    }

    tutorialStage.initModality(Modality.WindowModal)
    tutorialStage.show()
  }

  def showHistory(): Unit = {
    val resource = getClass.getResource("view/GameHistory.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]

    val historyStage = new Stage {
      title = "Battle History"
      width = 916
      height = 664
      icons += new Image(getClass.getResource("image/titleLogo.jpg").toURI.toString)
      scene = new Scene {
        root = roots
      }
    }

    historyStage.initModality(Modality.ApplicationModal)
    historyStage.show()
  }

  showTitle()
}
