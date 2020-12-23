package scala

import org.mongodb.scala.{MongoClient, MongoCollection}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.scalalogging.LazyLogging
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import scala.concurrent.Future
import org.mongodb.scala.Observable
import scala.concurrent.duration.{Duration, SECONDS}
import scala.util.{Success, Failure}
import org.bson.codecs.configuration.CodecRegistries.{
  fromProviders,
  fromRegistries
}
import org.mongodb.scala.FindObservable
class PokemonDao(mongoClient: MongoClient) extends LazyLogging {
  val DATABASE_NAME = "myPokemonDB"
  val pokemonCodecRegistry =
    fromRegistries(fromProviders(classOf[Pokemon], DEFAULT_CODEC_REGISTRY))

  val db =
    mongoClient
      .getDatabase(DATABASE_NAME)
      .withCodecRegistry(pokemonCodecRegistry)

  def insertCollectionIntoDatabase(
      nameOfCollection: String,
      data: Seq[Pokemon]
  ): Unit = {
    db.getCollection(nameOfCollection).drop() // drop it if exists
    val collection: MongoCollection[Pokemon] =
      db.getCollection(nameOfCollection)
    collection
      .insertMany(data)
      .toFuture()
      .recoverWith { case e: Throwable => { println(e); Future.failed(e) } }
  }

  def getAllPokemonFromCollection(nameOfCollection: String): Unit = {
    val collection: MongoCollection[Pokemon] =
      db.getCollection(nameOfCollection)
    collection.find()
  }

  // /** COMING SOON ...
  //   *
  //   * @param nameOfCollection
  //   * @param stat
  //   * @param limit
  //   */
  // def getAndPostPokemonByStat(
  //     nameOfCollection: String,
  //     stat: String,
  //     limit: Int
  // ): Unit = {
  //   val collection: MongoCollection[Pokemon] =
  //     db.getCollection("pokemon")
  //   val results = collection
  //     .find()
  //     .projection(include("podexNumber", "name", stat))
  //     .sort(descending(stat))
  //     .limit(limit)
  // }

}
