package com.example.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.Purple40
import kotlinx.coroutines.delay

@Composable
fun JogoDaVelha() {

    val board = remember {
        mutableStateOf(Array(3) { arrayOfNulls<String>(3) })
    }
    val jogadorAtual = remember {
        mutableStateOf("X")
    }
    val winner = remember {
        mutableStateOf<String?>(null)
    }
    val placarInicial = Array(3) { arrayOfNulls<String>(3) }
    val jogadorInicial = "X"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = AnnotatedString(
                    "Jogo da Velha do Manitos",
                    spanStyle = SpanStyle(fontStyle = FontStyle.Normal)
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Jogador Atual: ${jogadorAtual.value}",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )


        if (winner.value != null){
            Text(text = "Vencedor: ${winner.value}", style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.padding(4.dp)
            )

            LaunchedEffect(true ){
                delay(40000)
                board.value=placarInicial
                jogadorAtual.value = jogadorInicial
                winner.value = null
            }
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Column {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (col in 0..2) {
                            Button(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CutCornerShape(0.dp))
                                    .background(Color.Black)
                                    .padding(4.dp),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    if (board.value[row][col] == null && winner.value == null) {
                                        board.value[row][col] = jogadorAtual.value
                                        jogadorAtual.value =
                                            if (jogadorAtual.value == "X") "O" else "X"
                                        winner.value = verificarVencedor(board.value)
                                    }
                                }
                            ) {
                                Text(
                                    text = board.value[row][col] ?: "",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }


                Button(
                    onClick = {
                        board.value = placarInicial
                        jogadorAtual.value = jogadorInicial
                        winner.value = null
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        text = "Reiniciar",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun verificarVencedor(board: Array<Array<String?>>): String? {
    for (row in 0..2) {
        if (board[row][0] != null && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
            return board[row][0]
        }
    }
    for (col in 0..2) {
        if (board[0][col] != null && board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
            return board[0][col]
        }
    }
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
        return board[0][0]
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
        return board[0][2]
    }
    return null
}

@Composable
@Preview
fun prev(){
    JogoDaVelha()
}
