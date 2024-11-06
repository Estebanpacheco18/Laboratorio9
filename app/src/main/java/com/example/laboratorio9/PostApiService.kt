package com.example.laboratorio9

import retrofit2.http.*

interface PostApiService {
    @GET("posts")
    suspend fun getUserPosts(): List<PostModel>

    @GET("posts/{id}")
    suspend fun getUserPostById(@Path("id") id: Int): PostModel

    @POST("posts")
    suspend fun createPost(@Body post: PostModel): PostModel

    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @Body post: PostModel): PostModel

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}