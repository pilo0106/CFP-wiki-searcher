package com.example.myapplication.General_package;

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

public class GeneralInputActivity extends AppCompatActivity {
    Button general_add_btn;
    EditText ge_name, ge_start, ge_end, ge_where, ge_deadline, ge_brief;
    ListView lv_general;
    ArrayAdapter generalArrayadapter;
    GeneralDBHolder generalDBHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_input_layout);

        general_add_btn = findViewById(R.id.general_btn_add);
        ge_name = findViewById(R.id.general_name);
        ge_start = findViewById(R.id.general_start);
        ge_end = findViewById(R.id.general_end);
        ge_where = findViewById(R.id.general_where);
        ge_deadline = findViewById(R.id.general_deadline);
        ge_brief = findViewById(R.id.general_brief);
        lv_general = findViewById(R.id.lv_general);

        generalDBHolder = new GeneralDBHolder(GeneralInputActivity.this);
        ShowCustomerOnListView(generalDBHolder);

        general_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                General general;
                try{
                    general = new General(-1,
                            ge_name.getText().toString(),
                            Integer.parseInt(ge_start.getText().toString()),
                            Integer.parseInt(ge_end.getText().toString()),
                            ge_where.getText().toString(),
                            Integer.parseInt(ge_deadline.getText().toString()),
                            ge_brief.getText().toString());
                    Toast.makeText(GeneralInputActivity.this, general.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(GeneralInputActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    general = new General(-1, "error", -1, -1, "error", -1, "error");
                }
                GeneralDBHolder generalDBHolder = new GeneralDBHolder(GeneralInputActivity.this);
                boolean succes = generalDBHolder.addOne(general);

                ShowCustomerOnListView(generalDBHolder);
                //Toast.makeText(MainActivity.this, "Success= " + succes, Toast.LENGTH_SHORT).show();
            }
        });
        lv_general.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                General clickGeneral = (General) parent.getItemAtPosition(position);
                generalDBHolder.deleteOne(clickGeneral);
                ShowCustomerOnListView(generalDBHolder);
                Toast.makeText(GeneralInputActivity.this, "DELETE" + clickGeneral.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomerOnListView(GeneralDBHolder generalDBHolder) {
        generalArrayadapter = new ArrayAdapter<General>(GeneralInputActivity.this, android.R.layout.simple_list_item_1, generalDBHolder.getEveryone());
        lv_general.setAdapter(generalArrayadapter);
    }
}