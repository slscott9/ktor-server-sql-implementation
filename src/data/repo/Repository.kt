package com.sscott.data.repo

import com.sscott.data.tables.User

interface Repository {

    suspend fun registerUser(email: String, password: String, userName: String) : Boolean

    suspend fun checkIfUserExists(email: String): User?

    suspend fun checkPasswordForEmail(email: String, password: String) : Boolean?
}