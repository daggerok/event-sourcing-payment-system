package daggerok.app

import daggerok.api.Command
import daggerok.api.Error
import daggerok.api.Event
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

//tag::content[]
@Configuration
class Rest(val producer: Producer) {

  companion object {
    val ref = mutableMapOf<String, String>()::class.java
  }

  @Bean
  fun routes() = router {
    ("/").nest {
      contentType(MediaType.APPLICATION_JSON_UTF8)

      POST("/") {
        ok().body(it.bodyToMono(ref)
            .map { it["message"].orEmpty() }
            .map { MessageBuilder.withPayload(it).build() }
            .map { producer.send(it) }
            .subscribeOn(Schedulers.elastic())
            .flatMap { Mono.just("sending message...") }
        )
      }

      GET("/**") {
        val map = mapOf(
            "errors" to listOf(
                Error::class.java.name
            ),
            "commands" to listOf(
                Command::class.java.name
            )
        )
        ok().body(
            Mono.just(map), map.javaClass
        )
      }
    }
  }
}
//end::content[]
