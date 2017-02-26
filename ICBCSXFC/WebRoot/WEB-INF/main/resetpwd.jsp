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
        	function js_onLoad(){
        		myform = document.getElementById("myform");
        		myform.newpwd.focus();//默认焦点
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
        	<form action="resetPwd.do" method="post" id="myform" class="form-signin" role="form">
        		<h2 class="form-signin-heading">密码重置</h2>
	            <table>
	           		 <tr>
	                	<td><span class="btn btn-large">账号：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_devid" id="p_devid" class="form-control" value="<%=p_devid %>" disabled>
	                   		</p>
	                    </td>
	                    <td><span id="newpwd_msg"></span></td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">证件号码：</span></td>
	                    <td>
	                    	<p>
				            	<input type="text" name="p_idcard" id="p_idcard" class="form-control" value="<%=p_idcard %>" disabled >
	                   		</p>
	                    </td>
	                    <td><span id="newpwd_msg"></span></td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">新密码：</span></td>
	                    <td>
	                    	<p>
				            	<input type="password" name="newpwd" id="newpwd" class="form-control" placeholder="请输入新密码6-30位" 
				            		maxlength="30">
	                   		</p>
	                    </td>
	                    <td><span id="newpwd_msg"></span></td>
	                </tr>
	                <tr>
	                	<td><span class="btn btn-large">重复新密码：</span></td>
	                    <td>
	                    	<p>
				            	<input type="password" name="re_newpwd" id="re_newpwd" class="form-control" placeholder="请重复新密码" maxlength="30" 
				            	  onblur="javascript:check_login();">
	                   		</p>
	                    </td>
	                    <td><span id="re_newpwd_msg"></span></td>
	                </tr>
	                <tr>
	                    <td colspan="3">
	                    	<p><button id="login" class="btn btn-lg btn-primary btn-block" type="submit">重置密码</button></p>
	                    </td>    
	                    <td><span></span></td>                
	                </tr>
	            </table>
            </form>
        </div>
    </body>
</html>