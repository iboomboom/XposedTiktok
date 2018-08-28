package com.evayinfo.xposedtt.net;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    /**
     * 将语言翻译成中文
     *
     * @param randomNumber
     * @param sign
     * @return
     */
    @GET("trans/vip/translate?from=auto&to=zh&appid=20180824000198272")
    Observable<TranslateResult> translate2cn(@Query("q") String content,
                                             @Query("salt") String randomNumber,
                                             @Query("sign") String sign);
}
