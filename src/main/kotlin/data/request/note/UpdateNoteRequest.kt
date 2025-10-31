package com.data.request.note

data class UpdateNoteRequest(
    val id: String,
    val text: String,
    val title: String
)