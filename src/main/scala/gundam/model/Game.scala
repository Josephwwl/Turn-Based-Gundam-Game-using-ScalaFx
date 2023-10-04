package gundam.model

import scalafx.beans.property.{BooleanProperty, StringProperty}

import java.io.FileWriter
import java.util.Calendar

class Game(val playerLeft: Player, val playerRight: Player) {
  var endGame: BooleanProperty = BooleanProperty(false)

  //[false=player1] and [true=player2]
  var turn: BooleanProperty = BooleanProperty(false)

  var gameStatus: StringProperty = new StringProperty()

  //Determine the next turn
  def next(): Unit = {
    turn.value = !turn.value
  }

  //Check if game progress should continue or end
  def progress(activity : => Unit): Unit = {
    activity
    if (isEndGame) {
      endGame.value = true
      gameStatus.value = winner.name + " Won!"
      recordBattle(winner.name, loser.name)
    } else {
      next()
    }
  }

  //Determine game ended
  def isEndGame: Boolean = {
    playerLeft.lost || playerRight.lost
  }

  //Return winner
  def winner: Player = {
    if (playerLeft.lost) playerRight
    else if (playerRight.lost) playerLeft
    else null
  }

  //Return loser
  def loser: Player = {
    if (playerLeft.lost) playerLeft
    else if (playerRight.lost) playerRight
    else null
  }

  //Record winner, loser and endgame time
  def recordBattle(winnerName: String, loserName: String): Unit = {
    val winRecord = new FileWriter("src/main/scala/gundam/util/GameHistory.txt", true)
    try {
      winRecord.write(winnerName + " wins against " + loserName + " | " + Calendar.getInstance().getTime() + "\n")
    }
    finally winRecord.close()
  }
}
