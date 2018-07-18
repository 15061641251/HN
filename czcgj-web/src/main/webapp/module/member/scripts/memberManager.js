var dept_id = '';

var $table = $("#table");

bootstap_table_option.url = contextpath + '/member/getUserListByPage';
bootstap_table_option.queryParams = function (params) {
    var user_name = $('#user_name').val();
    var company_name = $('#company_name').val();
    var area_name = $('#area_name').val();
    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        user_name: user_name,
        dept_id: dept_id,
        company_name: company_name,
        area_name: area_name
    };
}

bootstap_table_option.columns = [
    {
        field: 'index',
        width: 100,
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            var page = $table.bootstrapTable("getPage");
            return page.pageSize * (page.pageNumber - 1) + index + 1;
        }
    }, {
        field: 'company_name',
        title: '机构名称',
        align: 'center'
    }, {
        field: 'area_name',
        title: '区域名称',
        align: 'center'
    }, {
        title: '姓名',
        align: 'center',
        formatter: function (value, row, index) {

            var result = '<a onclick="showuser(' + row.int_id + ')">' + row.username + '</a>';

            return result;
        }
    }, {
        field: 'account',
        title: '登录名',
        align: 'center'
    }, {
        field: 'sex',
        title: '性别',
        align: 'center'
    }, {
        field: 'mobile',
        title: '工作手机',
        align: 'center'
    }, {
        title: '状态',
        align: 'center',
        formatter: function (value, row, index) {
            var result = '';

            if(row.is_enabled == 1){
                result = '启用';
            }else{
                result = '<span style="color: red">禁用</span>';
            }
            return result;
        }
    }, {
        title: '操作',
        align: 'center',
        formatter: function (value, row, index) {
            var result = '';

            var edit = '<a onclick="edituser(' + row.int_id + ')">修改</a>';
            var del = '<a onclick="deluser(' + row.int_id + ')">删除</a>';
            var enable = '';
            if (row.is_enabled == 1) {
                enable = '<a onclick="stop_user(' + row.int_id + ')">禁用</a>';
            } else {
                enable = '<a onclick="start_user(' + row.int_id + ')">启用</a>';
            }

            result = edit + del + enable;
            return result;
        }
    }];

$table.bootstrapTable(bootstap_table_option);

var getUserListByPage = function () {
    var treeObj = $.fn.zTree.getZTreeObj("groupTree");
    if (treeObj != null) {
        var sNodes = treeObj.getSelectedNodes();

        if (sNodes.length > 0) {
            dept_id = sNodes[0].id;
        }
    }
    list_search();
}

var edituser = function (user_id) {
    var index = layer.open({
        type: 2,
        area: ['80%', '80%'],
        maxmin: true,
        title: '修改用户',
        skin: 'layui-layer-rim', // 加上边框
        content: [contextpath + '/member/editMember?userId=' + user_id]
    });
    layer.full(index);
}

var deluser = function (user_id) {
    layer.confirm('确定删除该用户吗？', {
        title: '提示',
        btn: ['确定', '取消']
    }, function () {
        // 确定删除
        layer.load(2);

        $.post(contextpath + '/member/delUser', {
            "userId": user_id
        }, function (response) {
            var success = response.success;
            layer.closeAll('loading');
            if (success == 0) {
                layer.msg('错误的请求');
            }
            else if (success == 1) {
                layer.msg('删除成功');
                list_search();
            }
        });
    });
}

var showuser = function (user_id) {
    var index = layer.open({
        type: 2,
        area: ['80%', '80%'],
        maxmin: true,
        title: ['用户详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
        skin: 'layui-layer-rim', // 加上边框
        content: [contextpath + '/member/showMember?int_id=' + user_id]
    });
    layer.full(index);
}

var list_search = function () {
    $table.bootstrapTable('refresh');
}

var start_user = function (user_id) {
    var message = '确定要启用该用户吗?';

    layer.confirm(message, {
        btn: ['确定', '取消']
    }, function (index, layero) {
        layer.load(2);

        $.get(contextpath + '/member/changestatus', {
                "userId": user_id,
                "is_enabled": 1
            }, function (response) {
                var success = response.success;
                layer.closeAll('loading');
                if (success == 0) {
                    layer.msg('错误的请求');
                }
                else if (success == 1) {
                    list_search();
                    layer.msg('操作成功');
                }
            }
        );
    }, function (index) {
        // 取消的回调
    });
}

var stop_user = function (user_id) {
    var message = '确定要禁用该用户吗?';

    layer.confirm(message, {
        btn: ['确定', '取消']
    }, function (index, layero) {
        layer.load(2);

        $.get(contextpath + '/member/changestatus', {
                "userId": user_id,
                "is_enabled": 0
            }, function (response) {
                var success = response.success;
                layer.closeAll('loading');
                if (success == 0) {
                    layer.msg('错误的请求');
                }
                else if (success == 1) {
                    list_search();
                    layer.msg('操作成功');
                }
            }
        );
    }, function (index) {
        // 取消的回调
    });
}

var addUser = function () {
    var dept_id = 0;
    var index = layer.open({
        type: 2,
        area: ['80%', '80%'],
        maxmin: true,
        title: '添加用户',
        skin: 'layui-layer-rim', // 加上边框
        content: [contextpath + '/member/addMember?dept_id=' + dept_id]
    });
    layer.full(index);

}

var showuser = function (user_id) {
    var index = layer.open({
        type: 2,
        area: ['80%', '80%'],
        maxmin: true,
        title: ['用户详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
        skin: 'layui-layer-rim', // 加上边框
        content: [getlocalhost() + '/member/showMember?int_id=' + user_id]
    });
    layer.full(index);
}
