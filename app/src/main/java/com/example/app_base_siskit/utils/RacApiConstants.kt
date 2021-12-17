package com.example.app_base_siskit.utils

class RacApiConstants {
    companion object {
        val PROTOCOL: String = "https://"

        val HOST: String = "test.crmnavarrosa.com.ar"

        val HOST_URL: String = "$PROTOCOL$HOST"

        val API_BASE_URL: String = "$HOST_URL/rac-api/"
    }
}