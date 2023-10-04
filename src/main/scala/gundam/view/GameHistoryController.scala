package gundam.view

import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

import scala.io.Source

@sfxml
class GameHistoryController(private var gameHistoryText: Text) {
  // Get and display battle history
  val dataStorage = Source.fromFile("src/main/scala/gundam/util/GameHistory.txt").getLines.mkString("\n")
  gameHistoryText.text = dataStorage
}
