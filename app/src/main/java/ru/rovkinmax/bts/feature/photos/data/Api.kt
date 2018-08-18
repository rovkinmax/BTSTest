package ru.rovkinmax.bts.feature.photos.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.rovkinmax.bts.BuildConfig

interface Api {
    companion object {
        private const val METHOD_SEARCH = "flickr.photos.search"
        private const val FORMAT = "json"
        private const val NO_JSON_CALLBACK = "1"
    }

    @GET("services/rest/")
    fun search(@Query("text") query: String,
               @Query("per_page") count: Int,
               @Query("page") pageNumber: Int,
               @Query("method") method: String = METHOD_SEARCH,
               @Query("api_key") apiKey: String = BuildConfig.FLICKR_KEY,
               @Query("format") format: String = FORMAT,
               @Query("nojsoncallback") noJsonCallback: String = NO_JSON_CALLBACK): Single<SearchResponse>
}