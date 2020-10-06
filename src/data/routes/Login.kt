package com.sscott.data.routes

import com.sscott.data.repo.RepoImpl
import com.sscott.data.requests.AccountRequest
import com.sscott.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.di


fun Route.loginRoute(repo: RepoImpl) {
    route("/login"){
        post {

            val request = try {
                call.receive<AccountRequest>()
            }catch (e : ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isPasswordCorrect = repo.checkPasswordForEmail(request.email, request.password)
            if(isPasswordCorrect){
                call.respond(OK, SimpleResponse(true, "You are now logged in"))
            }else {
                call.respond(OK , SimpleResponse(false, "The email or password is incorrect"))
            }
        }
    }
}