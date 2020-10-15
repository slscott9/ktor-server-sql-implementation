package com.sscott.data.requests

import com.sscott.data.tables.Set
import com.sscott.data.tables.Term

//incoming posts


data class AccountRequest(
        val email: String,
        val password : String,
        val userName: String
)


data class NewSetRequest(
        val set: Set,
        val termList : List<Term>
)


