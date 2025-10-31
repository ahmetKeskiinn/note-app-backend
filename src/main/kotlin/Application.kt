package com

import com.db.NoteDataAccessObject
import com.db.NoteDataBase
import com.google.gson.Gson
import com.note.noteRoutes
import com.user.userRoutes
import io.ktor.serialization.gson.gson
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.AuthenticationConfig
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.routing.Routing
import org.slf4j.event.Level

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

val dataBase: NoteDataAccessObject = NoteDataBase()
fun Application.module() {

    install(DefaultHeaders)
    install(CallLogging) {
        level = Level.TRACE
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(Authentication) {
        configureAuth()
    }
    install(Routing) {
        userRoutes()
        noteRoutes()
    }
}
private fun AuthenticationConfig.configureAuth() {
    basic {
        realm = "Notes Server"
        validate { credentials ->
            val email = credentials.name
            val password = credentials.password
            if (dataBase.checkPasswordForEmail(email, password)) {
                UserIdPrincipal(email)
            } else null
        }
    }
}
