package scala

import java.util.HashSet
import org.mongodb.scala._
import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import Pokemon._
import Pokemon_Util._
import scala.io.StdIn.readInt

object Project0 extends App {
  val t1 = System.nanoTime

// why case class? has the equals and hashCode method so instances can be compared
// these methods also let you use your objects in collections and sets

  val connection = Pokemon_Util

  val file = io.Source.fromFile("Pokemon.csv")

  val pokeAPI = Pokemon_Util

  val pokemonCollection: Set[Pokemon] = pokeAPI.getPokemonCollection(20)
  //pokemonCollection.foreach(println)

  val mongoClient = MongoClient()
  val database = mongoClient.getDatabase("pokemon")
  val collection = database
    .getCollection("collection")

  //test.foreach(println)
  println(database.name)

  // val doc: Document =
  //   Document("_id" -> 9, "name" -> "Charlie", "age" -> "55", "state" -> "TX")

  // // val doc: Document =
  // //   Document("_id" -> 0, "name" -> "MongoDB", "age" -> "13", "state" -> "Texas")
  // // Await.result(
  // //   database.getCollection("collection").insertOne(doc).head(),
  // //   Duration(10, TimeUnit.SECONDS)
  // // )

  // collection
  //   .find()
  //   .subscribe((doc: Document) => println(doc.toJson()))
  def findPokemonWithHighestAttack(): Set[Pokemon] = {
    pokemonCollection.filter(_.attack >= 20)
  }
  val highestAttack =
    findPokemonWithHighestAttack().toSeq.sortBy(_.attack)(Ordering[Int].reverse)
  // println(s"${highestAttack.name} = ${highestAttack.attack}")
  highestAttack.foreach(x => println(s"${x.name} = ${x.attack}"))
  highestAttack.foreach(println)

  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Time it took to run: $duration secs")

}
