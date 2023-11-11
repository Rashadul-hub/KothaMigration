package com.example.kothamigration.model

data class Post(
    val profile : Int ,
    val userName : String,
    val postImageList : List<Int>,
    val description : String,
    val likedBy: List<User>
)

data class User(
    val profile: Int,
    val userName: String,
    val storyCount: Int = 0,
    val stories: List<Int> = listOf()
)