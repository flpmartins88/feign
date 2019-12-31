package io.github.flpmartins88.requester

import feign.Feign
import feign.jackson.JacksonDecoder
import io.github.flpmartins88.requester.client.BookClient
import io.github.flpmartins88.requester.client.BookRequest
import io.github.flpmartins88.requester.client.Contributor
import io.github.flpmartins88.requester.client.GitHub
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class RequesterApplication(
        private val bookClient: BookClient
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        when (args!!.nonOptionArgs[0].toOperation()) {
            Operation.CREATE -> createBook(args.getOptionValues("name")[0])
            Operation.LIST ->   listBooks()
            Operation.GET ->    getBook(args.nonOptionArgs[1].toLong())
            Operation.GITHUB -> github()
        }

//        println("-> Non Options")
//        args!!.nonOptionArgs.forEach { println(it) }
//
//        println("-> Options")
//        args.optionNames.forEach { println(it) }
//
//        println("-> Source")
//        args.sourceArgs.forEach { println(it) }
    }

    private fun createBook(name: String) {
        bookClient.create(BookRequest(name)).also {
            println(it)
        }
    }

    private fun listBooks() {
        bookClient.list().forEach {
            println(it)
        }
    }

    private fun getBook(id: Long) {
        bookClient.get(id).also {
            println(it)
        }
    }

    private fun github() {

        val github = Feign.builder()
                .decoder(JacksonDecoder())
                .target(GitHub::class.java, "https://api.github.com")

        github.contributors("OpenFeign", "feign")
                .map { contributor -> println(contributor.resumedInfo()) }
                .forEach { println(it) }

    }

}

enum class Operation {
    CREATE, LIST, GET, GITHUB
}

private fun Contributor.resumedInfo() =
        "$login ($contributions)"

private fun String.toOperation() =
    Operation.valueOf(this.toUpperCase())

fun main(args: Array<String>) {
    runApplication<RequesterApplication>(*args)
}

