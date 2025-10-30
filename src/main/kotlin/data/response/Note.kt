package com.data.response

data class Note(
    val noteId: String,
    val owner: String,
    val title: String,
    val text: String,
    val timestamp: String
)