package com.sscott.data.routes

import com.sscott.data.repo.RepoImpl
import com.sscott.data.requests.AddFolderRequest
import com.sscott.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.folderRoute(repoImpl: RepoImpl){
    route("/addFolder"){
        authenticate {
            post {
                val request = try {
                    call.receive<AddFolderRequest>()

                }catch (e: ContentTransformationException){
                    call.respond(BadRequest)
                    return@post
                }

                if(repoImpl.addFolder(request)){
                    call.respond(SimpleResponse(true, "Successfully added folder"))
                }else{
                    call.respond(SimpleResponse(false, "Failed to add folder"))
                }
            }
        }
    }

    route("/getFolders"){
        authenticate {
            get {
                val email: String? = call.principal<UserIdPrincipal>()?.name

                if(email.isNullOrEmpty()){
                    call.respond(BadRequest)
                }else{
                    val folderList = repoImpl.getAllFoldersForUserEmail(email)
                    call.respond(OK, folderList)
                }
            }
        }
    }
}