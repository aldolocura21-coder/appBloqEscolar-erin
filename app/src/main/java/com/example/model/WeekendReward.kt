package com.example.model

data class WeekendMission(
    val id: String,
    val title: String,
    val description: String,
    val targetCount: Int,
    val currentCount: Int,
    val rewardCoins: Int,
    val isCompleted: Boolean = false
)

data class RedeemableReward(
    val id: String,
    val title: String,
    val description: String,
    val costCoins: Int,
    val iconName: String,
    val isRedeemed: Boolean = false
)
