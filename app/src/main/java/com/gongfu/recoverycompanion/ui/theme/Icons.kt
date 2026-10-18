package com.gongfu.recoverycompanion.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.gongfu.recoverycompanion.R

val LogoIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.heart_plus_24dp_e3e3e3_fill0_wght400_grad0_opsz24)

val successLog: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.sentiment_excited_24dp_e3e3e3_fill0_wght400_grad0_opsz24)

val neutralLog: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.sentiment_neutral_24dp_e3e3e3_fill0_wght400_grad0_opsz24)

val slipLog: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.sentiment_frustrated_24dp_e3e3e3_fill0_wght400_grad0_opsz24)

val backArrow: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.arrow_back_24px)

val backArrowAlt: ImageVector
    @Composable
    get() = ImageVector.vectorResource(R.drawable.arrow_back_ios_new_24px)

val helpQuestion: ImageVector
    @Composable
    get() = ImageVector.vectorResource((R.drawable.help_24dp_e3e3e3_fill0_wght400_grad0_opsz24))
