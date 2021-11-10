package com.example.book;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dataop.DataLoader;
import com.example.main.R;

import java.util.List;


public class BookListAdapter extends RecyclerView.Adapter {
    private final List<ShopItem> items;
    private final Context context;
    private final DataLoader dataLoader;

    public BookListAdapter(List<ShopItem> items, Context context) {
        this.items = items;
        this.context=context;
        this.dataLoader=new DataLoader(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitem_holder, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        // Fills data into view
        BookViewHolder bookviewholder = (BookViewHolder) viewholder;

        bookviewholder.getImageView().setImageResource(items.get(position).getImageid());
        bookviewholder.getTitleView().setText(items.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public static final int ID_FIREARM_ADD = 1;
        public static final int ID_FIREARM_EDIT = 2;
        public static final int ID_FIREARM_DEL = 3;
        public static final int REQUEST_CODE_ITEM_ADD = 997;
        // ViewHolder for RecyclerView to use
        // Views that goes into the ViewHolder must be appointed.
        private final View itemView;
        private final ImageView imageView;
        private final TextView titleView;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView=itemView;
            this.imageView = itemView.findViewById(R.id.imageView_shopitem);
            this.titleView = itemView.findViewById(R.id.textView_shopitem);
            itemView.setOnCreateContextMenuListener(this);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTitleView() {
            return titleView;
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // Create the context menu and define menu items in the context menu
            int position = getAdapterPosition();
            MenuItem menuItemAdd = contextMenu.add(Menu.NONE, ID_FIREARM_ADD, ID_FIREARM_ADD, "Add a firearm");
            MenuItem menuItemEdit = contextMenu.add(Menu.NONE, ID_FIREARM_EDIT, ID_FIREARM_EDIT, "Edit" + " " + items.get(position).getTitle());
            MenuItem menuItemDelete = contextMenu.add(Menu.NONE, ID_FIREARM_DEL, ID_FIREARM_DEL, "Delete" + " " + items.get(position).getTitle());

            menuItemAdd.setOnMenuItemClickListener(this);
            menuItemEdit.setOnMenuItemClickListener(this);
            menuItemDelete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
                int position= getAdapterPosition();
                switch(menuItem.getItemId())
                {
                    case ID_FIREARM_ADD:
                        // switch to InputActivity to input data
                        Intent intentToInputActivity = new Intent(itemView.getContext(),InputActivity.class);
                        intentToInputActivity.putExtra("position", position);
                        // Put default name from extra field in intent
                        intentToInputActivity.putExtra("name","Unnamed Firearm");
                        ((Activity) itemView.getContext()).startActivityForResult(intentToInputActivity, REQUEST_CODE_ITEM_ADD);

                        break;

                    case ID_FIREARM_EDIT:
                        View dialogueView= LayoutInflater.from(itemView.getContext()).inflate(R.layout.dialogue_input_item,null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                        alertDialogBuilder.setView(dialogueView);

                        alertDialogBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText editName=dialogueView.findViewById(R.id.edit_text_name);
                                // Change the item's title
                                items.get(position).setTitle(editName.getText().toString());
                                BookListAdapter.this.notifyItemChanged(position);

                                // Give user feedback
                                Toast.makeText(itemView.getContext(),"Item's title changed"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
                                dataLoader.saveData();      // write to file
                            }
                        });
                        alertDialogBuilder.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialogBuilder.create().show();

                        break;

                    case ID_FIREARM_DEL:
                        items.remove(position);
                        BookListAdapter.this.notifyItemRemoved(position);
                        dataLoader.saveData();      // write to file
                        Toast.makeText(itemView.getContext(),"Item deleted", Toast.LENGTH_LONG).show();
                        break;
                }

                return false;
        }

    }
}