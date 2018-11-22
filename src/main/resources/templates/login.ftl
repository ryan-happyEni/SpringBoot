<#ftl encoding="utf-8"/> 

<!DOCTYPE html>
<html lang="en">
	<head> 
		<#include "/meta.ftl">
	</head> 
	<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css">
	<script src="/static/jquery/jquery.min.js"></script>
	<script src="/static/jquery/jquery.min.js"></script>
	<style type="text/css">
	ul {list-style:none;}
	</style>
	<body> 
    	<div class="container">
			<div>
				<h1>Login</h1>
				<p class="lead">
					관리자 계정 : admin<br>
					사용자 계정 : test1 ~ test99999 
				</p>
				
				<form class="form-signin" name="frm" method="post" action="/login">
		            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
					<label for="username" class="sr-only">Id</label>
					<input type="text" id="userId" name="username" class="form-control" placeholder="Id" required="" autofocus="">
					<label for="password" class="sr-only">Password</label>
					<input type="password" id="pw" name="password" class="form-control" placeholder="Id" value="1234">
					<br>
					<button class="btn btn-lg btn-primary btn-block" id="btn_send" type="submit">Sign in</button>
				</form>
				<script type="text/javascript">
				</script>
			</div>
		</div>
	</body> 
</html>
