<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        
        	//登录校验
        	function register() {
        	
        		//1.判断账号是否为空
        		var account = $("#p_devid").val();
        		if(account == ""){
        		$.alert({title:'错误',type:'red',content:"投注机号或站号不能为空!"});
        			$("#d_devid").removeClass("has-success has-feedback");
    				$("#p_devid_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_devid").addClass("has-error has-feedback");
    				$("#p_devid_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
    			}else{
    				$("#d_devid").removeClass("has-error has-feedback");
    				$("#p_devid_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
    				$("#d_devid").addClass("has-success has-feedback");
    				$("#p_devid_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        		}
        	
        		//2.判断身份证格式是否正确
        		var idcard = $("#p_idcard").val();
        		var reg = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
            	if(!reg.test(idcard)){
            		$.alert({title:'错误',type:'red',content:"身份证号格式不正确!"});
            		$("#d_idcard").removeClass("has-success has-feedback");
        			$("#p_idcard_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_idcard").addClass("has-error has-feedback");
    				$("#p_idcard_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
            		return;
            	} else{
            		$("#d_idcard").removeClass("has-error has-feedback");
        			$("#p_idcard_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_idcard").addClass("has-success has-feedback");
        			$("#p_idcard_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
            	}
            	
            	//3.校验密码
        		var pwd  = document.getElementById("p_pwd").value; 
       			var repwd = document.getElementById("p_repwd").value;
        		if(pwd.length < 6){
        			$.alert({title:'错误',type:'red',content:"密码长度不能小于6位!"});
        			$("#d_pwd").removeClass("has-success has-feedback");
        			$("#p_pwd_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
        			$("#d_pwd").addClass("has-error has-feedback");
        			$("#p_pwd_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return false;
        		}else if(pwd != repwd){
        			if(pwd != repwd) $.alert({title:'错误',type:'red',content:"两次输入密码不一致!"});
        			$("#d_repwd").removeClass("has-success has-feedback");
        			$("#p_repwd_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_repwd").addClass("has-error has-feedback");
    				$("#p_repwd_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return false;
        		}else{
        			$("#d_pwd").removeClass("has-error has-feedback");
        			$("#p_pwd_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_pwd").addClass("has-success has-feedback");
        			$("#p_pwd_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        			$("#d_repwd").removeClass("has-error has-feedback");
        			$("#p_repwd_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
        			$("#d_repwd").addClass("has-success has-feedback");
        			$("#p_repwd_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        			
	        	}
	        	
        		//4.判断是否勾选协议
        		var xy = document.getElementById("p_xy");
	        	if(xy!=null && !xy.checked){
	        		$.alert({title:'错误',type:'red',content:"请您先阅读并勾选注册协议!"});
	        		return;
	        	}
	        	
        		document.getElementById("myform").action = "login/regDevid.do";
        		document.getElementById("myform").submit();
	        	
        	}
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	
        	function js_onLoad(){
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			String s_alertMsg = (String)session.getAttribute("s_alertMsg");
        			if(alertMsg != null){
        		%>
        		$.alert({title:'信息提示!',type:'red',content:"<%=alertMsg%>"});
        		<%}
        		  	if(s_alertMsg != null){%>
        		$.alert({title:'信息提示!',type:'red',content:"<%=s_alertMsg%>"});
        		<%}
        		session.removeAttribute("s_alertMsg");
        		%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br /><br /><br />
      	<form action="" method="post" id="myform" class="form-horizontal" role="form" >
      		<div id="d_devid" class="form-group"  align="center">
      			<div>
       			<input type="text" name="p_devid" id="p_devid" class="form-control" style="width: 80%" placeholder="投注机号或站号(注册绑定)" maxlength="20"/>
      			<span id="p_devid_msg" ></span>
      			</div>
      		</div>
      		<div id="d_idcard" class="form-group" align="center">
      			<div>
       			<input type="text" name="p_idcard" id="p_idcard" class="form-control" style="width: 80%" placeholder="身份证号" maxlength="18"/>
      			<span id="p_idcard_msg" ></span>
      			</div>
      		</div>
      		<div id="d_pwd" class="form-group" align="center">
      			<div>
       			<input type="password" name="p_pwd" id="p_pwd" class="form-control " style="width: 80%" placeholder="设置用户密码(6位)" maxlength="30"/>
      			<span id="p_pwd_msg" ></span>
      			</div>
      		</div>
      		<div id="d_repwd" class="form-group" align="center">
      			<div>
       			<input type="password" name="p_repwd" id="p_repwd" class="form-control " style="width: 80%" placeholder="再次输入用户密码" maxlength="30"/>
      			<span id="p_repwd_msg" ></span>
      			</div>
      		</div>
      		<div align="center" class="checkbox">
      			<label>
      				<input id="p_xy" name="p_xy" type="checkbox" value="1"/>　
      				<!-- <a href="#">我已阅读《XXX协议》</a>
		      		Button trigger modal -->
					<button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">
					  我已阅读《客户须知》
					</button>
      			</label>
      		</div>
      		<br />
          </form>
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:register();">确　认</button>
 			</div>
     	  </div>
          <!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true" style="width: 95%">
			<br /><br /><br /><br />
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">
							山西福彩相关协议
						</h4>
					</div>
					<div class="modal-body">
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
						这里有好多福彩的协议...<br />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							Close
						</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>