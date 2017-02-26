<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
	    <title>山西福彩工商银行缴费查询系统</title>
        <script language="javascript" type="text/javascript">
            var timer;
            //启动跳转的定时器
            function startTimes() {
                timer = window.setInterval(showSecondes,1000);
            }

            var i = 5;
            function showSecondes() {
                if (i > 0) {
                    i--;
                    document.getElementById("secondes").innerHTML = i;
                }
                else {
                    window.clearInterval(timer);
                    location.href = "pay/toNetPay.do";
                }
            }

            //取消跳转
            function resetTimer() {
                if (timer != null && timer != undefined) {
                    window.clearInterval(timer);
                    location.href = "pay/toNetPay.do";
                }
            }
        </script> 
    </head>
    <body class="error_page" onload="startTimes();">
    	<div class="jumbotron">
    		<h1>您访问的页面出现了错误</h1>
    		&nbsp;&nbsp;&nbsp;&nbsp;<span id="secondes" class="alert alert-danger">5</span>&nbsp;秒后将自动跳转，立即跳转请点击&nbsp;<a class="btn btn-primary btn-lg" href="javascript:resetTimer();" role="buttom">返回</a>
    	</div>
    </body>
</html>