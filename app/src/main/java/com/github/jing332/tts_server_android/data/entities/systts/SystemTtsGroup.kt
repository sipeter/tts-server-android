package com.github.jing332.tts_server_android.data.entities.systts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.jing332.tts_server_android.data.entities.AbstractListGroup

@kotlinx.serialization.Serializable
@Entity
data class SystemTtsGroup(
    @ColumnInfo("groupId")
    @PrimaryKey
    override val id: Long = System.currentTimeMillis(),

    override var name: String,

    @ColumnInfo(defaultValue = "0")
    override var order: Int = 0,
    override var isExpanded: Boolean = false
) : AbstractListGroup