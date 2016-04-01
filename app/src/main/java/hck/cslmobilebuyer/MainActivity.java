package hck.cslmobilebuyer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Context context;
    WebView webView;
    ImageView captchaImage;
    Spinner phoneSelect;
    Spinner colorSelect;
    Button autoFill;
    Button next;
    LinearLayout functionBar;
    TextView loading;

    String mainUrl;
    String selectedPhone;
    String selectedColor;
    int havePhoneIndex;
    Thread webViewThread;
    boolean pageFinish;
    boolean javascriptFinish;
    InformationData webData;
//    String[] selectedPhones = {"402000923", "402000925", "402000924"};
//    final String S7EdgePage = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609";
//    final String S7Page = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=608";
    final String informationPage = "https://shop.hkt.com/MobOs/stspersondtl.html";
    final String conformPage = "https://shop.hkt.com/MobOs/stsconfirmation.html";
    HashMap<String, String> phoneMap = getPhoneMap();
    HashMap<String, HashMap<String, String>> phoneColorMap = getPhoneColorMap();
    HashMap<String, Integer> loadingCount = new HashMap<>();

    private HashMap<String, String> getPhoneMap(){
        HashMap<String, String> phoneMap = new HashMap<>();
        phoneMap.put("S7", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=608");
        phoneMap.put("S7Edge", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609");
        phoneMap.put("NOTE5", "https://shop.hkt.com/MobOs/stsadditem.html?modelId=543");
        return phoneMap;
    }

    private HashMap<String, HashMap<String, String>> getPhoneColorMap(){
        HashMap<String, HashMap<String, String>> phoneColorMap = new HashMap<>();
        HashMap<String, String> colorMap;

        colorMap = new HashMap<>();
        colorMap.put("铂金", "402000921");
        colorMap.put("白色", "402000922");
        phoneColorMap.put("S7", colorMap);

        colorMap = new HashMap<>();
        colorMap.put("铂金", "402000923");
        colorMap.put("鋼黑色", "402000925");
        colorMap.put("鈦銀色", "402000924");
        phoneColorMap.put("S7Edge", colorMap);

        colorMap = new HashMap<>();
        colorMap.put("金色", "402000826");
        phoneColorMap.put("NOTE5", colorMap);

        return phoneColorMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        webView = (WebView) findViewById(R.id.webView);
        functionBar = (LinearLayout) findViewById(R.id.function);
        phoneSelect = (Spinner) findViewById(R.id.phoneSelect);
        colorSelect = (Spinner) findViewById(R.id.colorSelect);
        autoFill = (Button) findViewById(R.id.autoFill);
        next = (Button) findViewById(R.id.next);
        captchaImage = (ImageView) findViewById(R.id.captchaImage);
        loading = (TextView) findViewById(R.id.loading);

        selectedPhone = "NOTE5";
        mainUrl = phoneMap.get(selectedPhone);
        webData = new InformationData(context);
//        URL = "http://ddns.toraou.com:8888/TestHtml/csl%20Online%20Shop.html";
//        URL = "https://shop.hkt.com/MobOs/stsadditem.html?modelId=609";
//        String url = "https://www.hkcsl.com/tc/online-shop-standalone-handset-price/";
//        String url = "http://www.baidu.com";
//        String url = "http://ddns.toraou.com:8888/TestHtml/csl%20Online%20Shop.html";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
//        webView.requestFocus(View.FOCUS_DOWN);
//        webView.getSettings().setSavePassword(false);
//        webView.getSettings().setSaveFormData(false);
//        handleOnloadError();

//        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        JsHandler _jsHandler = new JsHandler(this, webView);
//        webView.addJavascriptInterface(_jsHandler, "JsHandler");

//        setWebView(selectPhone(selectedPhones[2]));
//        setWebView(null);

        ArrayAdapter phoneSelectAdapter = new ArrayAdapter(context,
                 android.R.layout.simple_spinner_item, phoneMap.keySet().toArray());
        phoneSelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneSelect.setAdapter(phoneSelectAdapter);
        int phonePos = phoneSelectAdapter.getPosition(selectedPhone);
        phonePos = (phonePos < 0)? 0:phonePos;
        phoneSelect.setSelection(phonePos);
        phoneSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                selectedPhone = String.valueOf(parent.getItemAtPosition(pos));
                mainUrl = phoneMap.get(selectedPhone);

                ArrayAdapter colorSelectAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, phoneColorMap.get(selectedPhone).keySet().toArray());
                colorSelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectedColor = String.valueOf(phoneColorMap.get(selectedPhone).keySet().toArray()[0]);
                colorSelect.setAdapter(colorSelectAdapter);
                int colorPos = colorSelectAdapter.getPosition(selectedColor);
                colorPos = (colorPos < 0)? 0:colorPos;
                colorSelect.setSelection(colorPos);
                colorSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int pos, long id) {
                        // An item was selected. You can retrieve the selected item using
                        // parent.getItemAtPosition(pos)
                        selectedColor = String.valueOf(parent.getItemAtPosition(pos));
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }
                });
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

//    final class ObjectExtension {
//        @JavascriptInterface
//        public void onLoad() {
//            onLoadCompleted();
//        }
//    }
//
//    public void onLoadCompleted() {
//        String url = webView.getUrl();
//        if (url.equalsIgnoreCase(URL)) {
//            webView.loadUrl("javascript:(function() { " +
//                    selectPhone("402000922") +
//                    "})()");
//        }else if (url.equalsIgnoreCase(informationPage)){
//            webView.loadUrl("javascript:(function() { " +
//                    inputInformation(new InformationData()) +
//                    "})()");
//        }else if (url.equalsIgnoreCase(conformPage)){
//            webView.loadUrl("javascript:(function() { " +
//                    conformInformation() +
//                    "})()");
//        }else{
//            Log.w("hck newURL",url);
//            Toast.makeText(context, url, Toast.LENGTH_LONG).show();
//        }
//    }

//    public void handleOnloadError(){
//        webView.addJavascriptInterface(new ObjectExtension(), "webviewScriptAPI");
//        String fulljs = "javascript:(\n    function() { \n";
//        fulljs += "        window.onload = function() {\n";
//        fulljs += "            webviewScriptAPI.onLoad();\n";
//        fulljs += "        };\n";
//        fulljs += "    })()\n";
//        webView.loadUrl(fulljs);
//    }

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

//    private AdapterView.OnItemClickListener phoneSelectListener = new AdapterView.OnMenuItemClickListener() {
//
//        public void onItemSelected(AdapterView<?> parent, View view,
//                                   int pos, long id) {
//            // An item was selected. You can retrieve the selected item using
//            // parent.getItemAtPosition(pos)
//            String selectPhone = String.valueOf(parent.getItemAtPosition(pos));
//        }
//
//
//    };

    public void setWebView(){
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(mainUrl);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged (WebView view, int newProgress){
                String url = view.getUrl();

                if (newProgress == 100){
                    int count = loadingCount.get(url);
                    if (count == 0) {
                        javascriptFinish = true;
                        if (pageFinish){
                           addJavaScript(view, url);
                        }
//                        loading.setVisibility(View.VISIBLE);
//                        if (url.equalsIgnoreCase(mainUrl)) {
//                            loadJavaScript(view, checkInventory() + selectPhone() + selectPhoneSubmit());
//                        } else if (url.equalsIgnoreCase(informationPage)) {
//                            loadJavaScript(view, inputInformation());
//                        } else if (url.equalsIgnoreCase(conformPage)) {
//                            loadJavaScript(view, conformInformation());
//                        } else {
//                            Log.w("hck newUrl", url);
////                        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
//                        }
//                        loadingCount.put(url, ++count);
                    }
                }
            }

//            @Override
//            public boolean onConsoleMessage(ConsoleMessage cm) {
////                Log.d("MyApplication", cm.message() + " -- From line "
////                        + cm.lineNumber() + " of "
////                        + cm.sourceId() );
//                Toast.makeText(context, "ERROR:" + cm.message() + " -- From line "
//                        + cm.lineNumber() + " of "
//                        + cm.sourceId(), Toast.LENGTH_LONG).show();
//                return true;
//            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted (WebView view, String url, Bitmap favicon){
                pageFinish = false;
                javascriptFinish = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pageFinish = true;
                if (javascriptFinish){
                    addJavaScript(view, url);
                }
//                loading.setVisibility(View.VISIBLE);
//                loadingUrl = url;
//                if (url.equalsIgnoreCase(mainUrl)) {
//                    loadJavaScript(view, selectPhone() + selectPhoneSubmit());
//                } else if (url.equalsIgnoreCase(informationPage)) {
//                    loadJavaScript(view, inputInformation());
//                } else if (url.equalsIgnoreCase(conformPage)) {
//                    loadJavaScript(view, conformInformation());
//                } else {
//                    Log.w("hck new url", url);
//                    Toast.makeText(context, url, Toast.LENGTH_LONG).show();
//                }
//                int count = loadingCount.get(url);
//                loadingCount.put(url, ++count);
                super.onPageFinished(view, url);
            }

//            @Override
//            public void onReceivedError (WebView view, WebResourceRequest request, WebResourceError error){
////                Toast.makeText(context, "Conflict! Last web is not complete close!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
////                handler.proceed() ;
//            }
        });

//        webView.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url);
////                return true;
////
////            }
//
////            @Override
////            public void onLoadResource(WebView view, String url){
////            }
//
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon){
////
////            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
////                Toast.makeText(context, url, Toast.LENGTH_LONG).show();
//                if (url.equalsIgnoreCase(mainUrl)) {
////                    view.loadUrl("javascript:(function() { " +
////                            selectPhone("402000922") +
////                            "})()");
//                    loadJavaScript(view, selectPhone() + selectPhoneSubmit());
//                } else if (url.equalsIgnoreCase(informationPage)) {
////                    view.loadUrl("javascript:(function() { " +
////                            inputInformation(new InformationData()) +
////                            "})()");
//                    loadJavaScript(view, inputInformation());
//                } else if (url.equalsIgnoreCase(conformPage)) {
//                    loadJavaScript(view, conformInformation());
////                    view.buildDrawingCache();
////                    Bitmap bitmap = getBitmapFromURL("https://shop.hkt.com/MobOs/captcha.html");
////                    Bitmap bitmap = getBitmapFromURL2("http://cdn1.theodysseyonline.com/files/2015/12/20/635861833670816810507191518_6670-perfect-snow-1920x1080-nature-wallpaper.jpg");
////                    captchaImage.setImageBitmap(bitmap);
////                    new ImageLoadTask("http://cdn1.theodysseyonline.com/files/2015/12/20/635861833670816810507191518_6670-perfect-snow-1920x1080-nature-wallpaper.jpg", captchaImage).execute();
////                    new ImageLoadTask("https://shop.hkt.com/MobOs/captcha.html", captchaImage).execute();
////                    String javascript = "javascript: var form = document.getElementsByClassName('form');"
////                            + "var body = document.getElementsByTagName('body');"
////                            + "body[0].innerHTML = form[0].innerHTML;;";
////                    view.loadUrl(javascript);
////                    view.setVisibility(View.VISIBLE);
////                    view.loadUrl("javascript:(function() { " +
////                            "$('body').html($('img#captcha').html());" +
////                            "})()");
//
//                    //THIS PIECE OF CODE RETURNING NULL BITMAP
////                    view.setDrawingCacheEnabled(true);
//////                    view.buildDrawingCache();
////                    Bitmap bmap = view.getDrawingCache();
////                    captchaImage.setImageBitmap(bmap);
//
//                } else {
//                    Log.w("hck new url", url);
//                    Toast.makeText(context, url, Toast.LENGTH_LONG).show();
//                }
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                Toast.makeText(context, "Received error " + error, Toast.LENGTH_LONG).show();
//            }
//
////            @Override
////            public void onReceivedSslError(WebView view,
////                                           SslErrorHandler handler, SslError error) {
////                // TODO Auto-generated method stub
////                super.onReceivedSslError(view, handler, error);
////                Toast.makeText(context, "Received error "+error, Toast.LENGTH_LONG).show();
////
////            }
//        });

//        webView.stopLoading();
//        if (webViewThread != null){
//            webViewThread.interrupt();
//        }
//        webViewThread = new loadUrlThread(webView, mainUrl);
//        webViewThread.start();
    }

    private void loadJavaScript(WebView view, String javaScript){
        view.loadUrl("javascript:(function() {$( document ).ready(function() { " +
                javaScript +
                "});})()");
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

    private String checkInventory(){
        String javaScript = "";
        javaScript += "var phone = $('input[type=radio][name=color][value="+phoneColorMap.get(selectedPhone).get(selectedColor)+"]');";
        javaScript += "var index = 0;";
        javaScript += "if (phone.is(':disabled')){";
        javaScript += "index = -1;";
        javaScript += "Android.OOS();";
        javaScript += "}else{";
        javaScript += "index = 1;";
//        javaScript += "Android.have();";
        javaScript += "}";
        return javaScript;
    }

    private String selectPhone(){
//        Toast.makeText(context, "selectPhone", Toast.LENGTH_LONG).show();
//        String javaScript = "";
        String javaScript = "";
        javaScript += "if (index > 0){";

        javaScript += "var color = $('input[type=radio][name=color]');";
        javaScript += "var price = $('input[type=radio][name=promoTypeId]');";
//        javaScript += "$('body').append('test');";
//        javaScript += "$('body').append( $('#dialog-promoCode').parent().css('display'));";
//        javaScript += "$('body').append($(\"input[type='radio'][name='promoTypeId']\").size());";
//        javaScript += "$('body').append(color.filter('[value=402000924]').val());";
        javaScript += "color.each(function( index ) {";
        javaScript += "if ($(this).val() !=  "+phoneColorMap.get(selectedPhone).get(selectedColor)+"){";
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

//        javaScript += "$('#sahsButton').click();";
//        javaScript += "var checkoutInterval = null;";
//
//        javaScript += "var promoInterval = setInterval(function () {";
//        javaScript += "if ($('#dialog-promoCode').parent().css('display') != 'none'){";
//        javaScript += "$('#promoCode').val('SPECIAL');";
//        javaScript += "$('#submitButton').click();";
//        javaScript += "clearInterval(promoInterval);";
//        javaScript += "}";
//        javaScript += "},2000);";
//
//        javaScript += "checkoutInterval = setInterval(function () {";
//        javaScript += "if ($('#dialog-cart').parent().css('display') != 'none'){";
//        javaScript += "$('#checkoutButton').click();";
//        javaScript += "clearInterval(checkoutInterval);";
//        javaScript += "}";
//        javaScript += "},2000);";
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

        javaScript += "}";

        return javaScript;
    }

    private String selectPhoneSubmit(){
        String javaScript = "";
        javaScript += "if (index > 0){";

        javaScript += "$('#sahsButton').click();";
        javaScript += "var checkoutInterval = null;";

        javaScript += "var promoInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-promoCode').parent().css('display') != 'none'){";
        javaScript += "$('#promoCode').val('SPECIAL');";
        javaScript += "$('#submitButton').click();";
        javaScript += "clearInterval(promoInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "checkoutInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-cart').parent().css('display') != 'none'){";
        javaScript += "$('#checkoutButton').click();";
        javaScript += "clearInterval(checkoutInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "}";
        return javaScript;
    }

    private String inputInformation(){
        InformationData data = new InformationData(context);
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
//        javaScript += "$('#deliveryStCatDescSelect').val('"+data.getDeliveryStCatDescSelect()+"');";

        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";

        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";

        javaScript += "$('#sectionSelectDelivery').val('"+ data.getSectionSelectDelivery()+"');";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList()+"');";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";

        javaScript += "var StCatInterval = setInterval(function () {";
        javaScript += "if ($('#deliveryStCatDescSelect').val() != '"+data.getDeliveryStCatDescSelect()+"'){";
        javaScript += "$('#deliveryStCatDescSelect').val('"+data.getDeliveryStCatDescSelect()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(StCatInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var areaInterval = setInterval(function () {";
        javaScript += "if ($('#areaSelectDelivery').val() != '"+data.getAreaSelectDelivery()+"'){";
        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(areaInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var districtInterval = setInterval(function () {";
        javaScript += "if ($('#districtSelectDelivery').val()!= '"+data.getDistrictSelectDelivery()+"'){";
        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(districtInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var sectionInterval = setInterval(function () {";
        javaScript += "if ($('#sectionSelectDelivery').val()!= '"+data.getSectionSelectDelivery()+"' || $('#timeslotList').val() != '"+data.getTimeslotList()+"'){";
        javaScript += "$('#sectionSelectDelivery').val('"+data.getSectionSelectDelivery()+"').change();";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList() +"').change();";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";
        javaScript += "}else{";
        javaScript += "clearInterval(sectionInterval);";
        javaScript += "$('button[name=submit]').focus();";
        javaScript += "Android.hideLoading();";
//        javaScript += "Android.showToast('Auto fill complete!');";
        javaScript += "}";
        javaScript += "},1000);";

//        javaScript += "var submitInterval = setInterval(function () {";
//        javaScript += "if ($('#deliveryStCatDescSelect').val() == '"+data.getDeliveryStCatDescSelect()+"' && $('#areaSelectDelivery').val() == '"+data.getAreaSelectDelivery()+"' && $('#districtSelectDelivery').val() == '"+data.getDistrictSelectDelivery()+"' && $('#sectionSelectDelivery').val() == '"+data.getSectionSelectDelivery()+"' && $('#timeslotList').val() == '"+data.getTimeslotList()+"'){";
//        javaScript += "$('button[name=submit]').click();";
//        javaScript += "clearInterval(submitInterval);";
//        javaScript += "}";
//        javaScript += "},5000);";

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

    private String inputInformationSubmit(){
        InformationData data = new InformationData(context);
        String javaScript = "";

        javaScript += "var submitInterval = setInterval(function () {";
        javaScript += "if ($('#deliveryStCatDescSelect').val() == '"+data.getDeliveryStCatDescSelect()+"' && $('#areaSelectDelivery').val() == '"+data.getAreaSelectDelivery()+"' && $('#districtSelectDelivery').val() == '"+data.getDistrictSelectDelivery()+"' && $('#sectionSelectDelivery').val() == '"+data.getSectionSelectDelivery()+"' && $('#timeslotList').val() == '"+data.getTimeslotList()+"'){";
        javaScript += "$('button[name=submit]').click();";
        javaScript += "clearInterval(submitInterval);";
        javaScript += "}";
        javaScript += "},5000);";

        return javaScript;
    }

    private String saveProfileData(){
        String javaScript = "";

//        javaScript += "function saveProfile(){";
//        javaScript += "var isMr;";
//        javaScript += "if ($('input[value=Mr]').prop('checked') == true){";
//        javaScript += "isMr = 'true';";
//        javaScript += "}else{";
//        javaScript += "isMr = 'false';";
//        javaScript += "}";
//        javaScript += "var lastName = $('#lastName').val();";
//        javaScript += "var firstName = $('#firstName').val();";
//        javaScript += "var contactPhone = $('#contactPhone').val();";
//        javaScript += "var emailAddr = $('#emailAddr').val();";
//        javaScript += "var cCHolderName = $('#cCHolderName').val();";
//        javaScript += "var unitNo = $('#unitNo').val();";
//        javaScript += "var floorNo = $('#floorNo').val();";
//        javaScript += "var buildNo = $('#buildNo').val();";
//        javaScript += "var strNo = $('#strNo').val();";
//        javaScript += "var strName = $('#strName').val();";
//        javaScript += "var deliveryStCatDescSelect = $('#deliveryStCatDescSelect').val();";
//        javaScript += "var areaSelectDelivery = $('#areaSelectDelivery').val();";
//        javaScript += "var districtSelectDelivery = $('#districtSelectDelivery').val();";
//        javaScript += "var sectionSelectDelivery = $('#sectionSelectDelivery').val();";
//        javaScript += "var deliveryDateDP = $('#deliveryDateDP').val();";
//        javaScript += "var timeslotList = $('#timeslotList').val();";
//        javaScript += "Android.setFormData(isMr, lastName, firstName, contactPhone, emailAddr, cCHolderName, unitNo, floorNo, buildNo, strNo, strName, deliveryStCatDescSelect, areaSelectDelivery, districtSelectDelivery, sectionSelectDelivery, deliveryDateDP, timeslotList);";
//        javaScript += "};";

        javaScript += "$('button[name=submit]').parent().append('<button type=\"button\" id=\"androidSaveProfile\" name=\"androidSaveProfile\" class=\"blackbtn\" style=\"float: right\" onclick=\"saveProfile()\" >Android Save Profile Data</button>');";

        return javaScript;
    }

    private String conformInformation(){
//        String javaScript = "$('#customer.title"+((data.isMr())? "1":"2")+"').prop('checked', true);";
//        String javaScript = "/MobOs/captcha.html";
        String javaScript = "";

//        javaScript += "$('#captchaInput').val('');";
        javaScript += "$('#confirmed').prop('checked', true);";
        javaScript += "$('#captchaInput').focus();";
        javaScript += "Android.hideLoading();";
//        javaScript += "Android.showToast('Auto fill complete!');";

//        javaScript += "var submitInterval = setInterval(function () {";
//        javaScript += "submitForm();";
//        javaScript += "},5000);";

        return javaScript;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String[] menuItems = {"Profile Data", "Select Phone"};

        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }

//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch(id) {
            case 0:
                Intent intent = new Intent(context, InformationActivity.class);
                startActivity(intent);
                return true;
            case 1:
                functionBar.setVisibility(View.VISIBLE);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goPage(View view){
        havePhoneIndex = 0;
//        webView.stopLoading();
        resetWebView();
        setWebView();
        functionBar.setVisibility(View.GONE);
    }

    public void resetWebView(){
        loadingCount = new HashMap<>();
        loadingCount.put(mainUrl, 0);
        loadingCount.put(informationPage, 0);
        loadingCount.put(conformPage, 0);
        clearCookie();

//        webView.removeAllViews();
//        webView.setWebChromeClient(null);
//        webView.setWebViewClient(null);
//        webView.removeAllViewsInLayout();
//        webView.removeJavascriptInterface("Android");
//        webView.getSettings().setJavaScriptEnabled(false);
//
//        if (Build.VERSION.SDK_INT < 18) {
//            webView.clearView();
//        } else {
//            webView.loadUrl("about:blank");
//        }
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
    }

    private void addJavaScript(WebView view, String url){
        int count = loadingCount.get(url);
        if (count == 0) {
            loading.setVisibility(View.VISIBLE);
            if (url.equalsIgnoreCase(mainUrl)) {
                loadJavaScript(view, checkInventory() + selectPhone() + selectPhoneSubmit());
            } else if (url.equalsIgnoreCase(informationPage)) {
                injectScriptFile(view, "js/script.js");
                loadJavaScript(view, inputInformation() + saveProfileData());
            } else if (url.equalsIgnoreCase(conformPage)) {
                loadJavaScript(view, conformInformation());
            } else {
                Log.w("hck newUrl", url);
//                        Toast.makeText(context, url, Toast.LENGTH_LONG).show();
            }
            loadingCount.put(url, ++count);
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void OOS() {
            runOnUiThread(new Runnable() {
                public void run() {
//                    havePhoneIndex = -1;
//                    webView.stopLoading();
//                    webView.clearFocus();
                    functionBar.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    webView.setVisibility(View.INVISIBLE);
                    Toast.makeText(mContext, "Out of stock!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @JavascriptInterface
        public void hideLoading() {
            runOnUiThread(new Runnable() {
                public void run(){
                    loading.setVisibility(View.GONE);
                }
            });

            Toast.makeText(mContext, "Auto fill complete!", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void setFormData(String isMr, String lastName, String firstName, String contactPhone, String emailAddr, String cCHolderName, String unitNo, String floorNo, String buildNo, String strNo, String strName, String deliveryStCatDescSelect, String areaSelectDelivery, String districtSelectDelivery, String sectionSelectDelivery, String deliveryDateDP, String timeslotList) {
            int c = 0;
            webData.getData().set(c++, isMr);
            webData.getData().set(c++, lastName);
            webData.getData().set(c++, firstName);
            webData.getData().set(c++, contactPhone);
            webData.getData().set(c++, emailAddr);
            webData.getData().set(c++, cCHolderName);
            webData.getData().set(c++, unitNo);
            webData.getData().set(c++, floorNo);
            webData.getData().set(c++, buildNo);
            webData.getData().set(c++, strNo);
            webData.getData().set(c++, strName);
            webData.getData().set(c++, deliveryStCatDescSelect);
            webData.getData().set(c++, areaSelectDelivery);
            webData.getData().set(c++, districtSelectDelivery);
            webData.getData().set(c++, sectionSelectDelivery);
            webData.getData().set(c++, deliveryDateDP);
            webData.getData().set(c++, timeslotList);

            webData.setAllData();
            boolean done = webData.saveData();

            if (done) {
                Toast.makeText(mContext, "Profile Data Save Success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "Profile Data Save Fail!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void injectScriptFile(WebView view, String scriptFile) {
        InputStream input;
        try {
            input = getAssets().open(scriptFile);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();

            // String-ify the script byte-array using BASE64 encoding !!!
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "script.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(script)" +
                    "})()");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public class loadUrlThread extends Thread {
//        private WebView  webView;
//        private String url;
//
//        loadUrlThread(WebView  webView, String url) {
//            this.webView = webView;
//            this.url = url;
//        }
//
//        public void run() {
//            runOnUiThread(new Runnable() {
//                public void run(){
//                    webView.loadUrl(url);
//                }
//            });
//        }
//    }
}
