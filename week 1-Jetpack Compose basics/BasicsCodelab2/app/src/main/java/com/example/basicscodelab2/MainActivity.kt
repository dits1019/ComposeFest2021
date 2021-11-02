package com.example.basicscodelab2

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab2.ui.theme.BasicsCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab2Theme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    // = 대신 by를 사용하면 .value를 사용하지 않다도 됨
    // remeber는 composable이 유지되는 동안에만 작동
    // 따라서 회전과 구성 변경(다크 모드등...), 프로세스 종료 시
    // 전체활동이 다시 시작되므로 모든 상태가 손실됨
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Log.d("LogcatLog", "none open the group")
        Greetings()
    }
}


// { "$it" } -> 목록 인덱스를 나타냄
@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}


//@Composable
//fun Greeting(name: String) {
//    // 데이터가 변경되면 새 데이터로 다시 실행하여 업데이트된 UI를 생성 = 재구성
//    // remember를 사용하지 않으면 변수도 같이 재구성되면서 바뀌지 않고 원래값으로 바뀜
//    // 따라서 remember를 사용하여 재설정되지 않게 막기(기억한다)
//    var expanded by remember { mutableStateOf(false) }
//
//    // expanded에 의존하고 간단한 계산만 하기에 remember를 사용할 필요 X
////    val extraPadding = if (expanded.value) 48.dp else 0.dp
//
//    val extraPadding by animateDpAsState(
//        if (expanded) 48.dp else 0.dp,
//        // 스프링 같은 애니메이션
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    Surface(
//        color = MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
//    ) {
//        Row(modifier = Modifier.padding(24.dp)) {
//            Column(
//                modifier = Modifier
//                    .weight(1f)
//                    // 최솟값이 음수가 되지 않게 설정(음수면 앱이 충돌할 수 있음)
//                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
//            ) {
//                Text(text = "Hello,")
//                Text(
//                    text = name, style = MaterialTheme.typography.h4.copy(
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                )
//            }
//            OutlinedButton(onClick = { expanded = !expanded }) {
//                Text(if (expanded) "Show less" else "Show more")
//            }
//        }
//    }
//}

@Composable
private fun Greeting(name: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}


@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }

            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        Greetings()
    }
}

@Composable
private fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelab2Theme {
        OnboardingScreen(onContinueClicked = {})
    }
}