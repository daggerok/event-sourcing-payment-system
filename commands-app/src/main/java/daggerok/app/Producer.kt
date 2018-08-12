package daggerok.app

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.messaging.Source
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.stereotype.Service

@Configuration
@EnableBinding(Source::class)
class ProducerConfig

@Service
class Producer(val output: MessageChannel) {
  //TODO: check if required @Output(Source.OUTPUT)
  fun <T> send(message: Message<T>) = output.send(message)
}
