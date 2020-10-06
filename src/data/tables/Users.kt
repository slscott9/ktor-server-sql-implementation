package com.sscott.data.tables

import org.jetbrains.exposed.sql.Table

//TODO need to implement the USER table in order to verify users
object UsersTable : Table() {
    val userEmail = varchar("email", 128).uniqueIndex()
    val displayName = varchar("display_name", 256)
    val password = varchar("password", 16)
}

object FolderTable: Table() {
    val id = integer("id").autoIncrement().primaryKey()
   val name = varchar("folder_name", 128)
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
}

object SetTable : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
    val term = varchar("term", 512)
    val answer = varchar("answer", 512)

}

data class User (
        val userEmail: String,
        val displayName: String,
        val password: String
)

data class Folder(
        val id: Int,
        val userEmail: String,
        val name: String
)

data class Set(
        val id: Int,
        val userEmail: String,
        val term: String,
        val answer: String
)