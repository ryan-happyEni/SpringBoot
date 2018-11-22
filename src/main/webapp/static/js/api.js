

function initValue(){
}

function initBtn(){
	$("#btn_api1").click(function(){
		processDim.showPleaseWait();

		var param = {};
		var request_type = "POST";
		var content_type = null;
		var return_data_type = 'json';
		var call_url = contextPath+"/prereservation/enroll/"+$("#api1_p1").val()+"/"+$("#api1_p2").val();
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			$("#api1_result").html("result="+data.result);
			processDim.hidePleaseWait();
		});
	});

	$("#btn_api2").click(function(){
		processDim.showPleaseWait();

		var param = {};
		var request_type = "POST";
		var content_type = null;
		var return_data_type = 'json';
		var call_url = contextPath+"/prereservation/count/"+$("#api2_p1").val();
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			$("#api2_result").html("cnt="+data.cnt);
			processDim.hidePleaseWait();
		});
	});
	$("#btn_api3").click(function(){
		processDim.showPleaseWait();

		var param = {};
		var request_type = "POST";
		var content_type = null;
		var return_data_type = 'json';
		var call_url = contextPath+"/prereservation/check/"+$("#api3_p1").val()+"/"+$("#api3_p2").val();
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			$("#api3_result").html("result="+data.result);
			processDim.hidePleaseWait();
		});
	});
}

var requiredEl = [];
function initElement(){
}

$(document).ready(function() {
	var menu_active_idx=5;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
});