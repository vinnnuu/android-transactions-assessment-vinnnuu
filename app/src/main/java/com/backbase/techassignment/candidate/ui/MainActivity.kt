package com.backbase.techassignment.candidate.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.backbase.techassignment.candidate.ui.transactions.TransactionScreen
import com.backbase.techassignment.candidate.viewmodel.TransactionViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TransactionScreen(viewModel)
        }
    }
}