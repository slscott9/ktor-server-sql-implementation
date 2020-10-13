package com.sscott.data.responses

import com.sscott.data.tables.Set


/*
    outgoing back to client
 */
data class SimpleResponse(
    val successful : Boolean,
    val message : String
)

data class SearchResponse(
        val successful: Boolean,
        val message: String,
        val setList: List<Set>
)