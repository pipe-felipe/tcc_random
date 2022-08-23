package tcc.random.remote

import tcc.random.remote.dto.EngineRequest
import tcc.random.remote.dto.EngineResponse

interface PostEngine {
    suspend fun sendToEngine(engineRequest: EngineRequest): EngineResponse?
}