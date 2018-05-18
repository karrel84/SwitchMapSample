package karrel.com.switchmapsample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import karrel.com.switchmapsample.etc.GoogleImageScrapper
import karrel.com.switchmapsample.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val eventDelayTime = 500L

    lateinit var adapter: ImageAdapter
    val gridLayoutManager = GridLayoutManager(this@MainActivity, 3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        setupSearchEvent() // 이미지 로딩
    }

    private fun setupSearchEvent() {
        inputChangeEvent()
                .doOnNext { adapter.clearImages() }
                .map { keyword -> GoogleImageScrapper(keyword.toString()) }
                .flatMap { it.imageUrls() } //구글 이미지 스크랩퍼에서 이미지 url을 가져온다
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addImageUrl)
    }

    private fun setupRecyclerView() {
        adapter = ImageAdapter(this@MainActivity)
        with(recyclerView) {
            layoutManager = gridLayoutManager
            adapter = this@MainActivity.adapter
        }
    }

    private fun inputChangeEvent(): Observable<CharSequence> {
        return RxTextView.textChanges(input)
                .filter { it.isNotEmpty() } // 값이 있어야한다
                .switchMap { msg -> Observable.timer(eventDelayTime, TimeUnit.MILLISECONDS).map { msg } } // 특정 초 후에 1번만 이벤트를 발행
    }

}
