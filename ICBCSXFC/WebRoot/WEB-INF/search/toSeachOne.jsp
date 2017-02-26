<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var registerform;
        
        	//登录校验
        	function search() {
        	
        		//彩票类型
        		var type = $(":input:radio:checked").val();
        		$("#p_type").val(type);
        	
        		//1.判断账号是否为空
        		var account = $("#p_account").val();
        		if(account == ""){
        		$.alert({title:'错误',type:'red',content:"投注机号不能为空!"});
        			$("#d_account").removeClass("has-success has-feedback");
    				$("#p_account_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_account").addClass("has-error has-feedback");
    				$("#p_account_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
    			}else{
    				$("#d_account").removeClass("has-error has-feedback");
    				$("#p_account_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
    				$("#d_account").addClass("has-success has-feedback");
    				$("#p_account_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
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
	        	
        		document.getElementById("searchform").action = "search/searchOne.do";
        		document.getElementById("searchform").submit();
	        	
        	}
        	
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
        		$.alert({title:"查询结果",content:"<%=alertMsg%>"});
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br /><br /><br />
    	<div align="center">
      	<form action="" method="post" id="searchform" class="form-horizontal" role="form" style="width: 95%">
      		<input type="hidden" id="p_type" name="p_type" value=""/>
      		<div id="d_account" name="d_account" class="form-group"  align="center">
      			<div>
       			<input type="text" name="p_account" id="p_account" class="form-control" style="width: 80%" placeholder="投注机号或站号(查询信息)"/>
      			<span id="p_account_msg" name="p_account_msg"></span>
      			</div>
      		</div>
      		<div id="d_idcard" name="d_idcard" class="form-group" align="center">
      			<div>
       			<input type="text" name="p_idcard" id="p_idcard" class="form-control" style="width: 80%" placeholder="身份证号" />
      			<span id="p_idcard_msg" name="p_idcard_msg"></span>
      			</div>
      		</div>
      		<hr />
           <div class="btn-group" data-toggle="buttons">
			  <label class="btn btn-default active">
			    <input type="radio" name="options" id="option1" value="0" checked>　电脑型彩票　</input>
			  </label>
			  <label class="btn btn-default">
			    <input type="radio" name="options" id="option2" value="1">　网点即开票　</input>
			  </label>
			</div>
          </form>
      		<br />
          </div>
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:search();">确　认</button>
 			</div>
     	  </div>
	</body>
</html>