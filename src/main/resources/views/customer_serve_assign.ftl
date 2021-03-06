<!doctype html>
<html>
<head>
    <#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/static/js/common.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/base.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/customer.server.assign.js"></script>
</head>
<body style="margin: 1px">


<table id="dg"  class="easyui-datagrid"
       fitColumns="true" pagination="true" rownumbers="true"
       url="${ctx}/customer_serve/list?state=fw_002" fit="true" toolbar="#tb" singleSelect="true" singleSelect="true">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="customer" width="200" align="center">客户名</th>
        <th field="serveType" width="50" align="center" formatter="formatterServeType">服务类型</th>
        <th field="overview" width="50" align="center">概要信息</th>
        <th field="serviceRequest" width="50" align="center">请求内容</th>
        <th field="createPeople" width="50" align="center">创建人</th>
        <th field="createDate" width="200" align="center" >创建时间</th>
        <th field="updateDate" width="200" align="center" >更新时间</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <div>
        <a href="javascript:openAssignDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">分配</a>
    </div>
    <div>
        客户：<input type="text" id="s_customer" size="20" onkeydown="if(event.keyCode==13) searchCustomerServeByParams()"/>
        服务类型:
        <select id="s_serveType" class="easyui-combobox" name="serveType" style="width:200px;" panelHeight="auto">
            <option value="">请选择</option>
            <option value="6">咨询</option>
            <option value="7">建议</option>
            <option value="8">投资</option>
            <option value="9">维修</option>
        </select>
        <a href="javascript:searchCustomerServeByParams()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
    </div>
</div>






<div id="dlg" class="easyui-dialog" style="width:600px;height:300px;padding: 10px 20px"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <table cellspacing="8px">
            <tr>
                <td>服务类型：</td>
                <td>
                    <select id="serveType" class="easyui-combobox" name="serveType" style="width:200px;" panelHeight="auto">
                        <option value="">请选择</option>
                        <option value="6">咨询</option>
                        <option value="7">建议</option>
                        <option value="8">投资</option>
                        <option value="9">维修</option>
                    </select>
                </td><td></td>
                <td>客户</td>
                <td><input type="text" name="customer"  class="easyui-validatebox" required="true"/></td>
            </tr>
            <tr>
                <td>概要：</td>
                <td><input type="text"  name="overview"/></td>
                <td></td>
                <td>服务请求</td>
                <td><input type="text" name="serviceRequest" /></td>
            </tr>
            <tr>
                <td>分配人：</td>
                <td>
                    <input id="s_assigner" class="easyui-combobox" name="assigner"
                           valueField="id" textField="name" url="${ctx}/user/queryAllCustomerManager"  panelHeight="auto"/>
                </td>
            </tr>
        </table>
        <input name="id" type="hidden"/>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:addAssign()" class="easyui-linkbutton" iconCls="icon-ok">分配</a>
    <a href="javascript:closeRoleDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>


</body>