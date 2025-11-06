name := """book-api-challenge"""
organization := "com.maudagnes"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.17"

// Dependency injection framework for Play
libraryDependencies += guice

// Database dependencies
libraryDependencies += javaJpa  // Java Persistence API for ORM
libraryDependencies += "com.h2database" % "h2" % "2.1.214"  // H2 in-memory database for development
libraryDependencies += "org.hibernate.orm" % "hibernate-core" % "6.3.1.Final"  // JPA implementation

// Testing dependencies
libraryDependencies += "org.assertj" % "assertj-core" % "3.24.2" % Test  // Fluent assertions
libraryDependencies += "org.mockito" % "mockito-core" % "5.3.1" % Test  // Mocking framework