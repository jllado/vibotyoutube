package com.vibot.youtube.api

import com.vibot.youtube.Category

data class CreateVideoRequest(val title: String, val description: String, val category: Category, val keywords: List<String>)
