package com.sscott.data.repo

import com.sscott.data.requests.AddFolderRequest
import com.sscott.data.requests.AddSetRequest
import com.sscott.data.tables.Folder
import com.sscott.data.tables.Set
import com.sscott.data.tables.User

interface Repository {

    suspend fun registerUser(email: String, password: String, userName: String) : Boolean

    suspend fun checkIfUserExists(email: String): User?

    suspend fun checkPasswordForEmail(email: String, password: String) : Boolean?

    suspend fun addNewSet(setToAddSetRequest: AddSetRequest) : Set?
    suspend fun addNewTerms(setID: Int, setToAddSetRequest: AddSetRequest) : Boolean

    suspend fun addFolder(addFolderRequest: AddFolderRequest) : Boolean

    suspend fun getAllFoldersForUserEmail(userEmail: String) : List<Folder>

    suspend fun getAllSetsForUserEmail(userEmail: String) : List<Set>

    suspend fun getAllSetsInFolder(folderId: Int) : List<Set>

    suspend fun getSetsFromSearchResult(userEmail: String, search: String) : List<Set>


}