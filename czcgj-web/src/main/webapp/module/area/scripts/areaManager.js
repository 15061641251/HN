var $table = $("#table");

bootstap_table_option.url = contextpath + '/area/getAreaListByPage';
bootstap_table_option.queryParams = function (params) {
    var company_name = $('#company_name').val();
    var area_name = $('#area_name').val();
    var leader_name = $('#leader_name').val();
    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        company_name: company_name,
        area_name: area_name,
        leader_name: leader_name
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
        field: 'area_name',
        title: '区域名称',
        align: 'center'
    }, {
        field: 'create_time',
        title: '创建时间',
        align: 'center'
    }, {
        field: 'leader_name',
        title: '负责人',
        align: 'center'
    }, {
        field: 'leader_phone',
        title: '负责人电话',
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

            var detail = '';
            var edit = '<a onclick="editArea(' + row.int_id + ')">修改</a>';
            var del = '<a onclick="delArea(' + row.int_id + ')">删除</a>';

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

var addArea = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '新增区域',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/area/addArea']
        });
    layer.full(index);
}

var showArea = function (areaId) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: ['区域详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/area/areaDetail?areaId=' + areaId]
        });
    layer.full(index);
}

var editArea = function (areaId) {

    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '修改区域信息',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/area/editArea?areaId=' + areaId]
        });
    layer.full(index);
}

var delArea = function (areaId) {
    layer.confirm('确定删除该区域吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);

            $.post(contextpath + '/area/delArea', {
                "areaId": areaId
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