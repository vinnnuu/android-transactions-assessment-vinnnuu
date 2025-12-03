package com.backbase.techassignment.candidate.ui.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backbase.techassignment.candidate.R
import com.backbase.techassignment.candidate.model.TransactionGroup

@Composable
fun TransactionGroupItem(group: TransactionGroup) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(R.color.card_color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    val painter = if (group.creditDebitIndicator == "DBIT") {
                        R.drawable.call_made
                    } else {
                        R.drawable.call_received
                    }
                    Icon(
                        painter = painterResource(painter),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    val text =
                        if (group.creditDebitIndicator == "DBIT") "Debit" else "Credit"
                    Text(
                        text = "${group.transactions.size} $text",
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = colorResource(R.color.text_default)
                        )
                    )
                }
                Text(
                    text = group.date,
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.colorTextDefault)
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                for (transaction in group.transactions) {
                    Text(
                        text = transaction.counterPartyName ?: "",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = colorResource(R.color.text_support)
                        ),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = colorResource(R.color.seperator)
                )
                val priceText = if (group.creditDebitIndicator == "DBIT") {
                    "-$${group.totalAmount}"
                } else {
                    "+$${group.totalAmount}"
                }
                val priceColor = if (group.creditDebitIndicator == "DBIT") {
                    colorResource(R.color.text_default)
                } else {
                    colorResource(R.color.success)
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = priceText,
                    style = TextStyle(
                        fontSize = 17.sp,
                        color = priceColor,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}