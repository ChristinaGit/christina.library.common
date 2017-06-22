package moe.christina.common.core

import android.os.Looper
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxSchedulers {
    fun io(): Scheduler = Schedulers.io()
    fun computation(): Scheduler = Schedulers.computation()
    fun newThread(): Scheduler = Schedulers.newThread()
    fun main(): Scheduler = AndroidSchedulers.mainThread()
    fun from(looper: Looper): Scheduler = AndroidSchedulers.from(looper)
}