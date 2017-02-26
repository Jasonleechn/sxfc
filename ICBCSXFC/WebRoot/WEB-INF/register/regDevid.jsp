<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	//登录校验
        	function register() {
        		var type = $(":input:radio:checked").val();
        		$("#p_type").val(type);
        		var contents = "彩票类型选择出现了异常";
        		if("0" == type){
        			contents = "您选择了--电脑型彩票";
        		}
        		if("1" == type){
        			contents = "您选择了--网点即开型彩票";
        		}
        		$.confirm({
					   title: '请确认彩票类型!',
					    content: contents,
					    buttons: {
					        logoutUser: {
					            text: '确认',
					            action: function () {
					            	$('#myform').attr("action","login/regCardno.do").submit();
					            }
					        },
					        '取消': function () {
					            $.alert('已取消!');
					        }
					    }
					});
        		return;
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
        		$.alert({title:"信息提示!",type:'red',content:"<%=alertMsg%>"});
        		<%}%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br />
    	<div align="center">
    		<div class="alert alert-warning" role="alert" style="width: 95%"><strong>提醒! </strong>若信息不符请联系福彩中心!</div>
	      	<form action="" method="post" id="myform" class="form-horizontal" role="form" style="width: 95%">
	     		<input type="hidden" id="p_type" name="p_type" value=""/>
	     		<table class="table table-striped table-bordered table-hover">
	  					<tr >
	   					<th colspan="2">投注机号或站号注册信息</th>
	  					</tr>
	  					<tr>
		   					<td><p><a>投注机号或站号</a></p></td>
		   					<td>${regObj.devid}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>投注站地址</a></p></td>
		   					<td>${regObj.devidaddr}</td>
	  					</tr>
	  					<tr>
		   					<td><p><a>负责人</a></p></td>
		   					<td>${regObj.username}</td>
	  					</tr>
	            </table>
	           <hr />
	           <div class="btn-group" data-toggle="buttons">
				  <label class="btn btn-default active">
				    <input type="radio" name="options" id="option1" value="0" checked>　　电脑彩票　　　</input>
				  </label>
				  <label class="btn btn-default">
				    <input type="radio" name="options" id="option2" value="1">　　网点即开票　　</input>
				  </label>
				</div>
	          </form>
          </div>
          <hr />
     	  <div align="center">
 			<div >
  				<button class="btn btn-lg btn-danger btn-block" style="width: 75%" onclick="javascript:register();">下一步</button>
 			</div>
     	  </div>
	</body>
</html>