package com.sumitgupta.mynotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment:Fragment(),CoroutineScope {

    private lateinit var job: Job // Job is the background task in coroutines

    // in coroutines we have dispatchers which define the thread to execute the job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job=Job()  // creating Job instance
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}