package scala
import org.bson.types.ObjectId

/** Factory for Pokemon instances */
object Pokemon {

  /** Creates a pokemon with given stats
    *
    * @param id for _id field in mongo db
    * id field = pokedex number
    * @param pokedexNumber pokemon's pokedex number
    * @param name pokemon's name
    * @param hp pokemon's base hp
    * @param attack pokemon's base attack
    * @param defense pokemon's base defense
    * @param specialAttack pokemon's base special attack
    * @param specialDefense pokemon's base special defense
    * @param speed pokemon's base speed
    * @param type1 pokemon's first type
    * @param type2 pokemon's second type ("none") if it has no type
    * @return Pokemon
    */
  def apply(
      id: Int,
      pokedexNumber: Int,
      name: String,
      hp: Int,
      attack: Int,
      defense: Int,
      specialAttack: Int,
      specialDefense: Int,
      speed: Int,
      type1: String,
      type2: String
  ): Pokemon = new Pokemon(
    id: Int,
    pokedexNumber: Int,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    specialAttack: Int,
    specialDefense: Int,
    speed: Int,
    type1: String,
    type2: String
  )
}
case class Pokemon(
    _id: Int,
    pokedexNumber: Int,
    name: String,
    hp: Int,
    attack: Int,
    defense: Int,
    specialAttack: Int,
    specialDefense: Int,
    speed: Int,
    type1: String,
    type2: String
)
