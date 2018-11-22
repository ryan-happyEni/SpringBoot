
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
	var call_url = contextPath+"/admin/rest/reserve/game/close/list";
	
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
				tbody.append(addTableData(ths, value, index, params.page, "", null, null));
			});
		}else{
			$("#vw_total").html(0);
			tbody.html("<tr><td colspan='4'>Empty.</td></tr>");
		}
		addTablePaging("table1", data, loadList);
		processDim.hidePleaseWait();
	});
}


function initValue(){
}

function initBtn(){
}

var requiredEl = [];
function initElement(){
}

$(document).ready(function() {
	var menu_active_idx=4;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
	loadList();
});