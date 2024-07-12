
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanif.earncash.DaO.Route
import com.hanif.earncash.DaO.UserDao
import com.hanif.earncash.R
import com.hanif.earncash.Utils.CallFunctions.Companion.fireObject
import kotlinx.coroutines.launch

// Your UserDao data class

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(args: Route.ProfileScreen) {
    var editInfo by remember {
        mutableStateOf(false)
    }


    var user by remember {
        mutableStateOf<UserDao?>(null)
    }

    val scope = rememberCoroutineScope()
    scope.launch {
        fireObject.getUserDetails().collect{
            user = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, top = 60.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( // Use Box to layer Image and Card
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.card),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                // EarnCash Text
                Text(
                    text = "EarnCash",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(
                    modifier = Modifier.weight(
                        1f
                    )
                ) // Push content to bottom

                // Name and Phone Number
                Column(
                    modifier = Modifier.align(
                        Alignment.Start
                    )
                ) {
                    user?.let {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                    user?.phoneNumber?.chunked(4)?.let {
                        Text(
                            text = it
                                .joinToString(" "), // Format phone number
                            fontSize = 14.sp,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )
                    }
                }

                // Balance
                Row(modifier = Modifier.align(Alignment.End)) {
                    Text(
                        text =args.earning ,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        color = Color.White,
                    )
                    Image(painter = painterResource(id = R.mipmap.coin), contentDescription = null, modifier = Modifier.size(30.dp) )
                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = {
//                    navController.navigate("order")
                },
            ) {
                Text(text = "Get Paid")
            }

            OutlinedButton(
                onClick = {
//                    editInfo = true
//                    AirtableApiClient().fetchRecords()
                          }
                ,
            ) {
                Text(text = "Edit")
            }
        }

        if (editInfo) {
//            EditProfileBottomSheet()
        }
    }
}



