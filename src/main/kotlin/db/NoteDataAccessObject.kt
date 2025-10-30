package com.db

import com.data.request.User
import com.data.response.Note

interface NoteDataAccessObject {
    suspend fun register(user: User): Boolean
    suspend fun checkPasswordForEmail(email: String, password: String): Boolean
    suspend fun checkEmailIfExist(email: String): Boolean
/*
    suspend fun getNotes(): List<Note>
    suspend fun insertNote(note: Note): Boolean
    suspend fun updateNote(note: Note): Boolean
    suspend fun deleteNote(noteId: String): Boolean*/
}