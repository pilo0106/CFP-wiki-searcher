package com.example.myapplication.Customer_package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myapplication.R;

public class customerInputActivity extends AppCompatActivity {
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_cumstomer;
    ArrayAdapter customerArraydapter;
    DataBaseHelper dataBaseHelper;
    //ContentTextDBholder contentTextDBholder;
    //KeyWordSetDBholder keyWordSetDBholder;
    //GeneralDBHolder generalDBHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_input);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.switch1);
        lv_cumstomer = findViewById(R.id.lt_view);

        dataBaseHelper = new DataBaseHelper(customerInputActivity.this);
        //contentTextDBholder = new ContentTextDBholder(MainActivity.this);
        //keyWordSetDBholder = new KeyWordSetDBholder(MainActivity.this);
        //generalDBHolder = new GeneralDBHolder(MainActivity.this);

        ShowCustomerOnListView(dataBaseHelper);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerModel customerModel;
                try{
                    customerModel = new CustomerModel(-1,
                            et_name.getText().toString(),
                            Integer.parseInt(et_age.getText().toString()),
                            sw_activeCustomer.isChecked());
                    Toast.makeText(customerInputActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(customerInputActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", 0, false);
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(customerInputActivity.this);
                boolean succes = dataBaseHelper.addOne(customerModel);

                ShowCustomerOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, "Success= " + succes, Toast.LENGTH_SHORT).show();
            }
        });
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(customerInputActivity.this);

                ShowCustomerOnListView(dataBaseHelper);
                //Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        lv_cumstomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickCustomer);
                ShowCustomerOnListView(dataBaseHelper);
                Toast.makeText(customerInputActivity.this, "DELETE" + clickCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomerOnListView(DataBaseHelper dataBaseHelper) {
        customerArraydapter = new ArrayAdapter<CustomerModel>(customerInputActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getEveryone());
        lv_cumstomer.setAdapter(customerArraydapter);
    }
}