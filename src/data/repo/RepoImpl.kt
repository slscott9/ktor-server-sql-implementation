package com.sscott.data.repo

import com.sscott.data.security.checkHashForPassword
import com.sscott.data.tables.User
import com.sscott.data.tables.UsersTable
import com.sscott.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement

class RepoImpl: Repository {
    override suspend fun registerUser(email: String, password: String, userName: String): Boolean {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = UsersTable.insert {
                it[UsersTable.userEmail] = email
                it[UsersTable.password] = password
                it[UsersTable.displayName] = userName
            }
        }

    return rowToUser(statement?.resultedValues?.get(0)) != null
    }

    override suspend fun checkIfUserExists(email: String): User? = dbQuery {

        UsersTable.select {
            UsersTable.userEmail.eq(email)
        }.map {
            rowToUser(it)
        }.singleOrNull()
    }

    override suspend fun checkPasswordForEmail(email: String, password: String): Boolean {

        val user = dbQuery {
          UsersTable.select {
                UsersTable.userEmail.eq(email)
            }.map {
               rowToUser(it)
           }
        }

        return if(user.isEmpty()){
            false
        }else{
            checkHashForPassword(password, user[0]!!.password)
        }
    }

    private fun rowToUser(row: ResultRow?): User? { //converts row to user data class
        if (row == null) {
            return null
        }
        return User(
                userEmail = row[UsersTable.userEmail],
                password = row[UsersTable.password],
                displayName = row[UsersTable.displayName],
        )
    }
}