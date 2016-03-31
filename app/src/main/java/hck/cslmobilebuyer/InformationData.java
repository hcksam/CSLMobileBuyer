package hck.cslmobilebuyer;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by hck on 21/3/2016.
 */
public class InformationData {
    public final static int DataCount = 17;

    private boolean isMr;
    private String lastName;
    private String firstName;
    private String contactPhone;
    private String emailAddr;
    private String cCHolderName;
    private String unitNo;
    private String floorNo;
    private String buildNo;
    private String strNo;
    private String strName;
    private String deliveryStCatDescSelect;
    private String areaSelectDelivery;
    private String districtSelectDelivery;
    private String sectionSelectDelivery;
    private String deliveryDateDP;
    private String timeslotList;

    private ArrayList<String> data;
    private SettingHandler settingHandler;

    public InformationData(){
        setDataDefault();
    }

    public InformationData(Context context){
        settingHandler = new SettingHandler(context.getFilesDir());

        if (!settingHandler.getFile().exists() || settingHandler.readFile().size()<DataCount){
            setDataDefault();
            saveData();
        }else{
            data = settingHandler.readFile();
            setAllData();
        }
    }

    public void setAllData(){
        int c = 0;
        isMr = data.get(c++).equalsIgnoreCase("true");
        lastName = data.get(c++);
        firstName = data.get(c++);
        contactPhone = data.get(c++);
        emailAddr = data.get(c++);
        cCHolderName = data.get(c++);
        unitNo = data.get(c++);
        floorNo = data.get(c++);
        buildNo = data.get(c++);
        strNo = data.get(c++);
        strName = data.get(c++);
        deliveryStCatDescSelect = data.get(c++);
        areaSelectDelivery = data.get(c++);
        districtSelectDelivery = data.get(c++);
        sectionSelectDelivery = data.get(c++);
        deliveryDateDP = data.get(c++);
        timeslotList = data.get(c++);
    }

    public boolean saveData(){
        if (data == null || data.size() < DataCount){
            data = new ArrayList<>();
            for (int i=0;i<DataCount;i++){
                data.add("");
            }
        }

        int c = 0;
        data.set(c++, String.valueOf(isMr));
        data.set(c++, lastName);
        data.set(c++, firstName);
        data.set(c++, contactPhone);
        data.set(c++, emailAddr);
        data.set(c++, cCHolderName);
        data.set(c++, unitNo);
        data.set(c++, floorNo);
        data.set(c++, buildNo);
        data.set(c++, strNo);
        data.set(c++, strName);
        data.set(c++, deliveryStCatDescSelect);
        data.set(c++, areaSelectDelivery);
        data.set(c++, districtSelectDelivery);
        data.set(c++, sectionSelectDelivery);
        data.set(c++, deliveryDateDP);
        data.set(c++, timeslotList);

        if (settingHandler != null){
            return settingHandler.writeFile(data);
        }else{
            return false;
        }
    }
    
    public void setDataDefault(){
        isMr = true;
        lastName = "CHAN";
        firstName = "TAI MAN";
        contactPhone = "51300568";
        emailAddr = "chy@ibbs.hk";
        cCHolderName = "CHAN TAI MAN";
        unitNo = "A";
        floorNo = "1";
        buildNo = "";
        strNo = "123";
        strName = "HK";
        deliveryStCatDescSelect = "ST";
        areaSelectDelivery = "HK";
        districtSelectDelivery = "124";
        sectionSelectDelivery = "ZZZZ";
        deliveryDateDP = "01/04/2016";
        timeslotList = "AM1";
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public boolean isMr() {
        return isMr;
    }

    public void setIsMr(boolean isMr) {
        this.isMr = isMr;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getcCHolderName() {
        return cCHolderName;
    }

    public void setcCHolderName(String cCHolderName) {
        this.cCHolderName = cCHolderName;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(String floorNo) {
        this.floorNo = floorNo;
    }

    public String getBuildNo() {
        return buildNo;
    }

    public void setBuildNo(String buildNo) {
        this.buildNo = buildNo;
    }

    public String getStrNo() {
        return strNo;
    }

    public void setStrNo(String strNo) {
        this.strNo = strNo;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getDeliveryStCatDescSelect() {
        return deliveryStCatDescSelect;
    }

    public void setDeliveryStCatDescSelect(String deliveryStCatDescSelect) {
        this.deliveryStCatDescSelect = deliveryStCatDescSelect;
    }

    public String getAreaSelectDelivery() {
        return areaSelectDelivery;
    }

    public void setAreaSelectDelivery(String areaSelectDelivery) {
        this.areaSelectDelivery = areaSelectDelivery;
    }

    public String getDistrictSelectDelivery() {
        return districtSelectDelivery;
    }

    public void setDistrictSelectDelivery(String districtSelectDelivery) {
        this.districtSelectDelivery = districtSelectDelivery;
    }

    public String getSectionSelectDelivery() {
        return sectionSelectDelivery;
    }

    public void setSectionSelectDelivery(String sectionSelectDelivery) {
        this.sectionSelectDelivery = sectionSelectDelivery;
    }

    public String getDeliveryDateDP() {
        return deliveryDateDP;
    }

    public void setDeliveryDateDP(String deliveryDateDP) {
        this.deliveryDateDP = deliveryDateDP;
    }

    public String getTimeslotList() {
        return timeslotList;
    }

    public void setTimeslotList(String timeslotList) {
        this.timeslotList = timeslotList;
    }
}
