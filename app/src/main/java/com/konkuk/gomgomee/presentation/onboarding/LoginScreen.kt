package com.konkuk.gomgomee.presentation.onboarding

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.presentation.navigation.Route
import com.konkuk.gomgomee.presentation.viewmodel.UserViewModel
import com.konkuk.gomgomee.ui.theme.White
import com.konkuk.gomgomee.util.modifier.noRippleClickable

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UserViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // 입력값이 변경될 때마다 상태 초기화
    LaunchedEffect(userId, password) {
        viewModel.resetStates()
    }

    // 로그인 상태 수집
    LaunchedEffect(viewModel.loginState) {
        viewModel.loginState.collect { user ->
            user?.let {
                Toast.makeText(context, "No.${it.userNo} ${it.name}님 환영합니다", Toast.LENGTH_SHORT).show()
                // 로그인 성공 시 userNo를 SharedPreferences에 저장
                context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    .edit()
                    .putInt("current_user_no", it.userNo)
                    .apply()
                
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Login.route) { inclusive = true }
                }
            }
        }
    }

    // 에러 메시지 수집
    LaunchedEffect(viewModel.errorMessage) {
        viewModel.errorMessage.collect { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
        // 제목
        Text(
            text = "로그인",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // 아이디 입력
        Text(
            text = "아이디",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 비밀번호 입력
        Text(
            text = "비밀번호",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF6FAB8E),
                focusedBorderColor = Color(0xFF6FAB8E)
            )
        )

        Spacer(modifier = Modifier.height(188.dp))

        // 로그인 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF6FAB8E))
                .noRippleClickable {
                    when {
                        userId.isBlank() -> Toast.makeText(context, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                        password.isBlank() -> Toast.makeText(context, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                        else -> viewModel.login(userId, password)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "로그인하기",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 회원가입 버튼
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF6FAB8E),
                    shape = RoundedCornerShape(12.dp)
                )
                .noRippleClickable {
                    navController.navigate(Route.SignUp.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "회원가입하기",
                color = Color(0xFF6FAB8E),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 16.sp
            )
        }
    }
}