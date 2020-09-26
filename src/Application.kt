package com.sscott

import com.sscott.data.entities.Books
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    Database.connect("jdbc:mysql://localhost:3306/cemetery?verifyServerCertificate=false", driver = "com.mysql.jdbc.Driver",
            user = "root", password = "Allstars9")

    createTables()
    install(DefaultHeaders) //append day to responses from the ktor server
    install(CallLogging) //log incoming and outgoing http requests

    install(Authentication){
//        configureAuth()
    }
    install(Routing) {

    } //enables url endpoints to make this REST api

    //Specifies what type of data ktor server will serve
    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
        }
    }

}

//private fun Authentication.Configuration.configureAuth() {
//    basic {
//        realm = "Cemetery Server"
//
//        validate {credentials ->
//            val email = credentials.name
//            val password = credentials.password
//
//            if(checkPasswordForEmail(email, password)){
//                UserIdPrincipal(email)
//            }else null
//
//        }
//    }
//}


private fun createTables() = transaction {
    SchemaUtils.create(
            Books
    )
}
