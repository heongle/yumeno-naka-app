package com.yumenonaka.ymnkapp.services.soine

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.yumenonaka.ymnkapp.MainActivity
import com.yumenonaka.ymnkapp.R
import com.yumenonaka.ymnkapp.data.DeepLink
import com.yumenonaka.ymnkapp.data.YmnkChannelId
import com.yumenonaka.ymnkapp.utility.SoineRandom

class SoinePlayerService: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private val soineBinder = SoineBinder()
    private var mediaPlayer: MediaPlayer? = null
    private val soineAudioPrefix = "shiori_shinnon_"
    private val soineAudioChannels = listOf("l", "r")
    private val soineNotificationId = 4021
    private val soineRandom = SoineRandom()

    inner class SoineBinder: Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService() = this@SoinePlayerService
    }

    override fun onBind(p0: Intent?): IBinder {
        return soineBinder
    }

    override fun onCreate() {
        super.onCreate()
        createSoineNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return when(intent?.action) {
            SoinePlayerAction.PLAY.value -> {
                // TODO
                // Create the TaskStackBuilder
                val pendingIntent = TaskStackBuilder.create(this).run {
                    // Create an Intent for the activity you want to start
                    val deepLinkIntent = Intent(
                        Intent.ACTION_VIEW,
                        "${DeepLink.SOINE}?playing=true".toUri(),
                        this@SoinePlayerService,
                        MainActivity::class.java
                    )
                    // Add the intent, which inflates the back stack
                    addNextIntentWithParentStack(deepLinkIntent)
                    // Get the PendingIntent containing the entire back stack
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                }

                startPlayer()
                startForeground(soineNotificationId, createNotification(pendingIntent))
                START_STICKY
            }
            SoinePlayerAction.STOP.value -> {
                stopPlayer()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                START_NOT_STICKY
            }
            else -> {
                stopSelf()
                START_NOT_STICKY
            }
        }
    }

    fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    private fun createSoineNotificationChannel() {
        // Notification channel is needed on android 8.0 or above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(YmnkChannelId.SOINE, getString(R.string.suyasuya_channel), NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = getString(R.string.suyasuya_channel_description)
                setShowBadge(false)
            }
            // Register the channel with the system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            setDataSource(getRandomSoineFd())
            setOnPreparedListener(this@SoinePlayerService)
            setOnCompletionListener(this@SoinePlayerService)
            prepareAsync()
        }
    }

    private fun stopPlayer() {
        if(isPlaying()) {
            mediaPlayer?.stop()
        }
        mediaPlayer?.reset()
        mediaPlayer?.release()
    }

    private fun createNotification(intent: PendingIntent?): Notification {
        return NotificationCompat.Builder(this, YmnkChannelId.SOINE)
            .setSmallIcon(R.drawable.outline_play_circle_24)
            .setSilent(true)
            .setContentTitle(getString(R.string.suyasuya))
            .setContentText(getString(R.string.playing_soine_sound))
            .setContentIntent(intent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    private fun getRandomSoineFd(): AssetFileDescriptor {
        return assets.openFd("soine/$soineAudioPrefix${soineAudioChannels[soineRandom.nextSoineIndex()]}.flac")
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    override fun onCompletion(mp: MediaPlayer) {
        mp.apply {
            stop()
            reset()
            setDataSource(getRandomSoineFd())
            prepareAsync()
        }
    }
}
