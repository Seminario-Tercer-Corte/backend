package com.seminario.api.utils

class Constants {
    companion object {
        private const val URL_API_BASE = "/api"
        private const val URL_API_VERSION = "/v1"
        private const val URL_BASE = URL_API_BASE + URL_API_VERSION

        //Base API endpoint para personas
        const val  URL_BASE_AUTH = "$URL_BASE/auth"
        const val  URL_BASE_USERS = "$URL_BASE/users"

        //Base API endpint para equipos
        const val  URL_BASE_TEAMS = "$URL_BASE/teams"

        //Base API endpint para organizaciones
        const val  URL_BASE_ORGANIZATIONS = "$URL_BASE/organizations"


        //Messages
        const val MESSAGE_USER_NOT_FOUND = "No se encontró el usuario"
        const val MESSAGE_ROLE_NOT_FOUND = "No se encontró el rol"
        const val MESSAGE_PERMISSION_NOT_FOUND = "No se encontró el permiso"
        const val MESSAGE_USERNAME_EXISTS = "Ya existe este nombre de usuario"
        const val MESSAGE_BAD_CREDENTIALS = "Credenciales incorrectas"
    }
}