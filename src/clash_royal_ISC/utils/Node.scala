package clash_royal_ISC.utils

case class Node(x: Int, y: Int, g: Double, h: Double, parent: Option[Node]) {
  def f: Double = g + h
}