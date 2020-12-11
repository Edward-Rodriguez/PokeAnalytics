package csv
import scala.collection.mutable.Map
object CSVReader extends App {

  val m = Map[String, Int]();
  val z = Map[String, Int]();

  val file = io.Source.fromFile("zillow.csv")
  for (line <- file.getLines().drop(1)) {

    var numOfBeds = line.split(",")(2)
    var zipCode = line.split(",")(4)
    var year = line.split(",")(5)
    var price = line.split(",")(6)

    if (m.contains(numOfBeds)) {
      m(numOfBeds) += 1
    } else {
      m(numOfBeds) = 1
    }
  }
  m.foreach(println)
}
