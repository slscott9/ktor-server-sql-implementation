package com.sscott.data.routes

import com.sscott.data.repo.RepoImpl
import com.sscott.data.requests.AddSetRequest
import com.sscott.data.requests.SearchRequest
import com.sscott.data.responses.SearchResponse
import com.sscott.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.setRoute(repoImpl: RepoImpl){
    route("/addSet"){
        authenticate {
            post {

                val email: String? = call.principal<UserIdPrincipal>()?.name
                if(email.isNullOrEmpty()){
                    call.respond(BadRequest)
                }else{

                    val request = try {
                        call.receive<AddSetRequest>()
                    }catch (e : ContentTransformationException){
                        call.respond(BadRequest)
                        return@post
                    }
                    val setResult = repoImpl.addNewSet(request)
                    val termResult = repoImpl.addNewTerms(setResult?.setId ?: 0, request)
                    if(setResult == null || !termResult){

                        call.respond(SimpleResponse(false, "Failed to add set"))

                    }else {
                        call.respond(SimpleResponse(true, "Successfully added set "))

                    }

                }

            }
        }
    }

    route("/getSets"){
        authenticate {
            get {
                val email: String? = call.principal<UserIdPrincipal>()?.name

                if(email.isNullOrEmpty()){
                    call.respond(BadRequest)
                }else{
                    val setList = repoImpl.getAllSetsForUserEmail(email)
                    call.respond(OK, setList)
                }
            }
        }
    }

    route("/getSetsWithQuery"){
        authenticate {
            post {
                val email: String? = call.principal<UserIdPrincipal>()?.name

                if(email.isNullOrEmpty()){
                    call.respond(BadRequest)
                }else{

                    val request = try {
                        call.receive<SearchRequest>()
                    }catch (e : ContentTransformationException){
                        call.respond(BadRequest)
                        return@post
                    }

                    val setList = repoImpl.getSetsFromSearchResult(request.userEmail, request.searchParam)
                    if(setList.isNotEmpty()){

                        call.respond(SearchResponse(true, "Successfully got sets", setList))
                    }else{
                        call.respond(SearchResponse(false, "No set found", setList))
                    }
                }
            }
        }
    }
}