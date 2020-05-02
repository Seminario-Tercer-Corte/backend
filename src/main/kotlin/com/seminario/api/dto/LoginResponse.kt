package com.seminario.api.dto

data class LoginResponse (
        var accessToken: String,
        var role: String,
        var scopes: ArrayList<String>
)