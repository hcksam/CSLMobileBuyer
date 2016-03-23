package hck.cslmobilebuyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    Context context;
    String URL;
    String[] selectedPhones = {"402000923", "402000925", "402000924"};
    final String S7EdgePage = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609";
    final String S7Page = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=608";
    final String informationPage = "https://shop.hkt.com/MobOs/stspersondtl.html";
    final String conformPage = "https://shop.hkt.com/MobOs/stsconfirmation.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        webView = (WebView) findViewById(R.id.webView);

        URL = S7Page;
//        URL = "http://ddns.toraou.com:8888/TestHtml/csl%20Online%20Shop.html";
//        URL = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609";
//        String url = "https://www.hkcsl.com/tc/online-shop-standalone-handset-price/";
//        String url = "http://www.baidu.com";
//        String url = "http://ddns.toraou.com:8888/TestHtml/csl%20Online%20Shop.html";

        clearCookie();

        webView.getSettings().setJavaScriptEnabled(true);
//        webView.requestFocus(View.FOCUS_DOWN);
//        webView.getSettings().setSavePassword(false);
//        webView.getSettings().setSaveFormData(false);
//        handleOnloadError();

//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        JsHandler _jsHandler = new JsHandler(this, webView);
//        webView.addJavascriptInterface(_jsHandler, "JsHandler");

        webView.loadUrl(URL);

//        setWebView(selectPhone(selectedPhones[2]));
        setWebView(null);
    }

    final class ObjectExtension {
        @JavascriptInterface
        public void onLoad() {
            onLoadCompleted();
        }
    }

    public void onLoadCompleted() {
        String url = webView.getUrl();
        if (url.equalsIgnoreCase(URL)) {
            webView.loadUrl("javascript:(function() { " +
                    selectPhone("402000922") +
                    "})()");
        }else if (url.equalsIgnoreCase(informationPage)){
            webView.loadUrl("javascript:(function() { " +
                    inputInformation(new InformationData()) +
                    "})()");
        }else if (url.equalsIgnoreCase(conformPage)){
            webView.loadUrl("javascript:(function() { " +
                    conformInformation() +
                    "})()");
        }else{
            Toast.makeText(context, url, Toast.LENGTH_LONG).show();
        }
    }

    public void handleOnloadError(){
        webView.addJavascriptInterface(new ObjectExtension(), "webviewScriptAPI");
        String fulljs = "javascript:(\n    function() { \n";
        fulljs += "        window.onload = function() {\n";
        fulljs += "            webviewScriptAPI.onLoad();\n";
        fulljs += "        };\n";
        fulljs += "    })()\n";
        webView.loadUrl(fulljs);
    }

    public void clearCookie(){
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
//        CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeAllCookie();

//        webView.clearCache(true);
//        webView.clearHistory();
    }

    public void setWebView(final String javaScript){
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//
//            }

//            @Override
//            public void onLoadResource(WebView view, String url){
//            }

//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon){
//
//            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                if (url.equalsIgnoreCase(URL)) {
                    view.loadUrl("javascript:(function() { " +
                            selectPhone("402000922") +
                            "})()");
                } else if (url.equalsIgnoreCase(informationPage)) {
                    view.loadUrl("javascript:(function() { " +
                            inputInformation(new InformationData()) +
                            "})()");
                } else {
                    Log.w("hck new url", url);
                    Toast.makeText(context, url, Toast.LENGTH_LONG).show();
                }
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(context, "Received error " + error, Toast.LENGTH_LONG).show();
            }

//            @Override
//            public void onReceivedSslError(WebView view,
//                                           SslErrorHandler handler, SslError error) {
//                // TODO Auto-generated method stub
//                super.onReceivedSslError(view, handler, error);
//                Toast.makeText(context, "Received error "+error, Toast.LENGTH_LONG).show();
//
//            }
        });
    }

    private String test(){
        String javaScript = "";
        javaScript += "$('body').append($('#index-kw').val());";
        return javaScript;
    }

    private String forward(String url){
        String javaScript = "window.location.href = '"+url+"';";
        return javaScript;
    }

    private String selectPhone(String selectedPhone){
//        Toast.makeText(context, "selectPhone", Toast.LENGTH_LONG).show();
//        String javaScript = "";
        String javaScript = "var color = $('input[type=radio][name=color]');";
        javaScript += "var price = $('input[type=radio][name=promoTypeId]');";
//        javaScript += "$('body').append('test');";
//        javaScript += "$('body').append( $('#dialog-promoCode').parent().css('display'));";
//        javaScript += "$('body').append($(\"input[type='radio'][name='promoTypeId']\").size());";
//        javaScript += "$('body').append(color.filter('[value=402000924]').val());";
        javaScript += "color.each(function( index ) {";
        javaScript += "if ($(this).val() !=  "+selectedPhone+"){";
        javaScript += "$(this).prop('checked', false);";
        javaScript += "}else{";
        javaScript += "$(this).prop('checked', true);";
        javaScript += "}";
        javaScript += "});";
//        javaScript += "color.filter('[value=402000923]').prop('checked', false);";
//        javaScript += "color.filter('[value=402000924]').prop('checked', true);";
        javaScript += "price.filter('[value=0]').prop('checked', false);";
        javaScript += "price.filter('[value=1]').prop('checked', true);";
//        javaScript += "if(price.is(':checked') === false) {";
//        javaScript += "price.filter('[value=1]').prop('checked', true);";
//        javaScript += "}";

        javaScript += "$('#sahsButton').click();";
        javaScript += "var checkoutInterval = null;";

        javaScript += "var promoInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-promoCode').parent().css('display') != 'none'){";
        javaScript += "$('#promoCode').val('SPECIAL');";
        javaScript += "$('#submitButton').click();";
        javaScript += "clearInterval(promoInterval);";

        javaScript += "checkoutInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-cart').parent().css('display') != 'none'){";
        javaScript += "$('#checkoutButton').click();";
        javaScript += "clearInterval(checkoutInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "}";
        javaScript += "},1000);";
//
//        javaScript += "clearInterval(promoInterval).delay(30000);";
//        javaScript += "clearInterval(checkoutInterval).delay(30000);";

//        javaScript += "var runSAHS = function () {";
//        javaScript += "var r = $.Deferred();";
//        javaScript += "$('#sahsButton').click();";
//        javaScript += "setTimeout(function () {r.resolve();}, 1000);";
//        javaScript += "return r;";
//        javaScript += "};";

//        javaScript += "var runPromo = function () {";
//        javaScript += "var r = $.Deferred();";
////        javaScript += "$('body').append('test');";
////        javaScript += "$('body').append( $('#dialog-promoCode').parent().css('display'));";
//        javaScript += "if ($('#dialog-promoCode').parent().css('display') != 'none'){";
//        javaScript += "$('#promoCode').val('SPECIAL');";
//        javaScript += "$('#submitButton').click();";
//        javaScript += "}";
//        javaScript += "setTimeout(function () {r.resolve();}, 1000);";
//        javaScript += "return r;";
//        javaScript += "};";

//        javaScript += "var runCheckout = function () {";
//        javaScript += "var r = $.Deferred();";
//        javaScript += "if ($('#dialog-cart').parent().css('display') != 'none'){";
//        javaScript += "$('#checkoutButton').click();";
//        javaScript += "}";
//        javaScript += "setTimeout(function () {r.resolve();}, 1000);";
//        javaScript += "return r;";
//        javaScript += "};";

//        javaScript += "runSAHS().done(runPromo().done(runCheckout));";
//        javaScript += "runSAHS().done(runPromo);";
//        javaScript += "runPromo().done(runCheckout);";

        return javaScript;
    }

    private String inputInformation(InformationData data){
//        String javaScript = "$('#customer.title"+((data.isMr())? "1":"2")+"').prop('checked', true);";
        String javaScript = "$('input[value="+((data.isMr())? "Mr":"Ms")+"]').prop('checked', true);";
        javaScript += "$('#lastName').val('" +data.getLastName()+"');";
        javaScript += "$('#firstName').val('" + data.getFirstName()+"');";
        javaScript += "$('#contactPhone').val('"+data.getContactPhone()+"');";
        javaScript += "$('#emailAddr').val('" + data.getEmailAddr()+"');";
        javaScript += "$('#confirmEmailAddr').val('" + data.getEmailAddr()+"');";
        javaScript += "$('#cCHolderName').val('"+data.getcCHolderName()+"');";
        javaScript += "$('#unitNo').val('"+data.getUnitNo()+"');";
        javaScript += "$('#floorNo').val('"+data.getFloorNo()+"');";
        javaScript += "$('#buildNo').val('"+data.getBuildNo()+"');";
        javaScript += "$('#strNo').val('"+data.getStrNo()+"');";
        javaScript += "$('#strName').val('"+data.getStrName()+"');";
        javaScript += "$('#deliveryStCatDescSelect').val('"+data.getDeliveryStCatDescSelect()+"');";

        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";

        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";

        javaScript += "$('#sectionSelectDelivery').val('"+data.getSectionSelectDelivery()+"');";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList()+"');";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";

        javaScript += "var StCatInterval = setInterval(function () {";
        javaScript += "if ($('#deliveryStCatDescSelect').val() != '"+data.getDeliveryStCatDescSelect()+"'){";
        javaScript += "$('#deliveryStCatDescSelect').val('"+data.getDeliveryStCatDescSelect()+"').change();";
        javaScript += "if ($('#deliveryStCatDescSelect').val() == '"+data.getDeliveryStCatDescSelect()+"')";
        javaScript += "clearInterval(StCatInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var areaInterval = setInterval(function () {";
        javaScript += "if ($('#areaSelectDelivery').val() != '"+data.getAreaSelectDelivery()+"'){";
        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";
        javaScript += "if ($('#areaSelectDelivery').val() == '"+data.getAreaSelectDelivery()+"')";
        javaScript += "clearInterval(areaInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var districtInterval = setInterval(function () {";
        javaScript += "if ($('#districtSelectDelivery').val() != '"+data.getDistrictSelectDelivery()+"'){";
        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";
        javaScript += "if ($('#districtSelectDelivery').val() == '"+data.getDistrictSelectDelivery()+"')";
        javaScript += "clearInterval(districtInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var sectionInterval = setInterval(function () {";
        javaScript += "if ($('#sectionSelectDelivery').val() != '"+data.getSectionSelectDelivery()+"' || $('#timeslotList').val() != '"+data.getTimeslotList()+"'){";
        javaScript += "$('#sectionSelectDelivery').val('"+data.getSectionSelectDelivery()+"').change();";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList() +"').change();";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";
        javaScript += "if ($('#sectionSelectDelivery').val() == '"+data.getSectionSelectDelivery()+"' && $('#timeslotList').val() != '"+data.getTimeslotList()+"'){";
        javaScript += "clearInterval(sectionInterval);";
        javaScript += "}";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var submitInterval = setInterval(function () {";
//        javaScript += "if ($('#sectionSelectDelivery').val() == '"+data.getSectionSelectDelivery()+"' && $('#timeslotList').val() != '"+data.getTimeslotList()+"'){";
        javaScript += "$('button[name=submit]').click();";
//        javaScript += "}";
        javaScript += "},5000);";

//        javaScript += "var selectArea = function () {";
//        javaScript += "var r = $.Deferred();";
//        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"');";
////        javaScript += "$('#areaSelectDelivery').change();";
//        javaScript += "setTimeout(function () {r.resolve();}, 2500);";
//        javaScript += "return r;";
//        javaScript += "};";

//        javaScript += "var selectDistrict = function () {";
//        javaScript += "var r = $.Deferred();";
//        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"');";
//        javaScript += "setTimeout(function () {r.resolve();}, 2500);";
//        javaScript += "return r;";
//        javaScript += "};";
//
//        javaScript += "var selectSection = function () {";
//        javaScript += "var r = $.Deferred();";
//        javaScript += "$('#sectionSelectDelivery').val('"+data.getSectionSelectDelivery()+"');";
//        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
//        javaScript += "$('#timeslotList').val('"+data.getTimeslotList()+"');";
//        javaScript += "$('input[type=checkbox]').prop('checked', true);";
//        javaScript += "setTimeout(function () {r.resolve();}, 2500);";
//        javaScript += "return r;";
//        javaScript += "};";
//
//        javaScript += "selectArea().done(selectDistrict().done(selectSection));";
        return javaScript;
    }

    private String conformInformation(){
//        String javaScript = "$('#customer.title"+((data.isMr())? "1":"2")+"').prop('checked', true);";
        String javaScript = "/MobOs/captcha.html";

        javaScript += "$('#captchaInput').val('');";
        javaScript += "$('#confirmed').prop('checked', true);";

        javaScript += "var submitInterval = setInterval(function () {";
        javaScript += "submitForm();";
        javaScript += "},5000);";

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
