package com.handson.chatbot.service;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImdbService {
    private static final Pattern MOVIE_PATTERN = Pattern.compile("\"titleNameText\":\"([^\"]*)\",\"titleReleaseText\":\"([0-9]{4})\"", Pattern.DOTALL);


    public String searchMovie(String keyword) throws IOException {
        return parseMovieHtml(getMovieHtml(keyword));
    }

    private String getMovieHtml(String keyword) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("https://www.imdb.com/find/?q=" + keyword + "&ref_=nv_sr_sm")
                .get()  // No body should be attached to GET requests
                .addHeader("authority", "www.imdb.com")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("accept-language", "en-US,en;q=0.9")
                .addHeader("cache-control", "max-age=0")
                .addHeader("cookie", "session-id=134-5163776-5013036; session-id-time=2082787201l; ubid-main=134-1462608-6718738; ad-oo=0; session-token=o3HLHkYZNXusTmA0efC2KJc+nh1Eka9fXaPW3m8ryO2MvYEw+ZszqEfJWy0nyrciAgz43C2TcOGjodCVyhFSmFeSTSzbSC+DChaIaZS6AF4DT7amy3RL/mM2ngUqlESsGakYsu6WvqAHmrW+RPQ+UCjYWiXdmZ0IASZ0vrakpXbbIbVoq6cOI6NLK0Z2+z7PPShN6P8oNfyNVeFlOG3+IFkVpbBnla4YUIMhHFigXm9oStak+g+h4GTMzq/NOB3YsNxGTGyqkfAWeochuTEH525Y3bRI84ZMNDBVE4gw7tE5zDkUoJn6xpCdcLI6bzmusfSuUNfsyyaEt1HOj/QjJIGd4hIIYwWC; ci=e30; __gads=ID=57230174467f7f35:T=1724284920:RT=1724404197:S=ALNI_MZ6FOG5FRrDaGvmusMs_Xup4-tQHg; __gpi=UID=00000ec431ed5388:T=1724284920:RT=1724404197:S=ALNI_MbilrIdY0WP70a_Cfejgs6A4Tckdg; __eoi=ID=d35bc4e15ece90fa:T=1724284920:RT=1724404197:S=AA-AfjZkrf37cREwtIh-GYQxP0bd; csm-hit=tb:Q6FYXZ4BZ5EDC520A170+s-GCYC09ZWWSRGYGS71AJH|1724404229991&t:1724404229991&adb:adblk_no; session-id=134-5163776-5013036; session-id-time=2082787201l; session-token=o3HLHkYZNXusTmA0efC2KJc+nh1Eka9fXaPW3m8ryO2MvYEw+ZszqEfJWy0nyrciAgz43C2TcOGjodCVyhFSmFeSTSzbSC+DChaIaZS6AF4DT7amy3RL/mM2ngUqlESsGakYsu6WvqAHmrW+RPQ+UCjYWiXdmZ0IASZ0vrakpXbbIbVoq6cOI6NLK0Z2+z7PPShN6P8oNfyNVeFlOG3+IFkVpbBnla4YUIMhHFigXm9oStak+g+h4GTMzq/NOB3YsNxGTGyqkfAWeochuTEH525Y3bRI84ZMNDBVE4gw7tE5zDkUoJn6xpCdcLI6bzmusfSuUNfsyyaEt1HOj/QjJIGd4hIIYwWC")
                .addHeader("referer", "https://www.imdb.com/title/tt13186482/?ref_=nv_sr_srsg_0_tt_8_nm_0_in_0_q_lion")
                .addHeader("sec-ch-ua", "\"Not_A Brand\";v=\"99\", \"Google Chrome\";v=\"109\", \"Chromium\";v=\"109\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Linux\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "same-origin")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        //String responseBody = response.body().string();
        //System.out.println("Response: " + responseBody);
        return response.body().string();

    }


    private String parseMovieHtml(String html) {
        String res = "";
        Matcher matcher = MOVIE_PATTERN.matcher(html);
        while (matcher.find()) {
            res += matcher.group(1) + " - " + matcher.group(2) + "<br>\n";
        }
        return res;
    }


}
