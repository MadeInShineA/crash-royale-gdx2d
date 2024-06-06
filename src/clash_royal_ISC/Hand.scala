package clash_royal_ISC

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.interfaces.DrawableObject
import clash_royal_ISC.Hand.{HEIGHT, P1_POSITION, P2_POSITION, SIZE, WIDTH, entitiesArray}
import clash_royal_ISC.Player.playersArray
import clash_royal_ISC.entities.{Deployable, Entity}
import clash_royal_ISC.entities.minions.TestMinion
import com.badlogic.gdx.math.Vector2

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Hand (player: Player) extends DrawableObject{

  val AVAILABLE_ENTITIES: Array[Entity with Deployable] = Array(new TestMinion(this.player))

  val position: Vector2 = (if(playersArray.isEmpty) P1_POSITION else P2_POSITION)

  val entities: ArrayBuffer[Entity with Deployable] = this.createHand()

  def createHand(): ArrayBuffer[Entity with Deployable] = {
    val res: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

    for(i <- 0 until SIZE){
      val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
//      res += this.AVAILABLE_ENTITIES(randomIndex).clone()
      val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
      newEntity.position = new Vector2(i * (WIDTH / SIZE), this.position.y)
      res += newEntity
      entitiesArray += newEntity
    }
    res
  }


  def addEntity(index: Int): Unit = {
    val randomIndex:Int = Random.nextInt(this.AVAILABLE_ENTITIES.length)
    val newEntity: Entity with Deployable = this.AVAILABLE_ENTITIES(randomIndex).copy()
    newEntity.position = new Vector2(index * (WIDTH / SIZE), this.position.y)

    this.entities.insert(index, newEntity)
    entitiesArray += newEntity
    assert(this.entities.length <= SIZE)
  }

  def removeEntity(entity: Entity with Deployable): Int = {
    assert(this.entities.contains(entity))
    val index: Int = this.entities.indexOf(entity)
    this.entities -= entity
    entitiesArray -= entity
    return index
  }

  override def draw(gdxGraphics: GdxGraphics): Unit = {
    for((entity ,index) <- entities.zipWithIndex){
      val xPosition: Float = (index) * (WIDTH / SIZE)
      val yPosition: Float = this.position.y
      entity.drawHandSprite(xPosition, yPosition, gdxGraphics)
    }
  }
}

object Hand {
  val SIZE: Int = 5

  val P1_POSITION: Vector2 = new Vector2(0, Grid.tileSize)
  val P2_POSITION :Vector2 = new Vector2(0, 30 * Grid.tileSize)

  val HEIGHT: Int = 1 * Grid.tileSize
  val WIDTH: Int = GameWindow.WINDOW_WIDTH

  val entitiesArray: ArrayBuffer[Entity with Deployable] = new ArrayBuffer()

  def getEntityAtPosition(x: Float, y: Float): Option[Entity with Deployable] = {
    for(entity: Entity with Deployable <- entitiesArray){
      if(entity.position.x <= x && entity.position.y <= y && entity.position.x + entity.handSpriteWidth >= x && entity.position.y + entity.handSpriteHeight >= y ){
        return Option(entity)
      }
    }
    None
  }

}