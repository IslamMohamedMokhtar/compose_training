package com.example.compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import kotlin.math.max
import kotlin.math.min

enum class ImageCollageLayout { Collage, Wrap }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageCollageGrid(
    items: List<String>,
    modifier: Modifier = Modifier,
    cornerRadiusDp: Int = 12,
    spacingDp: Int = 8,
    maxItems: Int = items.size,
    layout: ImageCollageLayout = ImageCollageLayout.Wrap,
    spanProvider: (index: Int) -> Int = { 1 }, // 1 -> 1x1, 2 -> 2x2
) {
    if (items.isEmpty()) return

    val displayedItemsCount = min(items.size, maxItems)
    val displayedItems = items.take(displayedItemsCount)
    val remainingCount = max(0, items.size - displayedItemsCount)

    val shape = RoundedCornerShape(cornerRadiusDp.dp)
    val spacing = spacingDp.dp

    if (layout == ImageCollageLayout.Wrap) {
        PackedGrid(
            modifier = modifier.fillMaxWidth(),
            itemCount = displayedItems.size,
            spacing = spacing,
            span = { index -> spanProvider(index).coerceIn(1, 2) }
        ) { index ->
            val url = displayedItems[index]
            val isLastVisible = index == displayedItems.lastIndex
            CollageImageTile(
                imageUrl = url,
                modifier = Modifier
                    .clip(shape),
                shape = shape,
                overlayText = if (isLastVisible && remainingCount > 0) "+$remainingCount" else null
            )
        }
        return
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        val leftUrl = displayedItems[0]
        CollageImageTile(
            imageUrl = leftUrl,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(shape),
            shape = shape
        )

        val rightItems = displayedItems.drop(1)
        if (rightItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(spacing)
            ) {
                val rows = rightItems.chunked(2)
                rows.forEachIndexed { rowIndex, rowItems ->
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(spacing)
                    ) {
                        rowItems.forEachIndexed { colIndex, url ->
                            val isLastTile = (rowIndex == rows.lastIndex) && (colIndex == rowItems.lastIndex)
                            val showOverlayCount = isLastTile && remainingCount > 0

                            CollageImageTile(
                                imageUrl = url,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clip(shape),
                                shape = shape,
                                overlayText = if (showOverlayCount) "+$remainingCount" else null
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CollageImageTile(
    imageUrl: String,
    modifier: Modifier,
    shape: RoundedCornerShape,
    overlayText: String? = null,
    fixedSize: Dp? = null,
) {

    val sizedModifier = if (fixedSize != null) {
        modifier
            .size(fixedSize)
    } else modifier

    Box(modifier = sizedModifier
        .let { base -> if (fixedSize != null) base.aspectRatio(1f) else base }
        .clickable {
        }
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray.copy(alpha = 0.4f), shape)
                )
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f), shape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Image",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        )

        if (overlayText != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f), shape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = overlayText,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
@Composable
private fun PackedGrid(
    modifier: Modifier = Modifier,
    itemCount: Int,
    spacing: Dp,
    span: (index: Int) -> Int,
    content: @Composable (index: Int) -> Unit,
) {
    Layout(
        modifier = modifier,
        content = { for (i in 0 until itemCount) content(i) }
    ) { measurables, constraints ->
        val spacingPx = spacing.roundToPx()
        val maxWidth = constraints.maxWidth
        val columns = 3
        val cellSize = (maxWidth - spacingPx * (columns - 1)) / columns

        // Track grid occupancy
        val grid = mutableListOf(BooleanArray(columns) { false })
        val positions = Array(itemCount) { IntArray(3) }

        fun ensureRows(rows: Int) {
            while (grid.size < rows) grid.add(BooleanArray(columns) { false })
        }

        for (i in measurables.indices) {
            val s = span(i).coerceIn(1, 2)
            var placed = false
            outer@for (r in 0..grid.size) {
                ensureRows(r + s)
                for (c in 0..(columns - s)) {
                    // Check if s x s block is free
                    var canPlace = true
                    for (rr in r until r + s) {
                        for (cc in c until c + s) {
                            if (grid[rr][cc]) {
                                canPlace = false
                                break
                            }
                        }
                        if (!canPlace) break
                    }
                    if (canPlace) {
                        // Mark occupied
                        for (rr in r until r + s) {
                            for (cc in c until c + s) grid[rr][cc] = true
                        }
                        positions[i][0] = r
                        positions[i][1] = c
                        positions[i][2] = s
                        placed = true
                        break@outer
                    }
                }
            }
            if (!placed) error("No space for item $i")
        }

        val placeable = measurables.mapIndexed { index, measurable ->
            val s = positions[index][2]
            val sizePx = s * cellSize + (s - 1) * spacingPx
            measurable.measure(
                constraints.copy(
                    minWidth = sizePx,
                    maxWidth = sizePx,
                    minHeight = sizePx,
                    maxHeight = sizePx
                )
            )
        }

        val totalRows = grid.size
        val height = totalRows * cellSize + spacingPx * (totalRows - 1)

        layout(width = maxWidth, height = height) {
            placeable.forEachIndexed { index, placeable ->
                val r = positions[index][0]
                val c = positions[index][1]
                val x = c * (cellSize + spacingPx)
                val y = r * (cellSize + spacingPx)
                placeable.placeRelative(x, y)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewImageCollageGrid() {
    ImageCollageGrid(
        items = listOf(
            "https://picsum.photos/seed/1/600/600",
            "https://picsum.photos/seed/2/600/600",
            "https://picsum.photos/seed/3/600/600",
            "https://picsum.photos/seed/4/600/600",
            "https://picsum.photos/seed/5/600/600",
            "https://picsum.photos/seed/6/600/600",
            "https://picsum.photos/seed/7/600/600",
            "https://picsum.photos/seed/8/600/600",
            "https://picsum.photos/seed/9/600/600",
        ),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(12.dp),
        layout = ImageCollageLayout.Wrap,
        spanProvider = { index -> if (index == 0) 2 else 1 }
    )
}