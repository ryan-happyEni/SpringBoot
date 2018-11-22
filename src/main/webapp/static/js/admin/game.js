
var totalSum=0;
var selectedOrderKey=null;
var selectedOrderBy=null;
function loadList(pageNum, orderKey){
	processDim.showPleaseWait();

	var param = {};
	param.pageNum=pageNum!=null?pageNum:1;
	param.viewCnt=10;
	if(orderKey!=null){
		selectedOrderBy= orderKey==selectedOrderKey&&selectedOrderBy=="desc"?"asc":"desc";
		param.orderKey=orderKey;
		param.orderBy=selectedOrderBy;
		selectedOrderKey=orderKey;
		selectedOrderBy=param.orderBy;
	}else if(selectedOrderKey!=null){
		param.orderKey=selectedOrderKey;
		param.orderBy=selectedOrderBy;
	}

	var request_type = "POST";
	var content_type = null;
	var return_data_type = 'json';
	var call_url = contextPath+"/admin/rest/reserve/game/list";
	
	sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
		var ths = $("#table1 > thead > tr > th");
		var tbody = $("#table1 > tbody");
		tbody.empty();
		if(data!=null && data.length>0){
			totalSum=data.length;
			var startNum = params.pageNum==1?1:Number(params.pageNum-1)*10+1;
			$("#vw_total").html(numberWithCommas(data[0].totalCnt));
			$.each(data, function( index, value ) {
				value.idx=startNum+index;
				value.viewCnt=10;
				tbody.append(addTableData(ths, value, index, params.page, "", viewInfo, null));
			});
		}else{
			$("#vw_total").html(0);
			tbody.html("<tr><td colspan='4'>Empty.</td></tr>");
		}
		addTablePaging("table1", data, loadList);
		processDim.hidePleaseWait();
	});
}

function viewInfo(data, index, page){
	var openDate = data.openTime.substring(0, 10).replaceAll("-", ".");
	var openHour = data.openTime.substring(11, 13);
	var closeDate = data.closeTime.substring(0, 10).replaceAll("-", ".");
	var closeHour = data.closeTime.substring(11, 13);
	$("#gameId").val(data.gameId);
	$("#gameName").val(data.gameName);
	$("#openDate").val(openDate);
	$("#openHour").val(openHour);
	$("#closeDate").val(closeDate);
	$("#closeHour").val(closeHour);
	$("#reserveCnt").val(data.reserveCnt);
	$("#installCnt").val(data.installCnt);
	
	$("#reserveRewardItem").val(data.reserveRewardItem);
	$("#installRewardItem").val(data.installRewardItem);
	$("#lottoRewardItem").val(data.lottoRewardItem);
	
	$("#btn_delete").show();
}

function doSave(){
	if(checkValidation()){
		var msg = "저장하시겠습니까?";
		
		if(confirm(msg)){
	        var param = getFormData($('#frm'));
	        param.openTime = $("#openDate").val().replaceAll("\.","-")+" "+$("#openHour").val()+":00:00";
	        param.closeTime = $("#closeDate").val().replaceAll("\.","-")+" "+$("#closeHour").val()+":00:00";
	    	param = JSON.stringify(param);
	    	
	    	var request_type = "POST";
	    	var content_type = "application/json; charset=UTF-8";
			var return_data_type = 'json';
			var call_url = contextPath+"/admin/rest/reserve/game/save";
			
			sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
				loadList();
				initValue();
			});
		}
	}
}

function checkValidation(){
	for(var i=0; i<requiredEl.length; i++){
		if(!isnull(requiredEl[i].id)){
			return false;
		}
	}
	
	var opendTime=$("#openDate").val().replaceAll("\.","")+$("#openHour").val();
	var closeTime=$("#closeDate").val().replaceAll("\.","")+$("#closeHour").val();
	
	if(Number(opendTime)>=Number(closeTime)){
		$("#closeDate").focus();
		alert("종료일시는 시작일시보다 커야합니다.");
		
		return false;
	}
	
	return true;
}

function doDelete(){
	var msg = "삭제하시겠습니까?";
	
	if(confirm(msg)){
        var param = {};
        param.gameId = $("#gameId").val();
    	param = JSON.stringify(param);
    	
    	var request_type = "POST";
    	var content_type = "application/json; charset=UTF-8";
		var return_data_type = 'json';
		var call_url = contextPath+"/admin/rest/reserve/game/delete";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			loadList();
			initValue();
		});
	}
}

function initSampleData(){
	var msg = "실행하시겠습니까?";
	
	if(confirm(msg)){
		processDim.showPleaseWait();
        var param = {};
    	
    	var request_type = "POST";
    	var content_type = "application/json; charset=UTF-8";
		var return_data_type = null;;
		var call_url = contextPath+"/admin/rest/reserve/init/sample";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			processDim.hidePleaseWait();
			loadList();
		});
	}
}

function initValue(){
	for(var i=0; i<requiredEl.length; i++){
		requiredEl[i].value="";
	}
	$("#gameId").val("");
	$("#openHour").val("09");
	$("#closeHour").val("09");
	$("#reserveCnt").val("");
	$("#installCnt").val("");
	$("#btn_delete").hide();
}

function initBtn(){
	$("#btn_save").click(function(){doSave();});
	$("#btn_delete").click(function(){doDelete();});
	$("#btn_new").click(function(){initValue();});
	$("#btn_delete").hide();
	$("#btn_init").click(function(){initSampleData();});
}

var requiredEl = [];
function initElement(){
	requiredEl = initBlurNullCheck("frm");

	buildRewardItem("reserveRewardItem", false);
	buildRewardItem("installRewardItem", false);
	buildRewardItem("lottoRewardItem", false);
}

$(document).ready(function() {
	var menu_active_idx=4;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
	loadList();
});