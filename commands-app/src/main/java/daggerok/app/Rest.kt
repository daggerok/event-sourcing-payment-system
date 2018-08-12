package daggerok.app

import daggerok.api.Command
import daggerok.api.Error
import daggerok.api.Event
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono

//tag::content[]
@Configuration
class Rest {

  @Bean
  fun routes() = router {
    ("/").nest {
      contentType(MediaType.APPLICATION_JSON_UTF8)
      GET("/**") {
        val map = mapOf(
            "errors" to listOf(Error::class.java.name),
            "commands" to listOf(Command::class.java.name),
            "events" to listOf(Event::class.java.name)
        )
        ok().body(
            Mono.just(map), map.javaClass
        )
      }
    }
  }
}
//end::content[]
