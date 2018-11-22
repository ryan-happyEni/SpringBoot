
var totalSum=0;
function loadList(pageNum){
	processDim.showPleaseWait();

	var param = {};
	param.pageNum=pageNum!=null?pageNum:1;
	param.viewCnt=9;

	var request_type = "POST";
	var content_type = null;
	var return_data_type = 'json';
	var call_url = contextPath+"/rest/reserve/game/open/list";
	
	sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
		var tbody = $("#table1");
		tbody.empty();
		if(data!=null && data.length>0){
			totalSum=data.length;
			var startNum = params.pageNum==1?1:Number(params.pageNum)*10+1;
			$("#vw_total").html(numberWithCommas(data[0].totalCnt));
			
			var cardDeck = null;
			$.each(data, function( index, value ) {
				value.idx=startNum+index; 
				if(index%3==0 || index==0){
					cardDeck = buildCardDeck();
					tbody.append(cardDeck);
				}
				cardDeck.appendChild(buildCard(value));
			});
			addTablePaging("table1", data, loadList);
		}else{
			$("#vw_total").html(0);
			tbody.html("<div>Empty.</div>");
		}
		processDim.hidePleaseWait();
	});
}

function buildCardDeck(){
	var cadDeck = document.createElement("div");
	cadDeck.className="card-deck text-center";
	return cadDeck;
}
function buildCard(data){
	var day=0;
	var hour=0;
	if(data.remaningHour>0){
		day = parseInt(data.remaningHour/24);
		hour = data.remaningHour<24?data.remaningHour:data.remaningHour%24;
	}
	
	var card = document.createElement("div");
	card.className="card col-md-4 box-shadow";
	
	var cardHeader = document.createElement("div");
	card.appendChild(cardHeader);
	cardHeader.className="card-header";

	var header = document.createElement("h4");
	cardHeader.appendChild(header);
	header.className="my-0 font-weight-normal";
	header.innerHTML = data.gameName;
	
	var cardBody = document.createElement("div");
	card.appendChild(cardBody);
	cardBody.className="card-body";


	var cardTitle = document.createElement("h1");
	cardBody.appendChild(cardTitle);
	cardTitle.className="card-title pricing-card-title";
	if(day>0 || hour>0){
		cardTitle.innerHTML = "<small class='text-muted'>예약 </small><span>"+numberWithCommas(data.reserveCnt)+"</span><small class='text-muted'> 명</small>";
	}else{
		cardTitle.innerHTML = "<small class='text-muted'>설치 </small><span>"+numberWithCommas(data.installCnt)+"</span><small class='text-muted'> 건</small>";
	}


	var cardList = document.createElement("li");
	cardBody.appendChild(cardList);
	cardList.className="list-unstyled mt-3 mb-4";

	var li = document.createElement("ul");
	cardList.appendChild(li);
	li.innerHTML = "<span>시작시간</span>&nbsp;:&nbsp;"+data.openTime+"";

	var li = document.createElement("ul");
	cardList.appendChild(li);
	li.innerHTML = "<span>종료시간</span>&nbsp;:&nbsp;"+data.closeTime+"";

	var li = document.createElement("ul");
	cardList.appendChild(li);
	if(day>0 || hour>0){
		li.innerHTML = "<span>남은시간</span>&nbsp;:&nbsp;"+(day>0?day+"일 ":"")+(hour+"시간")+"";
	}else{
		li.innerHTML="<span>사전예약 종료</span>";
	}

	var li = document.createElement("ul");
	cardList.appendChild(li);
	if(day>0 || hour>0){
		li.innerHTML = "<span>사전예약 사은품</span>&nbsp;:&nbsp;<span style='color:#e83e8c'>"+(RewardItem[data.reserveRewardItem])+"</span>";
	}else{
		li.innerHTML = "<span>게임설치 사은품</span>&nbsp;:&nbsp;<span style='color:#e83e8c'>"+(RewardItem[data.installRewardItem])+"</span>";
	}
	
	if(session_user_id!=""){
		var button = document.createElement("button");
		cardBody.appendChild(button);
		button.type="button";
		var btnTitle = "";
		var btnClass = "";
		var date = new Date();
		date = date.toISOString().replace("T"," ");
		date = date.substring(0, date.indexOf("."));
		if(data.closeTime>date){
			if(data.reserveYn=="Y"){
				btnClass="btn btn-lg btn-block btn-outline-danger";
				btnTitle="예약취소";
				button.onclick=function(){
					doDelete(data.gameId);
				}
			}else{
				btnClass="btn btn-lg btn-block btn-outline-primary";
				btnTitle="예약하기";
				button.onclick=function(){
					doSave(data.gameId);
				}
			}
		}else{
			btnClass="btn btn-lg btn-block btn-outline-success";
			btnTitle="게임설치";
			button.onclick=function(){
				doInstall(data.gameId);
			}
		}
		button.className=btnClass;
		button.innerHTML=btnTitle;

		var button = document.createElement("button");
		cardBody.appendChild(button);
		button.type="button";
		var btnTitle = "";
		var btnClass = "";
		var date = new Date();
		date = date.toISOString().replace("T"," ");
		date = date.substring(0, date.indexOf("."));
		btnClass="btn btn-lg btn-block btn-outline-success";
		btnTitle="5천건 예약만들기";
		button.onclick=function(){
			run(data.gameId);
		}
		button.className=btnClass;
		button.innerHTML=btnTitle;
	}
	
	return card;
}


function doSave(gameId){
	if(checkValidation()){
		var msg = "예약하시겠습니까?";
		
		if(confirm(msg)){
		    var param = {};
		    param.gameId = gameId;
	    	param = JSON.stringify(param);
	    	
	    	var request_type = "POST";
	    	var content_type = "application/json; charset=UTF-8";
			var return_data_type = 'json';
			var call_url = contextPath+"/rest/reserve/game/save";
			
			sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
				loadList();
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
	
	return true;
}

function doDelete(gameId){
	var msg = "예약을 취소하시겠습니까?";
	
	if(confirm(msg)){
	    var param = {};
	    param.gameId = gameId;
    	param = JSON.stringify(param);
    	
    	var request_type = "POST";
    	var content_type = "application/json; charset=UTF-8";
		var return_data_type = 'json';
		var call_url = contextPath+"/rest/reserve/game/delete";
		
		sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
			loadList();
			initValue();
		});
	}
}

function run(gameId){
	processDim.showPleaseWait();
	if(confirm("실행하시겠습니까?")){
		for(var i=1; i<=5000; i++){
			doMakeSample(gameId, "sample"+i+"_"+(new Date('1980-01-01')-new Date()), i);
		}
	}else{
		processDim.hidePleaseWait();
	}
}


function doMakeSample(gameId, userId, idx){
    var param = {};
    param.gameId = gameId;
	param = JSON.stringify(param);
	
	var request_type = "POST";
	var content_type = "application/json; charset=UTF-8";
	var return_data_type = 'json';
	var call_url = contextPath+"/prereservation/enroll/"+gameId+"/"+userId;
	
	sendRequest(request_type, call_url, param, content_type, return_data_type, function(params, data){
		if(idx==5000){
			processDim.hidePleaseWait();
			loadList();
		}
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
	var menu_active_idx=2;
	activeMenu(menu_active_idx);

	initElement();
	initBtn();
	loadList();
});