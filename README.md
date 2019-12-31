# Feign  

Esse projeto foi feito para estudar o Feign, ele consiste em dois submódulos:  
- **api**: Uma api simples com banco de dados para ser usada pelo Feign  
- **requester**: Um sistema que usa o Feign para fazer requests para a api  
  
Para compilar:  
`./gradlew clean build`

Para iniciar a API:
`./gradlew api:bootRun`

Para enviar requests com o Feign:
`./gradlew requester:bootRun --args "<command> [params]"`

O campo **command** pode ser:
 - **create**: cria um livro
 - **list**: lista todos os livros cadastrados
 - **get**: recupera algum livro pelo ID

## Exemplos de uso:

Criar um livro:
`./gradlew requester:bootRun --args "create --name='Domain Driven Design'"`

Resposta:
> BookResponse(id=1, name=Domain Driven Design)

Recuperar um livro criado anteriormente:
`./gradlew requester:bootRun --args "get 1"`

Resposta:
> BookResponse(id=1, name=Domain Driven Design)

Listar todos os livros:
`./gradlew requester:bootRun --args "list"`

Resposta:
> BookResponse(id=1, name=Domain Driven Design)
> BookResponse(id=2, name=Outro livro)

