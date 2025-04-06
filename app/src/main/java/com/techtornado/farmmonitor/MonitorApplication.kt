package com.techtornado.farmmonitor

import android.app.Application
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MonitorApplication: Application() {
    companion object {
        lateinit var httpClient: HttpClient
        lateinit var aiModel: GenerativeModel
    }

    override fun onCreate() {
        super.onCreate()

        httpClient = HttpClient(OkHttp) {
            install(Resources)
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }
            defaultRequest {
                host = "api.agromonitoring.com/agro/1.0"
            }
        }

        aiModel = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = BuildConfig.apiKey,
            systemInstruction = content {
                text(INSTRUCTIONS)
            }
        )
    }
}