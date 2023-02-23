package com.example.loginusingcompose.ui.theme.homeui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.loginusingcompose.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    userName: String?,
    userEmail: String?,
    password: String?,
    confirmPassword: String?,
    onUserNameEntered: (String) -> Unit,
    onUserEmailEntered: (String) -> Unit,
    onPasswordEntered: (String) -> Unit,
    onConfirmPasswordEntered: (String) -> Unit,
    errorMessage: String?,
    onErrorShown: () -> Unit,
    onSubmit: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
        sheetContent = { BottomSheetContent() },
        sheetPeekHeight = 0.dp,
    ) {
        val scaffoldState = rememberScaffoldState()
        val scaffoldScope = rememberCoroutineScope()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            backgroundColor = Color.Black,
            floatingActionButton = {
                Row {
                    AlertDialogBox()
                    Spacer(modifier = Modifier.padding(5.dp))
                    GetPermission()
                    Spacer(modifier = Modifier.padding(5.dp))
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState
                                }
                            }
                        },
                        elevation = FloatingActionButtonDefaults.elevation(8.dp),
                        backgroundColor = Color.White
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_add),
                            contentDescription = "bottom sheet Icon",
                            tint = Color.Black
                        )
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            drawerContent = { GetPermission() },
            content = {
                if (!errorMessage.isNullOrEmpty()) {
                    scaffoldScope.launch {
                        bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                            message = errorMessage
                        )
                    }
                    onErrorShown()
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 90.dp)
                        .padding(it)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )

                ) {
                    Text(
                        modifier = Modifier.padding(top = 30.dp),
                        text = "Sign Up",
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    EnterUserName(
                        userName = userName,
                        onUserNameEntered = onUserNameEntered
                    )
                    EnterEmail(
                        userEmail = userEmail ?: "",
                        onUserEmailEntered = onUserEmailEntered
                    )
                    EnterPassword(
                        password = password ?: "",
                        onPasswordEntered = onPasswordEntered
                    )
                    ConfirmPassword(
                        confirmPassword = confirmPassword ?: "",
                        onConfirmPasswordEntered = onConfirmPasswordEntered
                    )
                    SelectGender()

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        onClick = {
                            onSubmit()
                        }) {
                        Text(
                            text = "Sign Up",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        )
    }
}


@Composable
fun EnterUserName(
    userName: String?,
    onUserNameEntered: (String) -> Unit
) {
    OutlinedTextField(
        value = userName ?: "",
        onValueChange = onUserNameEntered,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray
        ),
        label = {
            Text(
                text = "Username",
                style = TextStyle(
                    color = Color.Black
                )
            )
        },
        placeholder = { Text(text = "Username") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User Icon",
                tint = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = KeyboardType.Text
        ),
    )
}

@Composable
fun EnterEmail(
    userEmail: String,
    onUserEmailEntered: (String) -> Unit
) {
    //var email by remember { mutableStateOf ("")}
    OutlinedTextField(
        value = userEmail,
        onValueChange = onUserEmailEntered,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray
        ),
        label = {
            Text(
                text = "Email",
                style = TextStyle(
                    color = Color.Black
                )
            )
        },
        placeholder = { Text(text = "Email") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon",
                tint = Color.Black
            )
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Email
        ),
    )
}

@Composable
fun EnterPassword(
    password: String,
    onPasswordEntered: (String) -> Unit
) {
    //var password by remember { mutableStateOf ("")}
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordEntered,
        label = {
            Text(
                text = "Password",
                style = TextStyle(
                    color = Color.Black
                )
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray
        ),
        placeholder = { Text(text = "Password") },
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_password), //to use from drawables
                contentDescription = "Password Icon",
                tint = Color.Black
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility })
            {
                if (!passwordVisibility) {
                    Icon(
                        painterResource(id = R.drawable.ic_visibility),
                        contentDescription = "not visible",
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.ic_visible),
                        contentDescription = "visible",
                        tint = Color.Black
                    )
                }
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(), // to hide passwords
    )

}

@Composable
fun ConfirmPassword(
    confirmPassword: String,
    onConfirmPasswordEntered: (String) -> Unit
) {
    //var confirmPassword by remember { mutableStateOf ("")}
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordEntered,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray
        ),
        label = {
            Text(
                text = "Re-type Password",
                style = TextStyle(
                    color = Color.Black
                )
            )
        },
        placeholder = { Text(text = "Re-type Password") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Retype Password Icon",
                tint = Color.Black
            )
        },
        trailingIcon = {
            IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility })
            {
                if (!confirmPasswordVisibility) {
                    Icon(
                        painterResource(id = R.drawable.ic_visibility),
                        contentDescription = "not visible",
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.ic_visible),
                        contentDescription = "visible",
                        tint = Color.Black
                    )
                }
            }
        },
        visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectGender() {
    val options = listOf("Male", "Female", "Other")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded
            })
        {
            OutlinedTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.LightGray
                ),
                label = {
                    Text(
                        text = "Gender",
                        style = TextStyle(
                            color = Color.Black
                        )
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false })
            {
                options.forEach {
                    DropdownMenuItem(onClick = {
                        selectedOption = it
                        isExpanded = false
                    }) {
                        Text(text = it)
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
}

@Composable
fun GetPermission() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    FloatingActionButton(
        onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp),
        backgroundColor = Color.White

    ) {
        Icon(
            painterResource(id = R.drawable.ic_storage),
            contentDescription = "storage Icon",
            tint = Color.Black
        )
    }
}

@Composable
fun AlertDialogBox() {
    val context = LocalContext.current
    Column {
        var openAlertDialog by remember { mutableStateOf(false) }
        FloatingActionButton(
            onClick = { openAlertDialog = true },
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            backgroundColor = Color.White
        ) {
            Icon(
                painterResource(id = R.drawable.ic_alert),
                contentDescription = "alert Icon",
                tint = Color.Black
            )
        }
        if (openAlertDialog) {
            AlertDialog(
                onDismissRequest = {
                    openAlertDialog = false
                },
                title = {
                    Text(
                        text = "Alert!",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to proceed?",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )
                },
                dismissButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        onClick = {
                            openAlertDialog = false
                            Toast.makeText(context, "alert dismissed", Toast.LENGTH_SHORT).show()
                        }
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color.White
                        )
                    }
                },
                confirmButton = {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Black),
                        onClick = {
                            openAlertDialog = false
                            Toast.makeText(context, "proceeding with action", Toast.LENGTH_SHORT)
                                .show()
                        }
                    ) {
                        Text(
                            text = "Proceed",
                            color = Color.White
                        )

                    }
                },
            )
        }
    }
}

@Composable
fun BottomSheetContent() {
    Column(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Black)
            .border(width = 2.dp, color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icon one",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Icon two",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Icon three",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Icon four",
                tint = Color.White
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icon one",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Icon two",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Icon three",
                tint = Color.White
            )
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Icon four",
                tint = Color.White
            )
        }

    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    LoginScreen(false,
//        "",
//        "",
//        "",
//        "",
//        {}, {}, {}, {}, "", { }, {})
    // AlertDialogBox()
    BottomSheetContent()
}