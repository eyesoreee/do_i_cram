package com.example.doicram.dashboard

data class DashboardCard(
    val title: String,
    val mainValue: String,
    val secondaryValue: String,
    val bottomText: String,
    val cardType: CardType
)