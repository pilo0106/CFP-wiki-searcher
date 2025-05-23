package com.example.myapplication.KeyWord_package;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;


public class KeyWordSetActivity extends AppCompatActivity {
    Button keyword_add_btn;
    EditText key_name, key_keyword;
    ListView lv_keyWordSet;
    ArrayAdapter keyWordSetArrayadapter;
    KeyWordSetDBholder keyWordSetDBholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_input_layout);

        keyword_add_btn = findViewById(R.id.keyword_btn_add);
        key_name = findViewById(R.id.keyword_name);
        key_keyword = findViewById(R.id.keyword_keyword);
        lv_keyWordSet = findViewById(R.id.lv_keyword);

        keyWordSetDBholder = new KeyWordSetDBholder(KeyWordSetActivity.this);
        ShowCustomerOnListView(keyWordSetDBholder);

        keyword_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyWordSet keyWordSet;
                try{
                    keyWordSet = new KeyWordSet(-1,
                            key_name.getText().toString(),
                            key_keyword.getText().toString());
                    Toast.makeText(KeyWordSetActivity.this, keyWordSet.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(KeyWordSetActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    keyWordSet = new KeyWordSet(-1, "error","error");
                }
                KeyWordSetDBholder keyWordSetDBholder = new KeyWordSetDBholder(KeyWordSetActivity.this);
                boolean succes = keyWordSetDBholder.addOne(keyWordSet);

                ShowCustomerOnListView(keyWordSetDBholder);
                //Toast.makeText(MainActivity.this, "Success= " + succes, Toast.LENGTH_SHORT).show();
            }
        });
        lv_keyWordSet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeyWordSet clickKeyWord = (KeyWordSet) parent.getItemAtPosition(position);
                keyWordSetDBholder.deleteOne(clickKeyWord);
                ShowCustomerOnListView(keyWordSetDBholder);
                Toast.makeText(KeyWordSetActivity.this, "DELETE" + clickKeyWord.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomerOnListView(KeyWordSetDBholder keyWordSetDBholder) {
        keyWordSetArrayadapter = new ArrayAdapter<KeyWordSet>(KeyWordSetActivity.this, android.R.layout.simple_list_item_1, keyWordSetDBholder.getEveryone());
        lv_keyWordSet.setAdapter(keyWordSetArrayadapter);
    }
}