<%@page pageEncoding="utf-8"%>
<%
	String p_devid = (String)session.getAttribute("p_devid");
	String p_idcard = (String)session.getAttribute("p_idcard");
	p_devid = p_devid==null?"":p_devid;
	p_idcard = p_idcard==null?"":p_idcard;
	
 %>
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
        	//登录校验
        	function check_login() {
        		var p_devid = $("#p_devid").val();
        		if(p_devid == "") {
        			window.alert("请输入已注册的投注站编号.");
        			myform.p_devid.focus();
        			return false;
        		}
        		
        		var p_idcard = $("#p_idcard").val();
        		if(p_idcard == "") {
        			window.alert("请输入证件号码.");
        			/*myform.p_idcard.focus();*/
        			return false;
        		}
        		
        		/*var code = $("#code").val();
        		if(code == "") {
        			window.alert("请输入验证码.");
        			myform.code.focus();
        			return false;
        		}*/
        	}
        	
        	//设置提示信息
        	function set_msg(id, msg) {
        		$("#"+id).text(msg);
        	}
        	//刷新验证码
        	function change() {
        		$("#code_image").attr("src", "../login/createImage.do?date=" + new Date().getTime());
        	}
        	function js_onLoad(){
        		myform = document.getElementById("myform");
        		/*myform.admin_code.focus();//默认焦点*/
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
    <body onload="javascript:js_onLoad();">
    	<div class="container">
        	<form action="resetCheck.do" method="post" id="myform" class="form-signin" role="form">
        		<h2 class="form-signin-heading">密码重置</h2>
	            <table>
	                <tr>
	                	<td><span class="btn btn-large">账　户：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_devid" id="p_devid" class="form-control" placeholder="投注站编号"  value="<%=p_devid %>">
	                   		</p>
	                    </td>
	                    <td><span id="p_devid_msg"></span></td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">证件号码：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_idcard" id="p_idcard" class="form-control" placeholder="证件号码" value="<%=p_idcard %>"
				            	  onblur="javascript:check_login();">
	                   		</p>
	                    </td>
	                    <td><span id="p_idcard_msg"></span></td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">验证码：</span></td>
	                    <td><p><input name="code" type="text" id="code" class="form-control" placeholder="验证码"  onfocus="set_msg('code_msg','');"/></p></td>
	                    <td><img src="../login/createImage.do" alt="验证码" title="点击更换" id="code_image" onclick="change();"/></td>  
	                    <td><span class="required" id="code_msg"></span></td>
	                </tr>
	                <tr>
	                    <td colspan="3">
	                    	<p><button id="login" class="btn btn-lg btn-primary btn-block" type="submit">申请重置</button></p>
	                    </td>    
	                    <td><span></span></td>                
	                </tr>
	            </table>
            </form>
        </div>
    </body>
</html>