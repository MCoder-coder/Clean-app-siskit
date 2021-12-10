package com.example.app_base_siskit.feature_map.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BroadcastReceiverDownload {

    companion object {

        var downloadID: Long = 0
        var onDownloadComplete: BroadcastReceiver? = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                //Fetching the download id received with the broadcast
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                //Checking if the received broadcast is for our enqueued download by matching download id
                if (downloadID == id) {
                    Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}