package com.sscott

import com.sscott.data.tables.FolderTable
import com.sscott.data.tables.SetTable
import com.sscott.data.tables.TermTable
import com.sscott.data.tables.UsersTable
import io.ktor.application.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@KtorExperimentalAPI
fun Application.initDB() {
    createTables()
}


private fun createTables() = transaction {

    SchemaUtils.create(
            UsersTable
    )
    SchemaUtils.create(
        SetTable
    )
    SchemaUtils.create(
        FolderTable
    )

    SchemaUtils.create(
            TermTable
    )
}