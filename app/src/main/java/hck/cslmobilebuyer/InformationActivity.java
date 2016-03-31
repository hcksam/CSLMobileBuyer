package hck.cslmobilebuyer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity {
    Context context;
    InformationData data;
    RadioButton isMr;
    RadioButton isMs;
    EditText lastName;
    EditText firstName;
    EditText contactPhone;
    EditText emailAddr;
    EditText cCHolderName;
    EditText unitNo;
    EditText floorNo;
    EditText buildNo;
    EditText strNo;
    EditText strName;
    EditText deliveryStCatDescSelect;
    EditText areaSelectDelivery;
    EditText districtSelectDelivery;
    EditText sectionSelectDelivery;
    EditText deliveryDateDP;
    EditText timeslotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        context = this;

        isMr = (RadioButton) findViewById(R.id.isMr);
        isMs = (RadioButton) findViewById(R.id.isMs);
        lastName = (EditText) findViewById(R.id.lastName);
        firstName = (EditText) findViewById(R.id.firstName);
        contactPhone = (EditText) findViewById(R.id.contactPhone);
        emailAddr = (EditText) findViewById(R.id.emailAddr);
        cCHolderName = (EditText) findViewById(R.id.cCHolderName);
        unitNo = (EditText) findViewById(R.id.unitNo);
        floorNo = (EditText) findViewById(R.id.floorNo);
        buildNo = (EditText) findViewById(R.id.buildNo);
        strNo = (EditText) findViewById(R.id.strNo);
        strName = (EditText) findViewById(R.id.strName);
        deliveryStCatDescSelect = (EditText) findViewById(R.id.deliveryStCatDescSelect);
        areaSelectDelivery = (EditText) findViewById(R.id.areaSelectDelivery);
        districtSelectDelivery = (EditText) findViewById(R.id.districtSelectDelivery);
        sectionSelectDelivery = (EditText) findViewById(R.id.sectionSelectDelivery);
        deliveryDateDP = (EditText) findViewById(R.id.deliveryDateDP);
        timeslotList = (EditText) findViewById(R.id.timeslotList);

        data = new InformationData(context);

        setTextData();
    }

    public void setTextData(){
        InformationData placeHolder = new InformationData();

        if (data.isMr()){
            isMr.setChecked(true);
        }else{
            isMs.setChecked(true);
        }

        lastName.setText(data.getLastName());
        firstName.setText(data.getFirstName());
        contactPhone.setText(data.getContactPhone());
        emailAddr.setText(data.getEmailAddr());
        cCHolderName.setText(data.getcCHolderName());
        unitNo.setText(data.getUnitNo());
        floorNo.setText(data.getFloorNo());
        buildNo.setText(data.getBuildNo());
        strNo.setText(data.getStrNo());
        strName.setText(data.getStrName());
        deliveryStCatDescSelect.setText(data.getDeliveryStCatDescSelect());
        areaSelectDelivery.setText(data.getAreaSelectDelivery());
        districtSelectDelivery.setText(data.getDistrictSelectDelivery());
        sectionSelectDelivery.setText(data.getSectionSelectDelivery());
        deliveryDateDP.setText(data.getDeliveryDateDP());
        timeslotList.setText(data.getTimeslotList());

//        lastName.setHint(placeHolder.getLastName());
//        firstName.setHint(placeHolder.getFirstName());
//        contactPhone.setHint(placeHolder.getContactPhone());
//        emailAddr.setHint(placeHolder.getEmailAddr());
//        cCHolderName.setHint(placeHolder.getcCHolderName());
//        unitNo.setHint(placeHolder.getUnitNo());
//        floorNo.setHint(placeHolder.getFloorNo());
//        buildNo.setHint(placeHolder.getBuildNo());
//        strNo.setHint(placeHolder.getStrNo());
//        strName.setHint(placeHolder.getStrName());
//        deliveryStCatDescSelect.setHint(placeHolder.getDeliveryStCatDescSelect());
//        areaSelectDelivery.setHint(placeHolder.getAreaSelectDelivery());
//        districtSelectDelivery.setHint(placeHolder.getDistrictSelectDelivery());
//        sectionSelectDelivery.setHint(placeHolder.getSectionSelectDelivery());
//        deliveryDateDP.setHint(placeHolder.getDeliveryDateDP());
//        timeslotList.setHint(placeHolder.getTimeslotList());
    }

    public void setData(){
        int c = 0;
        data.getData().set(c++, String.valueOf(isMr.isChecked()));
        data.getData().set(c++, lastName.getText().toString());
        data.getData().set(c++, firstName.getText().toString());
        data.getData().set(c++, contactPhone.getText().toString());
        data.getData().set(c++, emailAddr.getText().toString());
        data.getData().set(c++, cCHolderName.getText().toString());
        data.getData().set(c++, unitNo.getText().toString());
        data.getData().set(c++, floorNo.getText().toString());
        data.getData().set(c++, buildNo.getText().toString());
        data.getData().set(c++, strNo.getText().toString());
        data.getData().set(c++, strName.getText().toString());
        data.getData().set(c++, deliveryStCatDescSelect.getText().toString());
        data.getData().set(c++, areaSelectDelivery.getText().toString());
        data.getData().set(c++, districtSelectDelivery.getText().toString());
        data.getData().set(c++, sectionSelectDelivery.getText().toString());
        data.getData().set(c++, deliveryDateDP.getText().toString());
        data.getData().set(c++, timeslotList.getText().toString());

        data.setAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        String[] menuItems = {"Not Save", "Reset"};

        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }

//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch(id) {
            case 0:
                finish();
                return true;
            case 1:
                data.setDataDefault();
                setTextData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setData();
        boolean done = data.saveData();

        if (!done){
            Toast.makeText(context, "Save data error!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }

        super.onBackPressed();
    }

//    public void onRadioButtonClicked(View view) {
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.isMr:
//                if (checked)
//                    data.setIsMr(true);
//                    break;
//            case R.id.isMs:
//                if (checked)
//                    data.setIsMr(false);
//                    break;
//        }
//    }
}
