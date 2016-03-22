package hck.cslmobilebuyer;

/**
 * Created by hck on 21/3/2016.
 */
public class InformationData {
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
    
    public InformationData(){
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
