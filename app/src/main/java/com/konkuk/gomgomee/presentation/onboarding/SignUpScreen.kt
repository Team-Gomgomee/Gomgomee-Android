package com.konkuk.gomgomee.presentation.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SignUpScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "회원가입",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 아이디
        Text("아이디", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        // 비밀번호
        Text("비밀번호", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        // 나이
        Text("나이", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        // 거주지
        Text("거주지", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        // 특이사항
        Text("특이사항", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        OutlinedTextField(
            value = remarks,
            onValueChange = { remarks = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        // 회원가입 버튼
        Button(
            onClick = {
                if (userId.isNotBlank() && password.isNotBlank() && age.isNotBlank()) {
                    navController.navigate("login") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6FAB8E),
                contentColor = Color.Black
            )
        ) {
            Text("회원가입하기")
        }
    }
}