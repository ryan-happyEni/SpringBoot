
String.prototype.replaceAll = function(searchStr, replaceStr){
    var temp = this;
    while( temp.indexOf( searchStr ) != -1 ){
      temp = temp.replace( searchStr, replaceStr );
    }
    return temp;
} 


String.prototype.reverseXSS = function(){
    var temp = this;

    temp = temp.replaceAll("&#034;", "\"");
    temp = temp.replaceAll("&#036;", "\\$");
    temp = temp.replaceAll("&#037;", "%");
    temp = temp.replaceAll("&#039;", "'");
    temp = temp.replaceAll("&#040;", "(");
    temp = temp.replaceAll("&#041;", ")");
    temp = temp.replaceAll("&#047;", "/");
    temp = temp.replaceAll("&#060;", "<");
    temp = temp.replaceAll("&#062;", ">");
    temp = temp.replaceAll("&gt;", ">");
    temp = temp.replaceAll("&lt;", "<");
    
    return temp;
} 

function numberWithCommas(x) {
	if(x!=null){
		x = ""+x;
	    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}else{
		return "";
	}
}

function toNumberFormat(str, number_type){
	if(str!=null && str!=""){
		str = str+"";
	    var tempStr = str;
	    var exStr = new Array(",",".");
	    
	    var pointStr="";
	    if(str.indexOf(".")>-1){
	    	tempStr = str.substring(0, str.indexOf("."));
	    	pointStr = str.substring(str.indexOf("."));
	    }
	    
	    if(number_type!=null&&number_type==-1){
	    	if(str.indexOf("-")==-1) return "";
	    }else if(number_type!=null&&number_type==1){
	    	if(str.indexOf("-")!=-1) return "";
	    }
	    
	    for(var i=0; i<exStr.length; i++){
	        tempStr = tempStr.replaceAll(exStr[i], "");
	    }
	    
	    if(isNaN(tempStr) == true) { 
	        return tempStr;
	    }else{
	    	var monStr = "";   
	    	if(tempStr.indexOf("-")!=-1){ 
	    		monStr = "-";
	    		tempStr = tempStr.replace("-","");
	    	}

	    	var len = tempStr.length;
	    	for(var i=0; i<len; i++){ 
	    		monStr += tempStr.substring(i, i+1); 
	    		if((len>3&&i+1!=len)&&(len-(i+1))%3==0){
	    			monStr +=",";
	    		}
	    	}
	    	
	    	monStr += pointStr;
	    	return monStr;
	    }
	}
	return "";
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}


function sendRequest(request_type, call_url, param, content_type, return_data_type, call_back_fnc, errFnc){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	if(content_type!=null){
		if(return_data_type!=null){
			$.ajax({
				type: request_type,
		        contentType: content_type,
				url: call_url,
				data: param,
				dataType: return_data_type,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}else{
			$.ajax({
				type: request_type,
		        contentType: content_type,
				processData: false,
				contentType: false,
				url: call_url,
				data: param,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}
	}else{
		if(return_data_type!=null){
			$.ajax({
				type: request_type,
				url: call_url,
				data: param,
				dataType: return_data_type,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}else{
			$.ajax({
				type: request_type,
				processData: false,
				contentType: false,
				url: call_url,
				data: param,
				cache: false,
				timeout: 600000,
                beforeSend: function(xhr) {
                	if (header && token) {
                		xhr.setRequestHeader(header, token);
                	}
            	}, 
				success: function (data) {
		        	if(call_back_fnc!=null){
		        		call_back_fnc(param, data);
		        	}
				},
				error: function (e) {
					sendRequestErrorHandler(e, errFnc);
				}
			});
		}
	}
}

function sendRequestErrorHandler(e, errFnc){
	var errorData = JSON.parse(JSON.stringify(e));
	if(errFnc!=null){
		errFnc(errorData);
	}
}


function addTableData(list_header_ths, data, index, page, view_id, viewFnc, styleFnc){
	var tr = document.createElement("tr");
	if(viewFnc!=null){
		tr.style.cursor="pointer";
		tr.setAttribute("data-toggle", "modal");
		tr.setAttribute("data-target", "#"+view_id);
		tr.onclick=function(){
			$(this).parent().find("tr").removeClass("on");
			viewFnc(data, index, page);
			this.className="on";
		}
	}
	
	var size = list_header_ths.length;
	for(var i=0; i<size; i++){
		var th = $(list_header_ths[i]);
		var code = th.attr("code");
		var data_type = th.attr("data-type");
		var align = th.attr("data-align");
		var format = th.attr("format");
		var point = th.attr("point-length");
		var maxlength = th.attr("maxlength");
		var prefix = th.attr("prefix");
		var suffix = th.attr("suffix");
		var checkboxName = th.attr("checkbox-name");
		
		if(prefix==null){
			prefix="";
		}
		if(suffix==null){
			suffix="";
		}
		
		if(format!=null && format!=""){
			if(format.indexOf(".")>-1){
				var tt = format.substring(format.indexOf(".")+1);
				point = tt.length;
			}
		}
		
		var txt = data[code];
		if(txt!=null && txt!=""){
			if(data_type!=null && data_type!=""){
				if(format!=null && data_type=="date"){
					txt = toDate(txt, format);
				}else if(data_type=='int' || data_type=='float' || data_type=='double'){
					var dtype = data_type;
					txt=""+txt;
					txt=txt.replaceAll(",","");
					try{
						var nn = Number(txt);
						var v = txt;
						if(point!=null&&point!=""&&v.indexOf(".")>0){
							if(!isNaN(point)){ 
								point = Number(point);
								var p = v.substring(0, v.indexOf("."));
								var n = v.substring(v.indexOf(".")+1);
								var nn="";
								var last = point>n.length?n.length:point;
								for(var x=0; x<last; x++){
									nn+=n.substring(x, x+1);
								}
								if(nn!=""){
									v = p+"."+nn;
								}else{
									v = p;
								}
								txt = v;
							}
						}
						if(format!=null&&format!=""){
							txt = toNumberFormat(txt);
						}
					}catch(e){
					}
				}else if(data_type=="checkbox"){
					txt = "<input type='checkbox' id='"+(code+"_"+index)+"' name='"+(checkboxName!=null?checkboxName:code)+"' value='"+data[code]+"'>";
				}else if(data_type=="img"){
					txt = "<img src='"+data[code]+"' style='width:100px;height:30px;'>";
				}
			}
			
			if(maxlength!=null && maxlength!="" && !isNaN(maxlength)){
				var max = Number(maxlength);
				if(txt.length>maxlength){
					txt = txt.substring(0, maxlength)+" ...";
				}
			}
		}else{
			txt="";
		}
		
		var td = document.createElement("td");
		tr.appendChild(td);
		if(align!=null && align!=""){
			td.style.textAlign = align;
		}
		td.innerHTML = prefix+txt+suffix;
	}
	
	if(styleFnc!=null){
		styleFnc(tr, data, index);
	}
	
	return tr;
}


function addTablePaging(table_id, data, pageFnc){
	var attrTable={
			view_cnt:10,
			current_page:1,
			last_page:1,
			totalCnt:0
	}
	if(data!=null && data.length>0){
		attrTable.totalCnt=data[0].totalCnt;
		attrTable.current_page=data[0].pageNum;
		attrTable.view_cnt=data[0].viewCnt;
		attrTable.last_page=data[0].totalPage;
	}
	var ul = null;
	var totalTxt = null; 
	var call_page = null; 
	var call_page_id = null; 
	var parent = $("#"+table_id)[0].parentNode;
	var paging = $(".pagingWrap", parent);
	if(paging!=null && paging.length>0){
		ul = $(".pagingWrap > div", parent);
		totalTxt = $(".pagingWrap > div > div > span", parent);
		call_page = $(".pagingWrap > input", parent);
		call_page_id = call_page[0].id;
	}else{
		var pagingWrap = document.createElement("div");
		parent.appendChild(pagingWrap);
		pagingWrap.className="pagingWrap";
		
		var paging = document.createElement("div");
		pagingWrap.appendChild(paging);
		paging.className="paging";

		call_page_id = "call_page_";
		call_page = document.createElement("input");
		pagingWrap.appendChild(call_page);
		call_page.type="hidden";
		call_page.className="call_page";
		call_page.id=call_page_id;
		call_page.value=attrTable.current_page;
		
		ul = $(paging);
	}

	buildPageNav(ul, attrTable, pageFnc, call_page_id);
}

function buildPageNav(pageNav, pageInfo, callMethodTxt, call_page_id){
	if(pageInfo==null){
		pageInfo ={
			view_cnt:10,
			current_page:1,
			last_page:1,
			total_cnt:0
		}
	}
	if(pageInfo.current_page<1){
		pageInfo.last_page=1;
	}
	if(pageInfo.last_page==0){
		pageInfo.last_page=1;
	}
	pageNav.empty();
	
	var fncName = callMethodTxt.name;
	if(fncName==null || fncName==undefined){
		fncName = callMethodTxt.toString();
		fncName = fncName.substr('function '.length);
		fncName = fncName.substr(0, fncName.indexOf('('));
	}

	var s=1;
	var e=1;
	var page_size=10;
	var last_page = pageInfo.last_page;//Math.round(pageInfo.total_cnt/pageInfo.view_cnt);
	if(pageInfo.total_cnt>last_page*pageInfo.view_cnt){
		last_page++;
	}
	pageInfo.last_page=last_page;
	
	s = Math.floor( (pageInfo.current_page-1)/page_size, 0)*page_size+1
	e = s+(page_size-1);

	if(e>pageInfo.last_page){
		e=pageInfo.last_page;
	}
	
	if(e==0){
		e=1;
	}

	var a = document.createElement("a");
	a.className="pagingPrev";
	a.innerHTML = "Prev";
	if(s>10){
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(s-1)+";"+fncName+"("+(s-1)+");";
	}else{
		a.className="pagingPrev disabled";
	}
	pageNav.append(a);

	var div = document.createElement("div");
	div.className="pagingNumber";
	pageNav.append(div);
	
	for(var p=s; p<=e; p++){
		var a = document.createElement("a");
		if(p==pageInfo.current_page){
			a.className="active";
		}
		a.innerHTML=p;
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(p)+";"+fncName+"("+(p)+");";
		div.appendChild(a);
	}
	
	var a = document.createElement("a");
	a.className="pagingNext";
	a.innerHTML = "Next";
	if(e<pageInfo.last_page){
		a.href="javascript:document.getElementById('"+call_page_id+"').value="+(e+1)+";"+fncName+"("+(e+1)+");";
	}else{
		a.className="pagingNext disabled";
	}
	pageNav.append(a);
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function isnull(id, msg){
	var obj = $("#"+id);
	if(obj!=null){
		if(obj.val()==""||obj.val().trim()==""){
			if(msg!=null){
				alert(msg);
			}else{
				obj.parent().find(".invalid-feedback").show();
			}
			obj.focus();
			return false;
		}
		obj.parent().find(".invalid-feedback").hide();
		return true;
	}
	
	return true;
}

function initBlurNullCheck(form_id){
	var requiredEl = [];
	var params =$("#"+form_id).find("input, select, textarea");
	for(var i=0; i<params.length; i++){
		if($(params[i]).attr("required")!=undefined){
			requiredEl[requiredEl.length]=params[i];
			if(params[i].tagName=="INPUT" || params[i].tagName=="TEXTAREA"){

				if($(params[i]).attr("data-type")=="date"){
					$(params[i]).blur(function(){
						isnull(this.id);
						isDate(this);
					});
					$(params[i]).keydown(function(e){
						if( (e.keyCode>=48 && e.keyCode<=57) || (e.keyCode>=96 && e.keyCode<=105) ){
							if(this.value.length==4){
								this.value = this.value+".";
							}else if(this.value.length==7){
								this.value = this.value+".";
							}
						}
					});
				}else if($(params[i]).attr("data-type")=="int"){
					$(params[i]).blur(function(){
						isnull(this.id);
					});
					$(params[i]).keyup(function(e){
						if( (e.keyCode>=48 && e.keyCode<=57) || (e.keyCode>=96 && e.keyCode<=105) ){
						}else{
							if(this.value.length>0){
								this.value = this.value.substring(0, this.value.length-1);
							}
						}
					});
				}else{
					$(params[i]).blur(function(){isnull(this.id);});
				}
			}
		}
	}
	return requiredEl;
}

function isDate(obj){
	var date_pattern = /^(19|20)\d{2}.(0[1-9]|1[012]).(0[1-9]|[12][0-9]|3[0-1])$/; 
	//date_pattern =/[\d{2}|\d{4}][\.|\/|\-]\d{1,2}[\.|\/|\-]\d{1,2}/; 
	if(!date_pattern.test(obj.value)){
		obj.value = "";
		return false;
	}
	return true;
}