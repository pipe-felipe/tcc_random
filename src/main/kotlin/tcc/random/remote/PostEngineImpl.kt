package tcc.random.remote

import tcc.random.remote.dto.EngineRequest
import tcc.random.remote.dto.EngineResponse

import io.ktor.client.*


class PostEngineImpl(private val client: HttpClient) : PostEngine {
    override suspend fun sendToEngine(engineRequest: EngineRequest): EngineResponse? {
        TODO("Not yet implemented")
    }
}