package com.developer.code.itwaremachinetest.response

data class MovieModel(
    val data: List<Data>,
    val message: String
)

data class Data(
    val movie_id: Int,
    val movie_name: String,
    val status: Int
)