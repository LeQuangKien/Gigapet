package com.example.expandablelv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.expandablelv.Model.Category;
import com.example.expandablelv.Model.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<Category> listCate;
    private Map<Category,List<Service>> listService;
    private ExpandLVAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = findViewById(R.id.expandLV);

        listService = getListService();
        listCate = new ArrayList<>(listService.keySet());

        lvAdapter = new ExpandLVAdapter(listCate, listService);
        expandableListView.setAdapter(lvAdapter);
    }
    private Map<Category,List<Service>> getListService(){

        Map<Category, List<Service>> listMap =  new HashMap<>();
        Category category1 = new Category(1,"Category 1");
        Category category2 = new Category(2,"Category 2");
        Category category3 = new Category(3,"Category 3");

        List<Service> listS1 = new ArrayList<>();
        listS1.add(new Service(1,"Service 1"));
        listS1.add(new Service(2,"Service 2"));
        listS1.add(new Service(3,"Service 3"));

        List<Service> listS2 = new ArrayList<>();
        listS2.add(new Service(4,"Service 4"));
        listS2.add(new Service(5,"Service 5"));
        listS2.add(new Service(6,"Service 6"));

        List<Service> listS3 = new ArrayList<>();
        listS3.add(new Service(7,"Service 7"));
        listS3.add(new Service(8,"Service 8"));
        listS3.add(new Service(9,"Service 9"));

        listMap.put(category1,listS1);
        listMap.put(category2,listS2);
        listMap.put(category3,listS3);

        return listMap;
    }
}