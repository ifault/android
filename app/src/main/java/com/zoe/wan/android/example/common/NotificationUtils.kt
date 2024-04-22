import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.blankj.utilcode.util.ActivityUtils.startActivity
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.activity.home.TabActivity

object NotificationUtils {
    private const val CHANNEL_ID = "my_channel_id"
    private const val CHANNEL_NAME = "My Channel"
    private const val NOTIFICATION_ID = 1
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(context: Context, title: String, message: String) {
        createNotificationChannel(context)

        val intent = Intent(context, TabActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_hot_key_grey)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // 设置点击通知时的跳转操作

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            showPermissionDeniedMessage(context)
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showPermissionDeniedMessage(context: Context) {
        val message = "请打开通知权限"
        val builder = AlertDialog.Builder(context)
            .setTitle("通知权限受限")
            .setMessage(message)
            .setPositiveButton("打开设置") { dialog, _ ->
                openAppSettings(context)
                dialog.dismiss()
            }
            .setNegativeButton("结束") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }
    private fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}