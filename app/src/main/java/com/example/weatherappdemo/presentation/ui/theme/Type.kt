package com.example.weatherappdemo.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        color = Color.White
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        shadow = Shadow(
            color = Color.LightGray,
            offset = Offset(3.0f, 3.0f),
            blurRadius = 3f
        )
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        color = Color.White
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = Color.White,
        textAlign = TextAlign.Center
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = Color.White
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        color = Color.LightGray
    )
)