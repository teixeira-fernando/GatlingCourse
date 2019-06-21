package simulations

import baseConfig.BaseSimulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeederToCustom extends BaseSimulation {

  var idNumbers = (1 to 10).iterator

  def getNextGameId() = Map("gameId" -> idNumbers.next())
  val customFeeder = Iterator.continually(getNextGameId())

  def getSpecificVideoGame() = {
    repeat(10) {
      // now call the feeder here
      feed(customFeeder).
        exec(http("Get Specific Video Game")
          .get("videogames/${gameId}") // parameter for the gameId goes here
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Video Game DB")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConf)
}
