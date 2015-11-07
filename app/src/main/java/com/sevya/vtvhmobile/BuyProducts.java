package com.sevya.vtvhmobile;

/**
 * Created by abhinaym on 24/10/15.
 */
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sevya.vtvhmobile.db.DataBaseAdapter;
import com.sevya.vtvhmobile.models.CartModel;
import com.sevya.vtvhmobile.models.ProductsInfo;
import com.sevya.vtvhmobile.models.ResponseStatus;
import com.sevya.vtvhmobile.models.UserModel;
import com.sevya.vtvhmobile.util.SOAPServices;
import com.sevya.vtvhmobile.webservices.SOAPServiceClient;
import com.sevya.vtvhmobile.webservices.ServiceParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyProducts extends Activity  implements OnTouchListener {

    private Spinner spinner1, spinner2,spinner3;
    private Button btnSubmit;

    TextView dname;
    TextView dnum;
    EditText qty;
    EditText cprice;
    TextView totalPrice;
    AutoCompleteTextView autotv;
    int i;
    String date;
    ImageButton modelimagebutton;
    ImageButton qtyimagebutton;
    ImageButton priceimagebutton;
    Thread thread;
    ResponseStatus status;
    CartModel cartModel;
    JSONArray array;
    String actId;

    DataBaseAdapter dataBaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropdown);
        addItemsOnSpinner1();
        addListenerOnButton();


        Date pdate=new Date();
         date = new SimpleDateFormat("yyyy-MM-dd").format(pdate);


        dataBaseHelper=new DataBaseAdapter(this);

        Intent intent=getIntent();

        String name=intent.getStringExtra("cname");
        String num=intent.getStringExtra("cnum");
        actId=intent.getStringExtra("actId");
        i=intent.getIntExtra("listsize",0);

        Log.d("bac",""+actId);

        dname=(TextView)findViewById(R.id.dname);
        dnum=(TextView)findViewById(R.id.dnum);
        qty=(EditText)findViewById(R.id.edit_text);
        cprice=(EditText)findViewById(R.id.price);
        totalPrice=(TextView)findViewById(R.id.totalprice);
        autotv=(AutoCompleteTextView)findViewById(R.id.autoTv);
        autotv.requestFocus();
        modelimagebutton=(ImageButton)findViewById(R.id.modelimagebutton);
        qtyimagebutton=(ImageButton)findViewById(R.id.cbqty);
        priceimagebutton=(ImageButton)findViewById(R.id.cbup);

        modelimagebutton.setOnTouchListener(this);
        qtyimagebutton.setOnTouchListener(this);
        priceimagebutton.setOnTouchListener(this);
        autotv.setOnTouchListener(this);
        qty.setOnTouchListener(this);
        cprice.setOnTouchListener(this);


        dname.setText(name);
        dnum.setText(num);

        String items[]={"CR1223","BR1226","AB19I4RT5","EB19RT5","R4S5UIA","D5TY6H","P0O9KHA","T78U7JK","X4RXFV","M87RU46","JH8I9","Q7UW3W","W39OS8","Y67UI9",

                "H78UIA","S67Y6U","O9REFR","PKIOE34","U56YYH","VUHY76","ZXVF6","L90O8SS","IK6Y3W","GHT67S","NR5T7W","BT6YDJDK","kT9YI5","FTFYI44"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(BuyProducts.this,android.R.layout.simple_list_item_1,items);
        autotv.setAdapter(adapter);


        autotv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                //TODO: set focus on next view

                qty.setFocusableInTouchMode(true);
                qty.requestFocus();
            }
        });





        onButtonClick();

    }


    public void cancel(View v)
    {
        ButtonAnimation.animation(v);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                BuyProducts.this);

        // set title
        alertDialogBuilder.setTitle("Alert");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you want to cancel ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (i != 0) {
                            Intent intent = new Intent(BuyProducts.this, CartActivity.class);
                            intent.putExtra("cname", dname.getText().toString());
                            intent.putExtra("cnum", dnum.getText().toString());
                            intent.putExtra("Date",date);
                            startActivity(intent);
                        } else {
                            Intent intent=new Intent(BuyProducts.this,MainActivity.class);
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


    }
    public void onButtonClick() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {

                                             ButtonAnimation.animation(view);
                                             ProductsInfo productsInfo=new ProductsInfo();
                                             productsInfo.setName(dname.getText().toString());
                                             productsInfo.setNumber(dnum.getText().toString());
                                             productsInfo.setModelNo(autotv.getText().toString());
                                             productsInfo.setPrice(cprice.getText().toString());
                                             productsInfo.setQty(qty.getText().toString());


                                             if((autotv.getText().toString().length()==0))
                                                 autotv.setError("Please enter model no");
                                             else if(qty.getText().toString().length()==0)
                                                 qty.setError("Please enter Qty");
                                             else if(cprice.getText().toString().length()==0)
                                                 cprice.setError("Please enter UnitPrice");
                                             else {
                                                 int p=Integer.parseInt(cprice.getText().toString());
                                                 int q=Integer.parseInt(qty.getText().toString());
                                                 String tp=""+(p*q);
                                                 productsInfo.setTotalPrice(tp);


                                                long id = dataBaseHelper.insertDataItems(productsInfo);

                                                 Intent intent = new Intent(BuyProducts.this, CartActivity.class);
                                                 intent.putExtra("cname", dname.getText().toString());
                                                 intent.putExtra("cnum", dnum.getText().toString());
                                                 intent.putExtra("Date", date);
                                                 intent.putExtra("actId",actId);
                                                 startActivity(intent);


                                             }

                                         }
                                     }
        );


    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.stock_spinner);
        List<String> list = new ArrayList<String>();
        list.add("Select Stock Point");
        list.add("sp 1");
        list.add("sp 2");
        list.add("sp 3");
        list.add("sp 4");
        list.add("sp 5");
        list.add("sp 6");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);


    }

    // get the selected dropdown list value
    public void addListenerOnButton() {


        btnSubmit = (Button) findViewById(R.id.button_done);
        qty=(EditText) findViewById(R.id.edit_text);

        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(BuyProducts.this,
                        "Items Selected : " +
                                "\nCategory : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nManufacturer : "+ String.valueOf(spinner2.getSelectedItem()) +
                                "\nModel ID: " + String.valueOf(spinner3.getSelectedItem()) +
                                "\nQty: "+ qty.getText().toString() ,

                        Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.autoTv:
                modelimagebutton.setVisibility(View.VISIBLE);
                qtyimagebutton.setVisibility(View.INVISIBLE);
                priceimagebutton.setVisibility(View.INVISIBLE);
                break;

            case R.id.modelimagebutton:
                autotv.setText("");
                break;
            case R.id.edit_text:
                modelimagebutton.setVisibility(View.INVISIBLE);
                qtyimagebutton.setVisibility(View.VISIBLE);
                priceimagebutton.setVisibility(View.INVISIBLE);
                break;

            case R.id.cbqty:
                qty.setText("");
                break;

            case R.id.price:
                modelimagebutton.setVisibility(View.INVISIBLE);
                qtyimagebutton.setVisibility(View.INVISIBLE);
                priceimagebutton.setVisibility(View.VISIBLE);
                break;

            case R.id.cbup:
                cprice.setText("");
                break;


        }



        return false;

    }
}