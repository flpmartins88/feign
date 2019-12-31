package io.github.flpmartins88.requester

import feign.Feign
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder

/*
 * Site: https://github.com/OpenFeign/feign
 *
 * Annotations:
 *
 * @RequestLine:
 *  - Defines the HttpMethod and UriTemplate for request. Expressions, values wrapped in curly-braces {expression} are
 *    resolved using their corresponding @Param annotated parameters.
 *  - Applies to: Method
 *
 * @Param:
 *  - Defines a template variable, whose value will be used to resolve the corresponding template Expression, by name.
 *  - Applies to: Parameter
 *
 * @Headers:
 *  - Defines a HeaderTemplate; a variation on a UriTemplate. that uses @Param annotated values to resolve the
 *    corresponding Expressions. When used on a Type, the template will be applied to every request. When used on a
 *    Method, the template will apply only to the annotated method.
 *  - Applies to: Method, Type
 *
 * @QueryMap:
 *  - Defines a Map of name-value pairs, or POJO, to expand into a query string.
 *  - Applies to: Parameter
 *
 * @HeaderMap:
 *  - Defines a Map of name-value pairs, to expand into Http Headers
 *  - Applies to: Parameter
 *
 * @Body:
 *  - Defines a Template, similar to a UriTemplate and HeaderTemplate, that uses @Param annotated values to resolve the
 *    corresponding Expressions.
 *  - Applies to: Method
 */

interface GitHub {
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    fun contributors(@Param("owner") owner: String?, @Param("repo") repo: String?): List<Contributor>

    @RequestLine("POST /repos/{owner}/{repo}/issues")
    fun createIssue(issue: Issue?, @Param("owner") owner: String?, @Param("repo") repo: String?)
}

data class Contributor(
    var login: String? = null,
    var contributions: Int = 0
)

data class Issue(
    var title: String? = null,
    var body: String? = null,
    var assignees: List<String>? = null,
    var milestone: Int = 0,
    var labels: List<String>? = null
)

