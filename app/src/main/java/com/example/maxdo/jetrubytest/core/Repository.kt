package com.example.maxdo.jetrubytest.core

import com.example.maxdo.jetrubytest.App
import com.example.maxdo.jetrubytest.core.responses.EverythingResponse
import com.example.maxdo.jetrubytest.core.responses.SourcesResponse
import io.reactivex.Single

object Repository {

    val api = App.instance?.apiCalls?.api

    fun everything(query: String, pageSize: Int): Single<EverythingResponse> {
        return api?.everything(query, pageSize) ?: Single.just(EverythingResponse(null, null, null))
    }

    fun topHeadlines(query: String, pageSize: Int): Single<EverythingResponse> {
        return api?.topHeadlines(query, pageSize) ?: Single.just(EverythingResponse(null, null, null))
    }

    fun sources(): Single<SourcesResponse> {
        // TODO caching
        return api?.sources() ?: Single.just(SourcesResponse(null, null))
    }
}