package com.example.calculator


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.calculator.ui.theme.colornum
import com.example.calculator.ui.theme.colorsignal
import androidx.compose.ui.text.style.TextAlign


@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        var displayText by remember { mutableStateOf("0") }

        Text(
            text = displayText,
            modifier = Modifier

                .padding(28.dp)
                .fillMaxWidth()
                .height(154.dp),
            fontSize = 62.sp,
            maxLines = 1,
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
        for (row in listOf(
            listOf("%", "C", "DEL","/"),
            listOf("7", "8", "9", "x"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("", "0", ".", "="),
        )) {
            Row {
                row.forEach { number ->


                    Button(onClick = {

                        when (number) {



                            "C" -> displayText = "0"
                            "DEL" -> if (displayText.length > 1 && displayText != "Syntax Error" && displayText != "Infinity" ) displayText = displayText.dropLast(1) else displayText = "0"
                            "=" -> displayText = operacao(displayText)
                            else -> {
                                if (displayText == "0") displayText = if (number == ".") "0." else number
                                else displayText += number
                            }
                        }





                    },
                        modifier = Modifier
                            .weight(1f)
                            .height(100.dp)
                            .padding(1.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (number in listOf("%", "C", "DEL", "/", "x", "-", "+", "=")) colorsignal else colornum
                        )
                    ) {
                        Text(text = number, modifier = Modifier.padding(4.dp), fontSize = 22.sp)
                    }
                }
            }


        }


    }
}

fun operacao(expression: String): String {
    return try {
        val cleanedExpression = expression

       //para double
        val result = cleanedExpression.split(Regex("[+\\-x/%]"))
            .map { it.toDouble() }

        // operadoes
        val operators = cleanedExpression.filter { it == '+' || it == '-' || it == 'x' || it == '/' || it =='%' }
        var total = result[0]



        for (i in 1 until result.size) {
            total = when (operators[i - 1]) {
                '+' -> total + result[i]
                '-' -> total - result[i]
                'x' -> total * result[i]
                '/' -> total / result[i]
                '%' -> total / 100 * result[i]
                //Este não está a funcionar :(
                else -> total
            }
        }

        total.toString()
    } catch (e: Exception) {
        "Syntax Error"
    }
}






