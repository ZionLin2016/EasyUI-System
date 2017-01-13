<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	var editor;
	$(function() {
		window.setTimeout(function() {
			editor = KindEditor.create('#note', {
				width : '680px',
				height : '300px',
				items : [ 'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript', 'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak', 'anchor', 'link', 'unlink' ],
				uploadJson : '${pageContext.request.contextPath}/fileController/upload',
				fileManagerJson : '${pageContext.request.contextPath}/fileController/fileManage',
				allowFileManager : true
			});
			
			parent.$.messager.progress('close');
		}, 1);
		
		$('#form').form({
			url : '${pageContext.request.contextPath}/bugController/add',
			onSumbit : function() {
				editor.sync();
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result =　$.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload')
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
	
	function fileManage() {
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					//KindEditor('#url').val(url);
					editor.insertHtml($.formatString('<img src="{0}" alt="" />', url));
					editor.hideDialog();
				}
			});
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th>编号</th>
					<td><input name="id" type="text" class="span2" value="${bug.id}" readonly="readonly"></td>
					<th>BUG名称</th>
					<td><input name="name" type="text" placeholder="请输入BUG名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
				</tr>
				<tr>
					<th>BUG类型</th>
					<td><select name="typeId" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<c:forEach items="${bugTypeList}" var="bugType">
								<option value="${bugType.id}">${bugType.name}</option>
							</c:forEach>
					</select></td>
					<c:choose>
						<c:when test="${fn:contains(sessionInfo.resourceList, '/fileController/fileManage')}">
							<th>浏览服务器附件</th>
							<td>
								<button type="button" class="btn" onclick="fileManage();">浏览服务器</button>
							</td>
						</c:when>
						<c:otherwise>
							<th></th>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th>BUG描述</th>
					<td colspan="3"><textarea name="note" id="note" cols="50" rows="5" style="visibility: hidden;"></textarea></td>
				</tr>
			</table>		
		</form>
	</div>
</div>