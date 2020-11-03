<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>您好Springboot</title>

<!-- 1.导入函数类库 -->
<script src="../js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
	//让JS页面加载完成,之后执行JS
	$(function(){
		/* 
			需求: 利用ajax方式动态获取user数据信息
			请求网址:  /findAjax
			知识点:  
				返回值类型: 可以根据数据自动匹配类型,所以可以不写.
				1. $.get(url地址,传递的参数,回调函数,返回值类型)
				2. $.post(.....)
				3. $.getJSON(.....)
		 */
		$.get("/findAjax2",function(result){
			//1.可以使用js中的for循环
			/* for(let i=0; i<result.length;i++){
				
			} */
			
			/* for(let index in result){
				console.log(index);
			} */
			for(let user of result){
				let id = user.id;
				let name = user.name;
				let age = user.age;
				let sex = user.sex;
				let tr = "<tr align='center'><td>" +
						id +
						"</td><td>" +
						name +
						"</td><td>" +
						age +
						"</td><td>" +
						sex +
						"</td></tr>";
				$("#tab1").append(tr);
			}
		});
		/*
			原生ajax写法   $.ajax变形 jQuery的编程特点: 函数式编程
			需求:传递Id=100,name=喵
			参数写法1:data : {"id":100,"name":"喵"}
			参数写法2:data: "id=100&name=喵",
		*/
		$.ajax({
			url : "findAjax",
			type : "GET",
			success: function (result) {
				// debugger;
				for (let user of result) {
					let id = user.id;
					let name = user.name;
					let age = user.age;
					let sex = user.sex;
					let tr = "<tr align='center'><td>" +
							id +
							"</td><td>" +
							name +
							"</td><td>" +
							age +
							"</td><td>" +
							sex +
							"</td></tr>";
					$("#tab1").append(tr);
				}
			},
			error: function (result) {
				alert("请求失败，请联系管理员！")
			},
			cache : false,   //默认为 true
			async : false    //默认为 true
		});
	})
</script>

</head>
<body>
	<table id="tab1" border="1px" width="65%" align="center">
		<tr>
			<td colspan="6" align="center"><h3>学生信息</h3></td>
		</tr>
		<tr>
			<th>编号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>性别</th>
			<th></th>
		</tr>
	</table>
</body>
</html>