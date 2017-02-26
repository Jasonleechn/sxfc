<%@page pageEncoding="utf-8"%>
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
	    
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	function toUpdatePwd(){
        		document.getElementById("updateform").action = "toUpdatePwd.do";
        		document.getElementById("updateform").submit();
        	}
        	function updatePerInfo(){
        		document.getElementById("updateform").action = "updatePerInfo.do";
        		document.getElementById("updateform").submit();
        	}
        	function back(){
        		document.getElementById("updateform").action = "../login/showList.do";
        		document.getElementById("updateform").submit();
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
    	<h1 class="page-header">个人信息修改</h1>
    	<ul class="nav nav-tabs" role="tablist">
    		<li role="presentation" class="active"><a>修改个人信息</a></li>
    		<li role="presentation" id="n_pwd"><a onclick="toUpdatePwd();">修改登录密码</a></li>
    	</ul>
    	<label class="col-sm-2 control-label"></label>
    	<div class="col-sm-8">
    	<form action="" method="post" id="updateform" name="updateform" class="form-signin" role="form">
    	<h2 class="form-signin-heading">个人信息修改</h2>
       	<table class="table table-striped table-hover table-bordered">
      		 <tr>
           	<td><span class="btn btn-large">账号：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_devid" id="p_devid" class="form-control" value="${loginAcc.devid}" disabled />
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">用户名称：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_username" id="p_username" class="form-control" value="${loginAcc.username}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">证件号码：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_idcard" id="p_idcard" class="form-control" value="${loginAcc.personid}" />
              	</p>
               </td>
           </tr>
       </table>
       <div align="center" class="col-sm-12">
			 <div class="col-sm-12">
	 			<button class="btn btn-lg btn-primary" onclick="javascript:updatePerInfo();" style="width: 40%">提　交</button>
				<button class="btn btn-lg btn-primary" onclick="back();" style="width: 40%">返　回</button>
			</div>
		</div>
    	</form>
    	</div>
    	<label class="col-sm-2 control-label"></label>
    </body>
</html>