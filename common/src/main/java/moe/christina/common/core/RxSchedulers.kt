package moe.christina.common.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxSchedulers {
    val io: Scheduler = Schedulers.io()
    val computation: Scheduler = Schedulers.computation()
    val new: Scheduler = Schedulers.newThread()
    val main: Scheduler = AndroidSchedulers.mainThread()
}