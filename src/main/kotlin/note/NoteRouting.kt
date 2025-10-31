package com.note

import com.data.request.UserRegisterRequest
import com.data.request.note.AddNoteRequest
import com.data.request.note.UpdateNoteRequest
import com.data.response.Note
import com.dataBase
import com.util.checkHasForPassword
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.litote.kmongo.text

fun Route.noteRoutes() {
    route("/addNote") {
        authenticate {
            post {
                val addNoteRequest = try {
                    call.receive<AddNoteRequest>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest, e.toString())
                    return@post
                }
                if (dataBase.insertNote(
                        Note(
                            owner = dataBase.getUserIdWithEmail(
                                call.principal<UserIdPrincipal>()?.name.toString()
                            ),
                            title = addNoteRequest.title,
                            text = addNoteRequest.text,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                ) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
        }
    }
    route("/updateNote") {
        authenticate {
            post {
                val userId = dataBase.getUserIdWithEmail(call.principal<UserIdPrincipal>()?.name.toString())
                val updateNoteRequest = try {
                    call.receive<UpdateNoteRequest>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest, e.toString())
                    return@post
                }
                if (dataBase.updateNote(
                        Note(
                            owner = userId,
                            title = updateNoteRequest.title,
                            text = updateNoteRequest.text,
                            id = updateNoteRequest.id
                        )
                    )
                ) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.InternalServerError)
                    return@post
                }
            }
        }
    }
    route("/getNoteList") {
        authenticate {
            get {
                val userId = dataBase.getUserIdWithEmail(call.principal<UserIdPrincipal>()?.name.toString())
                if (userId.isNotEmpty()) {
                    val noteList = dataBase.getNotes(userId)
                    if (noteList.isNotEmpty()) {
                        call.respond(HttpStatusCode.OK, noteList)
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Boş Liste")
                    }
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Kullanıcı Bulunamadı")
                }
            }
        }
    }
    route("/deleteNote") {
        authenticate {
            delete {
                val userId = dataBase.getUserIdWithEmail(call.principal<UserIdPrincipal>()?.name.toString())
                val noteId = call.request.queryParameters["id"] ?: ""
                if (noteId == "") {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (dataBase.isNoteOwnedBy(userId, noteId)) {
                    if (dataBase.deleteNote(noteId)) call.respond(HttpStatusCode.OK)

                } else {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

            }
        }
    }
}