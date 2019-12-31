package io.github.flpmartins88.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.Valid
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class Book(

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        val name: String
)

interface BookRepository : JpaRepository<Book, Long>

@RestController
@RequestMapping("/books")
class BookController(private val bookRepository: BookRepository) {

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@Valid @RequestBody book: BookRequest) =
            ResponseEntity.created(URI.create("/books"))
                    .body(bookRepository.save(book.toDomain()).toResponse())

    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun list() = ResponseEntity.ok(bookRepository.findAll())

    @GetMapping("/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@PathVariable id: Long) =
        bookRepository.findByIdOrNull(id)?.let {
            ResponseEntity.ok(it.toResponse())
        } ?: ResponseEntity.notFound().build<BookResponse>()

}

data class BookRequest(
        @NotEmpty
        @Size(min = 3)
        val name: String
)

data class BookResponse(
        val id: Long,
        val name: String
)

private fun BookRequest.toDomain() =
        Book(name = name)

private fun Book.toResponse() =
        BookResponse(
                id = id!!,
                name = name
        )