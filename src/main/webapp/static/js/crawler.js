
var checkSum=0;
var totalSum=0;
function loadImageList(){
	if(checkValidation()){
		processDim.showPleaseWait();
		$("#vw_url").html($("#url").val());
		$("#vw_status").html("크롤링중..");
		$("#status_bar").removeClass("bg-green");
		$("#status_bar").addClass("bg-purple");

		var param = {};
		param.url = encodeURIComponent($("#url").val());

		var request_type = "POST";
		var content_type = null;
		var return_data_type = 'json';
		var call_url = contextPath+"/rest/crawler/load/image";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			$("#vw_start").html(data.start_date);
			$("#vw_end").html(data.end_date);

			var ths = $("#table1 > thead > tr > th");
			var tbody = $("#table1 > tbody");
			tbody.empty();
			if(data.imageList!=null && data.imageList.length>0){
				$("#vw_end").html("");
				checkSum=0;
				totalSum=data.imageList.length;
				$("#vw_total").html(totalSum);
				$.each(data.imageList, function( index, value ) {
					value.idx=index+1;
					value.status="wait";
					value.file_name=value.src.substring(value.src.lastIndexOf("/")+1);
					tbody.append(addTableData(ths, value, index, params.page, "", null, null));
					downloadImage(index+1, data.uuid, value.src);
				});
			}else{
				tbody.html("<tr><td colspan='5'>Empty.</td></tr>");
			}
			processDim.hidePleaseWait();
		});
	}
}

function downloadImage(idx, uuid, img_url){
	if(img_url!=null && img_url!=""){
		var param = {};
		param.image_url = encodeURIComponent(img_url);
		param.uuid = uuid;

		var request_type = "POST";
		var content_type = null;
		var return_data_type = 'json';
		var call_url = contextPath+"/rest/crawler/save/image";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			var result = "error.";
			var td = $("#table1 > tbody > tr")[idx-1].childNodes[5];
			if(data.imageList!=null && data.imageList.length>0){
				result = "complete";
			}
			td.innerHTML=result;
			checkSum++;
			if(checkSum==totalSum){
				$("#vw_end").html(data.end_date);
				$("#vw_status").html("크롤링 완료.");
				$("#status_bar").removeClass("bg-purple");
				$("#status_bar").addClass("bg-green");
			}
		});
	}
}

function checkValidation(){
	var url = $("#url");
	if(url.val()=="" || !regUrlType(url.val())){
		if(url.val()!=""){
			url.val("");
		}
		url.parent().find(".invalid-feedback").show();
		url.focus();
		return false;
	}
	url.parent().find(".invalid-feedback").hide();
	
	return true;
}

function regUrlType(data) {
	var regex = /^(((http(s?))\:\/\/)?)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?/;
	return regex.test(data);
}

function initBtn(){
	$("#btn_send").click(function(){loadImageList();});
}

function initElement(){
	$("#url").blur(function(){checkValidation();});
	$("#url").keyup(function(e){if(e.keyCode==13){loadImageList();}});
}

$(document).ready(function() {
	var menu_active_idx=1;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
});