package com.sscott

import com.sscott.data.entities.Books
import io.ktor.application.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@KtorExperimentalAPI
fun Application.initDB() {


    createTables()
}


private fun createTables() = transaction {
    SchemaUtils.create(
            Books
    )
}