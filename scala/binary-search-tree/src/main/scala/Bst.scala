import scala.annotation.tailrec

case class Bst[T <% Ordered[T]](value: T,
                                left: Option[Bst[T]] = None, right: Option[Bst[T]] = None)
{
  type Node = Option[Bst[T]]
  def childrenLeftRight: List[Bst[T]] = List(left, right).flatten
  def map[V <% Ordered[V]](f: T => V): Bst[V] =
    Bst(f(value), left.map(l => l.map(f)), right.map(r => r.map(f)))
  def insert(newValue: T): Bst[T] = {
    def createOrInsert(node: Node): Node = {
      node match {
        case n if n.isEmpty => Some(new Bst(newValue))
        case _ => Some(node.get.insert(newValue))
      }
    }

    if (newValue <= value)
      new Bst(value, createOrInsert(left), right)
    else
      new Bst(value, left, createOrInsert(right))
  }

}

object Bst {
  def fromList[T <% Ordered[T]](ls: List[T]) = {
    ls.tail.foldLeft(Bst(ls.head))((node, c) => node.insert(c))
  }
  def toList[T <% Ordered[T]](tree: Bst[T]) = {
    dfs(tree)
  }
  def bfs[T](tree: Bst[T]): List[T] = {
    @tailrec
    def bfsLoop(accum: List[List[T]], nextLayer: List[Bst[T]]): List[T] = nextLayer match {
      case Nil => accum.reverse.flatten
      case _ => bfsLoop(nextLayer.map(_.value) :: accum, nextLayer.flatMap(_.childrenLeftRight))
    }
    bfsLoop(List[List[T]](), List(tree))
  }
  def dfs[T <% Ordered[T]](tree: Bst[T]): List[T] = {
    var output = List[T]()
    tree.map(t => (output = t :: output))
    output.reverse.sortWith(_ < _)
  }
}