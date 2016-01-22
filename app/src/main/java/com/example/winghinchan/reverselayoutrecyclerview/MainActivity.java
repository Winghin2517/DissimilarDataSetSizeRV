package com.example.winghinchan.reverselayoutrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    MyAdapter mAdapter;
    ArrayList<String> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing dataset to 3 items
        for (int i = 0; i < 3; i++) {
            myDataset.add("Item " + i);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        mAdapter = new MyAdapter(myDataset, recyclerView);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMoreListener(new MyAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add progress item
                myDataset.add(null);
                mAdapter.notifyItemInserted(myDataset.size() - 1);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //remove progress item
                        myDataset.remove(myDataset.size() - 1);
                        mAdapter.notifyItemRemoved(myDataset.size());
                        //add items one by one
                        for (int i = 0; i < 1; i++) {
                            myDataset.add("Item" + (myDataset.size() + 1));
                            mAdapter.notifyItemInserted(myDataset.size());
                        }
                        mAdapter.setLoaded();
                        //or you can add all at once but do not forget to call mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
                System.out.println("load");
            }
        });
    }

}
