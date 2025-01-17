package com.github.jing332.tts_server_android.ui.systts.edit.http

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drake.net.utils.withIO
import com.github.jing332.tts_server_android.help.audio.AudioDecoder
import com.github.jing332.tts_server_android.model.tts.HttpTTS
import kotlinx.coroutines.launch

class HttpTtsEditViewModel : ViewModel() {
    fun doTest(
        tts: HttpTTS,
        text: String,
        onSuccess: suspend (audio: ByteArray, sampleRate: Int, mime: String, contentType: String) -> Unit,
        onFailure: suspend (reason: Throwable) -> Unit,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                val resp = withIO { tts.getAudioResponse(text) }
                val data = withIO { resp.body?.bytes() }

                if (resp.code != 200) {
                    onFailure.invoke(Exception("服务器返回错误信息：\n${data?.decodeToString()}"))
                    return@launch
                }

                if (data == null) onFailure.invoke(Exception("音频为空"))
                val contentType = resp.header("Content-Type", "无") ?: "无"

                data?.let {
                    val formats = AudioDecoder.getSampleRateAndMime(it)
                    resp.body?.close()

                    val mSampleRate = formats.first
                    val mMime = formats.second
                    onSuccess(it, mSampleRate, mMime, contentType)
                }
            }.onFailure {
                onFailure.invoke(it)
            }
        }
    }
}