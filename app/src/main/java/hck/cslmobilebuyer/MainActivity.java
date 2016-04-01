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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import hck.cslmobilebuyer.common.InformationData;
import hck.cslmobilebuyer.common.JavaScriptLib;
import hck.cslmobilebuyer.common.PhoneData;

public class MainActivity extends AppCompatActivity {
    Context context;
    WebView webView;
    Spinner phoneSelect;
    Spinner colorSelect;
    LinearLayout functionBar;
    TextView loading;

    String mainUrl;
    String selectedPhone;
    String selectedColor;
    boolean pageFinish;
    boolean javascriptFinish;
    InformationData webData;
    HashMap<String, Integer> loadingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        webView = (WebView) findViewById(R.id.webView);
        functionBar = (LinearLayout) findViewById(R.id.function);
        phoneSelect = (Spinner) findViewById(R.id.phoneSelect);
        colorSelect = (Spinner) findViewById(R.id.colorSelect);
        loading = (TextView) findViewById(R.id.loading);

        selectedPhone = "S7Edge";
        mainUrl = PhoneData.PhoneMap.get(selectedPhone);
        webData = new InformationData(context);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");

        setSpinner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String[] menuItems = {"Profile Data", "Select Phone"};

        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
        resetWebView();
        setWebView();
        functionBar.setVisibility(View.GONE);
    }

    public void clearCookie(){
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
    }

    public void loadJavaScript(WebView view, String javaScript){
        view.loadUrl("javascript:(function() {$( document ).ready(function() { " +
                javaScript +
                "});})()");
    }

    public void injectScriptFile(WebView view, String scriptFile) {
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
            e.printStackTrace();
        }
    }

    private void setSpinner(){
        ArrayAdapter phoneSelectAdapter = new ArrayAdapter(context,
                android.R.layout.simple_spinner_item, PhoneData.PhoneMap.keySet().toArray());
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
                mainUrl = PhoneData.PhoneMap.get(selectedPhone);

                ArrayAdapter colorSelectAdapter = new ArrayAdapter(context,
                        android.R.layout.simple_spinner_item, PhoneData.PhoneColorMap.get(selectedPhone).keySet().toArray());
                colorSelectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                selectedColor = String.valueOf(PhoneData.PhoneColorMap.get(selectedPhone).keySet().toArray()[0]);
                colorSelect.setAdapter(colorSelectAdapter);
                int colorPos = colorSelectAdapter.getPosition(selectedColor);
                colorPos = (colorPos < 0) ? 0 : colorPos;
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

    private void resetWebView(){
        loadingCount = new HashMap<>();
        loadingCount.put(mainUrl, 0);
        loadingCount.put(PhoneData.InformationPage, 0);
        loadingCount.put(PhoneData.ConformPage, 0);
        clearCookie();
    }

    private void setWebView(){
        webView.setVisibility(View.VISIBLE);
        webView.loadUrl(mainUrl);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                String url = view.getUrl();

                if (newProgress == 100) {
                    int count = loadingCount.get(url);
                    if (count == 0) {
                        javascriptFinish = true;
                        if (pageFinish) {
                            addJavaScript(view, url);
                        }
                    }
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pageFinish = false;
                javascriptFinish = false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pageFinish = true;
                if (javascriptFinish) {
                    addJavaScript(view, url);
                }
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
    }

    private void addJavaScript(WebView view, String url){
        int count = loadingCount.get(url);
        if (count == 0) {
            loading.setVisibility(View.VISIBLE);
            if (url.equalsIgnoreCase(mainUrl)) {
                String phoneColor = PhoneData.PhoneColorMap.get(selectedPhone).get(selectedColor);
                loadJavaScript(view, JavaScriptLib.checkInventory(phoneColor) +  JavaScriptLib.selectPhone(phoneColor) +  JavaScriptLib.selectPhoneSubmit());
            } else if (url.equalsIgnoreCase(PhoneData.InformationPage)) {
                injectScriptFile(view, "js/script.js");
                loadJavaScript(view,  JavaScriptLib.inputInformation(context) +  JavaScriptLib.addSaveProfileDataButton());
            } else if (url.equalsIgnoreCase(PhoneData.ConformPage)) {
                loadJavaScript(view,  JavaScriptLib.conformInformation());
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
}
