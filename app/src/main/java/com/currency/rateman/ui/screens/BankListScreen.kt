import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.currency.rateman.data.model.Bank
import com.currency.rateman.data.model.CurrencyCode

@Composable
fun BankListScreen(banks: List<Bank>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(banks) { bank ->
            Text(text = "${bank.getName()}: USD Buy ${bank.getRate(CurrencyCode.USD)?.getBuyRate()}")
        }
    }
}