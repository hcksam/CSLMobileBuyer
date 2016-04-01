package hck.cslmobilebuyer.common;

import android.content.Context;

/**
 * Created by hck on 2/4/2016.
 */
public class JavaScriptLib {
    public static String forward(String url){
        String javaScript = "window.location.href = '"+url+"';";
        return javaScript;
    }

    public static String checkInventory(String colorVar){
        String javaScript = "";
        javaScript += "var phone = $('input[type=radio][name=color][value="+colorVar+"]');";
        javaScript += "var index = 0;";
        javaScript += "if (phone.is(':disabled')){";
        javaScript += "index = -1;";
        javaScript += "Android.OOS();";
        javaScript += "}else{";
        javaScript += "index = 1;";
        javaScript += "}";
        return javaScript;
    }

    public static String selectPhone(String colorVar){
        String javaScript = "";
        javaScript += "if (index > 0){";

        javaScript += "var color = $('input[type=radio][name=color]');";
        javaScript += "var price = $('input[type=radio][name=promoTypeId]');";

        javaScript += "color.each(function( index ) {";
        javaScript += "if ($(this).val() !=  "+colorVar+"){";
        javaScript += "$(this).prop('checked', false);";
        javaScript += "}else{";
        javaScript += "$(this).prop('checked', true);";
        javaScript += "}";
        javaScript += "});";

        javaScript += "price.filter('[value=0]').prop('checked', false);";
        javaScript += "price.filter('[value=1]').prop('checked', true);";

        javaScript += "}";

        return javaScript;
    }

    public static String selectPhoneSubmit(){
        String javaScript = "";
        javaScript += "if (index > 0){";

        javaScript += "$('#sahsButton').click();";
        javaScript += "var checkoutInterval = null;";

        javaScript += "var promoInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-promoCode').parent().css('display') != 'none'){";
        javaScript += "$('#promoCode').val('SPECIAL');";
        javaScript += "$('#submitButton').click();";
        javaScript += "clearInterval(promoInterval);";
        javaScript += "}";
        javaScript += "},2000);";

        javaScript += "checkoutInterval = setInterval(function () {";
        javaScript += "if ($('#dialog-cart').parent().css('display') != 'none'){";
        javaScript += "$('#checkoutButton').click();";
        javaScript += "clearInterval(checkoutInterval);";
        javaScript += "}";
        javaScript += "},2000);";

        javaScript += "}";
        return javaScript;
    }

    public static String inputInformation(Context context){
        InformationData data = new InformationData(context);
        String javaScript = "";

        javaScript += "$('input[value="+((data.isMr())? "Mr":"Ms")+"]').prop('checked', true);";
        javaScript += "$('#lastName').val('" +data.getLastName()+"');";
        javaScript += "$('#firstName').val('" + data.getFirstName()+"');";
        javaScript += "$('#contactPhone').val('"+data.getContactPhone()+"');";
        javaScript += "$('#emailAddr').val('" + data.getEmailAddr()+"');";
        javaScript += "$('#confirmEmailAddr').val('" + data.getEmailAddr()+"');";
        javaScript += "$('#cCHolderName').val('"+data.getcCHolderName()+"');";
        javaScript += "$('#unitNo').val('"+data.getUnitNo()+"');";
        javaScript += "$('#floorNo').val('"+data.getFloorNo()+"');";
        javaScript += "$('#buildNo').val('"+data.getBuildNo()+"');";
        javaScript += "$('#strNo').val('"+data.getStrNo()+"');";
        javaScript += "$('#strName').val('"+data.getStrName()+"');";

        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";

        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";

        javaScript += "$('#sectionSelectDelivery').val('"+ data.getSectionSelectDelivery()+"');";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList()+"');";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";

        javaScript += "var StCatInterval = setInterval(function () {";
        javaScript += "if ($('#deliveryStCatDescSelect').val() != '"+data.getDeliveryStCatDescSelect()+"'){";
        javaScript += "$('#deliveryStCatDescSelect').val('"+data.getDeliveryStCatDescSelect()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(StCatInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var areaInterval = setInterval(function () {";
        javaScript += "if ($('#areaSelectDelivery').val() != '"+data.getAreaSelectDelivery()+"'){";
        javaScript += "$('#areaSelectDelivery').val('"+data.getAreaSelectDelivery()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(areaInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var districtInterval = setInterval(function () {";
        javaScript += "if ($('#districtSelectDelivery').val()!= '"+data.getDistrictSelectDelivery()+"'){";
        javaScript += "$('#districtSelectDelivery').val('"+data.getDistrictSelectDelivery()+"').change();";
        javaScript += "}else{";
        javaScript += "clearInterval(districtInterval);";
        javaScript += "}";
        javaScript += "},1000);";

        javaScript += "var sectionInterval = setInterval(function () {";
        javaScript += "if ($('#sectionSelectDelivery').val()!= '"+data.getSectionSelectDelivery()+"' || $('#timeslotList').val() != '"+data.getTimeslotList()+"'){";
        javaScript += "$('#sectionSelectDelivery').val('"+data.getSectionSelectDelivery()+"').change();";
        javaScript += "$('#deliveryDateDP').val('"+data.getDeliveryDateDP()+"');";
        javaScript += "$('#timeslotList').val('"+data.getTimeslotList() +"').change();";
        javaScript += "$('input[type=checkbox]').prop('checked', true);";
        javaScript += "}else{";
        javaScript += "clearInterval(sectionInterval);";
        javaScript += "$('button[name=submit]').focus();";
        javaScript += "Android.hideLoading();";
        javaScript += "}";
        javaScript += "},1000);";

        return javaScript;
    }

    public static String addSaveProfileDataButton(){
        String javaScript = "";

        javaScript += "$('button[name=submit]').parent().append('<button type=\"button\" id=\"androidSaveProfile\" name=\"androidSaveProfile\" class=\"blackbtn\" style=\"float: right\" onclick=\"saveProfile()\" >Android Save Profile Data</button>');";

        return javaScript;
    }

    public static String conformInformation(){
        String javaScript = "";

        javaScript += "$('#confirmed').prop('checked', true);";
        javaScript += "$('#captchaInput').focus();";
        javaScript += "Android.hideLoading();";

        return javaScript;
    }
}
