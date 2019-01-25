package com.example.maxdo.jetrubytest.core.responses

import com.example.maxdo.jetrubytest.core.entities.Source

data class SourcesResponse (
    val status: String?,
    val sources: List<Source>?
)