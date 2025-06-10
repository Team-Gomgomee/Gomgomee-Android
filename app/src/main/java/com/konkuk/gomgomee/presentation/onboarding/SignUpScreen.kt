package com.konkuk.gomgomee.presentation.onboarding

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.data.local.entity.UserEntity
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.presentation.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 입력값이 변경될 때마다 상태 초기화
    LaunchedEffect(userId, password, age, address, remarks, name) {
        viewModel.resetStates()
    }

    // 에러 메시지 수집
    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage.collect { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 회원가입 상태 수집
    LaunchedEffect(viewModel.registrationState) {
        viewModel.registrationState.collect { success ->
            success?.let {
                if (it) {
                    Toast.makeText(context, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                    navController.navigate(Route.Login.route) {
                        popUpTo(Route.SignUp.route) { inclusive = true }
                    }
                }
            }
        }
    }

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
        Text("아이디", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
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

        // 이름
        Text("이름", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
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
        Text("비밀번호", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
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
        Text("나이", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
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
        Text("거주지", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
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
        Text("특이사항", fontSize = 14.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
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
                // 입력 형식 검증
                when {
                    userId.isBlank() -> Toast.makeText(context, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                    name.isBlank() -> Toast.makeText(context, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    password.isBlank() -> Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    age.isBlank() -> Toast.makeText(context, "나이를 입력해주세요", Toast.LENGTH_SHORT).show()
                    !age.all { it.isDigit() } -> Toast.makeText(context, "나이는 숫자만 입력해주세요", Toast.LENGTH_SHORT).show()
                    else -> {
                        // 회원가입 처리
                        val user = UserEntity(
                            id = userId,
                            password = password,
                            name = name,
                            age = age.toInt(),
                            address = address.takeIf { it.isNotBlank() },
                            note = remarks.takeIf { it.isNotBlank() }
                        )
                        scope.launch {
                            viewModel.insert(user)
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6FAB8E),
                contentColor = Color.White
            )
        ) {
            Text("회원가입하기")
        }
    }
}