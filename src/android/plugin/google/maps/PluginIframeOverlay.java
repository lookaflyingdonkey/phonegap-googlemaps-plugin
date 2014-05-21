package plugin.google.maps;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.IceCreamCordovaWebViewClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class PluginIframeOverlay extends MyPlugin {
  private CordovaWebView iframeView = null;
  private CordovaActivity cordovaActivity;
  private CordovaWebViewClient webViewClient;
  private CordovaChromeClient webViewChromeClient;
  
  @SuppressLint("UseSparseArrays")
  @Override
  public void initialize(CordovaInterface cordova, final CordovaWebView webView) {
    super.initialize(cordova, webView);
    cordovaActivity = (CordovaActivity) cordova.getActivity();
  }

  /**
   * Create kml overlay
   * 
   * @param args
   * @param callbackContext
   * @throws JSONException
   */
  @SuppressLint("NewApi")
  @SuppressWarnings("unused")
  private void createIframeOverlay(JSONArray args, CallbackContext callbackContext) throws JSONException {
    JSONObject opts = args.getJSONObject(1);
    Bundle params = PluginUtil.Json2Bundle(opts);
    

    try {
      int divW = contentToView(opts.getLong("width") );
      int divH = contentToView(opts.getLong("height"));
      int divLeft = contentToView(opts.getLong("left"));
      int divTop = contentToView(opts.getLong("top"));
      
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(divW, divH);
      layoutParams.topMargin = divTop;
      layoutParams.leftMargin = divLeft;
      
      
      
      iframeView = new CordovaWebView(this.cordovaActivity);
      iframeView.setLayoutParams(layoutParams);
      iframeView.setBackgroundColor(Color.TRANSPARENT);
      if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
        webViewClient = new CordovaWebViewClient(this.cordovaActivity, webView);
      } else {
        webViewClient = new IceCreamCordovaWebViewClient(this.cordovaActivity, iframeView);
        iframeView.setLayerType(WebView.LAYER_TYPE_NONE, null);
      }
      this.webViewChromeClient = new CordovaChromeClient(this.cordovaActivity, iframeView);
      
      iframeView.setWebChromeClient(webViewChromeClient);
      iframeView.setWebViewClient(webViewClient);
      iframeView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
      iframeView.setOnTouchListener(new View.OnTouchListener() {

        public boolean onTouch(View v, MotionEvent event) {
          return (event.getAction() == MotionEvent.ACTION_MOVE);
        }
      });
      
      iframeView.loadUrl("file:///android_asset/www/" + opts.getString("src"));
      
      this.mapCtrl.mapView.addView(iframeView);
      
    } catch (Exception e) {}
    
  }

  private int contentToView(long d) {
    return Math.round(d * webView.getScale());
  }

}
