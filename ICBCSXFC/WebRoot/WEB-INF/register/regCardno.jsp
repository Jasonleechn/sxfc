<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        
        	//登录校验
        	function chooseCard(cardno) {
        		$("#p_cardno").val(cardno);
        		$("#btn_cardno").text(cardno);
        		$.alert({title:'请确认卡号!',
        				 content:$("#p_cardno").val()});
        		return;
        	}
        	
        	function regCardno(){
        		var cardno = $("#p_cardno").val();
        		if(cardno == ''){
        			$.alert({title:'错误!',type:'red',
        				 content:'银行卡号不能为空!'});
	        		return;
        		}
        		
        		document.getElementById("regCardno").action = "login/regResult.do";
        		document.getElementById("regCardno").submit();
        		
        	}
        	
        	//手机短信验证码js
			var InterValObj; //timer变量，控制时间
			var count = 120; //间隔函数，1秒执行
			var curCount = 120;//当前剩余秒数
			var MsgCode = ""; //编码
			var codeLength = 4;//验证码长度
			var returnMsg="";
			function sendMessage() {
				var cardno = $("#p_cardno").val();
				if(''==cardno){
					$.alert({title:'错误提示!',type:'red',content:"绑定的银行卡号不能为空!"});
					return;
				}
				if($("#btnSendCode").attr("disabled")=="true"||$("#btnSendCode").attr("disabled")=="disabled"){
					return;
				}
				$.ajax({
					type: "POST", //用POST方式传输
					dataType: "text", //数据格式:JSON
					url: "login/sendRegMsg.do", //目标地址
					data:{"p_cardno":cardno},//注册
					contentType:'application/x-www-form-urlencoded;charset=utf-8',
					//async:false,
					/*error: function (XMLHttpRequest, textStatus, errorThrown) { },*/
					success: function (msg){ 
						returnMsg = msg;
						if(msg=="true"){
							//设置button效果，开始计时
							$("#btnSendCode").attr("disabled", "true");
							//document.getElementById("btnSendCode").innerHTML ="请在" + curCount + "秒内输入验证码";
							$("#btnSendCode").html("请在" + curCount + "秒内输入验证码");
							InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
						}else{
							if('1111'==msg){
								msg="无注册信息,请先注册!";
							}
							if('false'==msg){
								msg="获取验证码异常,请重试!";
							}
							//$("#btnSendCode").attr("disabled", "true");
							$.alert({title:'错误',type:'red',content:msg});
							//document.getElementById("btnSendCode").innerHTML ="获取失败" + curCount + "秒后输重新获取"
							$("#btnSendCode").html("重新获取");
							//InterValObj = window.setInterval(SetRemainTime, 1000);
						}
					}
				});
			}
			//timer处理函数
			function SetRemainTime() {
				if (curCount == 0) {                
					window.clearInterval(InterValObj);//停止计时器
					$("#btnSendCode").removeAttr("disabled");//启用按钮
					$("#btnSendCode").html("重新发送验证码");
					curCount = 120; //重置时间  
				}else {
					curCount--;
					$("#btnSendCode").html("请在" + curCount + "秒内输入验证码");
					//document.getElementById("btnSendCode").innerHTML ="请在" + curCount + "秒内输入验证码";
				}
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
        		$.alert({title:"注册结果",content:"<%=alertMsg%>"});
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br />
    	<div align="center">
	      	<form action="" method="post" id="regCardno" class="form-horizontal" role="form" style="width: 95%">
	     		<input type="hidden" id="p_cardno" name="p_cardno" value=""/>
	     		<table class="table table-striped table-bordered table-hover">
	  					<tr >
	  						<c:choose>
		            			<c:when test="${regObj.type=='0'}"><th colspan="2">投注机号信息</th></c:when>
		            			<c:otherwise><th colspan="2">投注站号信息</th></c:otherwise>
		            		</c:choose>
	  					</tr>
	  					<tr>
	   					<td><p>
	   						<c:choose>
		            			<c:when test="${regObj.type=='0'}"><a>投注机号</a></c:when>
		            			<c:otherwise><a>投注站号</a></c:otherwise>
		            		</c:choose>
	   						</p>
	   					</td>
	   					<td>${regObj.devid}</td>
	  					</tr>
	  					<tr>
		   					<td><p>
								<c:choose>
			            			<c:when test="${regObj.type=='0'}"><a>投注机地址</a></c:when>
			            			<c:otherwise><a>投注站地址</a></c:otherwise>
		            			</c:choose>
								</p>
							</td>
		   					<td>${regObj.devidaddr}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>负责人</a></p></td>
		   					<td>${regObj.username}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>彩票类型</a></p></td>
		   					<c:choose>
		            			<c:when test="${regObj.type=='0'}"><td>电脑彩票</td></c:when>
		            			<c:otherwise><td>网点即开彩票</td></c:otherwise>
		            		</c:choose>
	  					</tr>
	  					<tr>
		   					<td><p><a>联系电话</a></p></td>
		   					<td>${regObj.telphone}</td>
	  					</tr>
	            </table>
	           <hr />
	           <div class="alert alert-info" role="alert"><strong>请选择银行卡</strong></div>
	          <!-- Single button -->
				<div class="btn-group">
				  <button id="btn_cardno" type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
				     　　=====请选择银行卡号=====　　<span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
				  	<c:forEach items="${cardList }" var="cards" varStatus="vs">
					    <li><a onclick="javascript:chooseCard('${cards }');">${cards }</a></li>
				    </c:forEach>
				  </ul>
				</div>
				<br /><br />
				<div >
	       			<input type="text" name="p_msg" id="p_msg" class="form-control" style="width: 70%" placeholder="请输入短信验证码"/>
	      			<div align="right" style="width: 70%">
		      			<a class="btn btn-sm btn-warning" id="btnSendCode" onclick="sendMessage()" align="right">获取验证码</a>
	      			</div>
		      	</div>
	          </form>
          </div>
          <hr />
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:regCardno();">绑　定</button>
 			</div>
     	  </div>
	</body>
</html>