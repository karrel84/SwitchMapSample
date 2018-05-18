package karrel.com.switchmapsample

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertFalse
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import java.util.regex.Pattern


/**
 * Created by kimmihye on 2018. 4. 13..
 */
class ImageUrlScrapperTest {
    // http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx
    val url = "https://www.google.co.kr/search?q=%EC%95%88%EB%85%95&source=lnms&tbm=isch&sa=X&ved=0ahUKEwipxPTktY7bAhVEvbwKHdITAFMQ_AUICigB&biw=1234&bih=960"


    @Test
    fun imageScrap() {
        Observable.just(url)
//                .subscribeOn(Schedulers.io())
                .map { Jsoup.connect(url).get() }
                .map { docToUrl(it) }
                .subscribe { println("it") }
    }

    private fun docToUrl(doc: Document): ArrayList<String> {
        val list = ArrayList<String>()
        val divs = doc.select("div")

        for (div in divs) {
            val elements = div.getElementsByClass("rg_meta notranslate")
            for (e in elements) {
                val json = e.html()
                println("json : ${JSONObject(json).getString("ou")}")
            }
        }
        return list
    }
}