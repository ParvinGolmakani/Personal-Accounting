package com.pg.personalaccounting.core.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.pg.personalaccounting.view.account.AddAccountActivity
import com.tomergoldst.timekeeper.model.Alarm


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) { /*
          Extract alarms from intent
         */
        val alarms: List<Alarm> = intent.getParcelableArrayListExtra("alarms")
        for (alarm in alarms)
            Log.e("ALARM", alarm.payload.toString())
        context?.startActivity(
            Intent(
                context,
                AddAccountActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}