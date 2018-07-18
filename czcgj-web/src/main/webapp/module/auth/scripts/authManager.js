var $table = $("#table");

bootstap_table_option.url = contextpath + '/auth/getAuthListByPage';
bootstap_table_option.queryParams = function (params) {
    var name = $('#name').val();
    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        name: name
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
        field: 'name',
        title: '角色名称',
        align: 'center'
    }, {
        field: 'description',
        title: '角色描述',
        align: 'center'
    }, {
        title: '操作',
        align: 'center',
        width: 200,
        formatter: function (value, row, index) {
            //通过formatter可以自定义列显示的内容
            //value：当前field的值，即id
            //row：当前行的数据
            var result = '';

            var detail = '<a onclick="showAuth(' + row.int_id + ')">查看</a>';
            var edit = '<a onclick="editAuth(' + row.int_id + ')">修改</a>';
            var del = '<a onclick="delAuth(' + row.int_id + ')">删除</a>';

            result = detail + edit + del;
            return result;
        }
    }];


$table.bootstrapTable(bootstap_table_option);

////////////////////////////////////////////////////////////////////////////////


//不能命名为 search,会提示找不到,具体原因不明
function list_search() {
    $table.bootstrapTable('refresh');
}

var addAuth = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '新增角色',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/auth/addAuth']
        });
    layer.full(index);
}

var showAuth = function (authId) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: ['角色详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/auth/authDetail?authId=' + authId]
        });
    layer.full(index);
}

var editAuth = function (authId) {

    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '修改角色',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/auth/editAuth?authId=' + authId]
        });
    layer.full(index);
}

var delAuth = function (authId) {
    layer.confirm('确定删除该角色吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);


            $.post(contextpath + '/auth/delAuth', {
                "authId": authId
            }, function (data) {
                var success = data.success;
                layer.closeAll('loading');
                if (success == 0) {
                    layer.msg('错误的请求');
                }
                else if (success == 1) {
                    layer.msg('删除成功');
                    // 从数组中删除
                    list_search();
                }
            });
        })

}