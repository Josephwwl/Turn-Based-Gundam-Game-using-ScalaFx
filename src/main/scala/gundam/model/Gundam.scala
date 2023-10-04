package gundam.model

import gundam.model.GundamMove.{BeamRifle, BeamSaber}

import scala.util.Random

abstract class Gundam extends SoundEffectPlayer {
  val name: String
  val maxHP: Double
  var hp: Double
  var attack: Int
  var critDamage: Double
  var critRate: Double
  var move1: AttackMove
  var move2: AttackMove
  var move3: StatusMove

  def critDamageCalculator(chosenMove: AttackMove, attack: Int, critDamage: Double, critRate: Double): Double = {
    val random = new Random()
    val critRoll = random.nextDouble()

    if (critRoll < critRate) {
      ((chosenMove.dealDamage * attack) + (critDamage * (chosenMove.dealDamage * attack)))
    } else {
      (chosenMove.dealDamage * attack)
    }
  }

  def attack(chosenMove: AttackMove, targetPlayer: Player): String = {
    var damage: Double = 0

    //Damage = Move damage x Gundam attack
    //Crit damage = Damage + (Gundam crit damage x Damage)
    //Divided by 30 to reduce the damage number
    if (chosenMove == move2) {
      damage = critDamageCalculator(chosenMove, this.attack, this.critDamage, this.critRate) / 30
    } else {
      damage = (chosenMove.dealDamage * this.attack) / 30
    }

    if (damage < 1) damage = 1
    targetPlayer.currentGundam.hp -= damage

    if (targetPlayer.currentGundam.hp <= 0) {
      targetPlayer.currentGundam.hp = 0
      targetPlayer.currentGundamHP.value = targetPlayer.currentGundam.hp
      targetPlayer.gundamDeathCount += 1
      playDeathSound()
      targetPlayer.checkGundam()
    } else {
      targetPlayer.currentGundamHP.value = targetPlayer.currentGundam.hp
    }

    playAttackSound()
    f"Damage dealt: $damage%.1f"
  }

  def enhancement(chosenMove: StatusMove, targetPlayer: Player): String = {
    var targetGundam: Gundam = null

    if (chosenMove.targetSelf) {
      targetGundam = this
    } else {
      targetGundam = targetPlayer.currentGundam
    }

    playEnhanceSound()
    // Increase gundam crit damage and crit rate
    targetGundam.critRate += chosenMove.critRateAdjustment
    targetGundam.critDamage += chosenMove.critDamageAdjustment
    targetGundam.name +
      "\nCritDmg +" + chosenMove.critDamageAdjustment.toString +
      "\nCritRate +" + chosenMove.critRateAdjustment.toString
  }
}

abstract class Move {
}

abstract class AttackMove extends Move{
  val dealDamage: Int
}

abstract class StatusMove extends Move{
  val critDamageAdjustment: Double
  val critRateAdjustment: Double
  val targetSelf: Boolean
}

object GundamMove {
  object BeamSaber extends AttackMove {
    val dealDamage = 25
  }

  object BeamRifle extends AttackMove {
    val dealDamage = 20
  }
}

object SeedMode extends StatusMove {
  val critDamageAdjustment = 0.2
  val critRateAdjustment = 0.2
  val targetSelf = true
}

abstract class FreedomGundam extends Gundam{
  val name = "FreedomGundam"
  val maxHP = 100
  var hp:Double = maxHP
  var attack = 18
  var critDamage = 0.4
  var critRate = 0.3
  var move1: AttackMove = BeamSaber
  var move2: AttackMove = BeamRifle
  var move3: StatusMove = SeedMode
}

abstract class JusticeGundam extends Gundam {
  val name = "JusticeGundam"
  val maxHP = 100
  var hp:Double = maxHP
  var attack = 21
  var critDamage = 0.15
  var critRate = 0.2
  var move1: AttackMove = BeamSaber
  var move2: AttackMove = BeamRifle
  var move3: StatusMove = SeedMode
}

abstract class EclipseGundam extends Gundam {
  val name = "EclipseGundam"
  val maxHP = 100
  var hp:Double = maxHP
  var attack = 20
  var critDamage = 0.2
  var critRate = 0.5
  var move1: AttackMove = BeamSaber
  var move2: AttackMove = BeamRifle
  var move3: StatusMove = SeedMode
}
