package com.example.internetsearch;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import java.util.Timer;
import java.util.TimerTask;

public class TestHomeFragment extends Fragment {

    ProgressBar progress_bar;
    EditText et_search;
    ImageButton b_search;
    TextView textView;
    TextView results_number;
    TextView top_textview;
    TextView ShareLocation;

    String userLocation = "Sri Lanka";
    ArrayList<String> arrayList;

    Dialog dialog;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    int counter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.test_home_fragment, container, false);

        et_search = view.findViewById(R.id.et_search);
        b_search = view.findViewById(R.id.b_search);
        top_textview = view.findViewById(R.id.top_textview);
        results_number = view.findViewById(R.id.results_number);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        progress_bar = view.findViewById(R.id.pb);
        ShareLocation = view.findViewById(R.id.ShareLocation);


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

        b_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String Product = et_search.getText().toString().substring(0,1).toUpperCase() + et_search.getText().toString().substring(1).toLowerCase();
                String searchQuery="buy "+et_search.getText().toString()+ " in " + userLocation ;
                top_textview.setText(Product + " in " + userLocation);
                searchGoogle(searchQuery);
            }
        });
        return view;
    }
    //----------------------------------------------------------------------------------------------
    private void searchGoogle(String searchQuery) {
        String searchUrl = "https://www.google.com/search" + "?q=" + searchQuery  + "&num=50";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(searchUrl).get();

                    Elements links = doc.select("cite");
                    Elements classes = doc.select("span.VuuXrf");
                    Elements images = doc.select("img.XNo5Ab");

                    final ArrayList<String> linkSet = new ArrayList<>();
                    final ArrayList<String> classSet = new ArrayList<>();
                    final ArrayList<String> iconSet = new ArrayList<>();

                    for (int j = 0; j < links.size(); j++) {
                        String text = links.get(j).text();
                        String class_name = classes.get(j).text();
                        String base64String = images.get(j).absUrl("src");

                    /*for (Element link : links) {
                        String text = link.text();*/
                        if (text.contains("›")) {
                            text = text.replace(" › ", "/");
                            text = text.replace(" ", "");
                        }   
                        linkSet.add(text);
                        classSet.add(class_name);
                        iconSet.add(base64String);

                        ArrayList<String> linkList = new ArrayList<>(linkSet);
                        ArrayList<String> classList = new ArrayList<>(classSet);

                        //ArrayList<String> iconList = new ArrayList<>(iconSet);


                    //--------------------------- addition of filter -------------------------------
                        final HashSet<String> private_linkSet = new HashSet<>();
                        final HashSet<String> private_classSet = new HashSet<>();

                        final HashSet<Bitmap> private_iconSet = new HashSet<>();


                        for (int i = 0; i < linkList.size(); i++) {
                            String private_link = linkList.get(i);
                            String class_link = classList.get(i);

                            String icon_link = iconSet.get(i);
                        /*}
                        for (String private_link : linkList) {*/
                            try {
                                Document Checkdoc = Jsoup.connect(private_link).get();
                                String html = Checkdoc.html();
                                if (html.contains("Buy Now") || html.contains("add to cart") || html.contains("BUY NOW") || html.contains("Select options")
                                        || html.contains("add-to-cart") || html.contains("Add to cart") || html.contains("quick-view") ) {
                                    private_linkSet.add(private_link);
                                    private_classSet.add(class_link);

                                    String base64Image = icon_link.split(",")[1];
                                    byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    private_iconSet.add(decodedByte);
                                }
                                else {
                                    progress();
                                }
                            } catch (IOException e) {
                                //progress();
                            }
                        }
                        ArrayList<String> private_linkList = new ArrayList<>(private_linkSet);
                        ArrayList<String> private_classList = new ArrayList<>(private_classSet);
                        ArrayList<Bitmap> private_iconList = new ArrayList<>(private_iconSet);

                    //------------------------------------------------------------------------------

                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                mLayoutManager=new LinearLayoutManager(getContext());
                                mAdapter=new MainAdapter(private_linkList,private_classList,private_iconList);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        });
                        results_number.setText(private_linkList.size() + getString(R.string.results_appeared));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //----------------------------------------------------------------------------------------------

    private void progress(){
        counter = 0;
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                progress_bar.setProgress(counter);
                if (counter ==50) {
                    t.cancel();
                }
            }
        };
        t.schedule(tt,0,50);
    }
    //----------------------------------------------------------------------------------------------
}


