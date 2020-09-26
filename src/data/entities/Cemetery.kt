package com.sscott.data.entities

import org.bson.types.ObjectId
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table


/*
    Define columns as properties of the our table class

    CemeteryTable is the columns in our table

    IntIdTable automatically has an id column added for us
 */

object CemeteryTable : IntIdTable() {
    val cemeteryId = integer("cemeteryId")
    val name = varchar("name",50)
    val location = varchar("location", 50)
    val state = varchar("state", 50)
    val county = varchar("county", 50)
    val township = varchar("township", 50)
    val range = varchar("range", 50)
    val spot = varchar("spot", 50)
    val firstYear = varchar("firstYear", 50)
    val section = varchar("section", 50)

    override val primaryKey = PrimaryKey(cemeteryId)
}

/*
    CemeteryEntity represents a row in our table. Using this class we can perform our database operations.

 */
class CemeteryEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<CemeteryEntity>(CemeteryTable)

    // we use Kotlin delegates to map the values of the row to their corresponding columns of the database table.
    var cemeteryId by CemeteryTable.cemeteryId
    var name by CemeteryTable.name
    var location by CemeteryTable.location
    var state by CemeteryTable.state
    var county by CemeteryTable.county
    var township by CemeteryTable.township
    var range by CemeteryTable.range
    var spot by CemeteryTable.spot
    var firstYear by CemeteryTable.firstYear
    var section by CemeteryTable.section

    //we have a conversion function to transform our Entity to a simple Kotlin data class

    fun toCemetery() = Cemetery(
        id = id.value,
        cemeteryId = cemeteryId,
        name = name,
        location = location,
        state = state,
        county = county,
        township = township,
        range = range,
        spot = spot,
        firstYear = firstYear,
        section = section
    )
}



data class Cemetery(

    val id: Int,

    val cemeteryId: Int,

    val name: String,

    val location: String,

    val state: String,

    val county: String,

    val township: String,

    val range: String,

    val spot: String,

    val firstYear: String,

    val section: String
)