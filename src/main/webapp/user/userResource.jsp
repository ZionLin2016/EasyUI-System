<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		var data = eval("(" + '${userResources}' + ")");
		$('#userResources').tree({
			data : data,
			parentField : 'pid',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
				if (data.length < 1) {
					$('#userResources').append('<img src="${pageContext.request.contextPath}/style/images/blue_face/bluefaces_35.png" alt="您没有任何权限" /><div>您没有任何权限</div>');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true, border:false">
	<div data-options="region:'center',border:false" titile="">
		<c:if test="${sessionInfo.name == null}">
			<img alt="" src="${pageContext.request.contextPath}/style/images/blue_face/bluefaces_35.png" />
			<div>登录已超时，请重新登录！</div>
			<script type="text/javascript" charset="utf-8">
				try {
					parent.$.messager.progress('close');
				} catch (e) {
				}
			</script>
		</c:if>
		<c:if test="${sessionInfo.name != null}">
			<ul id="userResources"></ul>
		</c:if>
	</div>
</div>