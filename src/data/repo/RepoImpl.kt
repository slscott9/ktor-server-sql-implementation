package com.sscott.data.repo

import com.sscott.data.requests.AddFolderRequest
import com.sscott.data.requests.AddSetRequest
import com.sscott.data.security.checkHashForPassword
import com.sscott.data.tables.*
import com.sscott.data.tables.Set
import com.sscott.dbQuery
import jdk.internal.jline.internal.Log
import org.jetbrains.exposed.sql.*
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

    override suspend fun addNewSet(setToAddSetRequest: AddSetRequest) : Set?{

        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = SetTable.insert {
                it[SetTable.userEmail] = setToAddSetRequest.set.userEmail
                it[setName] = setToAddSetRequest.set.setName
                it[folderId] = setToAddSetRequest.set.folderId
            }
        }


        return rowToSet(statement?.resultedValues?.get(0))
    }

    override suspend fun addNewTerms(setID: Int, setToAddSetRequest: AddSetRequest): Boolean {

        return dbQuery {
            val result = TermTable.batchInsert(setToAddSetRequest.termList){
                this[TermTable.term] = it.term
                this[TermTable.answer] = it.answer
                this[TermTable.setId] = setID

            }
            return@dbQuery result.size == setToAddSetRequest.termList.size
        }
    }

    override suspend fun addFolder(addFolderRequest: AddFolderRequest): Boolean {
        var statement: InsertStatement<Number>? = null
        dbQuery {
            statement = FolderTable.insert {
                it[name] = addFolderRequest.folderName
                it[userEmail] = addFolderRequest.userEmail
            }
        }

        return rowToFolder(statement?.resultedValues?.get(0)) != null

    }

    override suspend fun getAllSetsForUserEmail(userEmail: String): List<Set> {
        return dbQuery {
            SetTable.select {
                SetTable.userEmail.eq(userEmail)
            }.mapNotNull {
                rowToSet(it)
            }
        }

    }

    override suspend fun getAllFoldersForUserEmail(userEmail: String): List<Folder> {
        return dbQuery {
            FolderTable.select {
                FolderTable.userEmail.eq(userEmail)
            }.mapNotNull {
                rowToFolder(it)
            }
        }
    }

    override suspend fun getAllSetsInFolder(folderId: Int): List<Set> {
        return dbQuery {
            SetTable.select {
                SetTable.folderId.eq(folderId)
            }.mapNotNull {
                rowToSet(it)
            }
        }
    }

    override suspend fun getSetsFromSearchResult(userEmail: String, search: String): List<Set> {
        return dbQuery {
            SetTable.select {
                SetTable.setName.eq(search) and  SetTable.userEmail.eq(userEmail)
            }.mapNotNull {
                rowToSet(it)
            }
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

    private fun rowToSet(row: ResultRow?) : Set? {
        if(row == null) return null

        return Set(
                folderId = row[SetTable.folderId],
                userEmail = row[SetTable.userEmail],
                setName = row[SetTable.setName],
                setId = row[SetTable.setId]

        )
    }

//    private fun rowToSet(row: ResultRow?) : Set?  {
//        if(row == null){
//            return null
//        }
//        return Set(
//                setName = row[SetTable.setName],
//                term = row[SetTable.term],
//                answer = row[SetTable.answer]
//        )
//    }

    private fun rowToFolder(row: ResultRow?) : Folder? {
        if(row == null){
            return null
        }

        return Folder(
                name = row[FolderTable.name],
                folderId = row[FolderTable.folderId],
                userEmail = row[FolderTable.userEmail],
                userName = row[FolderTable.displayName],
                description = row[FolderTable.description]
        )
    }



//how to convert a query into usable objects for a get request
//    val actorDtos = transaction {
//        Actors.selectAll().map { mapToActorDto(it) }
//    }



}