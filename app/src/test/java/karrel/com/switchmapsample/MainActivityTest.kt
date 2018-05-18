package karrel.com.switchmapsample

import io.reactivex.Observable
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Created by Rell on 2018. 5. 18..
 */
class MainActivityTest {
    val eventDelayTime = 500L

    @Test
    fun onCreate() {
        inputChangeEvent()
                .doOnNext { println("text : $it") }
                .flatMap { GoogleImageScrapper(it.toString()).imageUrls() }
                .subscribe { println("url : $it") }

        Thread.sleep(10000L)
    }

    private fun inputChangeEvent(): Observable<CharSequence> {
        return Observable.just("안녕")
                .filter { it.isNotEmpty() }
                .switchMap { msg -> Observable.timer(eventDelayTime, TimeUnit.MILLISECONDS).map { msg } }
    }
}