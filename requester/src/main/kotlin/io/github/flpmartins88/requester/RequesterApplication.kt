package io.github.flpmartins88.requester

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.beans.ConstructorProperties


@SpringBootApplication
@EnableFeignClients
class RequesterApplication(
        private val bookClient: BookClient
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        when (args!!.nonOptionArgs[0].toOperation()) {
            Operation.CREATE -> createBook(args.getOptionValues("name")[0])
            Operation.LIST -> listBooks()
            Operation.GET -> getBook(args.nonOptionArgs[1].toLong())
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

}

@FeignClient(name = "bookClient", url="http://localhost:8080/books")
interface BookClient {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(book: BookRequest): BookResponse

    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun list(): List<BookResponse>

    @GetMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable id: Long): BookResponse

}

data class BookRequest(val name: String)

data class BookResponse @ConstructorProperties("id", "name") constructor(val id: Long, val name: String)

enum class Operation {
    CREATE, LIST, GET
}

private fun String.toOperation() =
    Operation.valueOf(this.toUpperCase())

fun main(args: Array<String>) {
    runApplication<RequesterApplication>(*args)
}

