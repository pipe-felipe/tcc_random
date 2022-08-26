package tcc.random.remote

import tcc.random.remote.dto.EngineRequest

interface EngineHandler {

    fun sendToEngine(engineRequest: EngineRequest)

}