

function activeMenu(menu_active_idx){
	var lis = $(".navbar-nav > li");
	if(lis.length>0){
		$.each(lis, function( index, li ) {
			if($(li).hasClass("dropdown")){
				$($(li).children()[0]).removeClass("active");
			}else{
				$(li).removeClass("active");
			}
		});

		if($(lis[menu_active_idx]).hasClass("dropdown")){
			$($(lis[menu_active_idx]).children()[0]).addClass("active");
		}else{
			$(lis[menu_active_idx]).addClass("active");
		}
	}
}



var processDim;
processDim = processDim || (function () {
    var pleaseWaitDiv = $('<div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false"><div class="modal-header" style="color:#fff;width:250px;margin-top:15%;margin-left:38%;border-bottom:0px;"><h1>Processing...</h1></div></div>');
    return {
        showPleaseWait: function() {
            pleaseWaitDiv.modal();
        },
        hidePleaseWait: function () {
            pleaseWaitDiv.modal('hide');
        },

    };
})();