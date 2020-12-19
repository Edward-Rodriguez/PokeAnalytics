package scala
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.io.{Codec, Source}

import play.api.libs.json._
case class Pokemon(
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
