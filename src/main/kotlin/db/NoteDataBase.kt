package com.db

import com.data.request.User
import com.data.response.Note
import com.util.checkHasForPassword
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.setValue

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

    override suspend fun insertNote(note: Note): Boolean {
        return notes.insertOne(note).wasAcknowledged()
    }

    override suspend fun getUserIdWithEmail(email: String): String {
        return users.findOne(User::email eq email)?.userId ?: ""
    }

    override suspend fun getNotes(userId: String): List<Note> {
        return notes.find(Note::owner eq userId).toList()
    }

    override suspend fun updateNote(note: Note): Boolean {
        notes.updateOneById(note.id, setValue(Note::title, note.title))
        notes.updateOneById(note.id, setValue(Note::text, note.text))
        return notes.updateOneById(note.id, setValue(Note::timestamp, System.currentTimeMillis())).wasAcknowledged()
    }

    override suspend fun deleteNote(noteId: String): Boolean {
        return notes.deleteOneById(noteId).wasAcknowledged()
    }

    override suspend fun isNoteOwnedBy(userId: String, noteId: String): Boolean {
        return notes.findOneById(noteId)?.owner == userId
    }

}