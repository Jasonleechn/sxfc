<%@page pageEncoding="utf-8"%>
<%@ include file="/commons/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
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
				//置灰按钮
				/*$("#myButtom").attr({"disabled":"disabled"});*/
				
				/*if(sf()==false){
					return false;
				}*/
        		
        		document.getElementById("myform").action = "pay/payDetail.do";
		    	document.getElementById("myform").submit();
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
			function js_onLoad(){
			
				document.oncontextmenu = function(){return false;};
        		<%
        			String alertMsg = (String)request.getAttribute("alertMsg");
        		%>
			
				$.confirm({
					   title: '错误提示!',
					    content: "<%=alertMsg%>"+'!--请选择要缴费的彩票类型!',
					    buttons: {
					        logoutUser: {
					            text: '电脑彩票',
					            action: function () {
					            	$('#myform').attr("action","pay/toComputePay.do").submit();
					            }
					        },
					        '网点即开票': function () {
					            $('#myform').attr("action","pay/toNetPay.do").submit();
					        }
					    }
					});
				
				/*$.confirm({
					buttons:{
						'电脑彩票':{
							text:'您将进入电脑彩票缴费页面',
							action:function(){
								$("#myform").attr("action","toComputePay.do");}
							},
						'网点即开票':{
							text:'您将进入网点即开票缴费页面',
							action:function(){
								$("#myform").attr("action","toNetPay.do");
							}
						}
					}
				});*/
			}
       </script>
	</head>
	<body onload="js_onLoad();">
	<form action="" method="post" id="myform" name="myform" class="form-signin" role="form" style="width: 95%"></form>
	</body>
</html>