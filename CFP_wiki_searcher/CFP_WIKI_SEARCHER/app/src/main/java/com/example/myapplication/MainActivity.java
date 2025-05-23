package com.example.myapplication;

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>>
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.Customer_package.DataBaseHelper;
import com.example.myapplication.General_package.General;
import com.example.myapplication.General_package.GeneralDBHolder;
import com.example.myapplication.KeyWord_package.KeyWordSet;
import com.example.myapplication.KeyWord_package.KeyWordSetDBholder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<<

public class MainActivity extends AppCompatActivity implements MyAdapter.OnNoteListener {
    ImageButton btn_when_up, btn_when_down, btn_deadline_up, btn_deadline_down, btn_ashcan, btn_search;
    EditText et_name, et_age;
    DataBaseHelper dataBaseHelper;
    RecyclerView recyclerView;

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>>
    TextView time;
    TextView time2;
    TextView time3;

    public int date1=10000101;
    public int date2=30000101;
    public int date3=30000101;
    public int date0=10000101;
    String result="10000101";
    int x=0;
    int y=0;
    int z=0;
    int check=0;

    String search_word="";

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<<

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        GeneralDBHolder gdb = new GeneralDBHolder(this);

        List<General> general = gdb.getEveryone();

        KeyWordSetDBholder kdb=new KeyWordSetDBholder(this);

        /*KeyWordSetDBholder kdb=new KeyWordSetDBholder(this);//****
        List<KeyWordSet> keyWordSet = kdb.getBack(search_word);//*****/
        //List<General> general = new ArrayList<General>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), general, this));

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>>
        time =findViewById(R.id.time);
        time2 =findViewById(R.id.time2);
        time3 =findViewById(R.id.time3);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtime1(gdb, recyclerView, kdb);
            }
        });


        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtime2(gdb, recyclerView, kdb);
            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtime3(gdb, recyclerView, kdb);
            }
        });
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<<


        btn_when_up = findViewById(R.id.when_up_imageButton);
        btn_when_down= findViewById(R.id.when_down_imageButton);
        btn_deadline_up = findViewById(R.id.deadline_up_imageButton);
        btn_deadline_down= findViewById(R.id.deadline_down_imageButton);
        btn_ashcan= findViewById(R.id.ashcan_imageButton);
        btn_search= findViewById(R.id.imgBtn_search);
        et_name = findViewById(R.id.et_name);


        btn_when_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=1;
                List<General> deadline_up_sort_general = gdb.searchWord(date1, date2, date3, date0, search_word, kdb, check);
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), deadline_up_sort_general, MainActivity.this));
            }
        });
        btn_when_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=2;
                List<General> deadline_up_sort_general = gdb.searchWord(date1, date2, date3, date0, search_word, kdb, check);
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), deadline_up_sort_general,MainActivity.this));

            }
        });
        btn_deadline_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=3;
                List<General> deadline_up_sort_general = gdb.searchWord(date1, date2, date3, date0, search_word, kdb, check);
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), deadline_up_sort_general,MainActivity.this));
            }
        });
        btn_deadline_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check=4;
                List<General> deadline_up_sort_general = gdb.searchWord(date1, date2, date3, date0, search_word, kdb, check);
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), deadline_up_sort_general,MainActivity.this));
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_word=et_name.getText().toString();
                List<General> search_list = gdb.searchWord(date1, date2, date3, date0, search_word, kdb, check);
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), search_list,MainActivity.this));
            }
        });

        btn_ashcan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), general,MainActivity.this));
                time.setText(1000 + "年" + (0+1) + "月" + 1 +"日");
                time2.setText(3000 + "年" + (0+1) + "月" + 1 +"日");
                time3.setText(3000 + "年" + (0+1) + "月" + 1 +"日");
                date1=10000101;
                date2=30000101;
                date3=30000101;
                et_name.setText("");
                search_word="";
                check=0;
            }
        });
        //recover record
        Bundle record = getIntent().getBundleExtra("record");
        if(record != null){
            int record_date0 = record.getInt("date0", date0);
            int record_date1 = record.getInt("date1", date1);
            int record_date2 = record.getInt("date2", date2);
            int record_date3 = record.getInt("date3", date3);
            String record_search_word = record.getString("search_word", search_word);
            int record_check = record.getInt("check", check);
            if(record_search_word == null){
                record_search_word = "";
            }
            if(record_check == 0){
                record_check = 1;
            }
            List<General> deadline_up_sort_general = gdb.searchWord(record_date1, record_date2, record_date3, record_date0,
                    record_search_word, kdb, record_check);
            recyclerView.setAdapter(new MyAdapter(getApplicationContext(), deadline_up_sort_general,MainActivity.this));
        }

    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>>
private void showtime1(GeneralDBHolder g, RecyclerView r, KeyWordSetDBholder k) {
    Calendar calendar = Calendar.getInstance();
    final DatePickerDialog datePicker = new DatePickerDialog(this, null,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
    datePicker.setCancelable(true);
    datePicker.setCanceledOnTouchOutside(true);
    datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //确定的逻辑代码在监听中实现
                    DatePicker picker = datePicker.getDatePicker();
                    int year = picker.getYear();
                    int monthOfYear = picker.getMonth();
                    int dayOfMonth = picker.getDayOfMonth();
                    //Toast.makeText(actInput.this, String.format("%d-%d-%d", year, monthOfYear, dayOfMonth), Toast.LENGTH_LONG).show();
                    // 取得的 monthOfYear 是 Base 0 的
                    time.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth +"日");
                    x=year;
                    y=monthOfYear+1;
                    z=dayOfMonth;
                    if((y)<10){
                        if(z<10){
                            result = String.valueOf(x)+"0"+ String.valueOf(y)+"0" + String.valueOf(z);
                        }
                        else{
                            result = String.valueOf(x)+"0" + String.valueOf(y) + String.valueOf(z);
                        }
                    }
                    else{
                        if(z<10){
                            result = String.valueOf(x)+ String.valueOf(y)+"0" + String.valueOf(z);
                        }
                        else{
                            result = String.valueOf(x) + String.valueOf(y) + String.valueOf(z);
                        }
                    }
                    date1=Integer.parseInt(result);
                    List<General> search_list = g.searchWord(date1, date2, date3, date0, search_word, k, check);
                    r.setAdapter(new MyAdapter(getApplicationContext(), search_list,MainActivity.this));
                }
            });
    datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //取消什么也不用做
                }
            });
    datePicker.show();
}
    private void showtime2(GeneralDBHolder g, RecyclerView r, KeyWordSetDBholder k) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePicker = new DatePickerDialog(this, null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(true);
        datePicker.setCanceledOnTouchOutside(true);
        datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定的逻辑代码在监听中实现
                        DatePicker picker = datePicker.getDatePicker();
                        int year = picker.getYear();
                        int monthOfYear = picker.getMonth();
                        int dayOfMonth = picker.getDayOfMonth();
                        //Toast.makeText(actInput.this, String.format("%d-%d-%d", year, monthOfYear, dayOfMonth), Toast.LENGTH_LONG).show();
                        // 取得的 monthOfYear 是 Base 0 的
                        time2.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth +"日");
                        x=year;
                        y=monthOfYear+1;
                        z=dayOfMonth;
                        if((y)<10){
                            if(z<10){
                                result = String.valueOf(x)+"0"+ String.valueOf(y)+"0" + String.valueOf(z);
                            }
                            else{
                                result = String.valueOf(x)+"0" + String.valueOf(y) + String.valueOf(z);
                            }
                        }
                        else{
                            if(z<10){
                                result = String.valueOf(x)+ String.valueOf(y)+"0" + String.valueOf(z);
                            }
                            else{
                                result = String.valueOf(x) + String.valueOf(y) + String.valueOf(z);
                            }
                        }
                        date2=Integer.parseInt(result);
                        List<General> search_list = g.searchWord(date1, date2, date3, date0, search_word, k, check);
                        r.setAdapter(new MyAdapter(getApplicationContext(), search_list,MainActivity.this));
                    }
                });
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消什么也不用做
                    }
                });
        datePicker.show();
    }
    private void showtime3(GeneralDBHolder g, RecyclerView r, KeyWordSetDBholder k) {
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePicker = new DatePickerDialog(this, null,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(true);
        datePicker.setCanceledOnTouchOutside(true);
        datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定的逻辑代码在监听中实现
                        DatePicker picker = datePicker.getDatePicker();
                        int year = picker.getYear();
                        int monthOfYear = picker.getMonth();
                        int dayOfMonth = picker.getDayOfMonth();
                        //Toast.makeText(actInput.this, String.format("%d-%d-%d", year, monthOfYear, dayOfMonth), Toast.LENGTH_LONG).show();
                        // 取得的 monthOfYear 是 Base 0 的
                        time3.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth +"日");
                        x=year;
                        y=monthOfYear+1;
                        z=dayOfMonth;
                        if((y)<10){
                            if(z<10){
                                result = String.valueOf(x)+"0"+ String.valueOf(y)+"0" + String.valueOf(z);
                            }
                            else{
                                result = String.valueOf(x)+"0" + String.valueOf(y) + String.valueOf(z);
                            }
                        }
                        else{
                            if(z<10){
                                result = String.valueOf(x)+ String.valueOf(y)+"0" + String.valueOf(z);
                            }
                            else{
                                result = String.valueOf(x) + String.valueOf(y) + String.valueOf(z);
                            }
                        }
                        date3=Integer.parseInt(result);
                        /*List<General> when_calendar = g.when_getDataBetweenTimestamps(date1, date2, date0, date3);
                        r.setAdapter(new MyAdapter(getApplicationContext(), when_calendar));*/
                        List<General> search_list = g.searchWord(date1, date2, date3, date0, search_word, k, check);
                        r.setAdapter(new MyAdapter(getApplicationContext(), search_list,MainActivity.this));
                    }
                });
        datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消什么也不用做
                    }
                });
        datePicker.show();
    }
    @Override
    public void onNoteClick(int position) {
        MyAdapter adapter = (MyAdapter) recyclerView.getAdapter();
        General clickedItem = adapter.getItemAtPosition(position);
        //record bundle
        Bundle record = new Bundle();
        record.putInt("date0", date0);
        record.putInt("date1", date1);
        record.putInt("date2", date2);
        record.putInt("date3", date3);
        record.putString("search_word", search_word);
        record.putInt("check", check);
        //
        Bundle bundle = new Bundle();
        String toName = clickedItem.getName();
        bundle.putString("name", toName);
        Intent intent = new Intent(MainActivity.this, InnerActivity.class);
        intent.putExtra("nameList",bundle);
        intent.putExtra("record", record);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++<<
}