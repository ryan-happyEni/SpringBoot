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
				<h1>API </h1>
				<div>
					<div class="row">
						<div class="col-md-12 mb-3">
							<div>
					          	<h6 class="mb-0 lh-100" id="vw_url">1.user_id 가 preserv_id 사전예약에 참여하는 api</h6>
							</div>
							<div class="d-flex align-items-center p-3 my-3 text-white-50 bg-secondary rounded shadow-sm">
					          	<h6 class="mb-0 lh-100" id="vw_url">/prereservation/enroll/{preserv_id}/{user_id}</h6>
							</div>
							<div class=" align-items-center p-3 my-3 bg-secondary rounded shadow-sm">
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Request</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>파라미터</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>preserv_id</td>
							          			<td>게임 고유 번호 </td>
							          		</tr>
							          		<tr>
							          			<td>user_id</td>
							          			<td>사용자 아이디 </td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Response</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>키</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>result</td>
							          			<td>
							          				결과 값<br>
							          				1=정상처리 <br>
							          				-998=게임 존재하지 않음  <br>
							          				-999=이미 사전 예약했음  <br>
							          			</td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Test</h6>
					          		<div class="p-3" style="background-color: #fff;">
				          				/prereservation/enroll/<small><input class='apiiput' id='api1_p1'></small>/<small><input class='apiiput' id='api1_p2'></small>/
							          	<button id="btn_api1" class="btn btn-primary btn-sm" type="button">Send</button>
							          	<br>
							          	Result.
							          	<div class="col-md-12" style="background-color:#6c757d;height:50px;color:#fff;" id="api1_result"></div>
					          		</div>
					          	</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12 mb-3">
							<div>
					          	<h6 class="mb-0 lh-100" id="vw_url">2.preserv_id 에 참여한 총 카운트 조회 api</h6>
							</div>
							<div class="d-flex align-items-center p-3 my-3 text-white-50 bg-secondary rounded shadow-sm">
					          	<h6 class="mb-0 lh-100" id="vw_url">/prereservation/count/{preserv_id}</h6>
							</div>
							<div class=" align-items-center p-3 my-3 bg-secondary rounded shadow-sm">
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Request</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>파라미터</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>preserv_id</td>
							          			<td>게임 고유 번호 </td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Response</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>키</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>cnt</td>
							          			<td>
							          				결과 값<br>
							          				1=정상처리 <br>
							          				-998=게임 존재하지 않음  <br>
							          			</td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Test</h6>
					          		<div class="p-3" style="background-color: #fff;">
				          				/prereservation/count/<small><input class='apiiput' id='api2_p1'></small>
							          	<button id="btn_api2" class="btn btn-primary btn-sm" type="button">Send</button>
							          	<br>
							          	Result.
							          	<div class="col-md-12" style="background-color:#6c757d;height:50px;color:#fff;" id="api2_result"></div>
					          		</div>
					          	</div>
							</div>
						</div>
					</div>
					
					<div class="row">
						<div class="col-md-12 mb-3">
							<div>
					          	<h6 class="mb-0 lh-100" id="vw_url">3.user_id 가 preserv_id 사전예약에 참여했는지, 했다면 몇번째 순번으로 참여했는지 확인하는 api</h6>
							</div>
							<div class="d-flex align-items-center p-3 my-3 text-white-50 bg-secondary rounded shadow-sm">
					          	<h6 class="mb-0 lh-100" id="vw_url">/prereservation/check/{preserv_id}/{user_id}</h6>
							</div>
							<div class=" align-items-center p-3 my-3 bg-secondary rounded shadow-sm">
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Request</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>파라미터</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>preserv_id</td>
							          			<td>게임 고유 번호 </td>
							          		</tr>
							          		<tr>
							          			<td>user_id</td>
							          			<td>사용자 아이디 </td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Response</h6>
					          		<div class="p-3" style="background-color: #fff;">
					          			<table class="table">
					          				<colgroup>
					          					<col width="30%">
					          					<col width="70%">
					          				</colgroup>
							          		<tr>
							          			<th>키</th>
							          			<th>설명</th>
							          		</tr>
							          		<tr>
							          			<td>result</td>
							          			<td>
							          				결과 값<br>
							          				1=정상처리 <br>
							          				-998=게임 존재하지 않음  <br>
							          				-999=사용자 존재하지 않음  <br>
							          			</td>
							          		</tr>
							          	</table>
					          		</div>
					          	</div>
					          	<br>
					          	<div>
					          		<h6 class="mb-0 lh-100 text-white-50 " id="vw_url">Test</h6>
					          		<div class="p-3" style="background-color: #fff;">
				          				/prereservation/check/<small><input class='apiiput' id='api3_p1'></small>/<small><input class='apiiput' id='api3_p2'></small>/
							          	<button id="btn_api3" class="btn btn-primary btn-sm" type="button">Send</button>
							          	<br>
							          	Result.
							          	<div class="col-md-12" style="background-color:#6c757d;height:50px;color:#fff;" id="api3_result"></div>
					          		</div>
					          	</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script src="/static/js/common.js"></script>
		<script src="/static/js/common_loader.js"></script>
		<script src="/static/js/api.js"></script>
	</body> 
</html>
