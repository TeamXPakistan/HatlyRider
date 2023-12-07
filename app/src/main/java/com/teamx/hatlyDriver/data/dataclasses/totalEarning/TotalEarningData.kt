package com.teamx.hatlyDriver.data.dataclasses.totalEarning

data class TotalEarningData(
    val riderDetail: RiderDetail,
    val spentTime: SpentTime,
    val totalEarning: Double,
    val totalOrder: Double,
    val totalParcel: Double
)