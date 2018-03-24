package com.example.fullsuper.danhba;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import customlistview.adapter.ContactAdapter;
import customlistview.model.Contact;

public class MainActivity extends AppCompatActivity {

    private ContactAdapter adapter;
    private List<Contact> arrayContact;
    private EditText edtName;

    private  EditText edtNumber;
    private TextView txtSexsua;
    private RadioButton rbtMale;
    private  RadioButton rbtFemale;
    private Button btnAddContact;
    private ListView lvContact;
    private ImageButton btnShowAddContact;
    private LinearLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this,R.layout.items_contact,arrayContact);

        lvContact.setAdapter(adapter);
        checkAndRequestPermissions();
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogConfirm(position);
//                Intent itent = new Intent(MainActivity.this, MainActivityA.class);
//                startActivity(itent);
            }
        });
    }
    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    private void intentSendMesseage(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    private void intentCall(int position) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + arrayContact.get(position).getmNumber()));
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
    }
    public void setWidget(){

        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        rbtMale = (RadioButton) findViewById(R.id.rbtn_male);
        rbtFemale = (RadioButton) findViewById(R.id.rbtn_female);
        btnAddContact = (Button) findViewById(R.id.btn_add_contact);
        lvContact = (ListView) findViewById(R.id.lv_contact);
        btnShowAddContact = (ImageButton) findViewById(R.id.showAddContact);
        txtSexsua = (TextView) findViewById(R.id.txtSexua);
        frame = (LinearLayout) findViewById(R.id.frame);
    }
    public void addContact(View view){
        if(view.getId() == R.id.btn_add_contact)
        {
            String name = edtName.getText().toString();
            String number = edtNumber.getText().toString().trim();
            boolean ismale = false;
            if (rbtMale.isChecked())
                ismale = true;
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number))
            {
                Toast.makeText(this, "Nhập đủ tên, số điện thoại" + name +number, Toast.LENGTH_SHORT).show();
            }
            else
            {
                Contact contact = new Contact(ismale,name,number);
                arrayContact.add(contact);
              //  frame.setVisibility(View.INVISIBLE);
                ViewGroup.LayoutParams params = frame.getLayoutParams();
                params.height = 0;
                frame.setLayoutParams(params);

            }
            adapter.notifyDataSetChanged();

        }
    }
    public  void showAddContact(View view)
    {
        ViewGroup.LayoutParams params = frame.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        frame.setLayoutParams(params);
    }
    public void showDialogConfirm(final int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        Button btnCall = (Button) dialog.findViewById(R.id.btn_call);
        Button btnSendMessage = (Button) dialog.findViewById(R.id.btn_send_message);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(position);
            }
        });
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentSendMesseage(position);
            }
        });
        dialog.show();

    }
}
