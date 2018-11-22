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
				<h1>게임</h1>
				<div>
					<div class="row">
						<div class="col-md-8 mb-3">
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
											<col width="190px">
											<col width="190px">
										</colgroup>
										<thead class="thead-inverse">
											<tr>
												<th code="idx">#</th>
												<th code="gameName"><a href="javascript:loadList(1, 'gameName')">Game</a></th>
												<th code="openTime" data-align="center"><a href="javascript:loadList(1, 'openTime')">Open</a></th>
												<th code="closeTime" data-align="center"><a href="javascript:loadList(1, 'closeTime')">Close</a></th>
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
						<div class="col-md-4 mb-3  bg-light">
							<div class="row">
								<div class="col-md-12 mb-3">
									<h5>Info. </h5>
								</div>
							</div>
	
							<form id="frm" name="frm">
					            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					            <input type="hidden" name="page" value="1"/>
					            <input type="text" name="temp" style="position:absolute;top:-1000px;"/>
							<div class="row">
								<div class="col-md-5 mb-3">
									<label for="url">게임ID</label>
									<div class="input-group">
										<input type="text" class="form-control" id="gameId" name="gameId" readonly>
									</div>
								</div>
								<div class="col-md-7 mb-3">
									<label for="url">게임명</label>
									<div class="input-group">
										<input type="text" class="form-control" id="gameName" name="gameName" placeholder="게임명입력" required="required" value="">
										<div class="invalid-feedback">
										게임명을 입력하세요.  
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="url">이벤트 시작일시</label>
									<div class="input-group">
										<input type="text" class="form-control" id="openDate" name="openDate" placeholder="연.월.일" required="required" value="" data-type="date" maxlength="10">
										<select class="custom-select d-block col-md-4" id="openHour" name="openHour">
											<#assign x=23>
											<#list 0..x as i>
											<option value="<#if i<10 >0</#if>${i}"><#if i<10 >0</#if>${i}</option>
											</#list>
										</select>
										<div class="invalid-feedback">
										시작일시를 입력하세요.  
										</div>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="url">이벤트 종료일시</label>
									<div class="input-group">
										<input type="text" class="form-control" id="closeDate" placeholder="연.월.일" required="required" value="" data-type="date" maxlength="10">
										<select class="custom-select d-block col-md-4" id="closeHour" name="closeHour">
											<#assign x=23>
											<#list 0..x as i>
											<option value="<#if i<10 >0</#if>${i}"><#if i<10 >0</#if>${i}</option>
											</#list>
										</select>
										<div class="invalid-feedback">
										종료일시를 입력하세요.  
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 mb-3">
									<label for="url">사전예약 보상 유형</label>
									<div class="input-group">
										<select class="custom-select d-block w-100" id="reserveRewardItem" name="reserveRewardItem" required="required">
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 mb-3">
									<label for="url">게임 설치 보상 유형</label>
									<div class="input-group">
										<select class="custom-select d-block w-100" id="installRewardItem" name="installRewardItem" required="required">
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 mb-3">
									<label for="url">로또 보상 유형</label>
									<div class="input-group">
										<select class="custom-select d-block w-100" id="lottoRewardItem" name="lottoRewardItem" required="required">
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 mb-3">
									<label for="url">사전 예약자 수</label>
									<div class="input-group">
										<input type="text" class="form-control" id="reserveCnt" readonly>
									</div>
								</div>
								<div class="col-md-6 mb-3">
									<label for="url">게임 설치 수</label>
									<div class="input-group">
										<input type="text" class="form-control" id="installCnt" readonly>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 mb-3">
									<button id="btn_save" class="btn btn-primary btn-lg" type="button">저장</button>
									<button id="btn_delete" class="btn btn-danger btn-lg" type="button">삭제</button>
									<button id="btn_new" class="btn btn-warning btn-lg" type="button">신규</button>
									<button id="btn_init" class="btn btn-warning btn-lg" type="button">샘플3개 생성</button>
								</div>
							</div>
							</form>
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
		<script src="/static/js/admin/game.js"></script>
	</body> 
</html>
