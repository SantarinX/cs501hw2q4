package edu.cs501hw2.q4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.cs501hw2.q4.ui.theme.Q4Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

val bio = "Born on 17 July 1983 in Shanghai, China, he is a pop male singer, music producer, film and television actor in Mainland China, graduated from Glion Hotel Management School. In 2005, he made his official debut by participating in the talent show \"My Style\"."

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Q4Theme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
                    ProfileScreen(Modifier.padding(innerPadding), snackbarHostState, scope)
                }
            }
        }
    }
}

@Composable
fun ImageDisplay() {
    Image(
        painter = painterResource(id = R.drawable.joker_xue),
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(150.dp).clip(CircleShape)
    )
}

@Composable
fun UserInfo(modifier: Modifier, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope){
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
            Text(text = "Joker Xue", style = MaterialTheme.typography.headlineMedium)
        }

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            ProfileStatItem("Songs", "491")
            ProfileStatItem("Videos", "4642")
            ProfileStatItem("Followers", "24.14M")
        }

        Row(){
            Button(onClick = { coroutineScope.launch { snackbarHostState.showSnackbar("Following!") } }) { Text(text = "Follow") }
        }

    }
}

@Composable
fun TopHalf(modifier: Modifier, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ImageDisplay()

        Spacer(Modifier.width(16.dp))

        UserInfo(Modifier.weight(1f), snackbarHostState, coroutineScope)
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState, coroutineScope: CoroutineScope) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        TopHalf(Modifier.fillMaxWidth(), snackbarHostState, coroutineScope)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = bio, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Start)
    }
}

@Composable
fun ProfileStatItem(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
