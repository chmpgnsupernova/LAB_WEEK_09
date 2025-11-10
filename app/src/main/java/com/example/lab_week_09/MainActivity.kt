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
import androidx.compose.runtime.getValue // IMPORT INI
import androidx.compose.runtime.mutableStateListOf // IMPORT INI
import androidx.compose.runtime.mutableStateOf // IMPORT INI
import androidx.compose.runtime.remember // IMPORT INI
import androidx.compose.runtime.setValue // IMPORT INI
import androidx.compose.runtime.snapshots.SnapshotStateList // IMPORT INI
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
                    // Step 7: Panggil Home() doang, ga pake parameter
                    Home() //
                }
            }
        }
    }
}

// Data class Student dari Step 2
data class Student(
    var name: String
)

// Step 3: Home Composable jadi Parent (State holder)
@Composable
fun Home() {
    // Step 3: Bikin state buat list data
    val listData = remember {
        mutableStateListOf( //
            Student("Tanu"), //
            Student("Tina"), //
            Student("Tono") //
        )
    }

    // Step 3: Bikin state buat input field
    // Pake var dan 'by' delegate biar lebih clean
    var inputField by remember { mutableStateOf(Student("")) } //

    // Step 3: Panggil Child Composable
    HomeContent(
        listData = listData, //
        inputField = inputField, //
        // Lambda buat update input field
        onInputValueChange = { newName ->
            inputField = inputField.copy(name = newName) //
        },
        // Lambda buat nambahin item ke list
        onButtonClick = {
            // Ini logic dari assignment (udah ada di modul)
            if (inputField.name.isNotBlank()) { //
                listData.add(inputField) //
                inputField = Student("") // Reset input field
            }
        }
    )
}

// Step 4: HomeContent Composable jadi Child (UI)
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>, //
    inputField: Student, //
    onInputValueChange: (String) -> Unit, //
    onButtonClick: () -> Unit //
) {
    LazyColumn {
        // item {} -> buat header input
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.enter_item))

                // Input field
                TextField(
                    value = inputField.name, // Ambil dari state
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // Ganti jadi Text
                    ),
                    onValueChange = { onInputValueChange(it) } // Panggil lambda
                )

                // Button Submit
                Button(onClick = { onButtonClick() }) { // Panggil lambda
                    Text(text = stringResource(id = R.string.button_click))
                }
            }
        }

        // items(list) {} -> buat nampilin list data
        items(listData) { item -> //
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.name) // Tampilin nama student
            }
        }
    }
}


// Preview buat Home()
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}