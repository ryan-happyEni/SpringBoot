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
				<h1>보상금 지급내역 </h1>
				<div>
					<div class="row">
						<div class="col-md-12 mb-3">
							<div class="row">
								<div class="col-md-12 mb-3">
									<h5>Search</h5>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3 mb-3">
									<div class="input-group">
										<span style="font-size:18px;padding-top:5px;">구분 :&nbsp;&nbsp;</span> 
										<select class="custom-select d-block col-md-12" id="searchType">
											<option value="reserve">사전예약</option>
											<option value="install">게임설치</option>
											<option value="lotto">로또</option>
										</select>
									</div>
								</div>
								<div class="col-md-3 mb-3">
									<div class="input-group">
										<span style="font-size:18px;padding-top:5px;">게임 :&nbsp;&nbsp;</span> 
										<select class="custom-select d-block col-md-12" id="gameId">
											<option value="">전체</option>
										</select>
									</div>
								</div>
								<div class="col-md-3 mb-3">
									<button id="btn_settle" class="btn btn-warning btn-lg" type="button" style="padding:3px 10px 3px 10px;display:none;">사전예약자 미지급 보상금 정산</button>
								</div>
							</div>
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
											<col width="25%">
											<col width="15%">
											<col width="15%">
											<col width="15%">
										</colgroup>
										<thead class="thead-inverse">
											<tr>
												<th code="idx">#</th>
												<th code="gameName">Game</th>
												<th code="userId" data-align="left">사용자</th>
												<th code="rewardTime" data-align="left">지급일시</th>
												<th code="rewardItemName" data-align="left">지급유형</th>
												<th code="msg" data-align="left">비고</th>
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
		<script src="/static/js/admin/reward.js"></script>
	</body> 
</html>
