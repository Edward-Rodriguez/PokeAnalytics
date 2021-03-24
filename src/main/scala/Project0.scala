package scala

import org.mongodb.scala._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import scala.util.Try

/** Fetches resources from PokeAPI for a given amount of Pokemon
  * Performs some analysis and uploads to database
  */
object Project0 extends App {
  val s3 = new S3Dao

  // // constants
  // val FIRST_GEN = "1st Generation (#1-151)"
  // val SECOND_GEN = "2nd Generation (#1-251)"
  // val THIRD_GEN = "3rd Generation (#1-386)"
  // val FOURTH_GEN = "4th Generation (#1-493)"
  // val FIFTH_GEN = "5th Generation (#1-649)"
  // val SIXTH_GEN = "6th Generation (#1-721)"
  // val SEVENTH_GEN = "7th Generation (#1-809)"

  // // Welcome screen
  // println(
  //   "\u001b[2J" + "\u001b[H" + // clear screen
  //     "\nWelcome to Poke Analytics! \n" +
  //     "Choose the Generation of Pokemon you want to analyze:\n"
  // )
  // println(
  //   s"[1]: $FIRST_GEN\n\n[2]: $SECOND_GEN\n\n[3]: $THIRD_GEN\n\n" +
  //     s"[4]: $FOURTH_GEN\n\n[5]: $FIFTH_GEN\n\n[6]: $SIXTH_GEN\n\n" +
  //     s"[7]: $SEVENTH_GEN\n"
  // )

  // val userInput = getUserInput(7)

  // val userGenChoice: Int = userInput match {
  //   case 1 => 151
  //   case 2 => 251
  //   case 3 => 386
  //   case 4 => 493
  //   case 5 => 649
  //   case 6 => 721
  //   case 7 => 809
  // }

  // val pokeAPI = PokeUtility
  // val pokemonList = Future {
  //   for (pokedexNumber <- 1 to userGenChoice)
  //     yield pokeAPI.getPokemon(pokedexNumber)
  // }
  // val mongoClient = MongoClient()
  // val pokemonDao = new PokemonDao(mongoClient)

  // //Posts entire pokemon list to db when future completes
  // pokemonList.onComplete {
  //   case Success(value) => {
  //     pokemonDao.insertCollectionIntoDatabase("pokemon", value)
  //   }
  //   case Failure(ex) => println(ex)
  // }

  // val genChoice: String = userInput match {
  //   case 1 => FIRST_GEN
  //   case 2 => SECOND_GEN
  //   case 3 => THIRD_GEN
  //   case 4 => FOURTH_GEN
  //   case 5 => FIFTH_GEN
  //   case 6 => SIXTH_GEN
  //   case 7 => SEVENTH_GEN
  // }

  // // Options screen
  // println(
  //   "\u001b[2J" + "\u001b[H" + // clear screen
  //     s"Great! You chose to analyze pokemon from the $genChoice\n\n" +
  //     "What would you like to do?:\n"
  // )
  // println(
  //   "[1]: Get Top (n) Pokemon with the highest Attack \n\n" +
  //     "[2]: Get Top (n) Pokemon with the highest Defense \n"
  // )

  // val userOptionChoice = getUserInput(2);

  // val statChosen: String = userOptionChoice match {
  //   case 1 => "Attack"
  //   case 2 => "Defense"
  // }
  // println(s"\nYou chose Top (n) Pokemon with the highest $statChosen")

  // print("\nPlease enter a value for (n): ")
  // val quantityOfPokemon = getUserInput(userGenChoice);

  // val results = userOptionChoice match {
  //   case 1 => getTopAttackers(quantityOfPokemon)
  //   case 2 => getTopDefenders(quantityOfPokemon)
  //   case _ => {}
  // }

  // print("\nResults have been uploaded to db! \n\n\n")

  // /** Returns a valid user Input
  //   * Recursive calls until valid user input
  //   * @param limit max number a user can input
  //   */
  // private def getUserInput(limit: Int): Int = scala.io.StdIn.readLine() match {
  //   case choice if Try(choice.toInt).isSuccess => {
  //     if (1 to limit contains choice.toInt) choice.toInt
  //     else invalidOption(limit)
  //   }
  //   case _ => invalidOption(limit)
  // }

  // def isValidUserInput(userInput: String): Boolean = {
  //   Try(userInput.toInt).isSuccess
  // }

  // /** Sorts the list of Pokemon by highest attack
  //   * and outputs the nth top attackers to the
  //   * `TopAttackers` collection in the pokemon db
  //   *
  //   * @param quantity top nth Attackers to output
  //   */
  // def getTopAttackers(quantity: Int): Unit = {
  //   val size = pokemonList.map(_.size)
  //   size.onComplete {
  //     case Failure(ex) => println(ex)
  //     case Success(value) => {
  //       pokemonList
  //         .map(
  //           _.sortBy(_.attack)(Ordering[Int].reverse)
  //             .dropRight(value - quantity)
  //         )
  //         .onComplete {
  //           case Failure(ex) => println(ex)
  //           case Success(result) =>
  //             pokemonDao.insertCollectionIntoDatabase(
  //               "highestattackers",
  //               result
  //             )
  //         }
  //     }
  //   }
  // }

  // /** Sorts the list of Pokemon by highest defense
  //   * and outputs the nth top defenders to the
  //   * `TopAttackers` collection in the pokemon db
  //   *
  //   * @param quantity top nth Attackers to output
  //   */
  // def getTopDefenders(quantity: Int): Unit = {
  //   val size = pokemonList.map(_.size)
  //   size.onComplete {
  //     case Failure(ex) => println(ex)
  //     case Success(value) => {
  //       pokemonList
  //         .map(
  //           _.sortBy(_.defense)(Ordering[Int].reverse)
  //             .dropRight(value - quantity)
  //         )
  //         .onComplete {
  //           case Failure(ex) => println(ex)
  //           case Success(result) =>
  //             pokemonDao.insertCollectionIntoDatabase(
  //               "highestdefenders",
  //               result
  //             )
  //         }
  //     }
  //   }
  // }

  // private def invalidOption(limit: Int): Int = {
  //   println(s"Please choose a value from 1 - $limit")
  //   getUserInput(limit)
  // }

}
