package com.backbase.techassignment.candidate.util

import com.backbase.techassignment.candidate.model.Transaction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object TransactionParserUtil {

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        ignoreUnknownKeys = true
        allowTrailingComma = true

    }

    fun parse(jsonString: String): List<Transaction> {
        return json.decodeFromString(jsonString)
    }
}
