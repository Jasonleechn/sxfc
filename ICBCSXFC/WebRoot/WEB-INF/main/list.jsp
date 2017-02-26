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
	    <link href="${pageContext.request.contextPath}/dist/css/bootstrap.min.css" rel="stylesheet"/>
	    <!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
	   
	    <!-- Bootstrap core CSS -->
	    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/dist/js/bootstrap.min.js"></script>
	    
	    <title>山西福彩工商银行缴费查询系统</title>
	
        <script type="text/javascript" language="javascript">
        	/*选择操作类型*/
        	function chooseOperType(devid,cardno,status){
        		if(status !='1'){
        			var action = window.confirm("确定删除此条记录?");
        			if(action == true){
        				alert("此功能敬请期待...");
        				//window.location.href="../oper/deleteOne.do?devid="+devid+"&cardno="+cardno;
        			}
        			return false;
        		}
        		window.location.href="../oper/operList.do?devid="+devid+"&cardno="+cardno;
        	}
        	
        	function toBind(){
        		document.getElementById("listform").action = "../bind/toBind.do";
        		document.getElementById("listform").submit;
        	}
        	function toUpdate(){
        		document.getElementById("listform").action = "../update/toUpdatePerInfo.do";
        		document.getElementById("listform").submit;
        	}
        	
        	function quite(){
        		document.getElementById("listform").action = "../login/quite.do";
        		document.getElementById("listform").submit();
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
    <body  onload="javascript:js_onLoad();">
		<h1 class="page-header">山西福彩缴费查询系统</h1>
   		<!--<div class="hero-unit" style="background-color: #428bca;color: white" >
   			<p>提供福彩的缴费、明细查询、银行卡绑定等功能</p>
   		</div>
   		-->
    		<div class="table-responsive">
	   		<form action="" method="post" id="listform" class="form-signin" role="form">
    			<table class="table table-bordered table-hover table-condensed">
    				<thead>
    					<tr>
	    					<th><p><a>序号</a></p></th>
	    					<th><p><a>投注站编号</a></p></th>
	    					<th><p><a>户名</a></p></th>
	    					<th><p><a>绑定卡号</a></p></th>
	    					<th><p><a>绑定状态</a></p></th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach items="${bindInfos }" var="bindInfo" varStatus="vs">
	    					<tr onclick="chooseOperType('${bindInfo.devid}','${bindInfo.cardno }','${bindInfo.status }');">
	    						<td>${vs.index + 1}</td>
	    						<td>${bindInfo.devid}</td>
	    						<td>${bindInfo.username}</td>
	    						<td>${bindInfo.cardno }</td>
	    						<c:choose >
	    							<c:when test="${bindInfo.status==1 }"><td>绑定成功</td></c:when>
	    							<c:otherwise><td style="color: red">失败--${bindInfo.errmsg}</td></c:otherwise>
	    						</c:choose>
	    					</tr>
	    				</c:forEach>
    				</tbody>
    				<tr><td colspan="12">* 须绑定账号之后才能进行缴费</td></tr>
    				<tr>
	                    <td colspan="12">
			    			<button class="btn btn-info" style="width:45%;margin-left:3%" onclick="javascript:toBind();">申请绑定</button>
			    			<button class="btn btn-info" style="width:45%;;margin-left:3%" onclick="javascript:toUpdate();">个人信息修改</button>
		               		<p><button class="btn btn-md btn-primary btn-block" style="margin-top:20px" onclick="javascript:quite();">退　出</button></p>
	                    </td>    
	                </tr>
    			</table>
	   		</form>
    		</div>
    </body>
</html>