import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "tweet-search"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
            "org.scalatest" %% "scalatest" % "1.6.1" % "test->default",
            "com.novocode" % "junit-interface" % "0.7" % "test->default",
            "ch.qos.logback" % "logback-classic" % "0.9.29" % "compile->default",
            "junit" % "junit" % "4.8" % "test->default",
            "org.jsoup" % "jsoup" % "1.6.2"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here

      testOptions in Test := Nil
    )

}
