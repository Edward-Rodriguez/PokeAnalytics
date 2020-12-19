package scala

import play.api.libs.json._
import scala.io.Source
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer

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

  private def parseJSON(pokemonIDOrName: String): JsValue = {
    val src = Source.fromURL(s"$baseURL$pokemonIDOrName").mkString
    Json.parse(src)
  }

  def getPokemonStats(pokemonIdOrName: String): ListBuffer[JsValue] = {
    val json = parseJSON(pokemonIdOrName)
    val pokemon_name = (json \ STAT_NAME).get
    val pokemon_ID = (json \ POKEMON_ID).get
    val typesCategory = (json \ TYPES_CATEGORY).get
    val type1 = getPokemonType(typesCategory, 1)
    val type2 = getPokemonType(typesCategory, 2)
    val statsCategory = (json \ STATS_CATEGORY).get
    val statsList = ListBuffer(pokemon_ID, pokemon_name, type1, type2);
    for (statCol <- 0 to 5)
      statsList.addOne((statsCategory \ statCol \ BASE_STAT).get)
    statsList
  }

  def getPokemonType(json: JsValue, typeNumber: Int): JsValue =
    typeNumber match {
      case 1 => (json \ 0 \ POKEMON_TYPE \ STAT_NAME).get
      case 2 =>
        (json.as[JsArray].value.size) match {
          case 1 => JsString(NO_SECOND_TYPE)
          case _ => (json \ 1 \ POKEMON_TYPE \ STAT_NAME).get
        }
    }

  def convertDataToPokemon(
      data: ListBuffer[JsValue]
  ): Pokemon = {
    Pokemon(
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

  def getPokemonCollection(numOfPokemon: Int): Set[Pokemon] = {
    val pokemonList = ListBuffer[Pokemon]()
    for (pokedexNumber <- 1 to numOfPokemon) {
      pokemonList.addOne(
        convertDataToPokemon(getPokemonStats(pokedexNumber.toString()))
      )
    }
    pokemonList.toSet
  }

}
