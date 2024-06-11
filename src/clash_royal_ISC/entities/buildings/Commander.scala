package clash_royal_ISC.entities.buildings
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import clash_royal_ISC.entities.Entity
import clash_royal_ISC.projectiles.Typhoon
import clash_royal_ISC.{GameWindow, Grid, Player}
import com.badlogic.gdx.math.Vector2


class Commander(player: Player) extends Building(player) {

  override val spriteWidth: Int = 32
  override val spriteHeight: Int = 32

  override val spriteSheet: Spritesheet = new Spritesheet("res/sprites/buildings/commander.png", this.spriteWidth, this.spriteHeight);

  override val MAX_HEALTH: Int = 50
  override var health: Int = this.MAX_HEALTH
  override val range: Int = 4 * Grid.TILE_SIZE
  override var textureY: Int = 1
  override val animationFramesAmount: Int = 3
  override val animationFramesWaitAmount: Int = 10


  override val attackSpeed: Int = 2
  override val attackDamage: Int = 1

  override def attack(entity: Entity): Unit = {
    new Typhoon(this.attackDamage, this.position, this.target)
  }

  override def dies(): Unit = {
    GameWindow.endGame()
  }
}