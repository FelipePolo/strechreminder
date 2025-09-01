package com.fpstudio.stretchreminder.ui.composable.question.imageSingleChoice

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiModel
import com.fpstudio.stretchreminder.ui.composable.question.QuestionUiSelection
import com.fpstudio.stretchreminder.ui.theme.Green2
import com.fpstudio.stretchreminder.ui.theme.Gray

@Composable
fun ImageSingleChoiceQuestion(
    model: QuestionUiModel.ImageSingleChoice,
    onSelect: (QuestionUiSelection.IntSelection) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = model.question,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (model.imagesResId.size == 3) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ImageItem(
                        imageRes = model.imagesResId[0],
                        isSelected = model.selected == 0,
                        onClick = {
                            onSelect(QuestionUiSelection.IntSelection( 0))
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(200.dp)
                    )
                    ImageItem(
                        imageRes = model.imagesResId[1],
                        isSelected = model.selected == 1,
                        onClick = {
                            onSelect(QuestionUiSelection.IntSelection( 1))
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .height(200.dp)
                    )
                }

                ImageItem(
                    imageRes = model.imagesResId[2],
                    isSelected = model.selected == 2,
                    onClick = {
                        onSelect(QuestionUiSelection.IntSelection( 2))
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            model.imagesResId.forEachIndexed { i, option ->
                ImageItem(
                    imageRes = option,
                    isSelected = model.selected == i,
                    onClick = {
                        onSelect(QuestionUiSelection.IntSelection( i))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ImageItem(
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Green2 else Color.White,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = "backgroundColor"
    )

    Card(
        modifier = modifier
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) Green2 else Gray
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Image $imageRes",
            modifier = Modifier
                .fillMaxSize()
                .size(100.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )
    }
}
