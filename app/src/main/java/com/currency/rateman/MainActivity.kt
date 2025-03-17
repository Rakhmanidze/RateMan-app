package com.currency.rateman

import BankListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.currency.rateman.data.model.Address
import com.currency.rateman.data.model.Bank
import com.currency.rateman.data.model.CurrencyCode
import com.currency.rateman.data.model.CurrencyRate
import com.currency.rateman.ui.theme.RateManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RateManTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BankListScreen(
                        banks = listOf( // Sample data
                            Bank(
                                name = "Bank1",
                                baseCurrency = CurrencyCode.CZK,
                                rates = listOf(CurrencyRate(CurrencyCode.USD, 24.0, 24.5)),
                                nearestBranchAddress = Address(
                                    street = "Náměstí Republiky",
                                    buildingNumber = "8",
                                    city = "Prague",
                                    postalCode = "11000",
                                    country = "Czechia"
                                ),
                                phoneNumber = "+420123456789"
                            ),
                            Bank(
                                name = "Bank2",
                                baseCurrency = CurrencyCode.CZK,
                                rates = listOf(CurrencyRate(CurrencyCode.USD, 23.8, 24.3)),
                                nearestBranchAddress = Address(
                                    street = "Václavské náměstí",
                                    buildingNumber = "12",
                                    city = "Prague",
                                    postalCode = "11000",
                                    country = "Czechia"
                                ),
                                phoneNumber = "+420987654321"
                            )
                        ),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RateManTheme {
        Greeting("Android")
    }
}