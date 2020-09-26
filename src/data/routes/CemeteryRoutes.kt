package com.sscott.data.routes

import com.sscott.data.service.CemeteryService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di


fun Route.cemeteryRoute() {

    val cemeteryService by di().instance<CemeteryService>()


    route("/getAllCems"){
        get {

            val allCems = cemeteryService.getAllCemeteries()

            if(allCems != null){
                call.respond(OK, allCems)
            }else {
                call.respond(BadRequest)
            }
        }
    }
}