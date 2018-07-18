var $table = $("#table");

bootstap_table_option.url = contextpath + '/company/getCompanyListByPage';
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
        field: 'company_name',
        title: '注册机构名称',
        align: 'center'
    }, {
        field: 'create_time',
        title: '创建时间',
        align: 'center'
    }, {
        field: 'person_name',
        title: '联系人',
        align: 'center'
    }, {
        field: 'person_phone',
        title: '联系电话',
        align: 'center'
    }, {
        field: 'address',
        title: '地址',
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

            var detail = '<a onclick="showCompany(' + row.int_id + ')">查看</a>';
            var edit = '<a onclick="editCompany(' + row.int_id + ')">修改</a>';
            var del = '<a onclick="delCompany(' + row.int_id + ')">删除</a>';

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

var addCompany = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '新增注册机构',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/company/addCompany']
        });
    layer.full(index);
}

var showCompany = function (authId) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: ['注册机构详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/company/companyDetail?companyId=' + authId]
        });
    layer.full(index);
}

var editCompany = function (companyId) {

    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '修改注册机构',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/company/editCompany?companyId=' + companyId]
        });
    layer.full(index);
}

var delCompany = function (companyId) {
    layer.confirm('确定删除该注册机构吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);

            $.post(contextpath + '/company/delCompany', {
                "companyId": companyId
            }, function (data) {
                var returnCode = data.returnCode;
                layer.closeAll('loading');
                if (returnCode == 0) {
                    layer.msg('错误的请求', {icon: 5});
                }
                else if (returnCode == 1) {
                    layer.msg('删除成功', {icon: 6});
                    // 从数组中删除
                    list_search();
                }
            });
        })

}