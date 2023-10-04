package gundam.view
import gundam.MainApp
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml

@sfxml
class RootLayoutController {
  //Exit game tab
  def handleExit(action: ActionEvent): Unit = {
    System.exit(0)
  }

  //Show tutorial tab
  def handleTutorial(action: ActionEvent): Unit = {
    MainApp.showTutorial()
  }

  //Show history tab
  def handleHistory(action: ActionEvent): Unit = {
    MainApp.showHistory()
  }
}
