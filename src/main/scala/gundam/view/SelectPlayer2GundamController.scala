package gundam.view

import gundam.MainApp
import gundam.model.{EclipseGundam, FreedomGundam, Gundam, JusticeGundam}
import gundam.util.GundamSound
import javafx.collections.{FXCollections, ObservableList}
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ChoiceBox}
import scalafx.util.StringConverter
import scalafxml.core.macros.sfxml

@sfxml
class SelectPlayer2GundamController(
                              private var gundamChoiceBox: ChoiceBox[Gundam],
                              private val confirmButton: Button
                            ) {

  private var selectedGundam: Gundam = _

  confirmButton.disable = true

  //Initialise an observable list with Gundam instances
  val gundamOptions: ObservableList[Gundam] = FXCollections.observableArrayList(
    new FreedomGundam() with GundamSound,
    new JusticeGundam() with GundamSound,
    new EclipseGundam() with GundamSound
  )
  //Populate the the choice box with Gundam choices
  gundamChoiceBox.setItems(gundamOptions)
  gundamChoiceBox.converter = StringConverter.toStringConverter[Gundam](_.name)

  gundamChoiceBox.setOnAction(_ => {
    selectedGundam = gundamChoiceBox.getSelectionModel.getSelectedItem
    confirmButton.disable = false // Enable the confirmButton
  })

  //Confirm player2 gundam choice and start game
  def handleConfirm(action: ActionEvent): Unit = {
    MainApp.player2.currentGundam = selectedGundam
    MainApp.showGame()
  }
}
