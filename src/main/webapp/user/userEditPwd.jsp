<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#editCurrentUserPwdForm').form({
			url : '${pageContext.request.contextPath}/userController/editCurrentUserPwd',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍候....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center', border:false" title="" style="overflow: hidden;">
		<c:if test="${sessionInfo.name == null}">
			<img src="${pageContext.request.contextPath}/style/images/blue_face/bluefaces_35.png" alt="" />
			<div>登录已超时，请重新登录！</div>
			<script type="text/javascript" charset="utf-8">
				try {
					parent.$.messager.progress('close');
				} catch (e) {
				}
			</script>
		</c:if>
		<c:if test="${sessionInfo.name != null}">
			<form id="editCurrentUserPwdForm" method="post">
				<table class="table table-hover table-condensed">
					<tr>
						<th>登录名</th>
						<td>${sessionInfo.name}</td>
					</tr>
					<tr>
						<th>原密码</th>
						<td><input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>新密码</th>
						<td><input name="pwd" type="password" placeholder="请输入新密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>重复</th>
						<td><input name="rePwd" type="password" placeholder="请再次输入新密码" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#editCurrentUserPwdForm input[name=pwd]\']'"></td>
					</tr>
				</table>
			</form>
		</c:if>
	</div>
</div>