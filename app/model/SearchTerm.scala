

package model

import collection.mutable.ListBuffer

case class SearchTerm(id:Long, text:String)

object SearchTerm {
  val terms  = new ListBuffer[SearchTerm]

  def all : Seq[SearchTerm] = List(terms:_*)

}