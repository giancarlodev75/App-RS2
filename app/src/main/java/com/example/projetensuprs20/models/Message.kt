package com.example.projetensuprs20.models

import java.util.*

data class Message(
    val authorId: String = " ",
    val message: String = " ",
    val profileImageUrl: String = " ",
    val sentAt: Date = Date() )
