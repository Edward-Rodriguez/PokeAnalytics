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
  val DATABASE_NAME = "pokemon"
  val pokemonCodecRegistry =
    fromRegistries(fromProviders(classOf[Pokemon], DEFAULT_CODEC_REGISTRY))

  val db =
    mongoClient
      .getDatabase(DATABASE_NAME)
      .withCodecRegistry(pokemonCodecRegistry)

  def insertCollectionIntoDatabase(
      nameOfCollection: String,
      data: Seq[Pokemon]
  ) {
    val collection: MongoCollection[Pokemon] =
      db.getCollection(nameOfCollection)
    collection
      .insertMany(data)
      .toFuture()
      .recoverWith { case e: Throwable => { println(e); Future.failed(e) } }
  }

  def getAllPokemonFromCollection(nameOfCollection: String) {
    val collection: MongoCollection[Pokemon] =
      db.getCollection(nameOfCollection)
    collection.find()
  }

  def getAndPostPokemonByStat(
      nameOfCollection: String,
      stat: String,
      limit: Int
  ) {
    val collection: MongoCollection[Pokemon] =
      db.getCollection("Pokemon")
    val results = collection
      .find()
      .projection(include("podexNumber", "name", stat))
      .sort(descending(stat))
      .limit(limit)
  }

  // private def postCollection(
  //     data: FindObservable[Pokemon],
  //     nameOfCollection: String
  // ) {
  //   val collection: MongoCollection[Pokemon] =
  //     db.getCollection(nameOfCollection)
  //   collection.insertOne(data)
  // }

}
