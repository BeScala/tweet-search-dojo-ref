package model

import collection.mutable.ListBuffer
import java.util.UUID
import java.util.concurrent.atomic.AtomicLong

case class SearchTerm(id: Long, text: String)

object SearchTerm {
  val atomicInteger = new AtomicLong(1)

  val terms = new ListBuffer[SearchTerm]

  def save(searchTerm: SearchTerm): SearchTerm = {
    delete(searchTerm.id)
    terms += searchTerm
    searchTerm
  }

  def apply(term: String): SearchTerm = new SearchTerm(atomicInteger.getAndIncrement, term)

  def all: Seq[SearchTerm] = List(terms: _*)

  def findByIdOpt(id: Long): Option[SearchTerm] = terms.find(e => e.id == id)

  def findById(id: Long): SearchTerm = terms.find(e => e.id == id).get

  def delete(id: Long) = {
    findByIdOpt(id).foreach( s=> terms -= s)
  }

  implicit def toActiveRecord(searchTerm: SearchTerm) = new {
    def save() = SearchTerm.save(searchTerm)
  }
}