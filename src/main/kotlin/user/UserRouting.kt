package com.user

import com.data.request.User
import com.data.request.UserRegisterRequest
import com.dataBase
import com.util.getHashWithSalt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.userRoutes() {
    route("/register") {
        post {
            val userRegisterRequest = try {
                call.receive<UserRegisterRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest, e.toString())
                return@post
            }
            if (dataBase.checkEmailIfExist(userRegisterRequest.email)) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }
            val emailPattern = ("^[a-zA-Z0-9_!#\$%&*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\$").toRegex()
            if (!emailPattern.matches(userRegisterRequest.email)) {
                call.respond(HttpStatusCode.BadRequest, "E-Mail Pattern Invalid")
                return@post
            }
            val hashPassword = getHashWithSalt(userRegisterRequest.password)
            if (dataBase.register(
                    User(
                        userName = userRegisterRequest.userName,
                        email = userRegisterRequest.email,
                        password = hashPassword
                    )
                )
            ) {
                call.respond(HttpStatusCode.Created)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
                return@post
            }
        }
    }
}