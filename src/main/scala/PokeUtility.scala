package scala

import play.api.libs.json._
import scala.io.Source
object PokeUtility {

  private val baseURL = "https://pokeapi.co/api/v2/pokemon/"
  // CONSTANTS
  private val STATS_CATEGORY = "stats"
  private val TYPES_CATEGORY = "types"
  private val STAT_NAME = "name"
  private val POKEMON_ID = "id"
  private val BASE_STAT = "base_stat"
  private val POKEMON_TYPE = "type"
  private val POKEMON_SEARCH_RESULTS = "results"
  private val NO_SECOND_TYPE = "none"

  /** Parses a String representing a JSON input and returns a JsValue
    *
    * Downloads the contents of the Pokemon endpoint into a string and converts
    * it to a JsValue
    * @param pokemonIDOrName Resource ID of API endpoint you want to parse
    * @return JsValue of parsed JSON String
    */
  private def parseJSON(pokemonIDOrName: Int): JsValue = {
    val src = Source.fromURL(s"$baseURL$pokemonIDOrName").mkString
    Json.parse(src)
  }

  /** Returns a List of Pokemon Stats
    *
    * Parses JSON and stores relevant data into a List
    * @param pokemonIdOrName Resource ID of API endpoint to fetch
    * @return List of JsValue's representing the pokemon stats
    */
  private def getPokemonStats(pokemonIdOrName: Int): List[JsValue] = {
    val json = parseJSON(pokemonIdOrName)
    val pokemon_name = (json \ STAT_NAME).get
    val pokemon_ID = (json \ POKEMON_ID).get
    val typesCategory = (json \ TYPES_CATEGORY).get
    val type1 = getPokemonType(typesCategory, 1)
    val type2 = getPokemonType(typesCategory, 2)
    val statsCategory = (json \ STATS_CATEGORY).get
    val statsList = List(pokemon_ID, pokemon_name, type1, type2);
    val tempStatList = for (statCol <- 0 to 5) yield {
      (statsCategory \ statCol \ BASE_STAT).get
    }
    statsList ++ tempStatList
  }

  // Parses JSON and Returns `Type` of Pokemon as a JsValue
  private def getPokemonType(json: JsValue, typeNumber: Int): JsValue =
    typeNumber match {
      case 1 => (json \ 0 \ POKEMON_TYPE \ STAT_NAME).get
      case 2 =>
        (json.as[JsArray].value.size) match {
          case 1 => JsString(NO_SECOND_TYPE)
          case _ => (json \ 1 \ POKEMON_TYPE \ STAT_NAME).get
        }
    }

  /** Returns object of type Pokemon with populated fields
    *
    * Converts list of stats into relevant field types and returns
    * an object of type Pokemon
    * @param data List of stats to be inserted into a Pokemon
    * @return Pokemon
    */
  private def convertDataToPokemon(
      data: List[JsValue]
  ): Pokemon = {
    Pokemon(
      data(0).as[Int], //sets _id = pokedex #
      data(0).as[Int], // pokedexNumber
      data(1).as[String], // name
      data(4).as[Int], // hp
      data(5).as[Int], // attack
      data(6).as[Int], // defense
      data(7).as[Int], // specialAttack
      data(8).as[Int], // specialDefense
      data(9).as[Int], // speed
      data(2).as[String], // type1
      data(3).as[String] // optional type 2
    )
  }

  // Returns a Pokemon that has been fetched and converted
  def getPokemon(pokedexNumber: Int): Pokemon = {
    convertDataToPokemon(getPokemonStats(pokedexNumber))
  }

}
