package scala
import org.bson.types.ObjectId

object Pokemon {
  def apply(
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
    new ObjectId,
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
    _id: ObjectId,
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
    // total: Int,
    // generation: Int,
    // legendary: Boolean
)
