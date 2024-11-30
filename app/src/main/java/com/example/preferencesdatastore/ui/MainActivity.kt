package com.example.preferencesdatastore.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.preferencesdatastore.ui.theme.MyappTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val dataStoreManager by lazy {
            DataStoreManager(this)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        dataStoreManager = dataStoreManager
                    )
                }
            }
        }
    }
}



@Composable
fun MainScreen(modifier: Modifier = Modifier , dataStoreManager: DataStoreManager) {


    val key = remember {
        mutableStateOf("")
    }
    val value = remember {
        mutableStateOf("")
    }
    val readKey = remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = key.value,
            onValueChange = {
                key.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(16.dp))

        TextField(
            value = value.value,
            onValueChange = {
                value.value = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick = {
                scope.launch {
                    dataStoreManager.saveString(key.value, value.value)
                }
            }
        ) {
            Text(text = "Save")
        }
        Spacer(modifier = Modifier.padding(16.dp))

        TextField(
            value = key.value,
            onValueChange = { key.value = it },
            label = { Text("Key to Read") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                scope.launch {
                    val result = dataStoreManager.getString(key.value)
                    readKey.value = result ?: "No value found for key '${key.value}'"
                }
            }
        ) {
            Text(text = "Read")
        }
        Text(text = readKey.value, color = MaterialTheme.colorScheme.primary)
    }

}