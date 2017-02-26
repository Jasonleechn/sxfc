<%@page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	    <meta name="description" content=""/>
	    <meta name="author" content=""/>
	    <!-- 新bootstrap 核心css文件 -->
	    <link href="${pageContext.request.contextPath}/dist/css/bootstrap.min.css" rel="stylesheet"/>
	    <!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
	   
	    <!-- Bootstrap core CSS -->
	    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/dist/js/bootstrap.min.js"></script>
	    
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var myform;
        	//登录校验
        	function check_login() {
        		var admin_code = $("#admin_code").val();
        		if(admin_code == "") {
        			window.alert("请输入登录账号.");
        			myform.admin_code.focus();
        			return false;
        		}
        		
        		var password = $("#password").val();
        		if(password == "") {
        			window.alert("请输入密码.");
        			myform.password.focus();
        			return false;
        		}
        		
        		var code = $("#code").val();
        		if(code == "") {
        			window.alert("请输入验证码.");
        			myform.code.focus();
        			return false;
        		}
        		document.getElementById("myform").action = "login.do";
        		document.getElementById("myform").submit;
        	}
        	
        	/*注册界面*/
        	function to_register(){
        		document.getElementById("myform").action = "toRegister.do";
        		document.getElementById("myform").submit;
        	}
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	//刷新验证码
        	function change() {
        		$("#code_image").attr("src", "createImage.do?date=" + new Date().getTime());
        	}
        	function js_onLoad(){
        		myform = document.getElementById("myform");
        		/*myform.admin_code.focus();//默认焦点*/
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			String s_alertMsg = (String)session.getAttribute("s_alertMsg");
        			if(alertMsg != null){
        		%>
        		window.alert("<%=alertMsg%>");
        		<%}
        		  	if(s_alertMsg != null){%>
        		window.alert("<%=s_alertMsg%>")
        		<%}
        		session.removeAttribute("s_alertMsg");
        		%>
        	}
        </script>
    </head>
    <body onload="javascript:js_onLoad();">
    	<div class="container">
        	<form action="login.do" method="post" id="myform" class="form-horizontal" role="form">
        		<h2 class="form-signin-heading" align="center">用户登录</h2>
        		<div id="d_account" name="d_account" class="form-group" >
	      			<label class="col-sm-2 control-label"></label>
	      			<label class="col-sm-2 control-label">账　户：</label>
	      			<div class="col-sm-4">
	       			<input type="text" name="p_account" id="p_account" class="form-control" value="${admin_code}" placeholder="投注站机号"/>
				    <span id="p_account_msg" name="p_account_msg"></span>
	      			</div>
	      			<label class="col-sm-4 control-label"></label>
      			</div>
        		<div id="d_pwd" name="d_pwd" class="form-group" >
	      			<label class="col-sm-2 control-label"></label>
	      			<label class="col-sm-2 control-label">密　码：</label>
	      			<div class="col-sm-4">
	       			<input type="password" name="p_pwd" id="p_pwd" class="form-control"  placeholder="登录密码"/>
				    <span id="p_pwd_msg" name="p_pwd_msg"></span>
	      			</div>
	      			<label class="col-sm-4 control-label"></label>
      			</div>
        		<div id="d_code" name="d_code" class="form-group" >
	      			<label class="col-sm-2 control-label"></label>
	      			<label class="col-sm-2 control-label">验证码：</label>
	      			<div class="col-sm-4">
	       			<input type="text" name="p_code" id="p_code" class="form-control" onfocus="set_msg('code_msg','');"  placeholder="验证码"/>
				    <img src="createImage.do" alt="验证码" title="点击更换" id="code_image" onclick="change();"/>
	      			</div>
	      			<label class="col-sm-4 control-label"></label>
      			</div>
      			<div align="center">
      				<label class="col-sm-2 control-label"></label>
      				<label class="col-sm-2 control-label"></label>
      				<div class="col-sm-4">
	      			<button id="login" class="btn btn-lg btn-primary btn-block" onclick="javascript:check_login();">登  录</button>
      				<button id="register" class="btn btn-lg btn-primary btn-block" onclick="javascript:to_register();">注  册</button>
      				</div>
      				<label class="col-sm-4 control-label"></label>
      			</div>
            </form>
        </div><br /><br />
        <div align="center">版权所有&copy;工商银行山西省分行</div>
    </body>
</html>