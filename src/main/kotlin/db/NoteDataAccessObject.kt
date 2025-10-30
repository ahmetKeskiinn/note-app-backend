package com.db

import com.data.request.User
import com.data.response.Note

interface NoteDataAccessObject {
    suspend fun register(user: User)
    suspend fun checkPasswordForEmail(email: String, password: String)
    suspend fun checkEmailIfExist(email: String)

    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(noteId: String)
}