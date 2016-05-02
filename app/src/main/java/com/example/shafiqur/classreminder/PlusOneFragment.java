package com.example.shafiqur.classreminder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class PlusOneFragment extends android.support.v4.app.Fragment {
    public ListView listView;
    public ArrayList<String> strArr= new ArrayList<String>();;
    public ArrayAdapter<String>  adapter;
    public Context context;
    public Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      //  Intent i = new Intent(getActivity(),MainActivity.class);
        //startActivity(i);

        View view = inflater.inflate(R.layout.activity_main2, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        //strArr= new ArrayList<String>();
        button = (Button) view.findViewById(R.id.mainActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);
            }
        });
                context= getActivity();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void onActivityCreated(Bundle savedInstanceState,String newNotice) {

        super.onActivityCreated(savedInstanceState);
        Intent i = new Intent(getActivity(),MainActivity.class);
        startActivity(i);
        if(!newNotice.isEmpty())
        {
            System.out.print("Insisde on create ");
            strArr.add(newNotice);
            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), strArr, android.R.layout.simple_list_item_1);
            adapter= new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,strArr);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

       // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);

        //setListAdapter(adapter);
        //getListView().setOnItemClickListener(this);


    }

    public  void settingUpList(String newNotice)
    {
       // if(getActivity()!=null)
       // {
            System.out.print("Inside list setting up\n");
            strArr.add(newNotice);
            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), strArr, android.R.layout.simple_list_item_1);
            adapter= new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,strArr);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //setListAdapter(adapter);
        //}
       // else
       //     System.out.print("Empty Activity in PlusOneFragment\n");


    }



    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT)
                .show();

    }*/

}

