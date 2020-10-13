package com.sscott.data.tables

import com.sscott.data.tables.SetTable.autoIncrement
import com.sscott.data.tables.SetTable.primaryKey
import com.sscott.data.tables.UsersTable.primaryKey
import org.jetbrains.exposed.sql.Table

/*
            UsersTable

    userEmail | displayName | password |
 */

object UsersTable : Table() {
    val userEmail = varchar("email", 128).primaryKey()
    val displayName = varchar("display_name", 256)
    val password = varchar("password", 256)
}

data class User (
        val userEmail: String,
        val displayName: String,
        val password: String
)


/*
                    FolderTable

            folderId | name | userEmail |
 */

object FolderTable: Table() {
    val folderId = integer("id").autoIncrement().primaryKey()
   val name = varchar("folder_name", 128)
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
    val displayName = varchar("display_name", 256)
    val description = varchar("description", 128).nullable()
}

data class Folder(
        val name: String,
        val folderId: Int,
        val userEmail: String,
        val userName: String,
        val description: String?
)

/*
                        SetTable

        folder id | setId | setName | term | answer |
 */

object SetTable : Table() {
    val setId = integer("setId").primaryKey().autoIncrement()
    val folderId = integer("folderId").references(FolderTable.folderId).nullable()
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
    val setName = varchar("setName", 128)

}

//outgoing to user for get

data class Set(
        val setId: Int,
        val folderId: Int?,
        val setName : String,
        val userEmail: String

)
object TermTable : Table() {
    val id = integer("termId").primaryKey().autoIncrement()
    val setId = integer("setId").references(SetTable.setId)
    val term = varchar("term", 512)
    val answer = varchar("answer", 512)
}
data class Term(
        val id: Int,
        val setId: Int,
        val term: String,
        val answer: String
)










