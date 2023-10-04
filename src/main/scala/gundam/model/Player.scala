package gundam.model

import gundam.util.GundamSound
import scalafx.beans.property.DoubleProperty

class Player(val name: String) {
  var gundamDeathCount = 0
  var currentGundam: Gundam = new FreedomGundam with GundamSound //initialize with a default gundam, will be changed by user
  var currentGundamHP: DoubleProperty = DoubleProperty(currentGundam.hp)
  var lost = false

  //Set lost to true if player gundam dies
  def checkGundam(): Unit = if (gundamDeathCount == 1) lost = true

  //Set lost to true
  def lose(): Unit = lost = true

  //Reset values when new game is started
  def reset(): Unit = {
    gundamDeathCount = 0
    lost = false
  }
}
