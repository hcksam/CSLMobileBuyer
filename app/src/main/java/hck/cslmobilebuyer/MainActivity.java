package hck.cslmobilebuyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    Context context;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

//        String url = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609";
        URL = "https://www.hkcsl.com/tc/online-shop-standalone-handset-price/";
//        String url = "http://www.baidu.com";
//        String url = "http://ddns.toraou.com:8888/TestHtml/csl%20Online%20Shop.html";
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(URL);

        setWebView(selectPhone());

    }

    public void setWebView(final String javaScript){
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onLoadResource(WebView view, String url){
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.equalsIgnoreCase(URL)) {
                    webView.loadUrl("javascript:(function() { " +
                            forword() +
                            "})()");
                } else {
                    webView.loadUrl("javascript:(function() { " +
                            selectPhone() +
                            "})()");
                }
            }
        });
    }

    private String test(){
        String javaScript = "";
        javaScript += "$('body').append($('#index-kw').val());";
        return javaScript;
    }

    private String forword(){
        String javaScript = "window.location.href = 'https://shop.hkt.com/MobOs/stsadditem.html?modelId=609';";
        return javaScript;
    }

    private String selectPhone(){
        String javaScript = "";
//        String javaScript = "var color = $('input:radio[name=color]');";
//        javaScript += "var price = $('input:radio[name=promoTypeId]');";
        javaScript += "$('body').append('test');";
        javaScript += "$('body').append($(\"input[type='radio'][name='promoTypeId']\").size());";
//        javaScript += "if(color.is(':checked') === false) {";
//        javaScript += "color.filter('[value=402000924]').prop('checked', true);";
//        javaScript += "}";
//        javaScript += "if(price.is(':checked') === false) {";
//        javaScript += "price.filter('[value=1]').prop('checked', true);";
//        javaScript += "}";
//        javaScript += "$('#index-form').submit();";
        return javaScript;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
