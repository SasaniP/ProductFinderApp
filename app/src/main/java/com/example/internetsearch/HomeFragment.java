package com.example.internetsearch;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {


    EditText et_search;
    ImageButton b_search;
    WebView search_webview;
    String userLocation = "Sri Lanka";
    TextView textView;
    ArrayList<String> arrayList;
    Dialog dialog;
    List<String> sriLankanUrls;
    TextView top_textview;
    //private ArrayAdapter<CharSequence> countryAdapter;
    //ImageButton filter_country;
    //private WebView search_webview;
    //private lateinit var search_webview: WebView



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        et_search=view.findViewById(R.id.et_search);
        b_search=view.findViewById(R.id.b_search);
        search_webview=view.findViewById(R.id.search_webview);
        top_textview=view.findViewById(R.id.top_textview);

// ------------------------------------------------- Fetch User Location -----------------------------------------------------//
        //Assign variable
        textView = view.findViewById(R.id.text_view);

        //Initialize array list
        arrayList = new ArrayList<>();
        arrayList.add("Any");
        arrayList.add("Akkaraipattu");arrayList.add("Ampara");arrayList.add("Anuradhapura");arrayList.add("Badulla");
        arrayList.add("Balangoda");arrayList.add("Bandarawela");arrayList.add("Battaramulla");arrayList.add("Batticaloa");
        arrayList.add("Chavakachcheri");arrayList.add("Chilaw");arrayList.add("Colombo");arrayList.add("Dambulla");
        arrayList.add("Dehiwela-Mount Lavinia");arrayList.add("Embilipitiya");arrayList.add("Eravur");arrayList.add("Galle");
        arrayList.add("Gampaha");arrayList.add("Hambantota");arrayList.add("Happutalle");arrayList.add("Homagama");
        arrayList.add("Jaffna");arrayList.add("Kalmunai");arrayList.add("Kalutata");arrayList.add("Kandy");arrayList.add("Kattankudy");
        arrayList.add("Kegalle");arrayList.add("Kinniya");arrayList.add("Kuliyapitiya");arrayList.add("Kurunegala");arrayList.add("Mannar");
        arrayList.add("Matale");arrayList.add("Matara");arrayList.add("Negombo");arrayList.add("Nuwara Eliya");arrayList.add("Pannipitiya");arrayList.add("Point Pedro");
        arrayList.add("Polonnaruwa");arrayList.add("Ratnapura");arrayList.add("Sri Jayewardenepura Kotte");arrayList.add("Trincomalee");
        arrayList.add("Valvettithurai");arrayList.add("Vavuniya");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize dialog
                dialog = new Dialog(requireContext());
                //Set custom dialog
                dialog.setContentView(R.layout.dialog_searchable_spinner);
                //Set custom height and width
                dialog.getWindow().setLayout( 650, 800);
                //Set transparent background
                dialog.getWindow().setBackgroundDrawable (new ColorDrawable(Color.TRANSPARENT));
                //Show dialog
                dialog.show();
                //Initialize and assign variable
                EditText editText = dialog.findViewById(R.id.edit_text);
                ListView listView = dialog.findViewById(R.id.list_view);

                //Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext()
                        ,android.R.layout.simple_list_item_1, arrayList);
                //Set adapter
                listView.setAdapter (adapter);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        //Filter array List
                        adapter.getFilter().filter(charSequence);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                        textView.setText(adapter.getItem(i));
                        userLocation = textView.getText().toString();
                        if (userLocation=="Any")
                        {
                            userLocation="Sri Lanka";
                        }
                        dialog.dismiss();
                    }
                });

            }
        });
// --------------------------------------------------------------------------------------------------------------------------//



// ------------------------------------------------- Search Mechanism ------------------------------------------------------//
        sriLankanUrls = new ArrayList<>();

        b_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String searchUrl="https://www.google.com/search?q="+ "buy "+et_search.getText().toString()+ " in " + userLocation ;
                WebSettings webSettings = search_webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                search_webview.setWebViewClient(new MyWebViewClient());
                search_webview.loadUrl(searchUrl);
                top_textview.setText(et_search.getText().toString()+ " in " + userLocation);

            }
        });
        return view;
    }
    //----------------------------------------------------------------------------------------------
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            // Check if the website contains the keyword "Sri Lanka"
            if (url.startsWith("http") && isWebsiteContainsKeyword(view)) {
                sriLankanUrls.add(url);
            }

            // Continue to the next search result URL
            loadNextSearchResult(view);
        }
    }
    //----------------------------------------------------------------------------------------------
    private boolean isWebsiteContainsKeyword(WebView view) {
        // Extract the website's HTML content
        view.evaluateJavascript("(function() { return document.documentElement.innerHTML; })();",
                html -> {
                    // Check if the HTML content contains the keyword "Sri Lanka"
                    if (html.contains("Sri Lanka")) {
                        displaySriLankanWebsites();
                    }
                });
        return false;
    }
    //----------------------------------------------------------------------------------------------
    private void loadNextSearchResult(WebView view) {
        // Scroll to the next search result
        view.evaluateJavascript("window.scrollTo(0, document.body.scrollHeight);", value -> {
            // Wait for the page to scroll
            new Handler().postDelayed(() -> {
                // Find the link to the next search result
                view.evaluateJavascript(
                        "(function() { var nextLink = document.getElementById('pnnext'); return (nextLink !== null) ? nextLink.href : ''; })();",
                        nextUrl -> {
                            if (!TextUtils.isEmpty(nextUrl) && !nextUrl.equals("null")) {
                                // Load the next search result URL
                                view.loadUrl(nextUrl);
                            } else {
                                // All search results have been checked, display Sri Lankan websites
                                displaySriLankanWebsites();
                            }
                        });
            }, 2000); // Adjust the delay time as needed for scrolling and loading the next page
        });
    }
    //----------------------------------------------------------------------------------------------
    /*private boolean isSriLankanWebsite(String url) {
        // Exclude URLs related to Google Maps
        if (url.contains("google.com/maps") || url.contains("google.com/maps/")) {
            return false;
        }
        // Implement your logic to check if the website is Sri Lankan
        // This can include checking the URL, domain, or any other relevant criteria
        // Return true if it is a Sri Lankan website, otherwise return false
        // Example: Checking if the URL contains '.lk' or 'sri lanka' in the domain name
        return url.contains(".lk") || url.contains("sri lanka");
    }*/
    //----------------------------------------------------------------------------------------------
    private void displaySriLankanWebsites() {
        // Create a StringBuilder to store the URLs
        StringBuilder sb = new StringBuilder();
        for (String url : sriLankanUrls) {
            sb.append(url).append("<br>");
        }
        // Load the HTML content with the Sri Lankan website URLs in the WebView
        search_webview.loadData(sb.toString(), "text/html", "utf-8");
    }


// --------------------------------------------------------------------------------------------------------------------------//


//----------------------------------------------------- Link Extractor ----------------------------------------------------------//
    private class LinkExtractorInterface {
        @JavascriptInterface
        public void extractLinks(String linksJson) {
            try {
                // Parse the JSON array of links returned by the JavaScript
                JSONArray linksArray = new JSONArray(linksJson);

                // Construct a custom HTML template to display the link hrefs
                StringBuilder htmlBuilder = new StringBuilder();
                htmlBuilder.append("<html><head></head><body>");

                // Loop through the links and display the href attribute of each link
                for (int i = 0; i < linksArray.length(); i++) {
                    JSONObject linkObject = linksArray.getJSONObject(i);
                    String href = linkObject.getString("href");
                    htmlBuilder.append("<a>").append(href).append("</a>");
                }

                htmlBuilder.append("</body></html>");

                // Load the custom HTML template into the WebView
                search_webview.loadDataWithBaseURL(null, htmlBuilder.toString(), "text/html", "UTF-8", null);
            } catch (JSONException e) {
                Log.e("LinkExtractor", "Error parsing links JSON", e);
            }
        }
    }
//------------------------------------------------------------------------------------------------------------------------------//


//---------------------------------------------function for the back button-----------------------------------------------------//
    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode){
                case KeyEvent.KEYCODE_BACK:
                    if (search_webview.canGoBack()){
                        search_webview.goBack();
                    }
                    else {
                        getActivity().finish();
                    }
                    return true;
            }
        }
        return onKeyDown(keyCode, event);
    }*/

//**function for the back button
    /*public void onBackPressed(){
        // now you don't need findViewById because you can access field prepared in onCreate
        if (search_webview.canGoBack()) {
            search_webview.goBack();
        } else {
            onBackPressed();
        }
    }*/
// ----------------------------------------------------------------------------------------------------------------------------//

}


