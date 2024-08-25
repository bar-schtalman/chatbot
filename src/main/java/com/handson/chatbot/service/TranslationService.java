package com.handson.chatbot.service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    private static final Pattern TRANSLATION_PATTERN = Pattern.compile(
            "<div class=\\\"QueryRelatedCollocations_TranslationPreview\\\">(.+?)<\\/div>",
            Pattern.DOTALL
    );



    public String Translate(String keyword) throws IOException {
        return parseTranslateHtml(getTranslateUrl(keyword));
    }

    private String getTranslateUrl(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .url("https://www.morfix.co.il/" + keyword)
                .get()  // No body should be attached to GET requests
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .addHeader("accept-language", "en-IL,en;q=0.9,he-IL;q=0.8,he;q=0.7,en-GB;q=0.6,en-US;q=0.5")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "schoolLevel=15; coins=300; CurrentUserType=Non Registered; _gid=GA1.3.175873335.1724581304; _gat_UA-5815264-1=1; _gcl_au=1.1.608799639.1724581305; refreshCount=0; trc_cookie_storage=taboola%2520global%253Auser-id%3D49483550-9b79-4a79-8e74-d6be3ed08d08-tuctda9640d; __hstc=202976718.0a2618bb7cbb2358c5457accdb04ff48.1724581310292.1724581310292.1724581310292.1; hubspotutk=0a2618bb7cbb2358c5457accdb04ff48; __hssrc=1; _gat=1; _gat_gtag_UA_5815264_1=1; lastSearchList=computer%7c; QueryList=18937; QueriesCount={\"computer\":[\"8/25/2024 1:21:51 PM\",\"1\"]}; interstitialAfterTranslations=1; numOfQueries=1; MonthlyPremiumPromotion=; _ga=GA1.1.1372202489.1724581304; _ga_YZCQQRKWV5=GS1.1.1724581305.1.1.1724581311.54.0.0; pageCountInSession=2; PromoteMorfixschoolDialog=1; FCNEC=%5B%5B%22AKsRol8yQajhFtOvnHERZyejWg0Nbamak_90lqlURPJR7AkPmKbDgVf-sJW-5dASOZnmCEWOig4jEsiHihp_NXwBTrj0137BjlqtTJZpEF-Zfd7W0-kq2qdjzcnhZr9TGCcka2Z4CbyuGQCh1pjJ5TYvoI_4yByKlQ%3D%3D%22%5D%5D; favoriteTipShown=true; AWSALB=CB6b6fpkuNu4VsPDanB6x8M5//31Hr1Kc4AFlgXEMcNCWPjhgca60aE2lq7c/dF5kImQaNm0qOWFanB2AYFCrlpMN9GKRJg4wCQ6+mCw3QOg/fyac95uC+wOzOmZ; AWSALBCORS=CB6b6fpkuNu4VsPDanB6x8M5//31Hr1Kc4AFlgXEMcNCWPjhgca60aE2lq7c/dF5kImQaNm0qOWFanB2AYFCrlpMN9GKRJg4wCQ6+mCw3QOg/fyac95uC+wOzOmZ; __hssc=202976718.2.1724581310292; _awl=3.1724581319.5-c7261613538b099b0ddfa9f27d5aada7-6763652d6575726f70652d7765737431-1; AWSALB=DcBR6dy7qons4ENYBOoTqfGcQB2lQljpWs+GBjDldhjgHqHMcNxAHHGh8ODrdwLae5Vk6w/2/y0MJVqsK83sA9kc01rrTq5VzvnYCpLGhE2JXZQqpeR3gGqQ2yQN; AWSALBCORS=DcBR6dy7qons4ENYBOoTqfGcQB2lQljpWs+GBjDldhjgHqHMcNxAHHGh8ODrdwLae5Vk6w/2/y0MJVqsK83sA9kc01rrTq5VzvnYCpLGhE2JXZQqpeR3gGqQ2yQN; CurrentUserType=Non Registered; QueriesCount={\"computer\":[\"8/25/2024 1:26:06 PM\",\"2\"]}; QueryList=18937; interstitialAfterTranslations=2; lastSearchList=computer%7c; numOfQueries=2")
                .addHeader("priority", "u=0, i")
                .addHeader("referer", "https://www.morfix.co.il/")
                .addHeader("sec-ch-ua", "\"Not)A;Brand\";v=\"99\", \"Google Chrome\";v=\"127\", \"Chromium\";v=\"127\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();

    }


    private String parseTranslateHtml(String html) {
        String res = "";
        Matcher matcher = TRANSLATION_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) +  "<br>\n";
        }
        return res;
    }
}
