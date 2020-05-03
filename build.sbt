scalaVersion := "2.12.7"

sbtPlugin := true

name := "sbt-iservport"

organization := "com.iservport"

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-web"             % "1.4.4")

enablePlugins(SbtPlugin)

publishMavenStyle := true

githubOwner := "iservport"

githubRepository := "sbt-iservport"

githubTokenSource := TokenSource.GitConfig("github.token") || TokenSource.Environment("GITHUB_TOKEN")

resolvers += Resolver.typesafeIvyRepo("releases")

resolvers += Resolver.typesafeRepo("releases")