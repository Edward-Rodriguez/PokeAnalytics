package scala

import java.util.HashSet
import org.mongodb.scala._
import scala.collection.JavaConverters._
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import Pokemon._
import PokeUtility._
import scala.io.StdIn.readInt

object Project0 extends App {
  val t1 = System.nanoTime
  val pokeAPI = PokeUtility

  val pokemonCollection: Set[Pokemon] = pokeAPI.getPokemonCollection(20)
  //pokemonCollection.foreach(println)

  val mongoClient = MongoClient()
  val pokemonDao = new PokemonDao(mongoClient)

  // collection
  //   .find()
  //   .subscribe((doc: Document) => println(doc.toJson()))

  // Matches pokemon who's attack >= `minAttack`.
  def findPokemonByAttackGT(minAttack: Int): Set[Pokemon] = {
    pokemonCollection.filter(_.attack >= minAttack)
  }
  val results =
    findPokemonByAttackGT(50).toSeq.sortBy(_.attack)(
      Ordering[Int].reverse
    )
  // println(s"${highestAttack.name} = ${highestAttack.attack}")
  pokemonDao.insertCollectionIntoDatabase("Attack", results)

  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Time it took to run: $duration secs")

}
