package karrel.com.switchmapsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        input.textChanges()
                .switchMap { msg ->
                    Observable.timer(300L, TimeUnit.MILLISECONDS)
                            .map { msg }
                }
                .subscribe { assert(it.equals("4")) }
//        input.textChanges()
//                .subscribe {
//                    searchData.text = it.toString()
//                }
//                .switchMap {
//                   Observable.just(it)
//                }
//                .switchMap {
//                    Observable.timer(300L, TimeUnit.MILLISECONDS)
//                            .map { it.toString() }
//
//                }
//                .switchMap { msg ->
//                    Observable.timer(300L, TimeUnit.MILLISECONDS)
//                            .map { msg }
//                }
//                .subscribe {
//                    searchData.text = it.toString()
//                }

    }
}
