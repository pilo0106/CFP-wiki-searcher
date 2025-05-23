package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Content_package.ContentText;
import com.example.myapplication.Content_package.ContentTextDBholder;
import com.example.myapplication.General_package.General;
import com.example.myapplication.General_package.GeneralDBHolder;
import com.example.myapplication.KeyWord_package.KeyWordSet;
import com.example.myapplication.KeyWord_package.KeyWordSetDBholder;
import android.text.util.Linkify;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;

import java.util.ArrayList;
import java.util.List;

public class InnerActivity extends AppCompatActivity {
    TextView title, link, notiD, finalD, deadline, article, similarTopic, similarTopicDescribe;
    String name = "error";
    ArrayList<Integer> previousSection = new ArrayList<>();
    private Bundle record;
    int number;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inside);

        title = findViewById(R.id.title_textView);
        link = findViewById(R.id.link_textView);
        notiD = findViewById(R.id.notiDate);
        finalD = findViewById(R.id.finalDate);
        deadline = findViewById(R.id.SD_Date);
        //tag = findViewById(R.id.tag_textView);
        article = findViewById(R.id.article_textView);

        Bundle bundle = getIntent().getBundleExtra("nameList");
        record = getIntent().getBundleExtra("record");
        Bundle previous = getIntent().getBundleExtra("previous");
        if(bundle != null){
            name = bundle.getString("name");
        }
        else if(previous != null){
            previousSection = previous.getIntegerArrayList("ArrayList");
            name = previous.getString("name");
        }

        title.setText(name);
        GeneralDBHolder Gdb = new GeneralDBHolder(this);
        ContentTextDBholder Cdb = new ContentTextDBholder(this);
        KeyWordSetDBholder Kdb = new KeyWordSetDBholder(this);
        General general = Gdb.getContent(name);
        ContentText contentText = Cdb.getContent(name);
        List<String> keywordList = Kdb.getContent(name);
        String url = contentText.geturl();
        String htmlText = "<a href='" + url + "'>" + url + "</a>";
        link.setText(Html.fromHtml(htmlText));
        Linkify.addLinks(link, Linkify.WEB_URLS);
        notiD.setText(Integer.toString(contentText.getNotiDue()));
        finalD.setText(Integer.toString(contentText.getFvDue()));
        deadline.setText(Integer.toString(general.getDeadline()));
/*
        StringBuilder allValues = new StringBuilder();
        for (String value : keywordList) {
            allValues.append(value).append(",   ");
        }
        tag.setText(allValues.toString());

 */
        article.setText(contentText.getContent());
        previousSection.add(general.getId());
        SimilarFunction similarFunction = new SimilarFunction(general.getId() - 1);
        for (Integer item : previousSection){
            similarFunction.addItem(item);
        }
        similarFunction.Structure();
        int id = similarFunction.findNext();
        General toNext = Gdb.getContentById(id);
        similarTopic = findViewById(R.id.similarTopicName);
        similarTopicDescribe = findViewById(R.id.similarTopicName_describe);
        String nextName = toNext.getName();
        similarTopic.setText(nextName);
        String fullText = toNext.getBrief();
        SpannableString spannableString = new SpannableString(toNext.getBrief());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putInt("task", 1);
                bundle.putString("name",nextName);
                bundle.putIntegerArrayList("ArrayList", previousSection);
                Intent intent = new Intent(InnerActivity.this, InnerActivity.class);
                intent.putExtra("previous",bundle);
                intent.putExtra("record", record);
                startActivity(intent);
                finish();
            }
        };
        // 將 ClickableSpan 附加到 SpannableString 的範圍中
        spannableString.setSpan(clickableSpan, 0, fullText.length(), 0);
        // 將 SpannableString 設置到 TextView
        similarTopicDescribe.setText(spannableString);
        // 設置 TextView 允許點擊
        similarTopicDescribe.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public void return_btn(View view){
        Bundle back = new Bundle();
        Intent intent = new Intent(InnerActivity.this, MainActivity.class);
        intent.putExtra("back", back);
        intent.putExtra("record", record);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
    }
}
