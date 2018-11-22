<#ftl encoding="utf-8"/> 
		
		<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
			<a class="navbar-brand" href="/">Test</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/crawler">웹 이미지 크롤</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/reserve">앱 출시 사전예약</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/reserve/install">앱 설치</a>
					</li>
					<li class="nav-item dropdown">
						<div class="dropdown">
							<a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">관리자</a>
							<div class="dropdown-menu" aria-labelledby="dropdown01">
								<a class="dropdown-item" href="/admin/reserve/game">게임</a>
								<a class="dropdown-item" href="/admin/reserve/open">사전예약 현황</a>
								<a class="dropdown-item" href="/admin/reserve/install">게임설치 현황</a>
								<a class="dropdown-item" href="/admin/reserve/reward">보상금 지급 내역</a>
							</div>
						</div>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/reserve/api">API</a>
					</li>
				</ul>
				<ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
				<#if Session.session_user_id?exists>
					<li class="nav-item">
						<a class="nav-link p-2" href="#" rel="noopener" aria-label="">
						  ${Session.session_user_id} (${Session.session_user_role})
						</a>
					</li>
					<li class="nav-item">
						<a class="nav-link p-2" href="/logout" rel="noopener" aria-label="">
						Logout
						</a>
					</li>
				<#else>
					<li class="nav-item">
						<a class="nav-link p-2" href="/login" rel="noopener" aria-label="">
						Login
						</a>
					</li>
				</#if>
				</ul>
			</div>
		</nav>
		
	    
		<script type="text/javascript">
		var contextPath="${rc.contextPath}";
		</script>
		
		<#if Session.session_user_id?exists>
		<script type="text/javascript">
		var session_user_id="${Session.session_user_id}";
		</script>
		<#else>
		<script type="text/javascript">
		var session_user_id="";
		</script>
		</#if>
		
		<script src="/static/js/menu.js"></script>

		