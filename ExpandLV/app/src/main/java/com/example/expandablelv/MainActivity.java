package com.example.expandablelv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.expandablelv.Adapter.ExpandLVCheckBox;
import com.example.expandablelv.Adapter.PaymentAdapter;
import com.example.expandablelv.Adapter.PetnameAdapter;
import com.example.expandablelv.Model.Payment;
import com.example.expandablelv.Model.PetName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    /*
    Phần khai báo cho adapter category
     */
    ExpandLVCheckBox listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listCategory;
    HashMap<String, List<String>> listService;
    /*
    Phần khai báo cho date and time
     */
    private TextView date_input;
    private TextView time_input;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    /*
    Phần khai báo cho adapter của spinner chọn pet name và payment
     */
    private Spinner spinnerPetName;
    private PetnameAdapter petnameAdapter;
    private Spinner spinnerPayment;
    private PaymentAdapter paymentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPetName = findViewById(R.id.spnPetName);
        petnameAdapter = new PetnameAdapter(this,R.layout.layout_selected_drop_down,getListName());
        spinnerPetName.setAdapter(petnameAdapter);

        spinnerPayment = findViewById(R.id.spnPayment);
        paymentAdapter = new PaymentAdapter(this,R.layout.layout_selected_drop_down,getListPay());
        spinnerPayment.setAdapter(paymentAdapter);

        expListView = (ExpandableListView) findViewById(R.id.expandLV);
        prepareListData();
        listAdapter = new ExpandLVCheckBox(this, listCategory, listService);
        expListView.setAdapter(listAdapter);

        date_input = findViewById(R.id.date_text);
        time_input = findViewById(R.id.time_text);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
    /*
        Hàm set date
    */
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLUE));
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day + "/" + month + "/" + year;
                date_input.setText(date);
            }
        };
    /*
        Hàm set time
    */
        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,timeSetListener,hour,minute,true
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });
        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = hour + ":" + minute;
                time_input.setText(time);
            }
        };

    /*
        Hàm của spinner petname và payment
    */
        spinnerPetName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, petnameAdapter.getItem(position).getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, paymentAdapter.getItem(position).getPayment(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    /*
        Hàm của expandable listview checkbox
    */
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listCategory.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listCategory.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listCategory.get(groupPosition)
                                + " : "
                                + listService.get(
                                listCategory.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

    /*
       Phần code tham khảo, nôm na là ấn button thì hiện ra đã ấn vào bao nhiêu cái checkbox
    */
//
//        Button button = (Button) findViewById(R.id.button);
//        final TextView textView = (TextView) findViewById(R.id.textView);
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                int count =0;
//                for(int mGroupPosition =0; mGroupPosition < listAdapter.getGroupCount(); mGroupPosition++)
//                {
//                    count = count +  listAdapter.getNumberOfCheckedItemsInGroup(mGroupPosition);
//                }
//                textView.setText(""+count);
//            }
//        });
//


    }
    private void prepareListData() {
        listCategory = new ArrayList<String>();
        listService = new HashMap<String, List<String>>();
        listCategory.add("Category");
        List<String> list = new ArrayList<String>();
        list.add("Pet Care");
        list.add("Pet Hotel");
        list.add("Pet Spa");
        list.add("Pet Bounding");
        list.add("Pet Fashion");
        list.add("Pet Gym");
        list.add("Pet Meal");
        listService.put(listCategory.get(0), list);
    }

    private List<PetName> getListName(){
        List<PetName> listN = new ArrayList<>();
        listN.add(new PetName("Pet's Name"));
        listN.add(new PetName("Alexander III. Pudding"));
        listN.add(new PetName("Cheems"));
        listN.add(new PetName("Nasus"));
        listN.add(new PetName("Yuumi"));
        return listN;
    }

    private List<Payment> getListPay(){
        List<Payment> listP = new ArrayList<>();
        listP.add(new Payment("Payment"));
        listP.add(new Payment("Banking"));
        listP.add(new Payment("Cash"));
        return listP;
    }
}