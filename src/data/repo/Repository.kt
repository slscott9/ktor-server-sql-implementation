package com.sscott.data.repo

import com.sscott.data.requests.NewSetRequest
import com.sscott.data.tables.Folder
import com.sscott.data.tables.Set
import com.sscott.data.tables.Term
import com.sscott.data.tables.User

interface Repository {

    //User

    suspend fun registerUser(email: String, password: String, userName: String) : Boolean

    suspend fun checkIfUserExists(email: String): User?

    suspend fun checkPasswordForEmail(email: String, password: String) : Boolean?


    //Adding Sets and terms

    suspend fun addNewSet(newSet : Set) : Set?



    suspend fun addNewTerms(termList: List<Term>) : Boolean



//
//    suspend fun addFolder(addFolderRequest: AddFolderRequest) : Boolean
//
//    suspend fun getAllFoldersForUserEmail(userEmail: String) : List<Folder>
//
//    suspend fun getAllSetsForUserEmail(userEmail: String) : List<Set>
//
//    suspend fun getAllSetsInFolder(folderId: Int) : List<Set>
//
//    suspend fun getSetsFromSearchResult(userEmail: String, search: String) : List<Set>
//
//    suspend fun getSetAndTermsWithId(setID: Int) : List<Term?>
//
//    suspend fun getSetWithId(setID: Int) : List<Set?>


}