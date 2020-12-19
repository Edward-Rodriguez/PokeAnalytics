package scala

import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import scala.concurrent.ExecutionContext.Implicits.global
import org.bson.codecs.configuration.CodecRegistries.{
  fromProviders,
  fromRegistries
}
import org.mongodb.scala.MongoCollection
import scala.concurrent.Future
import org.mongodb.scala.Observable
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class PokemonDao(mongoClient: MongoClient) {
  val pokemonCodecRegistry =
    fromRegistries(fromProviders(classOf[Pokemon], DEFAULT_CODEC_REGISTRY))

  val db =
    mongoClient
      .getDatabase("pokemon")
      .withCodecRegistry(pokemonCodecRegistry)

  def insertCollectionIntoDatabase(
      nameOfCollection: String,
      data: Seq[Pokemon]
  ) {
    val collection: MongoCollection[Pokemon] =
      db.getCollection(nameOfCollection)
    val query = collection.insertMany(data)
    Await.result(query.toFuture(), Duration(10, SECONDS))
  }

}
