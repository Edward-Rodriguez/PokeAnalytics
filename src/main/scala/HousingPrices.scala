object HousingPrices extends App {
  println("Zip Code, # of Beds, Year, Price")

  val file = io.Source.fromFile("zillow.csv")
  for (line <- file.getLines()) {
    val cols = line.split(",").map(_.trim)
    println(s"${cols(2)} | ${cols(4)} | ${cols(5)} | ${cols(6)}")
  }
}
