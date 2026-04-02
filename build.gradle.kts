plugins {
  kotlin("jvm") version "2.0.21"
  `maven-publish`
  signing
}

group = "com.c0x12c"
version = System.getenv("RELEASE_VERSION") ?: "0.1.0"

repositories {
  mavenCentral()
}

val arrowVersion = "1.2.4"
val jacksonVersion = "2.17.2"
val coroutinesVersion = "1.9.0"

dependencies {
  // Arrow for Either error handling
  api("io.arrow-kt:arrow-core:$arrowVersion")

  // Kotlin coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

  // Jackson for JSON
  implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

  // Logging
  implementation("org.slf4j:slf4j-api:2.0.16")

  // Test
  testImplementation(kotlin("test"))
  testImplementation("io.mockk:mockk:1.13.12")
  testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
  testRuntimeOnly("ch.qos.logback:logback-classic:1.5.12")
}

kotlin {
  jvmToolchain(21)
}

tasks.test {
  useJUnitPlatform()
}

java {
  withSourcesJar()
  withJavadocJar()
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])

      pom {
        name.set("PageIndex KT")
        description.set("LLM-powered hierarchical document indexing and retrieval for the JVM")
        url.set("https://github.com/c0x12c/pageindex-kt")

        licenses {
          license {
            name.set("Apache License 2.0")
            url.set("https://www.apache.org/licenses/LICENSE-2.0")
          }
        }

        developers {
          developer {
            id.set("c0x12c")
            name.set("c0x12c")
            url.set("https://github.com/c0x12c")
          }
        }

        scm {
          url.set("https://github.com/c0x12c/pageindex-kt")
          connection.set("scm:git:git://github.com/c0x12c/pageindex-kt.git")
          developerConnection.set("scm:git:ssh://git@github.com/c0x12c/pageindex-kt.git")
        }
      }
    }
  }

  repositories {
    maven {
      name = "OSSRH"
      url = uri(
        if (version.toString().endsWith("-SNAPSHOT"))
          "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        else
          "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
      )
      credentials {
        username = findProperty("ossrhUsername") as String? ?: System.getenv("OSSRH_USERNAME")
        password = findProperty("ossrhPassword") as String? ?: System.getenv("OSSRH_PASSWORD")
      }
    }
  }
}

signing {
  val signingKey = findProperty("signingKey") as String? ?: System.getenv("SIGNING_KEY")
  val signingPassword = findProperty("signingPassword") as String? ?: System.getenv("SIGNING_PASSWORD")
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications["maven"])
}
