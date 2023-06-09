package com.example.internetsearch;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TestHomeFragment extends Fragment {

    EditText et_search;
    ImageButton b_search;
    String userLocation = "Sri Lanka";
    TextView textView;
    TextView results_number;
    TextView top_textview;
    TextView second_textview;
    ArrayList<String> arrayList;
    Dialog dialog;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    int j = 0;
    int count;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.test_home_fragment, container, false);

        et_search = view.findViewById(R.id.et_search);
        b_search = view.findViewById(R.id.b_search);
        //search_webview=view.findViewById(R.id.search_webview);
        top_textview = view.findViewById(R.id.top_textview);
        second_textview = view.findViewById(R.id.second_textview);
        results_number = view.findViewById(R.id.results_number);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        //search_webview.setMovementMethod(new ScrollingMovementMethod());



// --------------------------------------- Fetch User Location------------------------------------//
        //Assign variable
        textView = view.findViewById(R.id.text_view);
        //Initialize array list
        arrayList = new ArrayList<>();
        arrayList.add("Any");
        arrayList.add("Akkaraipattu");arrayList.add("Ampara");arrayList.add("Anuradhapura");
        arrayList.add("Badulla");
        arrayList.add("Balangoda");arrayList.add("Bandarawela");arrayList.add("Battaramulla");
        arrayList.add("Batticaloa");
        arrayList.add("Chavakachcheri");arrayList.add("Chilaw");arrayList.add("Colombo");
        arrayList.add("Dambulla");
        arrayList.add("Dehiwela-Mount Lavinia");arrayList.add("Embilipitiya");
        arrayList.add("Eravur");arrayList.add("Galle");
        arrayList.add("Gampaha");arrayList.add("Hambantota");arrayList.add("Happutalle");
        arrayList.add("Homagama");
        arrayList.add("Jaffna");arrayList.add("Kalmunai");arrayList.add("Kalutata");
        arrayList.add("Kandy");arrayList.add("Kattankudy");
        arrayList.add("Kegalle");arrayList.add("Kinniya");arrayList.add("Kuliyapitiya");
        arrayList.add("Kurunegala");arrayList.add("Mannar");
        arrayList.add("Matale");arrayList.add("Matara");arrayList.add("Negombo");
        arrayList.add("Nuwara Eliya");arrayList.add("Pannipitiya");arrayList.add("Point Pedro");
        arrayList.add("Polonnaruwa");arrayList.add("Ratnapura");
        arrayList.add("Sri Jayewardenepura Kotte");arrayList.add("Trincomalee");
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
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){

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
                        if (userLocation.equals("Any"))
                        {
                            userLocation="Sri Lanka";
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
// -----------------------------------------------------------------------------------------------//



// ----------------------------------- Search Mechanism ------------------------------------------//
        //sriLankanUrls = new ArrayList<>();

        b_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Product = et_search.getText().toString().substring(0,1).toUpperCase() + et_search.getText().toString().substring(1);
                String searchQuery="buy "+et_search.getText().toString()+ " in " + userLocation ;
                top_textview.setText(Product + " in " + userLocation);
                searchGoogle(searchQuery);

            }
        });
        return view;

    }
    private void searchGoogle(String searchQuery) {
        // search over your preferred search engine
        //String searchQuery="buy "+et_search.getText().toString()+ " in " + userLocation ;
        String searchUrl = "https://www.google.com/search" + "?q=" + searchQuery /* + "&num=50"*/;


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    count=0;

                    Document doc = Jsoup.connect(searchUrl).get();
                    //String html = doc.html();
                    //Files.write(Paths.get("/sdcard/file1.html"), html.getBytes());
                    Elements links = doc.select("cite");

                    final HashSet<String> linkSet = new HashSet<>();



                    //final StringBuilder resultBuilder = new StringBuilder();
                    for (Element link : links) {

                        String text = link.text();
                        if (text.contains("›")) {
                            text = text.replace(" › ", "/");
                            text = text.replace(" ", "");
                        }   
                        linkSet.add(text);
                        count = count+1;
                        ArrayList<String> linkList = new ArrayList<>(linkSet);
                        //printLinkList(linkList);

                        //------------ addition of filter ------------------
                        LinkScraper scraper = new LinkScraper();
                        scraper.execute(linkList);
                        //--------------------------------------------------


                        //resultBuilder.append(text).append("\n");
                        //search_webview.setText(resultBuilder.toString());
                    }
                    results_number.setText(count + " results appeared ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //----------------------------------------------------------------------------------------------

    private class LinkScraper extends AsyncTask<ArrayList<String>, Void, StringBuilder> {

        @Override
        protected StringBuilder doInBackground(ArrayList<String>... arrayLists) {
            ArrayList<String> private_links = arrayLists[0];
            StringBuilder resultBuilder = new StringBuilder();

            for (String private_link : private_links) {
                try {
                    Document doc = Jsoup.connect(private_link).get();
                    String html = doc.html();
                    if (html.contains("Buy Now") || html.contains("add to cart") || html.contains("BUY NOW")) {
                        resultBuilder.append(private_link).append("\n");
                    } else {
                        resultBuilder.append("Not available").append("\n");
                    }
                } catch (IOException e) {
                    //String mess = e.getMessage();
                    //Links_text.setText(mess);
                }
            }
            return resultBuilder;
        }

        @Override
        protected void onPostExecute(StringBuilder resultBuilder) {
            super.onPostExecute(resultBuilder);


            //Links_text.setText(resultBuilder.toString());
            final HashSet<String> stockedLinkSet = new HashSet<>();
            //ArrayList<String> stockedLinkList = new ArrayList<>();
            stockedLinkSet.add(resultBuilder.toString());
            j = j+1;
            second_textview.setText(resultBuilder.toString());
            ArrayList<String> stockedLinkList = new ArrayList<>(stockedLinkSet);

            /*mLayoutManager = new LinearLayoutManager(getContext());
            mAdapter = new MainAdapter(stockedLinkList);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);*/

            if (j == count){
                mLayoutManager = new LinearLayoutManager(getContext());
                mAdapter = new MainAdapter(stockedLinkList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

            }

            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLayoutManager = new LinearLayoutManager(getContext());
                            mAdapter = new MainAdapter(stockedLinkList);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });
                }
            });*/
        }

    }
    //----------------------------------------------------------------------------------------------
}


