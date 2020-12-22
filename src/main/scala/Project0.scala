package scala

import org.mongodb.scala._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import play.api.libs.json.JsTrue
import scala.util.Try

/** Fetches resources from PokeAPI for a given amount of Pokemon
  * Performs some analysis and uploads stores into database
  */
object Project0 extends App {
  val t1 = System.nanoTime
  // constants
  val FIRST_GEN = "1st Generation (#1-151)"
  val SECOND_GEN = "2nd Generation (#1-251)"
  val THIRD_GEN = "3rd Generation (#1-386)"
  val FOURTH_GEN = "4th Generation (#1-493)"
  val FIFTH_GEN = "5th Generation (#1-649)"
  val SIXTH_GEN = "6th Generation (#1-721)"
  val SEVENTH_GEN = "7th Generation (#1-809)"

  // Welcome screen
  Console.println(
    "\u001b[2J" + "\u001b[H" + // clear screen
      "Welcome to Poke Analytics! \n" +
      "Choose the Generation of Pokemon you want to analyze:"
  )
  Console.println(
    s"[1]: $FIRST_GEN\n[2]: $SECOND_GEN\n[3]: $THIRD_GEN\n" +
      s"[4]: $FOURTH_GEN\n[5]: $FIFTH_GEN\n[6]: $SIXTH_GEN\n" +
      s"[7]: $SEVENTH_GEN\n"
  )

  val userGenChoice = getUserInput(7)
  val pokeAPI = PokeUtility
  val pokemonList = Future {
    for (pokedexNumber <- 1 to userGenChoice)
      yield pokeAPI.getPokemon(pokedexNumber)
  }

  val mongoClient = MongoClient()
  val pokemonDao = new PokemonDao(mongoClient)

  // Posts entire pokemon list to db when future completes
  pokemonList.onComplete {
    case Success(value) => {
      pokemonDao.insertCollectionIntoDatabase("Pokemon", value)
    }
    case Failure(ex) => println(ex)
  }

  // Options screen
  val genChoice: String = userGenChoice match {
    case 1 => FIRST_GEN
    case 2 => SECOND_GEN
    case 3 => THIRD_GEN
    case 4 => FOURTH_GEN
    case 5 => FIFTH_GEN
    case 6 => SIXTH_GEN
    case 7 => SEVENTH_GEN
  }
  Console.println(
    s"Great! You chose to analyze pokemon from the $genChoice\n" +
      "What would you like to do?:"
  )
  Console.println(
    "[1]: Get Pokemon with the highest Attack \n" +
      "[2]: COMING SOON ... \n" +
      "[3]: COMING SOON ... \n"
  )

  val userChoice = getUserInput(1)

  /** Returns a valid user Input
    * Recursive calls until valid user input
    * @param limit max number user can input
    */
  private def getUserInput(limit: Int): Int = scala.io.StdIn.readLine match {
    case choice if Try(choice.toInt).isSuccess => {
      if (1 to limit contains choice.toInt) choice.toInt
      else invalidOption(limit)
    }
    case _ => invalidOption(limit)
  }

  /** Sorts the list of Pokemon by highest attackers
    * and outputs the nth top attackers to the
    * `TopAttackers` collection in the pokemon db
    *
    * @param quantity top nth Attackers to output
    */
  def getTopAttackers(quantity: Int) {
    val size = pokemonList.map(_.size)
    size.onComplete {
      case Failure(ex) => println(ex)
      case Success(value) => {
        pokemonList
          .map(
            _.sortBy(_.attack)(Ordering[Int].reverse)
              .dropRight(value - quantity)
          )
          .onComplete {
            case Failure(ex) => println(ex)
            case Success(result) =>
              pokemonDao.insertCollectionIntoDatabase("TopAttackers", result)
          }
      }
    }
  }

  private def invalidOption(limit: Int): Int = {
    println(s"Please choose an option from 1 - $limit")
    getUserInput(limit)
  }

  val duration = (System.nanoTime - t1) / 1e9d
  println(s"Time it took to run: $duration secs")

}
