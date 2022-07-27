package com.yumenonaka.ymnkapp.screens.weeklyschedule

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.yumenonaka.ymnkapp.BuildConfig
import com.yumenonaka.ymnkapp.DI.httpClient
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.apis.getLatestWeeklyScheduleInfo
import com.yumenonaka.ymnkapp.models.request.WeeklyScheduleInfo
import com.yumenonaka.ymnkapp.utility.StoreImagesUtility
import com.yumenonaka.ymnkapp.utility.extractImageFileName
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

@Composable
fun rememberWeeklyScheduleState(coroutineScope: CoroutineScope = rememberCoroutineScope(), context: Context = LocalContext.current) = remember {
    WeeklyScheduleState(coroutineScope = coroutineScope, context = context)
}

class WeeklyScheduleState(val coroutineScope: CoroutineScope, val context: Context) {
    private val weeklyScheduleAppFolder = File(context.filesDir, "weekly_schedule")
    var weeklyScheduleImageName: String? = null
        private set
    private var scheduleInfo: WeeklyScheduleInfo? = null
    var weeklyScheduleImageBytes: ByteArray? by mutableStateOf(null)
        private set

    init {
        coroutineScope.launch {
            fetchData()
        }
    }

    private suspend fun fetchData() {
        try {
            scheduleInfo = getLatestWeeklyScheduleInfo()
            weeklyScheduleImageName = extractImageFileName(scheduleInfo!!.savedPath)
            weeklyScheduleImageBytes = getWeeklyScheduleImageBytes(weeklyScheduleImageName!!)
        } catch (e: IOException) {
            e.printStackTrace()
            delay(3500)
            fetchData()
        }
    }

    private suspend fun getWeeklyScheduleImageBytes(imageName: String): ByteArray {
        val imageFile = getInAppScheduleImage(imageName)
        if(imageFile != null) {
            return imageFile.readBytes()
        }
        val data = httpClient.get("${BuildConfig.YMNK_CDN_URL}${scheduleInfo!!.savedPath}").readBytes()
        saveImageToApp(data, imageName)
        return data
    }

    private fun getInAppScheduleImage(imageName: String): File? {
        val imageFile = File(weeklyScheduleAppFolder, imageName)
        return if(imageFile.exists()) imageFile else null
    }

    private fun saveImageToApp(byteArray: ByteArray, fileName: String) {
        if(!weeklyScheduleAppFolder.exists()) {
            weeklyScheduleAppFolder.mkdir()
        } else {
            weeklyScheduleAppFolder.listFiles()?.forEach { it.delete() }
        }
        val file = File(weeklyScheduleAppFolder, fileName)
        file.writeBytes(byteArray)
    }

    fun saveImageToPictures() {
        try {
            StoreImagesUtility.saveImages(context, weeklyScheduleImageName!!, weeklyScheduleImageBytes!!, Environment.DIRECTORY_PICTURES + "/YumenoNakaApp")
            Toast.makeText(context, context.getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
    }
}
