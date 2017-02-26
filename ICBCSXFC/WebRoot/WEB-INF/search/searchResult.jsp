<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var registerform;
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	
        	//返回
        	function reback(){
		   		document.getElementById("myfrom").action = "search/toSearchOne.do";
	        	document.getElementById("myfrom").submit();
        	}
        	
        	function js_onLoad(){
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
    	<br /><br /><br />
    	<div align="center">
	      	<form action="" method="post" id="myfrom" class="form-horizontal" role="form" style="width: 95%">
	     		<input type="hidden" id="p_cardno" name="p_cardno" value=""/>
	     		<table class="table table-striped table-bordered table-hover">
	  					<tr >
	  						<c:choose>
								<c:when test="${devidInfo.type==0}"><th colspan="2">投注机号绑定信息</th></c:when>
		 						<c:otherwise><th colspan="2">投注站号绑定信息</th></c:otherwise>
							</c:choose>
	  					</tr>
	  					<tr>
	  						<c:choose>
								<c:when test="${devidInfo.type==0}"><td><p><a>投注机号</a></p></td></c:when>
		 						<c:otherwise><td><p><a>投注站号</a></p></td></c:otherwise>
							</c:choose>
		   					<td>${devidInfo.devid}</td>
	  					</tr>
	  					<tr>
		   					<td><p>
		   						<c:choose>
			            			<c:when test="${devidInfo.devidaddr} == '0'"><a>投注机地址</a></c:when>
			            			<c:otherwise><a>投注站地址</a></c:otherwise>
		            			</c:choose>
		   						</p>
		   					</td>
		   					<td>${devidInfo.devidaddr}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>负责人</a></p></td>
		   					<td>${devidInfo.username}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>彩票类型</a></p></td>
		   					<c:choose>
								<c:when test="${devidInfo.type==0}"><td>电脑彩票</td></c:when>
		 						<c:when test="${devidInfo.type==1}"><td>网点即开票</td></c:when>
		 						<c:otherwise><td>彩票类型未知</td></c:otherwise>
							</c:choose>
		   				</tr>
	  					<tr>
		   					<td><p><a>银行卡号</a></p></td>
		   					<td>${devidInfo.accountno}</td>
	  					</tr>
	            </table>
	          </form>
          </div>
          <hr />
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:reback();">返　回</button>
 			</div>
     	  </div>
	</body>
</html>