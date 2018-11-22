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
				<h1>게임설치 현황 </h1>
				<div>
					<div class="row">
						<div class="col-md-12 mb-3">
							<div class="row">
								<div class="col-md-12 mb-3">
									<h5>List. Total [ <span id="vw_total"></span> ]</h5>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-12 mb-3">
									<table class="table" id="table1">
										<colgroup>
											<col width="70px">
											<col width="">
											<col width="15%">
											<col width="15%">
										</colgroup>
										<thead class="thead-inverse">
											<tr>
												<th code="idx">#</th>
												<th code="gameName"><a href="javascript:loadList(1, 'gameName')">Game</a></th>
												<th code="installCnt" data-align="right"><a href="javascript:loadList(1, 'installCnt')">설치수(건)</a></th>
												<th code="reserveCnt" data-align="right"><a href="javascript:loadList(1, 'reserveCnt')">예약자수(명)</a></th>
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
			</div>
		</div>
	    
		<script type="text/javascript">
		var contextPath="${rc.contextPath}";
		</script>
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script src="/static/js/common.js"></script>
		<script src="/static/js/common_loader.js"></script>
		<script src="/static/js/admin/install.js"></script>
	</body> 
</html>
