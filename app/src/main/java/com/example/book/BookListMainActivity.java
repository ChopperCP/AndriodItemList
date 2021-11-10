package com.example.book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.dataop.DataLoader;
import com.example.main.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import com.example.book.BookListAdapter;

public class BookListMainActivity extends AppCompatActivity {
    private List<ShopItem> items;
    private RecyclerView mainRecycleView;
    private DataLoader dataLoader;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== BookListAdapter.BookViewHolder.REQUEST_CODE_ITEM_ADD && resultCode==InputActivity.RESULT_CODE_ADD_ITEM) {
            // Get user input name from extra field
            String name = data.getStringExtra("name");
            items.add(new ShopItem(name, R.drawable.ak12));
            mainRecycleView.getAdapter().notifyItemInserted(items.size());      // An item was added, notify the recycle view
            dataLoader.saveData();      // save data to file
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_list_main);
        initdata();

        mainRecycleView = findViewById(R.id.recycle_view_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainRecycleView.setLayoutManager(layoutManager);
        mainRecycleView.setAdapter(new BookListAdapter(items,this));

        // Listen to the floating button
        FloatingActionButton fab = findViewById(R.id.floating_action_button_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to InputActivity to input data
                Intent intentToInputActivity = new Intent(BookListMainActivity.this,InputActivity.class);
                // Put default name from extra field in intent
                intentToInputActivity.putExtra("name","Unnamed Firearm");
                (BookListMainActivity.this).startActivityForResult(intentToInputActivity, BookListAdapter.BookViewHolder.REQUEST_CODE_ITEM_ADD);

            }
        });
    }

    public void initdata() {
        this.dataLoader =new DataLoader(this);
        this.items=dataLoader.loadData();
        this.items.add(new ShopItem("AK47", R.drawable.ak47));
        this.items.add(new ShopItem("AK12", R.drawable.ak12));
    }

}