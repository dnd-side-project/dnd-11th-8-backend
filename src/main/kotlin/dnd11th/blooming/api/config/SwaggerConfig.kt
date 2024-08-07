package dnd11th.blooming.api.config

import dnd11th.blooming.common.annotation.ApiErrorResponse
import dnd11th.blooming.common.annotation.ApiErrorResponses
import dnd11th.blooming.common.exception.ErrorResponse
import dnd11th.blooming.common.exception.ErrorType
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    companion object {
        const val AUTHORIZATION = "bearerToken"
    }

    @Bean
    fun springOpenApi(): OpenAPI {
        val tokenSchema: SecurityScheme =
            SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name(AUTHORIZATION)
                .`in`(SecurityScheme.In.HEADER)

        val securityItem: SecurityRequirement = SecurityRequirement().addList(AUTHORIZATION)

        return OpenAPI()
            .components(Components().addSecuritySchemes(AUTHORIZATION, tokenSchema))
            .addSecurityItem(securityItem)
            .info(
                Info()
                    .title("\uD83C\uDF40Blomming\uD83C\uDF40")
                    .description("API 명세서입니다")
                    .title("v0.0.1"),
            )
    }

    @Bean
    fun customize(): OperationCustomizer =
        OperationCustomizer { operation, handlerMethod ->
            handlerMethod.getMethodAnnotation(ApiErrorResponses::class.java)?.let { apiErrorResponses ->
                apiErrorResponses.value.forEach { value ->
                    generateErrorResponse(operation, value.errorType, value.description)
                }
            } ?: handlerMethod.getMethodAnnotation(ApiErrorResponse::class.java)?.let { apiErrorResponse ->
                generateErrorResponse(operation, apiErrorResponse.errorType, apiErrorResponse.description)
            }
            operation
        }

    private fun generateErrorResponse(
        operation: Operation,
        errorCode: ErrorType,
        description: String,
    ) {
        val responses = operation.responses
        val exampleDto = createExampleDto(errorCode)
        addExampleToApiResponse(responses, description, exampleDto)
    }

    private fun createExampleDto(errorCode: ErrorType): ExampleDto {
        val error = ErrorResponse.from(errorCode)
        val example =
            Example().apply { this.value(error) }
        return ExampleDto(
            name = errorCode.name,
            code = errorCode.status.value(),
            holder = example,
        )
    }

    private fun addExampleToApiResponse(
        responses: ApiResponses,
        description: String,
        exampleDto: ExampleDto,
    ) {
        val mediaType =
            MediaType().apply {
                addExamples(exampleDto.name, exampleDto.holder)
            }
        val content =
            Content().apply {
                addMediaType("application/json", mediaType)
            }
        val apiResponse =
            ApiResponse().apply {
                this.content = content
                this.description = description
            }
        responses.addApiResponse(exampleDto.code.toString(), apiResponse)
    }

    data class ExampleDto(
        val name: String,
        val code: Int,
        val holder: Example,
    )
}
