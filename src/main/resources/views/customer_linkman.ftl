<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.3.3/jquery.edatagrid.js"></script>
<script type="text/javascript" src="${ctx}/static/js/customer.linkman.js"></script>

<div id="dlg02" class="easyui-dialog" style="width:750px;height:400px;" resizable="true" closed="true">

    <table id="dg02" class="easyui-edatagrid"
           toolbar="#toolbar"  pagination="true" rownumbers="true" fit="true" singleSelect="true">
        <thead>
        <tr>
            <th field="cb" checkbox="true" align="center"></th>
            <th field="linkName" width="100" align="center" editor="{type:'validatebox',options:{required:true}}">联系人</th>
            <th field="sex" width="50" align="center" editor="text">性别</th>
            <th field="zhiwei" width="100" align="center" editor="{type:'validatebox',options:{required:true}}">职位</th>
            <th field="officePhone" width="120" align="center" editor="text">办公电话</th>
            <th field="phone" width="120" align="center" editor="{type:'validatebox',options:{required:true}}">电话</th>
        </tr>
        </thead>
    </table>
    <div id="toolbar">
            <a href="javascript:$('#dg02').edatagrid('addRow')" class="easyui-linkbutton" iconCls="icon-add" plain="true" >添加</a>
            <a href="javascript:deleteCustomerLinkman()" class="easyui-linkbutton" iconCls="icon-remove" plain="true" >删除</a>
            <a href="javascript:saveCustomerLinkman()" class="easyui-linkbutton" iconCls="icon-save" plain="true" >保存</a>
            <a href="javascript:$('#dg02').edatagrid('cancelRow')" class="easyui-linkbutton" iconCls="icon-undo" plain="true" >撤销行</a>
        <div>
            联系人： <input type="text" id="linkName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLinkman()"/>
            职位：<input type="text" id="zhiwei" size="20" onkeydown="if(event.keyCode==13) searchCustomerLinkman()"/>
            电话：<input type="text" id="phone02" size="20" onkeydown="if(event.keyCode==13) searchCustomerLinkman()"/>
            <a href="javascript:searchCustomerLinkman()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
        </div>
    </div>
</div>

