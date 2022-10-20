package com.example.weatherappdemo.domain.weather

import androidx.annotation.DrawableRes
import com.example.weatherappdemo.R

sealed class WindDirectionType(
    val directionDesc: String,
    @DrawableRes val iconRes: Int
) {
    object North : WindDirectionType(
        directionDesc = "North",
        iconRes = R.drawable.ic_north
    )
    object Northeast : WindDirectionType(
        directionDesc = "Northeast",
        iconRes = R.drawable.ic_north_east
    )
    object East : WindDirectionType(
        directionDesc = "East",
        iconRes = R.drawable.ic_east
    )
    object Southeast : WindDirectionType(
        directionDesc = "Southeast",
        iconRes = R.drawable.ic_south_east
    )
    object South : WindDirectionType(
        directionDesc = "South",
        iconRes = R.drawable.ic_south
    )
    object Southwest : WindDirectionType(
        directionDesc = "Southwest",
        iconRes = R.drawable.ic_south_west
    )
    object West : WindDirectionType(
        directionDesc = "West",
        iconRes = R.drawable.ic_west
    )
    object Northwest : WindDirectionType(
        directionDesc = "Northwest",
        iconRes = R.drawable.ic_north_west
    )
    object Windless : WindDirectionType(
        directionDesc = "Windless",
        iconRes = R.drawable.ic_windless
    )

    companion object {
        fun fromDir(code: Int?): WindDirectionType {
            return when (code) {
                in 0..11 -> North
                in 12..56 -> Northeast
                in 57..101 -> East
                in 102..146 -> Southeast
                in 147..191 -> South
                in 192..236 -> Southwest
                in 237..281 -> West
                in 282..326 -> Northwest
                in 327..360 ->  North
                else -> Windless
            }
        }
    }
}
