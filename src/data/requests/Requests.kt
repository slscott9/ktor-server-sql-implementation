package com.sscott.data.requests

import com.sscott.data.tables.Set
import com.sscott.data.tables.Term

//incoming posts


data class AccountRequest(
        val email: String,
        val password : String,
        val userName: String
)


data class AddFolderRequest(
        val folderName: String,
        val userEmail: String

) {
}


data class AddSetRequest(
        val set: ClientSetRequest,
        val termList: List<ClientTerm>
) {
}
data class ClientSetRequest(
        val userEmail: String,
        val folderId: Int?,
        val setName: String
)

data class ClientTerm(
        val setId: Int,
        val term: String,
        val answer: String
)


data class SearchRequest(
        val userEmail: String,
        val searchParam: String
)


