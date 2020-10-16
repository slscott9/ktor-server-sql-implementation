package com.sscott.data.repo

import com.sscott.data.security.checkHashForPassword
import com.sscott.data.tables.*
import com.sscott.data.tables.Set
import com.sscott.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.Date
import org.jetbrains.exposed.sql.statements.InsertStatement
import java.time.LocalDate

class RepoImpl : Repository {

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

        return if (user.isEmpty()) {
            false
        } else {
            checkHashForPassword(password, user[0]!!.password)
        }
    }

    override suspend fun addNewSet(newSet: Set): Set? {

        var statement: InsertStatement<Number>? = null

        dbQuery {
            statement = SetTable.insert {
                it[setId] = newSet.setId
                it[folderId] = newSet.folderId
                it[userEmail] = newSet.userEmail
                it[setName] = newSet.setName
                it[termCount] = newSet.termCount
                it[timeStamp] = newSet.timeStamp

            }
        }
        return rowToSet(statement?.resultedValues?.get(0))
    }


    override suspend fun addNewTerms(termList: List<Term>): Boolean {

        return dbQuery {
            val result = TermTable.batchInsert(termList) {
                this[TermTable.termId] = it.termId
                this[TermTable.answer] = it.answer
                this[TermTable.question] = it.question
                this[TermTable.answer] = it.answer
                this[TermTable.timeStamp] = it.timeStamp

            }
            return@dbQuery result.size == termList.size
        }
    }


    private fun rowToUser(row: ResultRow?): User? { //converts row to user data class
        if (row == null) {
            return null
        }
        return User(
                userEmail = row[UsersTable.userEmail],
                password = row[UsersTable.password],
                displayName = row[UsersTable.displayName]
        )
    }


    private fun rowToSet(row: ResultRow?): Set? {
        if (row == null) {
            return null
        }
        return Set(
                setName = row[SetTable.setName],
                setId = row[SetTable.setId],
                folderId = row[SetTable.folderId],
                userEmail = row[SetTable.userEmail],
                termCount = row[SetTable.termCount],
                timeStamp = row[SetTable.timeStamp]

        )
    }

    private fun rowToFolder(row: ResultRow?): Folder? {
        if (row == null) {
            return null
        }

        return Folder(
                name = row[FolderTable.name],
                folderId = row[FolderTable.folderId],
                userEmail = row[FolderTable.userEmail],
                userName = row[FolderTable.userName],
                description = row[FolderTable.description],
            timeStamp = row[FolderTable.timeStamp]

        )
    }

    private fun rowToTerm(row: ResultRow?): Term? {
        if (row == null) {
            return null
        }
        return Term(
                termId = row[TermTable.termId],
                question = row[TermTable.question],
                answer = row[TermTable.answer],
                setId = row[TermTable.setId],
            timeStamp = row[TermTable.timeStamp]
        )
    }


//how to convert a query into usable objects for a get request
//    val actorDtos = transaction {
//        Actors.selectAll().map { mapToActorDto(it) }
//    }



}