package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Ini dari Step 13: Bikin list dan pass ke Home
                    val list = listOf("Tanu", "Tina", "Tono") // [cite: 212]
                    Home(items = list) // [cite: 213]
                }
            }
        }
    }
}

// Ini dari Step 12: Home() sekarang ada parameter, @Preview ilang
@Composable
fun Home(items: List<String>, modifier: Modifier = Modifier) { //
    // Ganti Column jadi LazyColumn [cite: 131, 135]
    LazyColumn(modifier = modifier) {

        // item {} -> ini buat nampilin 1 UI (header input kita)
        item { //
            Column(
                modifier = Modifier
                    .padding(16.dp) // [cite: 146]
                    .fillMaxSize(), // [cite: 146]
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 155]
            ) {
                // Teks "Enter a name"
                Text(text = stringResource(id = R.string.enter_item)) // [cite: 156]

                // Input field
                TextField(
                    value = "", // [cite: 160]
                    keyboardOptions = KeyboardOptions( // [cite: 162]
                        keyboardType = KeyboardType.Number //
                    ),
                    onValueChange = { /* Nanti di Part 2 */ }, // [cite: 167]
                )

                // Button Submit
                Button(onClick = { /* Nanti di Part 2 */ }) { // [cite: 173]
                    Text(text = stringResource(id = R.string.button_click)) // [cite: 175]
                }
            }
        }

        // items(list) {} -> ini buat nampilin list data (pengganti RecyclerView)
        items(items) { item -> //
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 186]
                    .fillMaxSize(), // [cite: 186]
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 186]
            ) {
                Text(text = item) // [cite: 188]
            }
        }
    }
}

// Ini dari Step 12: Bikin function preview baru
@Preview(showBackground = true) // [cite: 198]
@Composable
fun PreviewHome() { // [cite: 200]
    LAB_WEEK_09Theme {
        Home(listOf("Tanu", "Tina", "Tono")) // [cite: 202]
    }
}