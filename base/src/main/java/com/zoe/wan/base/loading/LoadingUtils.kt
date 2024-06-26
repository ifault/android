package com.zoe.wan.base.loading

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.blankj.utilcode.util.ActivityUtils
import java.lang.ref.WeakReference

object LoadingUtils {
    private var activity: WeakReference<Activity>? = null
    private var loadingDialog: LoadingDialog? = null

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                LoadingUtils.activity = WeakReference(activity)
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

        })
    }

    fun showLoading() {
        if(loadingDialog != null && loadingDialog?.isShowing == true){
            return
        }
        loadingDialog = LoadingDialog(ActivityUtils.getTopActivity())
        loadingDialog?.showLoading()
    }

    fun dismiss() {
        loadingDialog?.dismiss()
    }
}