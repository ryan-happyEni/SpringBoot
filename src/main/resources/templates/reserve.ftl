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
				<h1>사전 예약 </h1>
				<div>
					<div class="row">
						<div class="col-md-12 mb-3">
							<div class="row">
								<div class="col-md-12 mb-3">
									<h5>List. Total [ <span id="vw_total"></span> ]</h5>
								</div>
							</div>
	
							<form id="frm" name="frm">
					            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					            <input type="hidden" name="page" value="1"/>
					            <input type="text" name="temp" style="position:absolute;top:-1000px;"/>
							<div class="row">
								<div class="col-md-12 mb-3">
									<div id="table1">
									</div>
								</div>
							</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script src="/static/js/common.js"></script>
		<script src="/static/js/common_loader.js"></script>
		<script src="/static/js/reserve.js"></script>
	</body> 
</html>
