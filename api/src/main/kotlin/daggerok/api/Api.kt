package daggerok.api

//tag::content[]

sealed class Error(override val message: String) : RuntimeException(message)
sealed class Command
sealed class Event

//end::content[]
