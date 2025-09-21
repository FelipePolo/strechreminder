package com.fpstudio.stretchreminder.ui.composable.statsitem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fpstudio.stretchreminder.R
import com.fpstudio.stretchreminder.ui.theme.Gray4
import com.fpstudio.stretchreminder.ui.theme.Green_quaternary
import com.fpstudio.stretchreminder.ui.theme.Green_tertiary

@Composable
fun StatsItem(
    statsItemUiModel: StatsItemUiModel
) {
    ConstraintLayout(modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(Green_tertiary)) {
        val (title, value, valueName, icon) = createRefs()
        Text(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, 8.dp)
                start.linkTo(parent.start, 8.dp)
            },
            text = statsItemUiModel.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )

        Text(
            modifier = Modifier.constrainAs(value) {
                bottom.linkTo(valueName.top)
                start.linkTo(valueName.start)
                end.linkTo(valueName.end)
            },
            text = statsItemUiModel.value,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Green_quaternary,
        )

        Text(
            modifier = Modifier.constrainAs(valueName) {
                bottom.linkTo(parent.bottom, 8.dp)
                start.linkTo(parent.start, 8.dp)
            },
            text = statsItemUiModel.valueName,
            fontSize = 10.sp,
            color = Gray4,
        )

        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(title.bottom, 16.dp)
                    start.linkTo(title.end, 12.dp)
                    bottom.linkTo(parent.bottom, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                }
                .size(50.dp),
            painter = painterResource(id = statsItemUiModel.iconId),
            contentDescription = "Stats Icon",
            tint = Green_tertiary,
        )
    }
}

@Preview
@Composable
fun StatsItemPreview() {
    StatsItem(
        statsItemUiModel = StatsItemUiModel(
            title = "Today's\nstretching time",
            value = "0",
            valueName = "Min",
            iconId = R.drawable.ic_clock,
        )
    )
}