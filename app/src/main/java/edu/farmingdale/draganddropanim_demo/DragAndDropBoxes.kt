@file:OptIn(ExperimentalFoundationApi::class)

package edu.farmingdale.draganddropanim_demo

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun DragAndDropBoxes(modifier: Modifier = Modifier) {
    var isPlaying by remember { mutableStateOf(true) }
    var animationDirection by remember { mutableStateOf("none") }
    var pOffset by remember { mutableStateOf(IntOffset(130, 100)) }
    var rtatView by remember { mutableStateOf(0f) }

    Column(modifier = Modifier.fillMaxSize()) {

        // Row with draggable boxes
        Row(
            modifier = modifier
                .fillMaxWidth()
                .weight(0.2f)
        ) {
            val boxCount = 4
            var dragBoxIndex by remember {
                mutableIntStateOf(0)
            }

            repeat(boxCount) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(10.dp)
                        .border(1.dp, Color.Black)
                        .dragAndDropTarget(
                            shouldStartDragAndDrop = { event ->
                                event
                                    .mimeTypes()
                                    .contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                            },
                            target = remember {
                                object : DragAndDropTarget {
                                    override fun onDrop(event: DragAndDropEvent): Boolean {
                                        val dropPositionY = event.offset.y // Get the Y-coordinate of the drop position

                                        if (dropPositionY < 300) {
                                            animationDirection = "up" // Drop is upwards
                                        } else {
                                            animationDirection = "down" // Drop is downwards
                                        }

                                        isPlaying = !isPlaying
                                        dragBoxIndex = index
                                        return true
                                    }
                                }
                            }

                        ),
                    contentAlignment = Alignment.Center
                ) {
                    this@Row.AnimatedVisibility(
                        visible = index == dragBoxIndex,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut() + fadeOut()
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward, // Example icon
                            contentDescription = "Arrow Right",
                            modifier = Modifier
                                .fillMaxSize()
                                .dragAndDropSource {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            // Add any functionality for starting the drag here
                                        }
                                    )
                                },
                            tint = Color.Red
                        )
                    }
                }
            }
        }

        // Animate the position of the face icon
        val pOffsetState by animateIntOffsetAsState(
            targetValue = pOffset,
            animationSpec = tween(3000, easing = LinearEasing)
        )

        // Animate the rotation or scaling of the face icon based on the drop direction
        val rtatViewState by animateFloatAsState(
            targetValue = when (animationDirection) {
                "up" -> 360f // Rotate if dropped upwards
                "down" -> 0f // Reset rotation if dropped downwards
                else -> 0f // Default state (no animation)
            },
            animationSpec = repeatable(
                iterations = if (isPlaying) 10 else 1,
                tween(durationMillis = 3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )

        // You could also use scaling animation if you prefer to differentiate the animations
        val scaleValue by animateFloatAsState(
            targetValue = when (animationDirection) {
                "up" -> 1.5f // Scale up if dropped upwards
                "down" -> 1f // Normal size if dropped downwards
                else -> 1f // Default state (no scaling)
            },
            animationSpec = tween(durationMillis = 300)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .background(Color.Red)
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Face",
                modifier = Modifier
                    .padding(10.dp)
                    .offset(x = pOffsetState.x.dp, y = pOffsetState.y.dp) // Correct usage of offset
                    .rotate(rtatViewState)
                    .scale(scaleValue) // Apply scaling animation based on drop direction
            )
        }


        // Add a reset button
        Button(
            onClick = {
                pOffset = IntOffset(130, 100) // Reset position
                rtatView = 0f // Reset rotation
                animationDirection = "none" // Reset animation direction
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Reset")
        }
    }
}
