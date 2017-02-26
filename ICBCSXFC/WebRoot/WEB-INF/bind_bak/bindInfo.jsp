<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        	var registerform;
        	
        	function bindInfos(){
        		var p_accno = $("#p_accno").val();
        		if('' == p_accno){
        			window.alert("银行卡号不能为空!");
        			return false;
        		}
        		document.getElementById("bindform").action = "bind.do";
        		document.getElementById("bindform").submit();
        	}
        	
        	function back(){
        		document.getElementById("bindform").action = "../login/showList.do";
        		document.getElementById("bindform").submit();
        	}
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	
        	function js_onLoad(){
        		myform = document.getElementById("bindform");
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
   		<label class="col-sm-2 control-label"></label>
    	<div class="table-responsive col-sm-8">
        	<form action="" method="post" id="bindform" class="form-signin" role="form">
        		<h2 class="page-header">缴费账号绑定</h2>
	            <table class="table table-striped table-hover table-bordered">
	                <tr>
	                	<td><span class="btn btn-large">投注站机号：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_account" id="p_account" class="form-control" value="${loginAcc.devid}" disabled/>
	                   		</p>
	                    </td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">负责人姓名：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_username" id="p_username" class="form-control" value="${loginAcc.username}" disabled />
	                   		</p>
	                    </td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">证件号码：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_idcard" id="p_idcard" class="form-control" value="${loginAcc.personid}" disabled />
	                   		</p>
	                    </td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">银行卡号：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_accno" id="p_accno" class="form-control" placeholder="输入银行卡号" />
	                   		</p>
	                    </td>
	                </tr>
	            </table>
	            <div align="center" class="col-sm-12">
			<!--<label class="col-sm-2 control-label"></label>
			--><div class="col-sm-12">
	 			<button class="btn btn-lg btn-primary" onclick="javascript:bindInfos();" style="width: 40%">申请绑定</button>
				<button class="btn btn-lg btn-primary" onclick="back();" style="width: 40%">返　回</button>
			</div>
			<!--<label class="col-sm-4 control-label"></label>
     --></div>
            </form>
        </div>
        <label class="col-sm-2 control-label"></label>
    </body>
</html>