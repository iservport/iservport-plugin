
sbtPlugin := true

name := "sbt-iservport"

organization := "com.iservport"

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.17")

addSbtPlugin("com.typesafe.sbt" % "sbt-web"             % "1.4.3")

addSbtPlugin("org.scala-js"     % "sbt-scalajs"         % "0.6.26")

enablePlugins(SbtPlugin)

scalacOptions += "-Ypartial-unification"

githubOwner := "iservport"

githubRepository := "sbt-iservport"

githubTokenSource := TokenSource.GitConfig("github.token") || TokenSource.Environment("GITHUB_TOKEN")
