<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var registerform;
        	
        	function cancleBind(){
        		document.getElementById("cancelfrom").action = "login/cancelBind.do";
        		document.getElementById("cancelfrom").submit();
        	}
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	
        	function js_onLoad(){
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			if(alertMsg != null){
        		%>
        		$.alert({title:"查询结果",type:'red',content:"<%=alertMsg%>"});
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br />
    	<div align="center">
	      	<form action="" method="post" id="cancelfrom" class="form-horizontal" role="form" style="width: 95%">
	     		<input type="hidden" id="p_devid" name="p_devid" value="${cancleInfo.devid}"/>
	     		<input type="hidden" id="p_type" name="p_type" value="${cancleInfo.type}"/>
	     		<table class="table table-striped table-bordered table-hover">
	  					<tr >
	  						<c:choose>
								<c:when test="${cancleInfo.devid==0}"><th colspan="2">投注机号绑定信息</th></c:when>
		 						<c:otherwise><th colspan="2">投注站号绑定信息</th></c:otherwise>
							</c:choose>
	  					</tr>
	  					<tr>
		   					<td><p><a>投注机(站)号</a></p></td>
		   					<td>${cancleInfo.devid}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>投注机(站)地址</a></p></td>
		   					<td>${cancleInfo.devidaddr}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>负责人</a></p></td>
		   					<td>${cancleInfo.username}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>彩票类型</a></p></td>
		   					<c:choose>
								<c:when test="${cancleInfo.type==0}"><td>电脑彩票</td></c:when>
		 						<c:when test="${cancleInfo.type==1}"><td>网点即开票</td></c:when>
	 							<c:otherwise><td>彩票类型未知</td></c:otherwise>
							</c:choose>
	  					</tr>
	  					<tr>
		   					<td><p><a>银行卡号</a></p></td>
		   					<td>${cancleInfo.accountno}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>密码</a></p></td>
		   					<td><input type="password" name="p_pwd" id="p_pwd" class="form-control" value="" placeholder="请输入用户密码"/></td>
	  					</tr>
	            </table>
	          </form>
          </div>
          <hr />
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="cancleBind();">解　绑</button>
 			</div>
     	  </div>
	</body>
</html>