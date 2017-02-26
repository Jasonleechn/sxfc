<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <title>山西福彩工商银行缴费查询系统</title>
		<script type="text/javascript" language="javascript">
			//手机短信验证码js
			var InterValObj; //timer变量，控制时间
			var count = 120; //间隔函数，1秒执行
			var curCount = 120;//当前剩余秒数
			var MsgCode = ""; //编码
			var codeLength = 4;//验证码长度
			var returnMsg="";
			function sendMessage() {
				
				var amount = $("#p_amount").val();
				if(amount == ''){
					$.alert({title:'错误提示',type:'red',content:"请输入缴费金额!"});
        			return false;
				}
				var reg = /^(([1-9]\d*)|0)(\.\d{0,2})?$/;
				if(!reg.test(amount)){
					$.alert({title:'错误提示',type:'red',content:"金额不正确!"});
					return false;
				}
				
				var p_devid=$("#p_devid").val();
				var p_type=$("#p_type").val();
				var p_amount=$("#p_amount").val();
				var p_phoneNum=$("#p_phoneNum").val();
				if($("#btnSendCode").attr("disabled")=="true"||$("#btnSendCode").attr("disabled")=="disabled"){
					return;
				}
				$.ajax({
					type: "POST", //用POST方式传输
					dataType: "text", //数据格式:JSON
					url: "pay/sendMsg.do", //目标地址
					data:{"actflag":"1","p_amount":p_amount},
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
								msg="无【"+p_devid+"】信息,请先注册!";
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
				
			function pay(){
				//置灰按钮
				$("#myButtom").attr({"disabled":"disabled"});
				var p_msg = $("#p_msg").val();
				if(p_msg == ''){
					$.alert({title:'错误提示',type:'red',content:"请输入短信验证码!"});
					$("#myButtom").removeAttr("disabled");//启用按钮
        			return false;
				}
				var p_pwd = $("#p_pwd").val();
				if(p_pwd == ''){
					$.alert({title:'错误提示',type:'red',content:"请输入用户密码!"});
					$("#myButtom").removeAttr("disabled");//启用按钮
        			return false;
				}
				if(sf()==false){
					return false;
				}
        		
        		document.getElementById("payform").action = "pay/pay.do";
		    	document.getElementById("payform").submit();
			}

			//设置提示信息
			function set_msg(id, msg) {
				$("#" + id).text(msg);
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
			function js_onLoad() {
				document.oncontextmenu = function() {
				return false;
			};
			<%
				String lsh = System.currentTimeMillis()+"";
		        String alertMsg = (String)request.getAttribute("alertMsg");
		        if(alertMsg != null){
		    %>
		        $.alert({title:'缴费信息提示!',type:'red',content:"<%=alertMsg%>"});
		    <%}%>
		 }
	  </script>
	</head>
	<body onload="js_onLoad();">
		<br /><br /><br />
		<div align="center">
			<!--<input type="hidden" name="p_username" id="p_username" value="${payDetail.username }" />
			<input type="hidden" name="p_phoneNum" id="p_phoneNum" value="${payDetail.telphone }" />
			--><form action="" method="post" id="payform" name="payform" class="form-signin" role="form" style="width: 95%">
				<input type="hidden" name="p_lsh" id="p_lsh" value="<%=lsh %>" />
				<table class="table table-striped table-bordered table-hover">
  					<tr >
   					<th colspan="2">缴款信息确认</th>
  					</tr>
  					<tr>
  						<c:choose>
	            			<c:when test="${payDetail.type=='0'}"><td><p><a>投注机号</a></p></td></c:when>
	            			<c:otherwise><td><p><a>投注站号</a></p></td></c:otherwise>
		            	</c:choose>
	   					<td>${payDetail.devid}</td>
  					</tr>
  					<tr>
	   					<td><p><a>银行卡号</a></p></td>
	   					<td>${payDetail.accountno}</td>
  					</tr>
  					<tr>
	   					<td><p><a>彩票类型</a></p></td>
	   					<c:choose>
	            			<c:when test="${payDetail.type=='0'}"><td>电脑彩票</td></c:when>
	            			<c:otherwise><td>网点即开票</td></c:otherwise>
		            	</c:choose>
	   				</tr>
	            </table>
	           <hr />
					<div id="d_amount" class="form-group"  align="center">
		      		<div>
		       			<input type="text" name="p_amount" id="p_amount" class="form-control" style="width: 80%" placeholder="缴款金额(元)"/>
		      			<span id="p_amount_msg"></span>
	      			</div>
		      		<div >
		       			<input type="text" name="p_msg" id="p_msg" class="form-control" style="width: 80%" placeholder="请输入短信验证码"/>
		      			<div align="right" style="width: 80%">
			      			<a class="btn btn-sm btn-warning" id="btnSendCode" onclick="sendMessage()" align="right">获取验证码</a>
		      			</div>
		      		</div>
      			</div>
				<div id="d_devid" class="form-group"  align="center">
	      			<div>
	       			<input type="password" name="p_pwd" id="p_pwd" class="form-control" style="width: 80%" placeholder="请输入用户密码"  />
	      			<span id="p_pwd_msg" ></span>
	      			</div>
      			</div>
			</form>
				<br />
          </div>
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" id="myButtom" onclick="javascript:pay();">缴费</button>
 			</div>
     	  </div>
		<label class="col-sm-2 control-label"></label>
	</body>
</html>