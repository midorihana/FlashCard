package com.yasai.flashcard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnCreateNewCard;
    private Button btnListCards;
    private Button btnSetting;


   private List<String> getFolders() {
        List<String> folders = new ArrayList<>();

        File listRoodDir = App.getListRootDir();

        for(File listFolder: listRoodDir.listFiles()) {
            folders.add(listFolder.getName());
        }

        Collections.sort(folders);

        return folders;
    }


    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//            String folder = folderAdapter.getItem(position);
//
//            Intent intent = new Intent("com.yasai.flashcard.ListActivity");
//            intent.putExtra(ListActivity.KEY_FOLDER, folder);
//            startActivity(intent);
        }
    };

    private void copyFileFromResource(int id, String fileName) {
        InputStream in = getResources().openRawResource(id);
        File folder = App.getListRootDir();
        File file = new File(folder, fileName);

        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer, 0, buffer.length)) != -1){
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void updateListFiles() {
        File fileroot = App.getListRootDir();
        if(!fileroot.exists())
        {
            fileroot.mkdirs();
        }
        copyFileFromResource(R.raw.business_plan, "business_plan.csv");
        copyFileFromResource(R.raw.contracts, "contracts.csv");

    }

    boolean deleteDirectory(File file) {
        // First delete all content recursively
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                if(!deleteDirectory(f)) {
                    return false;
                }
            }
        }

        // Then delete the folder itself
        return file.delete();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deleteDirectory(App.getListRootDir());
        updateListFiles();

//        // Flash Card Create Button
//        btnCreateNewCard = (Button) findViewById(R.id.btn_CreateFlashCard);
//        btnCreateNewCard.setOnClickListener(MainActivity.this);

        // List Card Button
        btnListCards = (Button) findViewById(R.id.btn_ListCard);
        btnListCards.setOnClickListener(MainActivity.this);

        // Setting Button
        btnSetting = (Button) findViewById(R.id.btn_Setting);
        btnSetting.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {

        if ( v == btnListCards) {
            // Goto ListCards Screen
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            // start the activity
            startActivity(intent);

        } else if( v == btnSetting) {
            // Goto Setting Screen
            //Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            // start the activity
            startActivity(intent);
        }
    }
}
