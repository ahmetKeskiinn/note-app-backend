package com.data.response

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Note(
    @BsonId
    val noteId: String = ObjectId().toString(),
    val owner: String,
    val title: String,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
)