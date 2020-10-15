package com.sscott.data.routes

import com.sscott.data.repo.RepoImpl

import com.sscott.data.requests.NewSetRequest
import com.sscott.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.setRoute(repoImpl: RepoImpl){
    route("/addSetWithTerms"){
        authenticate {
            post {

                val email: String? = call.principal<UserIdPrincipal>()?.name
                if(email.isNullOrEmpty()){
                    call.respond(BadRequest)
                }else{

                    val request = try {
                        call.receive<NewSetRequest>()
                    }catch (e : ContentTransformationException){
                        call.respond(BadRequest)
                        return@post
                    }
                    val setResult = repoImpl.addNewSet(request.set)
                    val termResult = repoImpl.addNewTerms(request.termList)


                    if(setResult == null || !termResult){

                        call.respond(SimpleResponse(message = "Failed to add set or terms", successful = false))

                    }else {
                        call.respond(SimpleResponse(successful = true, "Successfully added set"))

                    }

                }

            }
        }
    }

}