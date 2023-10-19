package com.wintermute.mobile.cashbutler.ui.theme.charts

import androidx.compose.ui.graphics.Color

enum class ChartColors(val color: Color) {
    OXFORD_BLUE(Color(0xFF01184a)),
    METALLIC_YELLOW(Color(0xFFFFCE08)),
    VIVID_ORANGE(Color(0xFFFD5F00)),
    SAPPHIRE(Color(0xFF1259b8)),
    ROBING_EGG_BLUE(Color(0xFF0fd4C4)),
    PETROLEUM_GRAY(Color(0xFF37474f)),
    PETROLEUM_LIGHT_GRAY(Color(0xFF455a64));

    companion object {
        fun getByIndex(index: Int): Color {
            return values()[index].color
        }
    }
}