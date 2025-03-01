package com.example.myapp2.view.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp2.model.entity.QuizWithQuestsAndOptions

@Composable
fun QuizCardHomeComponent(item: QuizWithQuestsAndOptions, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f)),
        color = Color.White,
        modifier = modifier
            .width(230.dp),
        onClick = {

        }
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {
            Surface(
                modifier = Modifier
                    .size(70.dp),
                shape = RoundedCornerShape(50),
                color = Color(item.quiz.color).copy(alpha = 0.15f)
                
            ) {
                Image(
                    painter = painterResource(
                        id = item.quiz.icon,
                    ),
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(text = item.quiz.title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 20.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 16.dp))
            Text(text = "${item.quests.size} Question", modifier = Modifier.padding(top = 8.dp))

            Box(modifier = Modifier.padding(top = 16.dp)) {
                LinearProgressIndicator(
                    progress = { 0.5f },
                    trackColor = Color(item.quiz.color).copy(alpha = 0.15f),
                    color = Color(item.quiz.color),
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.height(8.dp)
                )
            }
        }
    }
}