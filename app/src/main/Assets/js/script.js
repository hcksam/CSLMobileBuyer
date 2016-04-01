function saveProfile(){
	var isMr;
	if ($('input[value=Mr]').prop('checked') == true){
		isMr = 'true';
	}else{
		isMr = 'false';
	}
	var lastName = $('#lastName').val();
	var firstName = $('#firstName').val();
	var contactPhone = $('#contactPhone').val();
	var emailAddr = $('#emailAddr').val();
	var cCHolderName = $('#cCHolderName').val();
	var unitNo = $('#unitNo').val();
	var floorNo = $('#floorNo').val();
	var buildNo = $('#buildNo').val();
	var strNo = $('#strNo').val();
	var strName = $('#strName').val();
	var deliveryStCatDescSelect = $('#deliveryStCatDescSelect').val();
	var areaSelectDelivery = $('#areaSelectDelivery').val();
	var districtSelectDelivery = $('#districtSelectDelivery').val();
	var sectionSelectDelivery = $('#sectionSelectDelivery').val();
	var deliveryDateDP = $('#deliveryDateDP').val();
	var timeslotList = $('#timeslotList').val();
	Android.setFormData(isMr, lastName, firstName, contactPhone, emailAddr, cCHolderName, unitNo, floorNo, buildNo, strNo, strName, deliveryStCatDescSelect, areaSelectDelivery, districtSelectDelivery, sectionSelectDelivery, deliveryDateDP, timeslotList);
};