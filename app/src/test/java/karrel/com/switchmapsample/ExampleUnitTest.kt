package karrel.com.switchmapsample

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import junit.framework.TestCase
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import java.util.concurrent.TimeUnit


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun switchMapTest() {

        val publishSubject = PublishSubject.create<String>()
        publishSubject
                .switchMap { msg ->
                    Observable.timer(300L, TimeUnit.MILLISECONDS)
                            .map { msg }
                }
                .subscribe { assert(it.equals("4")) }


        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(5)
                .doOnNext { println("value : ${it}") }
                .map { it.toString() }
                .subscribe(publishSubject::onNext)

        CommonUtils.sleep(1000)
    }
}
