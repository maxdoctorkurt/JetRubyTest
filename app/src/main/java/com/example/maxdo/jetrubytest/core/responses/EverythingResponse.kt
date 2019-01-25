package com.example.maxdo.jetrubytest.core.responses

import com.example.maxdo.jetrubytest.core.entities.Article

data class EverythingResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>?
)