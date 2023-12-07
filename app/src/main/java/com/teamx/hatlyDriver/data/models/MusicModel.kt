package com.teamx.hatlyDriver.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music_table")
class MusicModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0


)