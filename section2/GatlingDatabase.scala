
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GatlingDatabase extends Simulation {

	val httpProtocol = http
		.baseUrl("http://computer-database.gatling.io")
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.9")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36")

	val headers_2 = Map("Origin" -> "http://computer-database.gatling.io")



	val scn = scenario("GatlingDatabase")
		.exec(http("LOAD_HOMEPAGE")
			.get("/computers"))
		.pause(1)
		.exec(http("LOAD_NEW_COMPUTER_PAGE")
			.get("/computers/new"))
		.pause(1)
		.exec(http("CREATE_NEW_COMPUTER")
			.post("/computers")
			.headers(headers_2)
			.formParam("name", "fteixeira-abc123")
			.formParam("introduced", "2019-01-01")
			.formParam("discontinued", "2019-02-02")
			.formParam("company", "1"))
		.pause(1)
		.exec(http("SEARCH_COMPUTER")
			.get("/computers?f=fteixeira"))
		.pause(1)
		.exec(http("VIEW_COMPUTER_PAGE")
			.get("/computers/5009"))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}