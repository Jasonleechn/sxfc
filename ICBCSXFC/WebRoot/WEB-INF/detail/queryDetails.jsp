<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script type="text/javascript" language="javascript">
        	
        	function oneDetails(devid,lsh){
        		document.getElementById("detailform").action = "search/oneDetail.do?devid="+devid+"&lsh="+lsh;
        		/*document.getElementById("detailform").submit;*/
        		document.getElementById("detailform").submit();
        	}
        	
        	function query(){
        		var type = $(":input:radio:checked").val();
        		$("#p_type").val(type);
        		var devid = $("#p_devid").val();
        		if(''==devid || devid == null){
        			$.alert({title:'错误提示!',type:'red',content:'请输入查询的投注机号!'});
        			return;
        		}
        		var type = $("#p_type").val();
        		if(''==type){
        			$.alert({title:'错误提示!',type:'red',content:'请选择查询的彩票类型!'});
        			return;
        		}
        		document.getElementById("queryform").action = "search/getPayDetails.do";
        		document.getElementById("queryform").submit();
        	}
        	
        	function js_onLoad(){
        		document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        			String s_alertMsg = (String)session.getAttribute("s_alertMsg");
        			if(alertMsg != null){
        		%>
        		$.alert({title:'提示信息!',type:'red',content:"<%=alertMsg%>"});
        		<%}
        		  	if(s_alertMsg != null){%>
        		$.alert({title:'提示信息!',type:'red',content:"<%=s_alertMsg%>"});
        		<%}
        		session.removeAttribute("s_alertMsg");
        		%>
        	}
        </script>
    </head>
    <body onload="js_onLoad();">
    	<br /><br /><br /><br /><br />
    	<div align="center">
    		<div align="center">
        	<form action="" method="post" id="queryform" class="form-signin" role="form" style="width: 90%">
        		<input type="hidden" id="p_type" name="p_type" value=""/>
        		<div id="d_devid" class="form-group"  align="center">
	      			<div>
		       			<input type="text" name="p_devid" id="p_devid" class="form-control"  placeholder="投注机号或站号(明细查询)"/>
	      			</div>
      			</div>
	            <div class="form-group">
	                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input2" data-link-format="yyyy-mm-dd" >
	                    <input class="form-control" size="16" type="text" id="beginDate" name="beginDate" readonly placeholder="查询起始日期" />
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                </div>
    			 </div>
    			 <div class="form-group" align="center" >
	                <div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input3" data-link-format="yyyy-mm-dd">
	                    <input class="form-control" size="16" type="text" id="endDate" name="endDate" readonly placeholder="查询截止日期"/>
	                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	                </div>
    			 </div>
    			 <div id="d_pwd" class="form-group"  align="center">
	      			<div>
		       			<input type="password" name="p_pwd" id="p_pwd" class="form-control" placeholder="请输入用户密码"/>
	      			</div>
      			</div>
      			<div align="center">
	      			<div class="btn-group" data-toggle="buttons"  >
					  <label class="btn btn-default">
					    <input type="radio" name="options" id="option1" value="0">　电脑彩票　　</input>
					  </label>
					  <label class="btn btn-default">
					    <input type="radio" name="options" id="option2" value="1">　网点即开票　</input>
					  </label>
					</div>
				</div>
				<br />
            </form>
            </div>
			<div class="form-group" style="width: 75%">
 					<button class="btn btn-lg btn-danger btn-block" onclick="javascript:query();">查　询</button>
			</div>
        </div>
        <script type="text/javascript" src="dist/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
		<script type="text/javascript" src="dist/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
        <script type="text/javascript">
	    $('.form_datetime').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
	        showMeridian: 1
	    });
		$('.form_date').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
		$('.form_time').datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 1,
			minView: 0,
			maxView: 1,
			forceParse: 0
	    });
	</script>
    </body>
</html>