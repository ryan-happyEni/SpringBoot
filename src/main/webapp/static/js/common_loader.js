
var RewardItem={}
RewardItem.EMOTICON="이모티콘";
RewardItem.GAME_ITEM="게임아이템";
RewardItem.GIFTICON="기프티콘";
RewardItem.POINT="포인트";

function buildRewardItem(target_id, isAll){
	var target = $("#"+target_id);
	target.empty();
	
	if(isAll){
		var opt = document.createElement("option");
		opt.text="전체";
		opt.value = "";
		target.append(opt);
	}else{
		var opt = document.createElement("option");
		opt.text="선택";
		opt.value = "";
		target.append(opt);
	}
	
	var values = ["EMOTICON","GAME_ITEM","GIFTICON","POINT"];
	var txts = ["이모티콘","게임아이템","기프티콘","포인트"];

	for(var i=0; i<values.length; i++){
		var opt = document.createElement("option");
		opt.text = txts[i];
		opt.value = values[i];
		target.append(opt);
	}
}