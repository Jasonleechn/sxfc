<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	var detailform;
        	
        	function oneDetails(devid,lsh){
        		window.location.href = "search/oneDetail.do?p_devid="+devid+"&p_lsh="+lsh;
        		/*document.getElementById("detailform").action = "oneDetail.do?p_devid="+devid+"&p_lsh="+lsh;
        		/*document.getElementById("detailform").submit;*/
        		//document.getElementById("detailform").submit();
        	}
        	
        	function searchByDate(){
        		document.getElementById("detailform").action = "search/getDetails.do";
        		document.getElementById("detailform").submit;
        	}
        	
        	/*function prePage(){
        		document.getElementById("detailform").action = "getPayDetails.do?currentPage=${trxDetailPage.currentPage+1}&cardno=${trxDetailPage.paycardno}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}";
        		document.getElementById("detailform").submit();
        	}
        	function nextPage(){
        		document.getElementById("detailform").action = "getPayDetails.do?currentPage=${trxDetailPage.currentPage-1}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}";
        		document.getElementById("detailform").submit();
        	}*/
        	
        	function back(){
        		window.location.href = "search/queryDetails.do";
        		/*document.getElementById("detailform").action = "queryDetails.do";
        		document.getElementById("detailform").submit();*/
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
        		$.alert({title:'提示信息!',style:'red',content:"<%=alertMsg%>"});
        		<%}
        		  	if(s_alertMsg != null){%>
        		$.alert({title:'提示信息!',style:'red',content:"<%=s_alertMsg%>"});
        		<%}
        		session.removeAttribute("s_alertMsg");
        		%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br/><br/><br/>
    	<div class="table-responsive" align="center" >
        	<!--<form action="" method="post" id="detailform" class="form-signin" role="form">-->
	            <table class="table table-bordered table-striped table-hover">
	                <thead>
    					<tr>
	    					<th><p><a>序号</a></p></th>
	    					<th><p>
	    						<c:choose>
			            			<c:when test="${accInfo.type== '0'}"><a>投注机号缴款记录</a></c:when>
			            			<c:otherwise><a>投注站号缴款记录</a></c:otherwise>
		            			</c:choose>
	    						</p></th>
    					</tr>
	    			</thead>
    				<tbody>
    					<c:forEach items="${trxDetails }" var="details" varStatus="vs">
	    					<tr onclick="oneDetails('${details.devid}','${details.lsh}');">
	    						<td>${(trxDetailPage.currentPage-1)*trxDetailPage.pageSize+(vs.index+1)}</td>
	    						<td><p class="text-left">
	    								<strong>
	    									<c:choose>
				            					<c:when test="${details.type== '0'}">投注机号</c:when>
				            					<c:otherwise>投注站号</c:otherwise>
		            						</c:choose>
	    									:${details.devid}
	    								</strong></p>
	    							<p class="text-left">
	    							<small>缴款时间：${details.workdate}</small><br />
	    							<small>金额：${details.amt} (元)</small><br />
	    							<small>状态：
	    							<c:choose>
		    							<c:when test="${details.trx_flag==0 }"><a style="color: #5cb85c">成功</a></c:when>
			    						<c:when test="${details.trx_flag==1 }"><a style="color: #d9534f">失败</a></c:when>
			    						<c:when test="${details.trx_flag==2 }"><a style="color: #f0ad4e">未知</a></c:when>
		    						<c:otherwise><a style="color: #d9534f">异常</a></c:otherwise>
	    							</c:choose>
	    							</small></p>
	    						</td>
	    					</tr>
	    				</c:forEach>
    				</tbody>
	            </table>
   			 	<div class="form-group">
		            <nav>
		            	<ul class="pager">
		            	<li>
			            	<a href="search/getPayDetails.do?currentPage=1&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1">首页</a>
	           			</li>
			            	<c:choose>
			            		<c:when test="${trxDetailPage.currentPage==1}">
			            			<li>
			            			<a href="search/getPayDetails.do?currentPage=1&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1" class="pager">上一页</a>
			            			</li>
			            		</c:when>
			            		<c:otherwise>
			            		<li>
			            			<a href="search/getPayDetails.do?currentPage=${trxDetailPage.currentPage-1}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1" >上一页</a>
		            			</li>
			            		</c:otherwise>
			            	</c:choose>
			            	<li>
		            			<a>
		            			<c:choose>
			            			<c:when test="${trxDetailPage.currentPage<1}">0/${trxDetailPage.totalPage}</c:when>
			            			<c:when test="${trxDetailPage.currentPage==trxDetailPage.totalPage}">${trxDetailPage.totalPage}/${trxDetailPage.totalPage}</c:when>
			            			<c:otherwise>${trxDetailPage.currentPage}/${trxDetailPage.totalPage}</c:otherwise>
		            			</c:choose>
		            			</a>
	            			</li>
			            	<c:choose>
			            		<c:when test="${trxDetailPage.currentPage==trxDetailPage.totalPage}">
			            		<li>
			            			<a href="search/getPayDetails.do?currentPage=${trxDetailPage.currentPage+1}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1" class="pager">下一页</a>
		            			</li>
			            		</c:when>
			            		<c:otherwise>
			            		<li>
			            			<a href="search/getPayDetails.do?currentPage=${trxDetailPage.currentPage+1}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1" >下一页</a>
		            			</li>
			            		</c:otherwise>
			            	</c:choose>
			            	<li>
			            		<a href="search/getPayDetails.do?currentPage=${trxDetailPage.totalPage}&beginDate=${trxDetailPage.beginDate}&endDate=${trxDetailPage.endDate}&p_devid=${accInfo.devid}&p_type=${accInfo.type}&p_flag=1" >末页</a>
	            			</li>
			            	</ul>
		            </nav>
   			 	</div>
    			 <div class="form-group" align="center" style="width: 75%">
	  				<div >
		   				<button class="btn btn-lg btn-danger btn-block" onclick="back();">返　回</button>
	  				</div>
      			</div>
        </div>
    </body>
</html>