<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var detailform;
        	//撤销冲正
        	function chongzheng(devid,lsh){
        		alert("lsh:"+lsh);
        		window.location.href = "pay/chongZheng.do?p_devid="+devid+"&p_lsh="+lsh;
        		//document.getElementById("oneDtlform").action = "../pay/chongZheng.do";
	        	//document.getElementById("oneDtlform").submit();
        	}
        	
        	//返回
        	function reback(){
		   		document.getElementById("oneDtlform").action = "search/getPayDetails.do";
	        	document.getElementById("oneDtlform").submit();
        	}
        	
        	function js_onLoad(){
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
    	<br /><br /><br />
    		<div align="center">
        	<form action="" method="post" id="oneDtlform" class="form-signin" role="form" style="width: 95%" >
        		<input type="hidden" id="p_devid" name="p_devid" value="${oneDtl.devid}"/>
        		<input type="hidden" id="p_flag" name="p_flag" value="${oneDtl.devid}"/>
        		<input type="hidden" id="p_type" name="p_type" value="${oneDtl.type}"/>
        		<input type="hidden" id="p_lsh" name="p_lsh" value="${oneDtl.lsh}"/>
	            <table class="table table-striped table-bordered table-hover">
   					<tr>
    					<td><p><a>流水号</a></p></td>
    					<td>${oneDtl.lsh}</td>
   					</tr>
   					<tr>
    					<td><p><a>投注站机(站)号</a></p></td>
    					<td>${oneDtl.devid}</td>
   					</tr>
   					<tr>
    					<td><p><a>付款人名称</a></p></td>
    					<td>${oneDtl.username}</td>
   					</tr>
   					<tr>
    					<td><p><a>付款卡号</a></p></td>
    					<td>${oneDtl.paycardno}</td>
   					</tr>
   					<tr>
    					<td><p><a>付款金额</a></p></td>
    					<td>${oneDtl.amt}</td>
   					</tr>
   					<tr>
    					<td><p><a>彩票类型</a></p></td>
	   					<c:choose>
							<c:when test="${oneDtl.type==0}"><td>电脑彩票</td></c:when>
	 						<c:when test="${oneDtl.type==1}"><td>网点即开票</td></c:when>
	 						<c:otherwise><td>彩票类型未知</td></c:otherwise>
						</c:choose>
   					</tr>
   					<tr>
    					<td><p><a>交易日期</a></p></td>
    					<td>${oneDtl.workdate}</td>
   					</tr>
   					<tr>
    					<td><p><a>交易状态</a></p></td>
    					<c:choose>
   							<c:when test="${oneDtl.trx_flag==0 }"><td style="color: #5cb85c">成功</td></c:when>
    						<c:when test="${oneDtl.trx_flag==1 }"><td style="color: #d9534f">失败</td></c:when>
    						<c:when test="${oneDtl.trx_flag==3 }"><td style="color: #f0ad4e">已撤销</td></c:when>
    						<c:otherwise><td style="color: #d9534f">异常</td></c:otherwise>
   						</c:choose>
   					</tr>
	            </table>
            </form>
            </div>
        	<div align="center">
	          <div align="center" class="form-group" style="width: 75%"  >
	            <!-- 考虑到当天缴款完之后，投注站会出现立马把额度用完的情况，所以取消当日冲正功能 -->
  				<!--<c:if test="${oneDtl.trx_flag==0}">
  					<c:if test="${isToday}">
		   				<button  class="btn btn-lg btn-danger btn-block" onclick="javascript:chongzheng('${oneDtl.devid}','${oneDtl.lsh}');">撤　销</button>
  					</c:if>
  				</c:if>
  				-->
  				<button class="btn btn-lg btn-danger btn-block" onclick="javascript:reback();">返　回</button>
      		</div>
      	  </div>
    </body>
</html>