package com.iservport.plugin

import sbt._
import Keys._
import com.typesafe.sbt.packager.Keys._
import com.typesafe.sbt.SbtNativePackager
import com.typesafe.sbt.web.SbtWeb

object SbtIservportPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires = SbtWeb && SbtNativePackager

  object autoImport {

    val appSources = Def.settingKey[List[Symbol]]("Name of the app source folders as Symbols list")

  }

  import Libraries._
  import autoImport._

  unmanagedSourceDirectories in Compile ++=
    appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/main/scala"))

  unmanagedSourceDirectories in Test ++=
    appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/test/scala"))

  unmanagedResourceDirectories in Compile ++=
    appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/main/resources"))

  override def projectSettings = Seq(
    shellPrompt := { state =>
      "sbt (%s)> ".format(Project.extract(state).currentProject.id) },
    libraryDependencies ++= ConfigDeps.all ++
      SpringSecurityDeps.all ++
      SpringDataDeps.all ++
      GeneralDeps.all ++
      OtherDeps.all ++
      TestDeps.all ++
      ProvidedDeps.all

  )

  override def buildSettings = Seq(
    organization := "com.iservport",
    maintainer   := "mauricio@iservport.com",
    scalaVersion := "2.12.6"
  )

  object Libraries {

    private lazy val springVersion     = "4.3.20.RELEASE"
    private lazy val springBootVersion = "2.1.0.RELEASE"

    object ConfigDeps {
      lazy val all = Seq(
        "com.github.pureconfig"             %% "pureconfig"                    % "0.9.1"
      )
    }

    object ProvidedDeps {
      lazy val all = Seq(
        "javax.servlet"                      % "javax.servlet-api"              % "3.0.1"              % "provided"
      )
    }

    object SpringDataDeps {
      private lazy val hibernateVersion      = "4.3.11.Final"
      // private lazy val querydslVersion       = "4.1.4"
      private lazy val mySqlConnectorVersion = "8.0.11"
      private lazy val hikariVersion         = "3.2.0"
      lazy val all = Seq(
        "mysql"                              % "mysql-connector-java"           % mySqlConnectorVersion,
        "com.fasterxml.jackson.module"      %% "jackson-module-scala"           % "2.9.7",
        "com.zaxxer"                         % "HikariCP"                       % hikariVersion,
        "org.springframework.boot"           % "spring-boot-starter-data-jpa"   % springBootVersion
        // exclude("org.hibernate", "hibernate-core")
        // exclude("org.hibernate", "hibernate-entitymanager")
      )
    }

    object SpringSecurityDeps {
      private lazy val securityVersion       = "1.0.9.RELEASE"
      private lazy val securityOauthVersion  = "2.0.11.RELEASE" //2.3.4.RELEASE
      lazy val all = Seq(
        "org.springframework.boot"           % "spring-boot-starter-security"   % springBootVersion,
        "org.springframework.security.oauth" % "spring-security-oauth2"         % securityOauthVersion,
        "org.springframework.security"       % "spring-security-jwt"            % securityVersion
      )
    }

    object SpringSocialDeps {
      private lazy val socialGoogleVersion   = "1.0.0.RELEASE"
      lazy val all = Seq(
        // "org.springframework.social"         % "spring-social-facebook-autoconfigure" % "3.0.0.BUILD-SNAPSHOT",
        // "org.springframework.social"         % "spring-social-linkedin-autoconfigure" % "2.0.0.BUILD-SNAPSHOT",
        // "org.springframework.social"         % "spring-social-twitter-autoconfigure"  % "2.0.0.BUILD-SNAPSHOT",
        "org.springframework.social"         % "spring-social-facebook"              % "2.0.3.RELEASE",
        // "org.springframework.social"         % "spring-social-linkedin-autoconfigure" % "2.0.0.BUILD-SNAPSHOT",
        // "org.springframework.social"         % "spring-social-twitter-autoconfigure"  % "2.0.0.BUILD-SNAPSHOT",
        // "org.springframework.boot"           % "spring-boot-starter-social-facebook"  % "1.5.17.RELEASE", //springBootVersion,
        "org.springframework.social"         % "spring-social-google"                % socialGoogleVersion
      )
    }

    object OtherDeps {
      lazy val all = Seq(
        "commons-io"                         % "commons-io"                     % "2.4",
        "net.java.dev.jets3t"                % "jets3t"                         % "0.8.1",
        "net.coobird"                        % "thumbnailator"                  % "0.4.8",
        "com.google.guava"                   % "guava"                          % "21.0"
      )
    }

    object TestDeps {
      private lazy val scalatestVersion      = "3.0.0"
      private lazy val mockitoVersion        = "1.10.19"
      private lazy val junitVersion          = "4.12"
      lazy val all = Seq(
        "org.springframework.boot"           % "spring-boot-starter-test"       % springBootVersion,
        "org.springframework.security"       % "spring-security-test"           % "4.2.9.RELEASE"    % Test,
        "org.scalactic"                     %% "scalactic"                      % scalatestVersion   % Test,
        "org.scalatest"                     %% "scalatest"                      % scalatestVersion   % Test,
        "org.mockito"                        % "mockito-all"                    % mockitoVersion     % Test,
        "junit"                              % "junit"                          % junitVersion

      )
    }

    object GeneralDeps {
      lazy val all = Seq(
        "org.springframework.boot"           % "spring-boot-starter-web"         % springBootVersion,
        "org.springframework.boot"           % "spring-boot-starter-freemarker"  % springBootVersion,
        "org.springframework"                % "spring-webmvc"                   % springVersion,
        "org.springframework.boot"           % "spring-boot-properties-migrator" % springBootVersion,
        "org.projectlombok"                  % "lombok"                          % "1.18.2",
        "org.typelevel"                     %% "cats-core"                       % "2.0.0",
        "org.apache.tika"                    % "tika-core"                       % "1.19.1",
        "com.github.kenglxn.QRGen"           % "javase"                          % "2.1.0",
        "net.coobird"                        % "thumbnailator"                   % "0.4.8",
        "com.google.guava"                   % "guava"                           % "21.0",
        "io.springfox"                       % "springfox-swagger2"              % "2.9.2",
        "io.springfox"                       % "springfox-swagger-ui"            % "2.9.2",
        "org.apache.pdfbox"                  % "pdfbox"                          % "2.0.16",
        "com.novocode"                       % "junit-interface"                 % "0.11"     % "test"
      )

    }

  }


}

