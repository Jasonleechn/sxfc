<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String devid_apply = (String)session.getAttribute("devid");
	if(devid_apply == null){devid_apply = "";}
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	    <meta name="description" content=""/>
	    <meta name="author" content=""/>
	    <!-- 新bootstrap 核心css文件 -->
	    <link href="../dist/css/bootstrap.min.css" rel="stylesheet"/>
	    <!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
        <script type="text/javascript" language="javascript" src="../js/jquery-1.11.1.js"></script>
	   
	    <!-- Bootstrap core CSS -->
	    <script type="text/javascript" language="javascript" src="../dist/js/bootstrap.min.js"></script>
	    
	    <!--<link rel="icon" href="http://v3.bootcss.com/favicon.ico"/>-->
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var registerform;
        	
        	function check_account(){
        	
        		//判断是否为空
        		var account = $("#p_account").val();
        		if(account == ""){
        			$("#d_account").addClass("has-error has-feedback");
        			$("#p_account_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
        			window.alert("投注站编号不能为空!");
        			return;
        		}
        		
        		$.post(
        			"searchDevid.do",
        			{p_account:account},
        			function(data){
        				if(data==""){
        					$("#d_account").removeClass("has-error has-feedback");
        					$("#p_account_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        					$("#d_account").addClass("has-success has-feedback");
        					$("#p_account_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        					return;
        					
        				}else{
        					$("#d_account").removeClass("has-success has-feedback");
        					$("#p_account_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
        					$("#d_account").addClass("has-error has-feedback");
        					$("#p_account_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
        					window.alert("["+account+"],已被注册!");
        					return;
        				}
        			}
        		);
        	}
        	//校验密码
        	function check_pwd(){
        		var pwd  = document.getElementById("p_pwd").value;
        		if(pwd.length < 6){
        			//window.alert("密码须大于6位!");
        			$("#d_pwd").removeClass("has-success has-feedback");
        			$("#p_pwd_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
        			$("#d_pwd").addClass("has-error has-feedback");
        			$("#p_pwd_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return false;
        		}else{
        			$("#d_pwd").removeClass("has-error has-feedback");
        			$("#p_pwd_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_pwd").addClass("has-success has-feedback");
        			$("#p_pwd_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        			return;
        		}
        	}
        	
        	//校验密码
        	function check_repwd(){
        		var pwd  = document.getElementById("p_pwd").value;
        		var repwd = document.getElementById("p_repwd").value;
        		if(pwd != repwd || (repwd.length<6)){
        			if(pwd != repwd) window.alert("两次输入密码不一致!");
        			$("#d_repwd").removeClass("has-success has-feedback");
        			$("#p_repwd_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_repwd").addClass("has-error has-feedback");
    				$("#p_repwd_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return false;
        		}else{
        			$("#d_repwd").removeClass("has-error has-feedback");
        			$("#p_repwd_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_repwd").addClass("has-success has-feedback");
        			$("#p_repwd_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        			return;
        		}
        	}
        
        	//登录校验
        	function register() {
        		var username  = document.getElementById("p_username").value;
        		if(username == ''){
        			window.alert("用户名(站点负责人)不能为空!");
        			$("#d_username").removeClass("has-success has-feedback");
        			$("#p_username_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_username").addClass("has-error has-feedback");
    				$("#p_username_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
        		}else{
        			$("#d_username").removeClass("has-error has-feedback");
        			$("#p_username_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_username").addClass("has-success has-feedback");
        			$("#p_username_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        		}
        		var username  = document.getElementById("p_idcard").value;
        		if(username == ''){
        			window.alert("证件号码不能为空!");
        			$("#d_idcard").removeClass("has-success has-feedback");
        			$("#p_idcard_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_idcard").addClass("has-error has-feedback");
    				$("#p_idcard_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
        		}else{
        			$("#d_idcard").removeClass("has-error has-feedback");
        			$("#p_idcard_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_idcard").addClass("has-success has-feedback");
        			$("#p_idcard_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        		}
        		var devidName  = document.getElementById("p_devidName").value;
        		if(devidName == ''){
        			window.alert("投注站名称(投注站地址)不能为空!");
        			$("#d_devidName").removeClass("has-success has-feedback");
        			$("#p_devidName_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_devidName").addClass("has-error has-feedback");
    				$("#p_devidName_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
        		}else{
        			$("#d_devidName").removeClass("has-error has-feedback");
        			$("#p_devidName_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_devidName").addClass("has-success has-feedback");
        			$("#p_devidName_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        		}
        		document.getElementById("registerform").action = "register.do";
        		document.getElementById("registerform").submit();
        	}
        	
        	//校验身份证号码
        	/*function check_idcard(){
        		var idcard = $("#idcard").val();
        		var reg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
            	if(!reg.test(idcard)){
            		$("#idcard_msg").text("身份证号格式不正确！").addClass("btn btn-danger");
            		return;
            	} 
        	}*/
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	
        	
        	
        	function js_onLoad(){
        		myform = document.getElementById("registerform");
        		/*myform.admin_code.focus();//默认焦点*/
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			if(alertMsg != null){
        		%>
        		window.alert("<%=alertMsg%>");
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
   		<h2 class="form-signin-heading" align="center">用户注册</h2>
      	<form action="" method="post" id="registerform" class="form-horizontal" role="form">
      		<div id="d_account" name="d_account" class="form-group" >
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">投注站机号:</label>
      			<div class="col-sm-4">
       			<input type="text" name="p_account" id="p_account" class="form-control" onblur="check_account();" placeholder="投注站机号"/>
			    <span id="p_account_msg" name="p_account_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_devidName" name="d_devidName" class="form-group" >
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">投注站名称:</label>
      			<div class="col-sm-4">
       			<input type="text" name="p_devidName" id="p_devidName" class="form-control"  placeholder="福彩投注站地址"/>
			    <span id="p_devidName_msg" name="p_devidName_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_pwd" name="d_pwd" class="form-group" >
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">密　码：</label>
      			<div class="col-sm-4">
       			<input type="password" name="p_pwd" id="p_pwd" class="form-control " onblur="check_pwd();" placeholder="登录密码,小于30位"/>
			    <span id="p_pwd_msg" name="p_pwd_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_repwd" name="d_repwd" class="form-group">
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">重复密码：</label>
      			<div class="col-sm-4">
       			<input type="password" name="p_repwd" id="p_repwd" class="form-control " onblur="check_repwd();" placeholder="重复密码" />
			    <span id="p_repwd_msg" name="p_repwd_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_username" name="d_username" class="form-group">
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">负责人姓名：</label>
      			<div class="col-sm-4">
       			<input type="text" name="p_username" id="p_username" class="form-control"  placeholder="负责人姓名" />
			    <span id="p_username_msg" name="p_username_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_idcard" name="d_idcard" class="form-group" >
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">证件号码：</label>
      			<div class="col-sm-4">
       			<input type="text" name="p_idcard" id="p_idcard" class="form-control"  placeholder="填银行卡开户证件" />
			    <span id="p_idcard_msg" name="p_idcard_msg"></span>
      			</div>
      			<label class="col-sm-4 control-label"></label>
      		</div>
      		<div id="d_zoneno" name="d_zoneno" class="form-group" >
      			<label class="col-sm-2 control-label"></label>
      			<label class="col-sm-2 control-label">所属地区：</label>
      			<div class="col-sm-4">
      				<select class="form-control" id="p_zoneno" name="p_zoneno" >
                   		<option value=''>---请选择地区---</option>
                   		<c:forEach items="${zoneList }" var="zonelist" >
                   			<option value='${zonelist.zoneno}'>${zonelist.zonename}</option>
                   		</c:forEach>
                  	</select>
      			</div>
      		</div>
      		<div align="center">
  				<label class="col-sm-2 control-label"></label>
  				<label class="col-sm-2 control-label"></label>
  				<div class="col-sm-4">
   				<button class="btn btn-lg btn-primary btn-block" onclick="javascript:register();">提　交</button>
  				<button class="btn btn-lg btn-primary btn-block" onclick="history.back();">返　回</button>
  				</div>
  				<label class="col-sm-4 control-label"></label>
      		</div>
          </form>
    </body>
</html>