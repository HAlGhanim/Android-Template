package com.example.androidtemplate.data.dtos

data class User(
    var email: String,
    var password: String,
    val id: Long? = null,
    val role: Role? = null,
    val status: String = "Active",
    val token: String? = null
)

enum class Role {
    USER, ADMIN
}