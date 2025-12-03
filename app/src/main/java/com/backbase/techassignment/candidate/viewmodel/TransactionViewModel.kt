
package com.backbase.techassignment.candidate.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backbase.techassignment.candidate.model.Transaction
import com.backbase.techassignment.candidate.model.TransactionGroup
import com.backbase.techassignment.candidate.util.TransactionParserUtil
import com.backbase.techassignment.networking.NetworkBandwidth
import com.backbase.techassignment.networking.TransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<TransactionUIState>(TransactionUIState.Idle)
    val uiState: StateFlow<TransactionUIState> = _uiState

    fun getTransactions(userId: Int) {
        viewModelScope.launch {
            _uiState.value = TransactionUIState.Loading
            try {
                val transactions = TransactionsUseCase(NetworkBandwidth.Medium).getTransactions(userId)
                if(transactions is TransactionsUseCase.Result.Error) {
                    Log.d("TransactionViewModel", "transactions error: ${transactions.error.message}")
                    _uiState.value = TransactionUIState.Error(transactions.error.message ?: "")
                } else if(transactions is TransactionsUseCase.Result.Success) {
                    Log.d("TransactionViewModel", "Transactions data : ${transactions.data}")
                    val transactionsList = TransactionParserUtil.parse(transactions.data)
                    val (pending, completed) = groupAndSortTransactions(transactionsList)
                    _uiState.value = TransactionUIState.Success(pending, completed)
                }
            } catch (e: Exception) {
                _uiState.value = TransactionUIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    private fun groupAndSortTransactions(transactions: List<Transaction>): Pair<List<TransactionGroup>, List<TransactionGroup>> {
        val pendingTransactions = transactions.filter { it.state == "PENDING" }
        val completedTransactions = transactions.filter { it.state == "COMPLETED" }

        val pendingGroups = groupTransactions(pendingTransactions)
        val completedGroups = groupTransactions(completedTransactions)

        return Pair(pendingGroups, completedGroups)
    }

    private fun groupTransactions(transactions: List<Transaction>): List<TransactionGroup> {
        return transactions.groupBy { it.creationTime.substring(0, 10) + it.creditDebitIndicator }
            .map {
                val date = it.value.first().creationTime.substring(0, 10)
                val creditDebitIndicator = it.value.first().creditDebitIndicator
                val totalAmount = it.value.sumOf { t -> t.transactionAmountCurrency.amount.toBigDecimal() }
                TransactionGroup(date, creditDebitIndicator, it.value, totalAmount)
            }.sortedByDescending { it.date }
    }

}

sealed class TransactionUIState {
    object Idle: TransactionUIState()
    object Loading : TransactionUIState()
    data class Success(val pending: List<TransactionGroup>, val completed: List<TransactionGroup>) : TransactionUIState()
    data class Error(val message: String) : TransactionUIState()
}
