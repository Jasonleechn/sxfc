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
        		
        		type = $("#p_type").val();
        		if(''==type || type==null){
        			$.alert({title:'错误提示!',type:'red',content:'请选择彩票类型!'});
        			return ;
        		}
        	
        		document.getElementById("cancleform").action = "search/searchResult.do";
        		document.getElementById("cancleform").submit();
	        	
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
        		$.alert({title:"信息提示",type:'red',content:"<%=alertMsg%>"});
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br /><br /><br />
    	<div align="center">
      	<form action="" method="post" id="cancleform" class="form-horizontal" role="form" style="width: 95%">
      		<input type="hidden" id="p_type" name="p_type" value=""/>
      		<div id="d_account" name="d_account" class="form-group"  align="center">
      			<div>
       			<input type="text" name="p_account" id="p_account" class="form-control" style="width: 80%" placeholder="投注机号或站号(解绑)"/>
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