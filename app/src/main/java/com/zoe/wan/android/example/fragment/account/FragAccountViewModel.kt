package com.zoe.wan.android.example.fragment.account

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.example.repository.Repository
import com.zoe.wan.base.BaseViewModel
import kotlinx.coroutines.launch

class FragAccountViewModel(application: Application): BaseViewModel(application) {
    fun getAccountList(){
       viewModelScope.launch {
          val data =  Repository.getAccountList()
       }
    }
}