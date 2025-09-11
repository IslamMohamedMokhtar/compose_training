package com.example.compose.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

@Composable
fun SlideableCell(
    modifier: Modifier = Modifier,
    isRevealed: Boolean,
    action: @Composable RowScope.() -> Unit,
    onExpand: () -> Unit,
    onCollapsed: () -> Unit,
    content: @Composable () -> Unit
) {
    var actionsWidth by remember {
        mutableFloatStateOf(0f)
    }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isRevealed, actionsWidth) {
        scope.launch {
            if (isRevealed) {
                offset.animateTo(-actionsWidth) // 👈 negative = slide left
            } else {
                offset.animateTo(0f)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Actions aligned to the right
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged { actionsWidth = it.width.toFloat() },
            verticalAlignment = Alignment.CenterVertically,
            content = action
        )

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.toInt(), 0) }
                .pointerInput(actionsWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-actionsWidth, 0f) // 👈 clamp negative
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (offset.value <= -actionsWidth / 2f) {
                                    offset.animateTo(-actionsWidth)
                                    onExpand()
                                } else {
                                    offset.animateTo(0f)
                                    onCollapsed()
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}
