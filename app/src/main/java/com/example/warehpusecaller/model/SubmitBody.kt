package com.example.warehpusecaller.model

data class SubmitBody(
    val CallerID: String,
    val WarehouseID: String,
    val StateID: String,
    val LoanproposalID: String,
    val RecordedBody: String,
    val Status: String
)