package com.sscott

import com.sscott.data.repo.RepoImpl
import com.sscott.data.routes.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    Database.connect("jdbc:mysql://localhost:3306/quizlet_clone?verifyServerCertificate=false", driver = "com.mysql.jdbc.Driver",
            user = "root", password = "Allstars9")

    initDB()

    val repo = RepoImpl()

    install(DefaultHeaders) //append day to responses from the ktor server
    install(CallLogging) //log incoming and outgoing http requests

    install(Authentication){
        configureAuth(repo)
    }
    install(Routing) {
        loginRoute(repo)
        registerRoute(repo)
        setRoute(repo)
        folderRoute(repo)
    } //enables url endpoints to make this REST api

    //Specifies what type of data ktor server will serve
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }

}

private fun Authentication.Configuration.configureAuth(repo: RepoImpl) {
    basic {
        realm = "Quizlet Server"

        validate {credentials ->
            val email = credentials.name
            val password = credentials.password

            if(repo.checkPasswordForEmail(email, password)){
                UserIdPrincipal(email)
            }else null

        }
    }
}

suspend fun <T> dbQuery(
        block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
