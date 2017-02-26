<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<meta name="description" content=""/>
<meta name="author" content=""/>
<!-- 新bootstrap 核心css文件 -->
<link href="./dist/css/bootstrap.min.css" rel="stylesheet"/>
<link href="./dist/css/jquery-confirm.min.css" rel="stylesheet"/>
<!-- jQuery文件 ,必须在bootstrap.min.js之前引入 -->
<script type="text/javascript" language="javascript" src="./js/jquery-1.11.1.js"></script>

<!-- Bootstrap core -->
<script type="text/javascript" language="javascript" src="./dist/js/bootstrap.min.js"></script>
<script type="text/javascript" language="javascript" src="./dist/js/placeholder.js"></script>
<script type="text/javascript" language="javascript" src="./dist/js/jquery-confirm.min.js"></script>
