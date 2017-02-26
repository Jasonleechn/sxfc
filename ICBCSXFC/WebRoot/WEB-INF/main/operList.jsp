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
        	var myform;
        	function toPay(){
        		document.getElementById("listform").action = "toPay.do";
        		document.getElementById("listform").submit();
        	}
        	
        	function getAllDetails(){
        		document.getElementById("listform").action = "getDetails.do?currentPage=1";
        		document.getElementById("listform").submit();
        	}
        	function toCancel(){
        		document.getElementById("listform").action = "toCancel.do";
        		document.getElementById("listform").submit();
        	}
        	
        	function back(){
        		document.getElementById("listform").action = "../login/showList.do";
        		document.getElementById("listform").submit();
        	}
        </script>
    </head>
    <body>
    	<ul class="nav nav-tabs" role="tablist">
    		<li role="presentation" class="active"><a>基本信息</a></li>
    		<li role="presentation"><a onclick="javascript:toPay();">缴费</a></li>
    		<li role="presentation"><a onclick="javascript:getAllDetails();">明细</a></li>
    		<li role="presentation"><a onclick="javascript:toCancel();">解绑</a></li>
    	</ul>
    	<label class="col-sm-2 control-label"></label>
    	<div class="col-sm-8">
    	<form action="" method="post" id="listform" name="listform" class="form-signin" role="form">
    	<h2 class="page-header">基本信息</h2>
       	<table class="table table-striped table-hover table-bordered">
      		 <tr>
           		<td><span class="btn btn-large">投注站机号：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_devid" id="p_devid" class="form-control" value="${userInfo.devid}" disabled />
              	</p>
               </td>
           </tr>
      		 <tr>
           		<td><span class="btn btn-large">投注站名称：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_devidname" id="p_devidname" class="form-control" value="${loginAcc.note2}" disabled />
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">责任人名称：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_username" id="p_username" class="form-control" value="${userInfo.username}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">证件号码：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_idcard" id="p_idcard" class="form-control" value="${loginAcc.personid}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">投注站地区号：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_zoneno" id="p_zoneno" class="form-control" value="${userInfo.zoneno}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">投注站地区名：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_zonenoname" id="p_zonenoname" class="form-control" value="${userInfo.zonenoname}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">绑定卡号：</span></td>
               <td>
               	<p>
          			<input type="text" name="p_cardno" id="p_cardno" class="form-control" value="${userInfo.cardno}" disabled/>
              	</p>
               </td>
           </tr>
           <tr>
           	<td><span class="btn btn-large">绑定状态：</span></td>
               <td>
           		<c:choose >
 					<c:when test="${userInfo.status==1 }">
	 					<p>
	          				<input type="text" name="p_trx_flag" id="p_trx_flag" class="form-control" value="绑定成功" disabled/>
	              		</p>
             		</c:when>
 					<c:otherwise>
 					<p>
         				<input type="text" name="p_trx_flag" id="p_trx_flag" class="form-control" style="color: red" value="失败--${bindInfo.errmsg}" disabled/>
              		</p>
              		</c:otherwise>
 				</c:choose>
               </td>
           </tr>
       </table>
       <div align="center" class="col-sm-12">
			 <div class="col-sm-12">
				<button class="btn btn-lg btn-primary" onclick="javascript:back();" style="width: 40%">返　回</button>
			</div>
		</div>
    	</form>
    	</div>
    	<label class="col-sm-2 control-label"></label>
    </body>
</html>