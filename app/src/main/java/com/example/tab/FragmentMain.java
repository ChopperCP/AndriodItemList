package com.example.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book.BookListAdapter;
import com.example.book.InputActivity;
import com.example.book.ShopItem;
import com.example.dataop.DataLoader;
import com.example.main.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class FragmentMain extends Fragment {
    private RecyclerView mainRecycleView;
    private List<ShopItem> items;
    private DataLoader dataLoader;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== BookListAdapter.BookViewHolder.REQUEST_CODE_ITEM_ADD && resultCode==InputActivity.RESULT_CODE_ADD_ITEM) {
            // Get user input name from extra field
            String name = data.getStringExtra("name");
            items.add(new ShopItem(name, R.drawable.ak12));
            mainRecycleView.getAdapter().notifyItemInserted(items.size());      // An item was added, notify the recycle view
            dataLoader.saveData();      // save data to file
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container);

        initdata();

        mainRecycleView = view.findViewById(R.id.recycle_view_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mainRecycleView.setLayoutManager(layoutManager);
        mainRecycleView.setAdapter(new BookListAdapter(items,this.getContext()));

        // Listen to the floating button
        FloatingActionButton fab = view.findViewById(R.id.floating_action_button_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to InputActivity to input data
                Intent intentToInputActivity = new Intent(getActivity(), InputActivity.class);
                // Put default name from extra field in intent
                intentToInputActivity.putExtra("name","Unnamed Firearm");
                startActivityForResult(intentToInputActivity, BookListAdapter.BookViewHolder.REQUEST_CODE_ITEM_ADD);

            }
        });


        return view;
    }

    public void initdata() {
        this.dataLoader =new DataLoader(this.getContext());
        this.items=dataLoader.loadData();
        this.items.add(new ShopItem("AK47", R.drawable.ak47));
        this.items.add(new ShopItem("AK12", R.drawable.ak12));
    }
}
