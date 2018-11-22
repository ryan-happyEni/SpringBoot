
var totalSum=0;
var selectedOrderKey=null;
var selectedOrderBy=null;
function loadList(pageNum, orderKey){
	var searchType = $("#searchType").val();
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
	param.orderKey="millisec";
	param.orderBy="desc";

	var request_type = "POST";
	var content_type = null;
	var return_data_type = 'json';
	var call_url = contextPath+"/admin/rest/reserve/reward/"+searchType+"/list";
	if($("#gameId").val()!=""){
		call_url+="/"+$("#gameId").val();
	}
	
	sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
		var ths = $("#table1 > thead > tr > th");
		var tbody = $("#table1 > tbody");
		tbody.empty();
		if(data!=null && data.length>0){
			totalSum=data.length;
			var startNum = params.pageNum==1?1:Number(params.pageNum-1)*10+1;
			$("#vw_total").html(numberWithCommas(data[0].totalCnt));
			$.each(data, function( index, value ) {
				if(value==null){
					value={}
				}
				value.idx=startNum+index;
				value.viewCnt=10;
				value.rewardItemName=RewardItem[value.rewardItem]
				tbody.append(addTableData(ths, value, index, params.page, "", null, null));
			});
		}else{
			$("#vw_total").html(0);
			tbody.html("<tr><td colspan='4'>Empty.</td></tr>");
		}
		addTablePaging("table1", data, loadList);
		
		if($("#gameId").val()!="" && $("#searchType").val()=="reserve"){
			$("#btn_settle").show();
		}else{
			$("#btn_settle").hide();
		}
		processDim.hidePleaseWait();
	});
}

function loadGameId(){
	var searchType = $("#searchType").val();
	var param = {};
	param.pageNum=1;
	param.viewCnt=10000;
	param.orderKey="gameName";
	param.orderBy="asc";

	var request_type = "POST";
	var content_type = null;
	var return_data_type = 'json';
	var call_url = contextPath+"/admin/rest/reserve/game/close/list";
	if(searchType=="lotto"){
		call_url = contextPath+"/admin/rest/reserve/game/list";
	}
	
	sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
		var target = $("#gameId");
		target.empty();

		var opt = document.createElement("option");
		opt.text="전체";
		opt.value = "";
		target.append(opt);
		
		if(data!=null && data.length>0){
			$.each(data, function( index, value ) {
				var opt = document.createElement("option");
				opt.text = value.gameName;
				opt.value = value.gameId;
				target.append(opt);
			});
		}
		$("#btn_settle").hide();
		loadList();
	});
}

function doSettle(){
	var msg = "정산하시겠습니까?";
	
	if(confirm(msg)){
		processDim.showPleaseWait();
        var param = getFormData($('#frm'));
        param.gameId = $("#gameId").val();
    	param = JSON.stringify(param);
    	
    	var request_type = "POST";
    	var content_type = "application/json; charset=UTF-8";
		var return_data_type = 'json';
		var call_url = contextPath+"/admin/rest/reserve/reward/settle";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			loadList();
			processDim.hidePleaseWait();
		});
	}
}

function initValue(){
}

function initBtn(){
	$("#searchType").change(function(){loadGameId();});
	$("#gameId").change(function(){loadList();});
	$("#btn_settle").click(function(){doSettle();});
	$("#btn_settle").hide();
}

var requiredEl = [];
function initElement(){
}

$(document).ready(function() {
	var menu_active_idx=4;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
	//loadList();
	loadGameId();
});