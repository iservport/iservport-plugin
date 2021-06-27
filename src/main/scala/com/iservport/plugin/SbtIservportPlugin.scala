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

    unmanagedSourceDirectories in Compile ++=
      appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/main/scala"))

    unmanagedSourceDirectories in Test ++=
      appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/test/scala"))

    unmanagedResourceDirectories in Compile ++=
      appSources.value.map(s => (baseDirectory.value / s"app-${s.name}/src/main/resources"))

  }

  import Libraries._
  import autoImport._

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
    scalaVersion := "2.13.2"
  )

  object Libraries {

    private lazy val springVersion     = "4.3.20.RELEASE"
    private lazy val springBootVersion = "2.5.2"

    object ConfigDeps {
      lazy val all = Seq(
        "com.github.pureconfig"             %% "pureconfig"                    % "0.12.3",
        "io.kontainers"                     %% "purecsv"                       % "0.3.3"
      )
    }

    object ProvidedDeps {
      lazy val all = Seq(
        "javax.servlet"                      % "javax.servlet-api"              % "3.0.1"              % "provided"
      )
    }

    object GeneralDeps {
      lazy val all = Seq(
        "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
        "org.springframework.boot" % "spring-boot-starter-freemarker" % springBootVersion,
        "org.springframework" % "spring-webmvc" % springVersion,
        "org.springframework.boot" % "spring-boot-properties-migrator" % springBootVersion,
        "org.projectlombok" % "lombok" % "1.18.12",
        "com.google.guava" % "guava" % "29.0-jre",
        "io.springfox" % "springfox-swagger2" % "2.9.2",
        "io.springfox" % "springfox-swagger-ui" % "2.9.2"
      )
    }

    object SpringDataDeps {
      private lazy val mySqlConnectorVersion = "8.0.20"
      private lazy val hikariVersion         = "3.4.3"
      lazy val all = Seq(
        "mysql"                              % "mysql-connector-java"           % mySqlConnectorVersion,
        "com.fasterxml.jackson.module"      %% "jackson-module-scala"           % "2.11.0",
        "com.zaxxer"                         % "HikariCP"                       % hikariVersion,
        "org.springframework.boot"           % "spring-boot-starter-data-jpa"   % springBootVersion
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
        "org.springframework.social"         % "spring-social-facebook"              % "2.0.3.RELEASE",
        "org.springframework.social"         % "spring-social-google"                % socialGoogleVersion
      )
    }

    object AkkaDeps {
      private lazy val akkaVersion = "2.6.15"
      lazy val all = Seq(
        "com.sendgrid"          % "sendgrid-java"            % "4.3.0",
        "com.typesafe.akka"    %% "akka-actor-typed"         % akkaVersion,
        "com.typesafe.akka"    %% "akka-actor-testkit-typed" % akkaVersion % Test,
        "com.typesafe.akka"    %% "akka-actor"               % akkaVersion,
        "com.typesafe.akka"    %% "akka-slf4j"               % akkaVersion
      )
    }

    object ZioDeps {
      private lazy val zioVersion       = "1.0.9"
      private lazy val zioS3Version     = "0.3.0"
      private lazy val zioConfigVersion = "1.0.2"
      lazy val all = Seq(
        "dev.zio" %% "zio-config"          % zioConfigVersion,
        "dev.zio" %% "zio-config-magnolia" % zioConfigVersion,
        "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
        "dev.zio" %% "zio-s3"              % zioS3Version,
        "dev.zio" %% "zio-test"            % zioVersion % "test",
        "dev.zio" %% "zio-test-sbt"        % zioVersion % "test"
      )
    }

    object OtherDeps {
      lazy val all = Seq(
        "commons-io"                         % "commons-io"                     % "2.6",
        "net.java.dev.jets3t"                % "jets3t"                         % "0.8.1",
        "org.apache.pdfbox"                  % "pdfbox"                         % "2.0.16",
        "org.apache.tika"                    % "tika-core"                      % "1.19.1",
        "net.coobird"                        % "thumbnailator"                  % "0.4.8",
        "org.asciidoctor"                    % "asciidoctorj"                   % "2.0.0",
        "org.jsoup"                          % "jsoup"                          % "1.12.1"
      )
    }

    object TestDeps {
      private lazy val scalatestVersion      = "3.1.1"
      private lazy val mockitoVersion        = "3.3.3"
      private lazy val junitVersion          = "4.12"
      lazy val all = Seq(
        "org.springframework.boot"           % "spring-boot-starter-test"       % springBootVersion,
        "org.springframework.security"       % "spring-security-test"           % "4.2.9.RELEASE"    % Test,
        "org.scalactic"                     %% "scalactic"                      % scalatestVersion   % Test,
        "org.scalatest"                     %% "scalatest"                      % scalatestVersion   % Test,
        "org.mockito"                        % "mockito-core"                   % mockitoVersion     % Test,
        "junit"                              % "junit"                          % junitVersion,
        "com.novocode"                       % "junit-interface"                % "0.11"             % Test,
        "javax.xml.bind"                     % "jaxb-api"                       % "2.3.1"            % Test,
        "org.glassfish.jaxb"                 % "jaxb-runtime"                   % "2.3.2"            % Test

      )
    }

  }


}

