package com.db

import com.data.request.User
import com.data.response.Note
import com.util.checkHasForPassword
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class NoteDataBase : NoteDataAccessObject {

    private val client = KMongo.createClient().coroutine
    private val dataBase = client.getDatabase("NoteDataBase")
    private val users = dataBase.getCollection<User>()
    private val notes = dataBase.getCollection<Note>()

    override suspend fun register(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

    override suspend fun checkPasswordForEmail(email: String, password: String): Boolean {
        val actualPassword = users.findOne(User::email eq email)?.password ?: return false
        return checkHasForPassword(password, actualPassword)
    }

    override suspend fun checkEmailIfExist(email: String): Boolean {
        return users.findOne(User::email eq email) != null
    }
/*
    override suspend fun getNotes(): List<Note> {

    }

    override suspend fun insertNote(note: Note): Boolean {

    }

    override suspend fun updateNote(note: Note): Boolean {

    }

    override suspend fun deleteNote(noteId: String): Boolean {

    }*/
}