package com.sscott.data.routes

import com.sscott.data.repo.RepoImpl
import com.sscott.data.requests.AccountRequest
import com.sscott.data.responses.SimpleResponse
import com.sscott.data.security.getHashWithSalt
import com.sscott.data.tables.User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(repo: RepoImpl) {
    route("/register"){
        post {

            val request = try {
                call.receive<AccountRequest>()
            }catch (e : ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val user = repo.checkIfUserExists(request.email)
            if(user == null){
                if(repo.registerUser(request.email, getHashWithSalt(request.password), request.userName)){
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully created account"))
                }else{
                    call.respond(HttpStatusCode.OK, SimpleResponse(false, "An unknown error occurred"))
                }
            }else {
                call.respond(HttpStatusCode.OK, SimpleResponse(false, "User with that email already exists"))
            }
        }
    }
}