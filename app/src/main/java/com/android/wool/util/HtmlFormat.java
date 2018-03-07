package com.android.wool.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * Created by AiMr on 2017/10/30
 */
public class HtmlFormat {
    public static String getNewContent(String htmltext){

        Document doc= Jsoup.parse(htmltext);
        Elements elements =doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width","100%").attr("height","auto");
        }

        return doc.toString();
    }
}
