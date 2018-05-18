package karrel.com.switchmapsample.etc

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by Rell on 2018. 5. 18..
 */
class GoogleImageScrapper(val keyword: String) {
    val baseUrl = "https://www.google.co.kr/search?q=%s&source=lnms&tbm=isch&sa=X&ved=0ahUKEwipxPTktY7bAhVEvbwKHdITAFMQ_AUICigB&biw=1234&bih=960"

    fun imageUrls(): Observable<String>? {
        val url = String.format(baseUrl, keyword)
        return Observable.just(url)
                .doOnNext { "imageUrls url : $it" }
                .subscribeOn(Schedulers.io()) // io 스케쥴러
                .map { Jsoup.connect(it).get() }
                .flatMap { docToImageUrl(it) }
    }


    private fun docToImageUrl(doc: Document): Observable<String> {
//        println("doc : $doc")
        return Observable.just(doc)
                .flatMap {
                    val list = mutableListOf<String>()
                    val divs = it.select("div")
                    for (div in divs) {
                        val elements = div.getElementsByClass("rg_meta notranslate")
                        for (e in elements) {
                            val json = e.html()
                            val url = JSONObject(json).getString("ou");
                            list.add(url)
                        }
                    }
                    Observable.fromIterable(list.toList())
                }
//                .flatMap { Observable.fromArray(it.select("div")) } // div 를 가진 엘리먼트로 변환
//                .flatMap { Observable.fromArray(it) } // 해당 클래스명을 가진 엘리먼트를 반환
//                .doOnNext { println("JSON은 >>>>>  : ${it.html()}") }
//                .map { it.html() } // json 으로 변환
//                .doOnNext { println("json : $it") }
//                .map { JSONObject("$it") }
//                .map { it.getString("ou") }
//                .doOnNext { println("image Url : $it") }
                .filter { it.endsWith(".png") || it.endsWith(".jpg") }
    }
}