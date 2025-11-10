package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // IMPORT BARU
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController // IMPORT BARU
import androidx.navigation.NavType // IMPORT BARU
import androidx.navigation.compose.NavHost // IMPORT BARU
import androidx.navigation.compose.composable // IMPORT BARU
import androidx.navigation.compose.rememberNavController // IMPORT BARU
import androidx.navigation.navArgument // IMPORT BARU
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Step 5: Bikin NavController & panggil App()
                    val navController = rememberNavController() //
                    App(navController = navController) //
                }
            }
        }
    }
}

// Step 4: Composable App jadi Root
@Composable
fun App(navController: NavHostController) { //
    // Step 4: NavHost buat ngatur graph navigasi
    NavHost(
        navController = navController, //
        startDestination = "home" //
    ) {
        // Route "home"
        composable("home") { //
            // Panggil Home, kasih lambda buat navigasi
            Home { listDataString -> //
                navController.navigate("resultContent/?listData=$listDataString") //
            }
        }

        // Route "resultContent"
        composable(
            route = "resultContent/?listData={listData}", //
            arguments = listOf(navArgument("listData") { //
                type = NavType.StringType //
            })
        ) {
            // Ambil argumen & panggil ResultContent
            ResultContent( //
                listData = it.arguments?.getString("listData").orEmpty() //
            )
        }
    }
}


data class Student(
    var name: String
)

// Step 6: Modif Home(), tambah parameter lambda
@Composable
fun Home(navigateFromHomeToResult: (String) -> Unit) { //
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    var inputField by remember { mutableStateOf(Student("")) }

    // Step 8: Panggil HomeContent dengan param navigasi baru
    HomeContent(
        listData = listData,
        inputField = inputField,
        onInputValueChange = { newName ->
            inputField = inputField.copy(name = newName)
        },
        onButtonClick = {
            if (inputField.name.isNotBlank()) {
                listData.add(inputField)
                inputField = Student("")
            }
        },
        navigateFromHomeToResult = { //
            // Kirim list data sebagai string
            navigateFromHomeToResult(listData.toList().toString()) //
        }
    )
}

// Step 7: Modif HomeContent(), tambah parameter lambda
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit,
    navigateFromHomeToResult: () -> Unit //
) {
    // Step 9: Update LazyColumn
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                TextField(
                    value = inputField.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    onValueChange = { onInputValueChange(it) } // <-- VHOOMM!                )
                )
                // Step 9: Tambah Row buat 2 tombol
                Row { //
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click),
                        onClick = { onButtonClick() }
                    )
                    // Tombol Finish
                    PrimaryTextButton( //
                        text = stringResource(id = R.string.button_navigate), //
                        onClick = { navigateFromHomeToResult() } //
                    )
                }
            }
        }

        items(listData) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OnBackgroundItemText(text = item.name)
            }
        }
    }
}

// Step 10: Composable baru ResultContent
@Composable
fun ResultContent(listData: String) { //
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tampilin string data list-nya
        OnBackgroundItemText(text = listData) //
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Kasih lambda kosong aja buat preview
        Home(navigateFromHomeToResult = {}) //
    }
}