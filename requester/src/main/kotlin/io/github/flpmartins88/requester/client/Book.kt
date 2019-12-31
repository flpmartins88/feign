package io.github.flpmartins88.requester.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.beans.ConstructorProperties

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
