package com.marks0mmers.budgetcreator.config

import com.apurebase.kgraphql.ContextBuilder
import com.apurebase.kgraphql.GraphQLError
import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.configuration.PluginConfiguration
import com.apurebase.kgraphql.context
import com.apurebase.kgraphql.schema.Schema
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.marks0mmers.budgetcreator.graphql.BudgetSchema.budgetSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseCategorySchema.expenseCategorySchema
import com.marks0mmers.budgetcreator.graphql.ExpenseItemSchema.expenseItemSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseSourceSchema.expenseSourceSchema
import com.marks0mmers.budgetcreator.graphql.ExpenseSubCategorySchema.expenseSubCategorySchema
import com.marks0mmers.budgetcreator.graphql.IncomeItemSchema.incomeItemSchema
import com.marks0mmers.budgetcreator.graphql.IncomeSourceSchema.incomeSourceSchema
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.util.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import com.apurebase.kgraphql.schema.dsl.SchemaConfigurationDSL
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.decodeFromString
import kotlinx.serialization.json.JsonObject
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.json.*

@Serializable
data class GraphqlRequest(
    val id: String? = null,
    val operationName: String? = null,
    val variables: JsonObject? = null,
    val query: String
)

class KtorGraphQLConfiguration(
    val playground: Boolean,
    val endpoint: String
): PluginConfiguration

class KtorConfigurationDSL {
    /**
     * This adds support for opening the graphql route within the browser
     */
    var playground: Boolean = false

    var endpoint: String = "graphql"


    internal fun build() = KtorGraphQLConfiguration(
        playground = playground,
        endpoint = endpoint
    )
}

fun SchemaConfigurationDSL.ktor(block: KtorConfigurationDSL.() -> Unit) {
    val plugin = KtorConfigurationDSL().apply(block).build()
    install(plugin)
}

class GraphQL(val schema: Schema) {

    class Configuration: SchemaConfigurationDSL() {
        fun schema(block: SchemaBuilder.() -> Unit) {
            schemaBlock = block
        }

        /**
         * This adds support for opening the graphql route within the browser
         */
        var playground: Boolean = false

        var endpoint: String = "/graphql"

        fun context(block: ContextBuilder.(ApplicationCall) -> Unit) {
            contextSetup = block
        }

        fun wrap(block: Route.(next: Route.() -> Unit) -> Unit) {
            wrapWith = block
        }

        internal var contextSetup: (ContextBuilder.(ApplicationCall) -> Unit)? = null
        internal var wrapWith: (Route.(next: Route.() -> Unit) -> Unit)? = null
        internal var schemaBlock: (SchemaBuilder.() -> Unit)? = null

    }


    companion object Feature: ApplicationFeature<Application, Configuration, GraphQL> {
        override val key = AttributeKey<GraphQL>("KGraphQL")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): GraphQL {
            val config = Configuration().apply(configure)
            val schema = KGraphQL.schema {
                configuration = config
                config.schemaBlock?.invoke(this)
            }

            val routing: Routing.() -> Unit = {
                val routing: Route.() -> Unit = {
                    route(config.endpoint) {
                        post {
                            val request = decodeFromString(GraphqlRequest.serializer(), call.receiveText())
                            val ctx = context {
                                config.contextSetup?.invoke(this, call)
                            }
                            val result = schema.execute(request.query, request.variables.toString(), ctx)
                            call.respondText(result, contentType = ContentType.Application.Json)
                        }
                        if (config.playground) get {
                            @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                            val playgroundHtml = KtorGraphQLConfiguration::class.java.classLoader.getResource("playground.html").readBytes()
                            call.respondBytes(playgroundHtml, contentType = ContentType.Text.Html)
                        }
                    }
                }

                config.wrapWith?.invoke(this, routing) ?: routing(this)
            }

            pipeline.featureOrNull(Routing)?.apply(routing) ?: pipeline.install(Routing, routing)

            pipeline.intercept(ApplicationCallPipeline.Monitoring) {
                try {
                    coroutineScope {
                        proceed()
                    }
                } catch (e: Throwable) {
                    if (e is GraphQLError) {
                        context.respond(HttpStatusCode.OK, e.serialize())
                    } else throw e
                }
            }
            return GraphQL(schema)
        }

        private fun GraphQLError.serialize(): String = buildJsonObject {
            put("errors", buildJsonArray {
                addJsonObject {
                    put("message", message)
                    put("locations", buildJsonArray {
                        locations?.forEach {
                            addJsonObject {
                                put("line", it.line)
                                put("column", it.column)
                            }
                        }
                    })
                    put("path", buildJsonArray {
                        // TODO: Build this path. https://spec.graphql.org/June2018/#example-90475
                    })
                }
            })
        }.toString()
    }

}

object GraphQLConfig {
    fun Application.graphQlConfig() {
        install(GraphQL) {
            playground = true
            schema {
                budgetSchema()
                incomeSourceSchema()
                incomeItemSchema()
                expenseSourceSchema()
                expenseItemSchema()
                expenseCategorySchema()
                expenseSubCategorySchema()

                stringScalar<LocalDate> {
                    description = "The conversion between string and Kotlin LocalDate"
                    deserialize = { date: String -> date.toLocalDate() }
                    serialize = LocalDate::toString
                }
            }
        }
    }
}