package com.bylazar.pinger

data class PingRequest(
    val sentTimestamp: Long
)
data class PingAnswer(
    val sentTimestamp: Long,
    val receivedTimestamp: Long,
)

