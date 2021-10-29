package com.amirhusseinsoori.grpcmviarch.data.source

import com.amirhusseinsoori.grpcmviarch.data.network.TimeoutInterceptor
import com.arad.domain.entity.TurnOn
import io.grpc.ManagedChannel
import io.grpc.domain.request.RequestNodesGrpc
import io.grpc.domain.request.SettingReply
import io.grpc.domain.request.TurnOnRequest

import javax.inject.Inject
class NetworkSource @Inject constructor(
    val channel: ManagedChannel
) {

    lateinit var api: RequestNodesGrpc.RequestNodesBlockingStub
    fun setBlockingStub(): RequestNodesGrpc.RequestNodesBlockingStub {
        return RequestNodesGrpc.newBlockingStub(channel)
            .withInterceptors(TimeoutInterceptor())
    }

    suspend fun requestTurnOnWithSafe(turnOn: TurnOn): SettingReply {
        api = setBlockingStub()
        val (androidId, PowerOnTime) = turnOn
        return api.setTurnOn(
            TurnOnRequest.newBuilder().setAndroidId(androidId).setPowerOnTime(PowerOnTime).build()
        )

    }

}