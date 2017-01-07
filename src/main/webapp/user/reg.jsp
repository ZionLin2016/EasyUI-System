<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	$(function() {
		$('#registerDialog').show().dialog({
			modal : true,
			closed : true,
			buttons : [ {
				text : '注册',
				handler : function() {
					register();
				}
			} ]
		});
	});
	function register() {
		if ($('#regForm').form('validate')) {
			parent.$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			$.post('${pageContext.request.contextPath}/userController/reg', $('#regForm').serialize(), function(result) {
				if (result.success) {
					$('#registerDialog').dialog('close');
					$.messager.show({
						title : '提示',
						msg : result.msg,
						showType : 'slide'
					});
					$('#loginForm').form('load', result.obj);
				} else {
					$.messager.alert('错误', result.msg, 'error');
				}
				parent.$.messager.progress('close');
			}, "JSON");
		}
	}
</script>
<div id="registerDialog" title="用户注册" style="width: 300px; height: 220px; overflow: hidden; display: none;">
	<form id="regForm" method="post">
		<table class="table table-hover table-condensed">
			<tr>
				<th>登录名</th>
				<td><input name="name" type="text" placeholder="请输入登录名" class="easyui-validatebox" data-options="required:true"></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true"></td>
			</tr>
			<tr>
				<th>重复</th>
				<td><input name="rePwd" type="password" placeholder="请再次输入密码" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#regForm input[name=pwd]\']'"></td>
			</tr>
		</table>
	</form>
</div>
