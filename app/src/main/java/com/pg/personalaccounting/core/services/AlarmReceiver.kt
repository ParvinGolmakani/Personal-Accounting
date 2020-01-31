package com.pg.personalaccounting.core.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.pg.personalaccounting.core.models.Transaction
import com.pg.personalaccounting.view.alarm.AlarmDialog
import com.tomergoldst.timekeeper.model.Alarm


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) { /*
          Extract alarms from intent
         */
        val alarms: List<Alarm> = intent.getParcelableArrayListExtra("alarms")
        for (alarm in alarms) {
            Log.e("ALARM", alarm.payload.toString())
            AlarmDialog.transaction = Gson().fromJson(alarms[0].payload, Transaction::class.java)
        }

        context?.startActivity(
            Intent(
                context,
                AlarmDialog::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}