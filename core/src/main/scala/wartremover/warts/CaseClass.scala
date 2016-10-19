package org.wartremover
package warts

object CaseClass extends WartTraverser {
  def apply(u: WartUniverse): u.Traverser = {
    import u.universe._
    import u.universe.Flag._

    new u.Traverser {
      override def traverse(tree: Tree): Unit = {
        tree match {
          case t if hasWartAnnotation(u)(t)                  => // Ignore trees marked by SuppressWarnings
          case ClassDef(mods, _, _, _) if mods.hasFlag(CASE) => u.error(tree.pos, "case classes are disabled")
          case t                                             => super.traverse(tree)
        }
      }
    }
  }
}
