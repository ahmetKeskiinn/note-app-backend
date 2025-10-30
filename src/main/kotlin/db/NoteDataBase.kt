package com.db

import com.data.request.User
import com.data.response.Note

class NoteDataBase: NoteDataAccessObject {
    override suspend fun register(user: User) {

    }

    override suspend fun checkPasswordForEmail(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkEmailIfExist(email: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getNotes(): List<Note> {
        TODO("Not yet implemented")
    }

    override suspend fun insertNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNote(noteId: String) {
        TODO("Not yet implemented")
    }
}