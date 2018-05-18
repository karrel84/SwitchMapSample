package karrel.com.switchmapsample;

import com.google.gson.Gson;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Rell on 2018. 5. 18..
 */
public class ExampleJava {
    @Test
    public void test(){
        Gson gson = new Gson();
        ImageItem imageItem = gson.fromJson("asdf", ImageItem.class);

        String keyword = "안녕";
        String url = String.format("https://www.google.co.kr/search?q=%s&source=lnms&tbm=isch&sa=X&ved=0ahUKEwipxPTktY7bAhVEvbwKHdITAFMQ_AUICigB&biw=1234&bih=960", keyword);

        Observable.just("")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
