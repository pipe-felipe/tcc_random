package tcc.random.remote

import tcc.random.models.Customer
import tcc.random.remote.dto.EngineRequest

interface PostEngine {
    suspend fun sendToEngine()
}