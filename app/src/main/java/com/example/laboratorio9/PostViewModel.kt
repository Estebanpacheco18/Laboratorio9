package com.example.laboratorio9

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class PostViewModel : ViewModel() {
    val posts: SnapshotStateList<PostModel> = mutableStateListOf()
    var selectedPost: PostModel? by mutableStateOf(null)

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val postList = PostModule.postApiService.getUserPosts()
            posts.addAll(postList)
        }
    }

    fun fetchPostById(id: Int) {
        viewModelScope.launch {
            selectedPost = PostModule.postApiService.getUserPostById(id)
        }
    }
}