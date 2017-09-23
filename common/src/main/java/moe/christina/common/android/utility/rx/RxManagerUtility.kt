package moe.christina.common.android.utility.rx

import io.reactivex.Flowable
import io.reactivex.Observable

fun <T> Observable<T>.autoManage(manager: RxManager): Observable<T> =
    manager.autoManage(this)

fun <T> Flowable<T>.autoManage(manager: RxManager): Flowable<T> =
    manager.autoManage(this)