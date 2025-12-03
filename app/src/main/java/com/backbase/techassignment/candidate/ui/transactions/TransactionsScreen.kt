package com.backbase.techassignment.candidate.ui.transactions

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backbase.techassignment.candidate.R
import com.backbase.techassignment.candidate.viewmodel.TransactionUIState
import com.backbase.techassignment.candidate.viewmodel.TransactionViewModel

@Composable
fun TransactionScreen(viewModel: TransactionViewModel) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var userId by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transactions",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp
                        )
                    )
                },
                backgroundColor = colorResource(R.color.colorPrimary),
                contentColor = colorResource(R.color.colorOnPrimary)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.foundation))
                .padding(24.dp)
        ) {
            OutlinedTextField(
                value = userId,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        userId = newValue
                    }
                },
                label = { Text(
                    "User ID",
                    color = colorResource(R.color.text_default)
                ) },
                modifier = Modifier.fillMaxWidth(),
                isError = userId.isEmpty(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(R.color.text_default),
                )
            )
            Button(
                onClick = {
                    if(!userId.isEmpty()) {
                        viewModel.getTransactions(userId.toInt())
                    } else {
                        showToast = true
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Get Transactions")
            }

            LaunchedEffect(showToast) {
                if(showToast) {
                    Toast.makeText(
                        context,
                        "Invalid User ID",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            when (val state = uiState) {
                is TransactionUIState.Idle -> {}

                is TransactionUIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }

                is TransactionUIState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 16.dp)
                    ) {
                        if (state.pending.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Pending",
                                    style = MaterialTheme.typography.h6,
                                    modifier = Modifier
                                        .padding(bottom = 16.dp),
                                    color = colorResource(R.color.text_default),
                                )
                            }
                            items(state.pending) {
                                TransactionGroupItem(it)
                            }
                        }
                        if (state.completed.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Completed",
                                    style = MaterialTheme.typography.h6,
                                    color = colorResource(R.color.text_default),
                                    modifier = Modifier
                                        .padding(bottom = 16.dp)
                                )
                            }
                            items(state.completed) {
                                TransactionGroupItem(it)
                            }
                        }
                    }
                }

                is TransactionUIState.Error -> {
                    LaunchedEffect(state.message) {
                        Toast.makeText(
                            context,
                            "Invalid User ID",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
