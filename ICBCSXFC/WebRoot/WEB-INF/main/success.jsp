<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    
	    <title>山西福彩工商银行缴费查询系统</title>
        <script language="javascript" type="text/javascript">
	        function js_onLoad(){
	        		document.oncontextmenu = function(){return false;};
	        		<%
	        			String alertMsg = (String)request.getAttribute("alertMsg");
	        			if(alertMsg != null){
	        		%>
	        		window.alert("<%=alertMsg%>");
	        		<%}%>
	        		window.location.href = "login/toLogin.do";
	        	}
        </script> 
    </head>
    
    <body onload="javascript:js_onLoad();">
    
    </body>
</html>