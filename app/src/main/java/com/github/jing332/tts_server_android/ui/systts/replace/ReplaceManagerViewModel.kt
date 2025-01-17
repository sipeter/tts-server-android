package com.github.jing332.tts_server_android.ui.systts.replace

import androidx.lifecycle.ViewModel
import com.github.jing332.tts_server_android.App
import com.github.jing332.tts_server_android.data.appDb
import com.github.jing332.tts_server_android.data.entities.replace.GroupWithReplaceRule
import kotlinx.serialization.encodeToString
import java.util.*

class ReplaceManagerViewModel : ViewModel() {
    fun configToJson(): String {
        val list = appDb.replaceRuleDao.allGroupWithReplaceRules()
        return App.jsonBuilder.encodeToString(list)
    }

    @Suppress("UNCHECKED_CAST")
    fun exportGroup(model: GroupModel): String {
        return App.jsonBuilder.encodeToString(
            GroupWithReplaceRule(
                model.data, (model.itemSublist as List<ItemModel>).map { it.data }
            )
        )
    }

}