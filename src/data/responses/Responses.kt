package com.sscott.data.responses

import com.sscott.data.tables.Folder
import com.sscott.data.tables.Set


//outgoing gets from user

data class UserResponse(
        val userEmail: String,
        val displayName: String,
        val password: String
) {
}



data class FolderResponse(
        val folder: Folder
)

//data class Folder(
//        val name: String,
//        val folderId: Int,
//        val userEmail: String
//)





data class SetResponse(
        val folderId: Int,
        val setList: List<Set>
)

//data class Set(
//        val folderId: Int,
//        val setName : String,
//        val term: String,
//        val answer : String
//        val userEmail: String
//)