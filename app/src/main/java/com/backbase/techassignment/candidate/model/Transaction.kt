
package com.backbase.techassignment.candidate.model

import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Transaction(
    val id: String,
    val description: String,
    val typeGroup: String,
    val type: String,
    val category: String,
    val transactionAmountCurrency: TransactionAmountCurrency,
    val creditDebitIndicator: String,
    val counterPartyName: String? = null,
    val counterPartyAccountNumber: String? = null,
    val counterPartyCity: String? = null,
    val counterPartyAddress: String? = null,
    val creationTime: String,
    val state: String
)

@Serializable
data class TransactionAmountCurrency(
    val amount: String,
    val currencyCode: String
)

data class TransactionGroup(
    val date: String,
    val creditDebitIndicator: String,
    val transactions: List<Transaction>,
    val totalAmount: BigDecimal
)
