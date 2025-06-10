package com.konkuk.gomgomee.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.konkuk.gomgomee.ui.theme.White
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import com.konkuk.gomgomee.data.local.entity.ChecklistResultEntity
import com.konkuk.gomgomee.data.local.entity.TestSessionEntity
import kotlinx.coroutines.launch

@Composable
fun TestHistoryScreen(
    navController: NavController,
    viewModel: TestHistoryViewModel = viewModel()
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("체크리스트", "영역별 진단")
    val checklistResults by viewModel.checklistResults.collectAsState(initial = emptyList())
    val areaTestSessions by viewModel.areaTestSessions.collectAsState(initial = emptyList())

    // 화면이 표시될 때마다 데이터 다시 로드
    LaunchedEffect(Unit) {
        viewModel.loadTestHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(start = 20.dp, end = 20.dp, top = 50.dp)
    ) {
        Text(
            text = "테스트 기록",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 18.dp, bottom = 16.dp)
        )

        // 탭 레이아웃
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            containerColor = Color.White,
            contentColor = Color(0xFF6FAB8E),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color(0xFF6FAB8E)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title, color = Color.Black) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> ChecklistHistoryTab(viewModel)
            1 -> AreaTestHistoryTab(viewModel)
        }
    }
}

@Composable
fun ChecklistHistoryTab(viewModel: TestHistoryViewModel) {
    val checklistResults by viewModel.checklistResults.collectAsState(initial = emptyList())
    
    if (checklistResults.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "아직 체크리스트 기록이 없습니다.",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(checklistResults) { result ->
                ChecklistResultItem(result)
            }
        }
    }
}

@Composable
fun AreaTestHistoryTab(viewModel: TestHistoryViewModel) {
    val areaTestSessions by viewModel.areaTestSessions.collectAsState(initial = emptyList())
    
    if (areaTestSessions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "아직 영역별 진단 기록이 없습니다.",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(areaTestSessions) { session ->
                AreaTestSessionItem(session)
            }
        }
    }
}

@Composable
fun ChecklistResultItem(result: ChecklistResultEntity) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5F1E7)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormat.format(Date(result.createdAt)),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "자가진단 체크리스트",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand"
                )
            }
            
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                val riskScore = (result.yesCount.toFloat() / 17 * 100).toInt() // 체크리스트 문항 수 하드코딩..
                val recommendation = when {
                    riskScore < 30 -> "현재는 특별한 이상이 없어 보입니다.\n정기적인 관찰만으로도 충분합니다."
                    riskScore < 70 -> "전문가와의 상담이 권장됩니다.\n조기 발견이 중요할 수 있습니다."
                    else -> "전문의의 정밀 진단이 필요합니다.\n가까운 병원에서 진단을 받아보세요."
                }
                Text(
                    text = "위험도: $riskScore%",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = recommendation,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun AreaTestSessionItem(session: TestSessionEntity) {
    var expanded by remember { mutableStateOf(false) }
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE5F1E7)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = dateFormat.format(Date(session.startedAt.toLong())),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = when(session.domain) {
                            "읽기" -> "읽기 영역 테스트"
                            "쓰기" -> "쓰기 영역 테스트"
                            "산수" -> "산수 영역 테스트"
                            "듣기" -> "듣기 영역 테스트"
                            else -> "${session.domain} 테스트"
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand"
                )
            }
            
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "정답률: ${(session.correctCount.toFloat() / session.totalCount.toFloat() * 100).toInt()}%",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "맞은 문제: ${session.correctCount}개 / 전체 문제: ${session.totalCount}개",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
} 