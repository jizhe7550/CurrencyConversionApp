package com.joeji.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    val currencyType: String,
    val rate: Double,
)