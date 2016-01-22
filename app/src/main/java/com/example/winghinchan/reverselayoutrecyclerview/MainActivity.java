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
    final static String PROGRESS_BAR = "progressBar";
    boolean progressBarAdded;
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
                //we can scroll and progress bar is not yet added
                if (!progressBarAdded && llm.findLastCompletelyVisibleItemPosition() != mAdapter.getItemCount() - 1) {
                    progressBarAdded = true;
                    myDataset.add(PROGRESS_BAR);
                    mAdapter.notifyItemInserted(myDataset.size() - 1);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //remove progress item
                        boolean removed = myDataset.remove(PROGRESS_BAR);
                        if (removed) {
                            mAdapter.notifyItemRemoved(myDataset.size());
                            progressBarAdded = false; //progress bar is now removed
                        }
                        //add items one by one
                        for (int i = 0; i < 3; i++) {
                            myDataset.add("Item" + (myDataset.size() + 1));
                            mAdapter.notifyItemInserted(myDataset.size());
                        }
                    //we can scroll
                    if(llm.findLastCompletelyVisibleItemPosition()!=mAdapter.getItemCount()-1) {
                        mAdapter.setLoaded();
                    }
                }
            }, 500);
                System.out.println("load");
            }
        });
    }

}
