package com.example.myapplication.Content_package;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.KeyWord_package.KeyWordSet;
import com.example.myapplication.KeyWord_package.KeyWordSetActivity;
import com.example.myapplication.KeyWord_package.KeyWordSetDBholder;
import com.example.myapplication.R;

public class ContentTextImputActivity extends AppCompatActivity {
    Button contentText_add_btn;
    EditText contentText_name, contentText_nameList, contentText_url, contentText_notiDue, contentText_fvDue, contentText_content;
    ListView lv_contentText;
    ArrayAdapter contentTextArrayadapter;
    ContentTextDBholder contentTextDBholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_input_layout);

        contentText_add_btn = findViewById(R.id.contentText_btn_add);
        contentText_name = findViewById(R.id.contentText_name);
        contentText_nameList = findViewById(R.id.contentText_nameList);
        contentText_url = findViewById(R.id.contentText_url);
        contentText_notiDue = findViewById(R.id.contentText_notiDue);
        contentText_fvDue = findViewById(R.id.contentText_fvDue);
        contentText_content = findViewById(R.id.contentText_content);
        lv_contentText = findViewById(R.id.lv_contentText);

        contentTextDBholder = new ContentTextDBholder(ContentTextImputActivity.this);
        ShowCustomerOnListView(contentTextDBholder);

        contentText_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentText contentText;
                try {
                    contentText = new ContentText(-1,
                            contentText_name.getText().toString(),
                            contentText_nameList.getText().toString(),
                            contentText_url.getText().toString(),
                            Integer.parseInt(contentText_notiDue.getText().toString()),
                            Integer.parseInt(contentText_fvDue.getText().toString()),
                            contentText_content.getText().toString());
                    Toast.makeText(ContentTextImputActivity.this, contentText.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ContentTextImputActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    contentText = new ContentText(-1, "error", "error", "error", -1, -1, "error");
                }
                ContentTextDBholder contentTextDBholder = new ContentTextDBholder(ContentTextImputActivity.this);
                boolean succes = contentTextDBholder.addOne(contentText);

                ShowCustomerOnListView(contentTextDBholder);
                //Toast.makeText(MainActivity.this, "Success= " + succes, Toast.LENGTH_SHORT).show();
            }
        });

        lv_contentText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentText clickContent = (ContentText) parent.getItemAtPosition(position);
                contentTextDBholder.deleteOne(clickContent);
                ShowCustomerOnListView(contentTextDBholder);
                Toast.makeText(ContentTextImputActivity.this, "DELETE" + clickContent.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCustomerOnListView(ContentTextDBholder contentTextDBholder) {
        contentTextArrayadapter = new ArrayAdapter<ContentText>(ContentTextImputActivity.this, android.R.layout.simple_list_item_1, contentTextDBholder.getEveryone());
        lv_contentText.setAdapter(contentTextArrayadapter);
    }
}