package com.alyjak.shopping.ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BasePresenter(private val view: BaseView) {

    private val job = Job()
    val coroutineContext = CoroutineScope(Dispatchers.IO + job)

}