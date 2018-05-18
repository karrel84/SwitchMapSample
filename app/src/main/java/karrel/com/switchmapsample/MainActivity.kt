package karrel.com.switchmapsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val eventDelayTime = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputChangeEvent()
                .doOnNext { println("text : $it") }
                .map { GoogleImageScrapper(it.toString()) }
                .flatMap { it.imageUrls() } //구글 이미지 스크랩퍼에서 이미지 url을 가져온다
                .subscribe(object : Observer<String> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(value: String) {
                        println("url : $value")
                    }

                    override fun onError(e: Throwable) {
                        println("e.message : " + e.message)
                        e.printStackTrace()
                    }

                    override fun onComplete() {

                    }
                })
    }

    private fun inputChangeEvent(): Observable<CharSequence> {
        return RxTextView.textChanges(input)
                .filter { it.isNotEmpty() } // 값이 있어야한다
                .switchMap { msg -> Observable.timer(eventDelayTime, TimeUnit.MILLISECONDS).map { msg } } // 특정 초 후에 1번만 이벤트를 발행
    }

}
