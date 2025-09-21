package com.fpstudio.stretchreminder.ui.composable.triangle

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fpstudio.stretchreminder.ui.theme.Green_primary

/**
 * Enumeration defining the orientation of the triangle
 */
enum class TriangleOrientation {
    TOP, BOTTOM, LEFT, RIGHT
}

/**
 * A composable that draws a triangle with customizable properties
 *
 * @param modifier Modifier to be applied to the triangle
 * @param color Color of the triangle
 * @param orientation Orientation of the triangle (TOP, BOTTOM, LEFT, RIGHT)
 * @param size Size of the triangle
 * @param strokeWidth Width of the stroke (if 0, the triangle is filled)
 * @param isFilled Whether the triangle should be filled or just outlined
 */
@Composable
fun Triangle(
    modifier: Modifier = Modifier,
    color: Color = Green_primary,
    orientation: TriangleOrientation = TriangleOrientation.TOP,
    size: Dp = 24.dp,
    strokeWidth: Float = 0f,
    isFilled: Boolean = true
) {
    Box(modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            val path = Path()
            val width = this.size.width
            val height = this.size.height

            when (orientation) {
                TriangleOrientation.TOP -> {
                    // Points for triangle pointing upward
                    path.moveTo(width / 2f, 0f)
                    path.lineTo(width, height)
                    path.lineTo(0f, height)
                    path.close()
                }
                TriangleOrientation.BOTTOM -> {
                    // Points for triangle pointing downward
                    path.moveTo(0f, 0f)
                    path.lineTo(width, 0f)
                    path.lineTo(width / 2f, height)
                    path.close()
                }
                TriangleOrientation.LEFT -> {
                    // Points for triangle pointing left
                    path.moveTo(0f, height / 2f)
                    path.lineTo(width, 0f)
                    path.lineTo(width, height)
                    path.close()
                }
                TriangleOrientation.RIGHT -> {
                    // Points for triangle pointing right
                    path.moveTo(0f, 0f)
                    path.lineTo(width, height / 2f)
                    path.lineTo(0f, height)
                    path.close()
                }
            }

            if (isFilled) {
                drawPath(path = path, color = color, style = Fill)
            } else {
                drawPath(path = path, color = color, style = Stroke(width = strokeWidth))
            }
        }
    }
}

/**
 * A composable that draws an equilateral triangle with customizable properties
 *
 * @param modifier Modifier to be applied to the triangle
 * @param color Color of the triangle
 * @param orientation Orientation of the triangle (TOP, BOTTOM, LEFT, RIGHT)
 * @param size Size of the triangle
 * @param strokeWidth Width of the stroke (if 0, the triangle is filled)
 * @param isFilled Whether the triangle should be filled or just outlined
 */
@Composable
fun EquilateralTriangle(
    modifier: Modifier = Modifier,
    color: Color = Green_primary,
    orientation: TriangleOrientation = TriangleOrientation.TOP,
    size: Dp = 24.dp,
    strokeWidth: Float = 0f,
    isFilled: Boolean = true
) {
    Box(modifier = modifier.size(size)) {
        Canvas(modifier = Modifier.size(size)) {
            val path = Path()
            val width = this.size.width
            val height = this.size.height

            when (orientation) {
                TriangleOrientation.TOP -> {
                    // Equilateral triangle pointing upward
                    val halfWidth = width / 2f
                    val height = (width * Math.sqrt(3.0) / 2f).toFloat()

                    path.moveTo(halfWidth, 0f)
                    path.lineTo(width, height)
                    path.lineTo(0f, height)
                    path.close()
                }
                TriangleOrientation.BOTTOM -> {
                    // Equilateral triangle pointing downward
                    val halfWidth = width / 2f
                    val triangleHeight = (width * Math.sqrt(3.0) / 2f).toFloat()

                    path.moveTo(0f, 0f)
                    path.lineTo(width, 0f)
                    path.lineTo(halfWidth, triangleHeight)
                    path.close()
                }
                TriangleOrientation.LEFT -> {
                    // Equilateral triangle pointing left
                    val halfHeight = height / 2f
                    val triangleWidth = (height * Math.sqrt(3.0) / 2f).toFloat()

                    path.moveTo(0f, halfHeight)
                    path.lineTo(triangleWidth, 0f)
                    path.lineTo(triangleWidth, height)
                    path.close()
                }
                TriangleOrientation.RIGHT -> {
                    // Equilateral triangle pointing right
                    val halfHeight = height / 2f
                    val triangleWidth = (height * Math.sqrt(3.0) / 2f).toFloat()

                    path.moveTo(0f, 0f)
                    path.lineTo(triangleWidth, halfHeight)
                    path.lineTo(0f, height)
                    path.close()
                }
            }

            if (isFilled) {
                drawPath(path = path, color = color, style = Fill)
            } else {
                drawPath(path = path, color = color, style = Stroke(width = strokeWidth))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrianglePreview() {
    Box {
        Triangle(
            size = 100.dp,
            color = Green_primary,
            orientation = TriangleOrientation.TOP
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TriangleOutlinedPreview() {
    Box {
        Triangle(
            size = 100.dp,
            color = Green_primary,
            orientation = TriangleOrientation.BOTTOM,
            isFilled = false,
            strokeWidth = 2f
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EquilateralTrianglePreview() {
    Box {
        EquilateralTriangle(
            size = 100.dp,
            color = Green_primary,
            orientation = TriangleOrientation.RIGHT
        )
    }
}
