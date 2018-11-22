<#ftl encoding="utf-8"/> 

<!DOCTYPE html>
<html lang="en">
	<head> 
		<#include "/meta.ftl">
	</head> 
	<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="/static/css/default.css">
	<script src="/static/jquery/jquery.min.js"></script>
	<body> 
		<#include "/menu.ftl">
		
    	<div class="container">
			<div>
				<h1>웹 이미지 크롤러</h1>
				<div>
					<form name="frm">
			            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			            <input type="hidden" name="page" value="1"/>
			            <input type="text" name="temp" style="position:absolute;top:-1000px;"/>
					<div class="row">
						<div class="col-md-12 mb-3">
							<label for="url">대상주소</label>
							<div class="input-group">
								<input type="text" class="form-control" id="url" placeholder="http:// or https://" required="required" value="http://www.kakaogames.com">
								<div class="input-group-append">
									<button type="button" id="btn_send" class="btn btn-primary">Run</button>
								</div>
								<div class="invalid-feedback">
								대상주소를 입력하세요.  
								</div>
							</div>
						</div>
					</div>
					</form>
					
					<div class="row">
						<div class="col-md-12 mb-3">
							<div id="status_bar" class="d-flex align-items-center p-3 my-3 text-white-50 bg-purple rounded shadow-sm">
						        <div class="lh-100">
						          	<h6 class="mb-0 text-white lh-100" id="vw_url">http://</h6>
						          	<small>Status : </small><small id="vw_status"></small><br>
						          	<small>Start &nbsp;&nbsp;: </small><small id="vw_start">0.0.0.0 00:00:00</small><br>
						          	<small>End &nbsp;&nbsp;&nbsp;&nbsp;: </small><small id="vw_end">0.0.0.0 00:00:00</small>
						        </div>
				      		</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12 mb-3">
							<h5>Image List. Total [ <span id="vw_total"></span> ]</h5>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12 mb-3">
							<table class="table" id="table1">
								<colgroup>
									<col width="10px">
									<col width="">
									<col width="100px">
									<col width="200px">
									<col width="100px">
									<col width="100px">
								</colgroup>
								<thead class="thead-inverse">
									<tr>
										<th code="idx">#</th>
										<th code="src">URL</th>
										<th code="size" data-align="right" data-type="int" format="#,###">Size</th>
										<th code="file_name" maxlength="30">File Name</th>
										<th code="src" data-type="img">Thumb</th>
										<th code="status">Status</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan="5">Empty.</td>
									</tr>
								</tdoby>
							</table>
							<div style="border-top:1px solid #cdcdcd;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	    
		<script type="text/javascript">
		var contextPath="${rc.contextPath}";
		</script>
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script src="/static/js/common.js"></script>
		<script src="/static/js/crawler.js"></script>
	</body> 
</html>
