package com.example.dataop;

import android.content.Context;

import com.example.book.ShopItem;
import com.example.main.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private final String DATA_FILE_NAME;
    private Context context = null;
    List<ShopItem> shopItemList;

    public DataLoader(Context context) {
        this.context=context;
        this.DATA_FILE_NAME=context.getResources().getString(R.string.DATA_FILE_NAME);
    }

    public List<ShopItem> loadData() {
        shopItemList=new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            shopItemList = (ArrayList<ShopItem>) objectInputStream.readObject();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return shopItemList;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(shopItemList);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
