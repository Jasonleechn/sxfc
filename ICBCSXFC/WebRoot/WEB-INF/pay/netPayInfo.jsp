<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
	    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	    <meta name="description" content=""/>
	    <meta name="author" content=""/>
	    <!-- 新bootstrap 核心css文件 -->
	    <link href="${pageContext.request.contextPath}/dist/css/bootstrap.min.css" rel="stylesheet"/>
	    <link href="${pageContext.request.contextPath}/dist/css/jquery-confirm.min.css" rel="stylesheet"/>
	    <!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.js"></script>
	   
	    <!-- Bootstrap core -->
	    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/dist/js/bootstrap.min.js"></script>
	    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/dist/js/placeholder.js"></script>
	    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/dist/js/jquery-confirm.min.js"></script>
	    
	    <!--<link rel="icon" href="http://v3.bootcss.com/favicon.ico"/>-->
	    <title>山西福彩工商银行缴费查询系统</title>
		<script type="text/javascript" language="javascript">
			var registerform;

			var sf_flag = 0;
			function sf(){
					if(sf_flag==1){
						$.alert({title:"提示!",type:'red',content:"请勿重复提交!"});
						return false;
					}else{
						sf_flag=1;
						return true;
					}
				}
				
			function paynext(){
			
				var devid = $("#p_devid").val();
				if(''==devid || devid==null){
					$.alert({title:'信息提示!',type:'red',content:'请输入缴费的投注站编号!'});
					return ;
				}
			
				//1.判断验证码是否为空
        		var account = $("#p_code").val();
        		if(account == ""){
        		$.alert({title:'错误',type:'red',content:"验证码不能为空!"});
        			$("#d_code").removeClass("has-success has-feedback");
    				$("#p_code_msg").removeClass("glyphicon glyphicon-ok form-control-feedback");
    				$("#d_code").addClass("has-error has-feedback");
    				$("#p_code_msg").addClass("glyphicon glyphicon-remove form-control-feedback");
    				return;
    			}else{
    				$("#d_code").removeClass("has-error has-feedback");
    				$("#p_code_msg").removeClass("glyphicon glyphicon-remove form-control-feedback");
    				$("#d_code").addClass("has-success has-feedback");
    				$("#p_code_msg").addClass("glyphicon glyphicon-ok form-control-feedback");
        		}
        		
        		if(sf()==false){
					return false;
				}
        		document.getElementById("payform").action = "pay/payDetail.do";
		    	document.getElementById("payform").submit();
			}

			//设置提示信息
			function set_msg(id, msg) {
				$("#" + id).text(msg);
			}
			
			//刷新验证码
        	function change() {
        		$("#code_image").attr("src", "pay/createImage.do?date=" + new Date().getTime());
        	}
        	
			/*function chooseBasicInfo(devid,cardno,status){
        		/*if(status != '1'){
        			window.alert("对不起,需要绑定成功才能进行下一步操作!");
        			return false;
        		}
        		window.location.href="../oper/operList.do?devid="+devid+"&cardno="+cardno;
        	}
        	function toCancel(){
        		document.getElementById("payform").action = "toCancel.do";
        		document.getElementById("payform").submit();
        	}*/
			function js_onLoad(){
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			String s_alertMsg = (String)session.getAttribute("s_alertMsg");
        			if(alertMsg != null){
        		%>
        		$.alert({title:'信息提示!',type:'red',content:'<%=alertMsg%>'});
        		<%}
        		  	if(s_alertMsg != null){%>
        		  	$.alert({title:'信息提示!',type:'red',content:'<%=s_alertMsg%>'});
        		<%}
        		session.removeAttribute("s_alertMsg");
        		%>
        	}
       </script>
	</head>
	<body onload="js_onLoad();">
		<br /><br /><br /><br /><br />
		<div align="center">
			<form action="" method="post" id="payform" name="payform" class="form-signin" role="form" style="width: 95%">
				<input type="hidden" name="p_type" id="p_type" value="1" />
				<div id="d_devid" class="form-group"  align="center">
	      			<div>
	       			<input type="text" name="p_devid" id="p_devid" class="form-control" style="width: 80%" placeholder="投注站号(网点即开票缴费)"/>
	      			<span id="p_devid_msg" ></span>
	      			</div>
      			</div>
      			<div id="d_code" class="form-group"  align="center">
      				<div style="width: 80%">
	                   <input name="p_code" type="text" id="p_code" class="form-control" placeholder="验证码"  onfocus="set_msg('code_msg','');"/>
	                   <img align="right"  src="pay/createImage.do" alt="验证码" title="点击更换" id="code_image" onclick="change();"/>
	                   <span id="p_code_msg"></span>
	                </div>
	           </div>
			</form>
          </div>
			<br /><br /><br />
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:paynext();">下一步</button>
 			</div>
     	  </div>
		<label class="col-sm-2 control-label"></label>
	</body>
</html>