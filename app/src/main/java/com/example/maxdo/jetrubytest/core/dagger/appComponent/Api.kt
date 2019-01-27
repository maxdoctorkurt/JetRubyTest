package com.example.maxdo.jetrubytest.core.dagger.appComponent

import com.example.maxdo.jetrubytest.core.responses.EverythingResponse
import com.example.maxdo.jetrubytest.core.responses.SourcesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("everything/")
    fun everything(@Query("sources") sources: String): Single<EverythingResponse>

    @GET("top-headlines/")
    fun topHeadlines(@Query("q") query: String, @Query("pageSize") pageSize: Int) : Single<EverythingResponse>

    @GET("sources/")
    fun sources() : Single<SourcesResponse>
}