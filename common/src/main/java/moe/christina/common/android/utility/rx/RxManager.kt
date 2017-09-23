package moe.christina.common.android.utility.rx

import io.reactivex.Flowable
import io.reactivex.Observable

interface RxManager {
    fun <T> autoManage(observable: Observable<T>): Observable<T>
    fun <T> autoManage(observable: Flowable<T>): Flowable<T>
}