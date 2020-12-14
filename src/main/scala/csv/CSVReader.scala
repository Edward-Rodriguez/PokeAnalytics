package csv
import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
object CSVReader extends App {

  case class Pokemon(
      pokedexNumber: Int,
      name: String,
      type1: String,
      type2: String,
      totalStat: Int,
      hp: Int,
      attack: Int,
      defense: Int,
      specialAttack: Int,
      specialDefense: Int,
      speed: Int,
      gen: Int,
      legendary: Boolean
  )

  val file = io.Source.fromFile("Pokemon.csv")

  /** convert string array (representing one record from csv)
    * into a tuple (pokedex#, Pokemon)
    * to be stored in Map later on
    *
    * @param record
    * @return
    */
  def convertRecordToPokemon(record: Array[String]): (Int, Pokemon) = {
    record(0).toInt -> Pokemon(
      record(0).toInt, // pokedexNumber
      record(1), // name
      record(2), // type1
      record(3), // type 2
      record(4).toInt, // total
      record(5).toInt, // hp
      record(6).toInt, // attack
      record(7).toInt, // defense
      record(8).toInt, // specialAttack
      record(9).toInt, // specialDefense
      record(10).toInt, // speed
      record(11).toInt, // generation
      record(12).toBoolean // legendary
    )
  }

  val pokeMap =
    file
      .getLines()
      .drop(1)
      .map(line => convertRecordToPokemon(line.split(",")))
      .toMap

  /** WORKING PROGRESS ...
    *
    * @param flag
    * @param data
    * @return
    */
  def searchPokemonForType(
      flag: String,
      data: scala.collection.immutable.Map[Int, Pokemon]
  ): scala.collection.immutable.Map[Int, Pokemon] = {
    data
      .filter(x =>
        (x._2.type1.toLowerCase == flag || x._2.type2.toLowerCase == flag)
      )
      .toMap
  }

  val newMap = searchPokemonForType(args(0).toLowerCase(), pokeMap)
  if (!newMap.isEmpty) newMap.foreach(x => println(x._2.name))
  else "No Pokemon of that type sorry . . ."

}
