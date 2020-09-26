package com.sscott.data.service

import com.sscott.data.entities.Cemetery
import com.sscott.data.entities.CemeteryEntity
import org.jetbrains.exposed.sql.transactions.transaction


//contains methods to perform queries that the route will use for get and posts

/*
    Now that we have defined our data model and configured Exposed to correctly map the Book class to the database,
    we create a BookService containing functions for manipulating the data.

    In the src directory create a services package and add a new BookService.kt file to it.
 */

class CemeteryService {

    suspend fun getAllCemeteries() : List<Cemetery>? = transaction {
        CemeteryEntity.all().map(CemeteryEntity::toCemetery)
    }

    suspend fun addNewCemeteries(newCemeteryList : List<Cemetery>) = transaction {
        newCemeteryList.forEachIndexed {i, cemetery->
            CemeteryEntity.new {
                this.cemeteryId = cemetery.cemeteryId
                this.county = cemetery.county
                this.firstYear = cemetery.firstYear
                this.location = cemetery.location
                this.name = cemetery.name
                this.spot = cemetery.spot
                this.range = cemetery.range
                this.township = cemetery.township
                this.state = cemetery.state
            }

        }

    }
}