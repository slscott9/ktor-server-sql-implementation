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
    val folderId = long("folder_id").primaryKey()
   val name = varchar("folder_name", 128)
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
    val userName = varchar("display_name", 256)
    val description = varchar("description", 128).nullable()
    val date = date
}

data class Folder(
        val folderId: Long,
        val name: String,
        val userEmail: String,
        val userName: String,
        val description: String?
)

/*
                        SetTable

        folder id | setId | setName | term | answer |
 */

object SetTable : Table() {
    val setId = long("set_id").primaryKey()
    val folderId = long("folder_id").references(FolderTable.folderId).nullable()
    val userEmail = varchar("email", 128).references(UsersTable.userEmail)
    val setName = varchar("set_name", 128)
    val termCount = integer("term_count")

}

//outgoing to user for get

data class Set(
        val setId: Long,
        val folderId: Long?,
        val userEmail: String,
        val setName: String,
        val termCount: Int = 0

)
object TermTable : Table() {
    val termId = long("term_id").primaryKey()
    val setId = long("set_id").references(SetTable.setId)
    val question = varchar("term", 512)
    val answer = varchar("answer", 512)
}
data class Term(
        val termId: Long,
        val setId: Long,
        val question: String,
        val answer: String
)










