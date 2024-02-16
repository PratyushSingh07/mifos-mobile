package org.mifos.mobile.core.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mifos.mobile.core.ui.theme.MifosMobileTheme

/**
 * @author pratyush
 * @since 20/12/2023
 */

@Composable
fun NoInternet(
    icon: Int,
    error: Int,
    isRetryEnabled: Boolean = true,
    retry: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 12.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )

        Text(
            text = stringResource(id = error),
            style = TextStyle(fontSize = 20.sp),
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(12.dp))
        if(isRetryEnabled) {
            FilledTonalButton(onClick = { retry.invoke() }) {
                Text(text = "Retry")
            }
        }
    }
}