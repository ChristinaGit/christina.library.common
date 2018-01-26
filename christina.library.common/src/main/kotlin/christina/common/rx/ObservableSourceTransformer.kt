package christina.common.rx

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

interface ObservableSourceTransformer<Upstream, Downstream> :
    CompletableTransformer,
    SingleTransformer<Upstream, Downstream>,
    MaybeTransformer<Upstream, Downstream>,
    ObservableTransformer<Upstream, Downstream>,
    FlowableTransformer<Upstream, Downstream>
