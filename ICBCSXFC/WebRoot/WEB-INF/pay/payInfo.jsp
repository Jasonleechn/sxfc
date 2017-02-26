<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<meta name="description" content="" />
		<meta name="author" content="" />
		<!-- 新bootstrap 核心css文件 -->
		<link href="../dist/css/bootstrap.min.css" rel="stylesheet" />
		<!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
		<script type="text/javascript" language="javascript"
			src="../js/jquery-1.11.1.js">
</script>

		<!-- Bootstrap core CSS -->
		<script type="text/javascript" language="javascript"
			src="../dist/js/bootstrap.min.js">
</script>

		<title>山西福彩工商银行缴费查询系统</title>
		<script type="text/javascript" language="javascript">
var registerform;

//登录校验
function register() {
	var p_account = $("#p_account").val();
	if (p_account == "") {
		window.alert("账户名不能为空.");
		registerform.p_account.focus();
		return;
	}

	var pwd = $("#pwd").val();
	if (pwd == "") {
		window.alert("密码不能为空.");
		registerform.pwd.focus();
		return false;
	}

	//校验身份证号码。因不一定是身份证号码，此处校验就不做限制，但要求客户提供的号码与银行卡的一致
	var idcard = $("#idcard").val();
	if (idcard == "") {
		window.alert("证件号码不能为空.");
		registerform.idcard.focus();
		return false;
	} else {
		window.alert("请填写银行卡号开户对应的证件号码,否则无法进行绑定缴费!");
	}

	var zoneno = $("#zoneno").val();
	alert("zoneno:" + zoneno);
	if (zoneno == "") {
		window.alert("地区号不能为空.");
		registerform.zoneno.focus();
		return false;
	}

	var username = $("#username").val();
	alert("username:" + username);
	if (username == "") {
		window.alert("姓名不能为空.");
		registerform.username.focus();
		return false;
	}

	document.getElementById("payform").action = "register.do";
	document.getElementById("payform").submit();

}

//校验身份证号码
/*function check_idcard(){
	var idcard = $("#idcard").val();
	var reg = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{4}$/;
	if(!reg.test(idcard)){
		$("#idcard_msg").text("身份证号格式不正确！").addClass("btn btn-danger");
		return;
	} 
}*/

			//设置提示信息
			function set_msg(id, msg) {
				$("#" + id).text(msg);
			}

			//校验密码
			function check_pwd() {
				var pwd = document.getElementById("pwd").value;
				if ('' == pwd) {
					$("#regsubmit").prop("disabled", "disabled");
				}
				if (pwd.length < 6) {
					$("#pwd_msg").text("须大于6位").addClass("btn btn-danger");
					$("#regsubmit").prop("disabled", "disabled");
				} else {
					$("#pwd_msg").text("").removeClass("btn btn-danger");
				}
				var repwd = document.getElementById("repwd").value;
				if (pwd != repwd) {
					$("#repwd_msg").text("密码不一致").addClass("btn btn-danger");
					$("#regsubmit").prop("disabled", "disabled");
				} else {
					$("#repwd_msg").text("").removeClass("btn btn-danger");
					$("#regsubmit").prop("disabled", false);
				}
			}

			function back(){
		    	document.getElementById("payform").action = "../login/showList.do";
		    	document.getElementById("payform").submit();
        	}
        	
			function getAllDetails(){
        		document.getElementById("payform").action = "getDetails.do?currentPage=1";
        		document.getElementById("payform").submit();
        	}
			function chooseBasicInfo(devid,cardno,status){
        		if(status != '1'){
        			window.alert("对不起,需要绑定成功才能进行下一步操作!");
        			return false;
        		}
        		window.location.href="../oper/operList.do?devid="+devid+"&cardno="+cardno;
        	}
        	function toCancel(){
        		document.getElementById("payform").action = "toCancel.do";
        		document.getElementById("payform").submit();
        	}
			function js_onLoad() {
				myform = document.getElementById("registerform");
				/*myform.admin_code.focus();//默认焦点*/
				document.oncontextmenu = function() {
				return false;
			};
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
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation">
				<a onclick="chooseBasicInfo('${userInfo.devid}','${userInfo.cardno }','${userInfo.status }');">基本信息</a>
			</li>
			<li role="presentation" class="active">
				<a>缴费</a>
			</li>
			<li role="presentation">
				<a onclick="javascript:getAllDetails();">明细</a>
			</li>
			<li role="presentation">
				<a onclick="javascript:toCancel();">解绑</a>
			</li>
		</ul>
		<label class="col-sm-2 control-label"></label>
		<div class="col-sm-8">
			<form action="" method="post" id="payform" name="payform" class="form-signin" role="form">
				<input type="hidden" name="p_zoneno" id="p_zoneno" value="${userInfo.zoneno }" />
				<h2 class="page-header">
					用户缴费
				</h2>
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<td>
							<span class="btn btn-large">投注站机号：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_devid" id="p_devid"
									class="form-control" value="${userInfo.devid }"
									disabled="disabled" />
							</p>
						</td>
						<td>
							<span id="p_account_msg" class="btn" style="margin-left: 10px">*
								机号</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">投注站名称：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_devidname" id="p_devidname"
									class="form-control" value="${loginAcc.note2 }"
									disabled="disabled" />
							</p>
						</td>
						<td>
							<span id="p_account_msg" class="btn" style="margin-left: 10px"></span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">负责人姓名：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_username" id="p_username"
									class="form-control" value="${userInfo.username }"
									disabled="disabled" />
							</p>
						</td>
						<td>
							<span id="p_username_msg"></span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">银行卡号：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_cardno" id="p_cardno"
									class="form-control" value="${userInfo.cardno }"
									disabled="disabled" />
							</p>
						</td>
						<td>
							<span id="p_cardno_msg"></span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">所属地区名：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_zonenoname" id="p_zonenoname"
									class="form-control" value="${userInfo.zonenoname }"
									disabled="disabled" />
							</p>
						</td>
						<td>
							<span id="p_cardno_msg"></span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">缴费金额：</span>
						</td>
						<td>
							<p>
								<input type="text" name="p_amount" id="p_amount"
									class="form-control" placeholder="缴费金额（元）" />
							</p>
						</td>
						<td>
							<span id="p_amount_msg" class="btn" style="margin-left: 10px">*(元)</span>
						</td>
					</tr>
					<tr>
						<td>
							<span class="btn btn-large">福彩类型：</span>
						</td>
						<td>
							<select id="p_fcacc" name="p_fcacc" class="form-control">
								<c:forEach items="${fcAcc}" var="fcAcc">
									<option value="${fcAcc.type}">
										${fcAcc.type} - ${fcAcc.typename}
									</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<span id="p_fcamount_msg" class="btn" style="margin-left: 10px"></span>
						</td>
					</tr>
				</table>
				<div align="center" class="col-sm-12">
					<div class="col-sm-12">
						<button class="btn btn-lg btn-primary" style="width: 40%"
							onclick="javascript:register();">
							提 交
						</button>
						<button class="btn btn-lg btn-primary" style="width: 40%"
							onclick="javascript:back();">
							返 回
						</button>
					</div>
				</div>
			</form>
		</div>
		<label class="col-sm-2 control-label"></label>
	</body>
</html>