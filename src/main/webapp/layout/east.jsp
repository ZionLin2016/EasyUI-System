<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" charset="utf-8">
	$(function() {

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
				}
			} ]
		});
		
		$('#win').click(function(){
			$('#showPay').window('open'); 
		});
	});
	

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
		<div id="layout_east_calendar"></div>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<div id="layout_east_onlinePanel" data-options="fit:true,border:false" title="其它">
			<div class="well well-small" style="margin: 3px;">
				<img id="win" alt="捐助我" src="${pageContext.request.contextPath}/style/images/alipay.jpg" />
				<hr />
				<div>
					<span class="label label-success">*鹏</span><br />2016-07-09 10:54:33(10元)
				</div>
				<div>
					<span class="label label-important">*阳</span><br />2016-07-10 22:23:46(5元)
				</div>
				<div>
					<span class="label label-info">*雪刚</span><br />2016-07-12 15:17:26(200元)
				</div>
				<div>
					<span class="label label-success">*泽</span><br />2016-07-15 14:24:40(5元)
				</div>
				<div>
					<span class="label label-success">*文强</span><br />2016-07-18 14:44:32(8元)
				</div>
				<div>
					<span class="label label-success">*灿佳</span><br />2016-07-24 20:07:09(2元)
				</div>
				<hr />
				如果发现系统有BUG，请给我发Email:1362005078@qq.com
			</div>
		</div>
	</div>
</div>
<div id="showPay" class="easyui-window" title="捐助我" style="width:250px;height:275px;overflow:hidden"   
        data-options="modal:true,closed:true, minimizable:false, maximizable:false, collapsible:false">   
   <img id="win" alt="捐助我" src="${pageContext.request.contextPath}/style/images/pay.jpg" onclick="$('#win').window('open');"/>
</div>  