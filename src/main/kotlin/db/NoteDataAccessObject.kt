package com.db

import com.data.request.User
import com.data.response.Note

interface NoteDataAccessObject {
    suspend fun register(user: User): Boolean
    suspend fun checkPasswordForEmail(email: String, password: String): Boolean
    suspend fun checkEmailIfExist(email: String): Boolean
    suspend fun insertNote(note: Note): Boolean
    suspend fun getUserIdWithEmail(email: String): String
    suspend fun getNotes(id: String): List<Note>
    suspend fun updateNote(note: Note): Boolean
    suspend fun deleteNote(noteId: String): Boolean
    suspend fun isNoteOwnedBy(userId: String,  noteId: String): Boolean

    /*
        suspend fun deleteNote(noteId: String): Boolean*/
}