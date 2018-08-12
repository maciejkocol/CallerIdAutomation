package webservice.gatling.simulation

import java.util.Calendar

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import scala.util.Random

class WebServiceCallSimulation extends Simulation {

  val rampUpTimeSecs = 5
  val testTimeSecs = 20
  val noOfUsers = 10
  val minWaitMs = 1000 milliseconds
  val maxWaitMs = 3000 milliseconds

  val simPort = System.getProperty("serverPort")

  val baseURL = s"http://localhost:${simPort}"
  val baseName = "webservice-call-callerid"

  val httpConf = http
    .baseURL(baseURL)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")

  var randomNumber = Iterator.continually(Map("randnumber" -> ( new java.text.SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime()))))
  var randomContext = Iterator.continually(Map("randcontext" -> ( Random.alphanumeric.take(30).mkString )))
  var randomName = Iterator.continually(Map("randname" -> ( Random.alphanumeric.take(30).mkString )))

  val scenarioPost = scenario(baseName + "-post-scenario")
    .during(testTimeSecs) {
      feed(randomNumber)
      .feed(randomContext)
        .feed(randomName)
        .exec(
          http(baseName + "-post-request")
            .post("/number")
            .body(StringBody("""{"number": "${randnumber}","context": "${randcontext}","name": "${randname}"}""")).asJSON
            .check(status.is(201))
        ).pause(minWaitMs, maxWaitMs)
    }

  val scenarioGet = scenario(baseName + "-get-scenario")
    .feed(randomNumber)
    .during(testTimeSecs) {
      exec(
        http(baseName + "-get-request")
          .get("/query")
          .queryParam("number", "${randnumber}")
          .check(status.is(200))
      )
        .pause(minWaitMs, maxWaitMs)
    }

  setUp(
    scenarioPost.inject(rampUsers(noOfUsers) over (rampUpTimeSecs)),
    scenarioGet.inject(rampUsers(noOfUsers) over (rampUpTimeSecs))
  ).protocols(httpConf)
}