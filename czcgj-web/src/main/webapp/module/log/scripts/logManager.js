var $table = $("#table");

bootstap_table_option.url = contextpath + '/log/getLogListByPage';
bootstap_table_option.queryParams = function (params) {
    var ip = $('#ip').val();
    var module_name = $("#module_name").val();

    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        ip: ip,
        module_name: module_name
    };
}

bootstap_table_option.columns = [
    {
        field: 'index',
        width: 50,
        title: '序号',
        align: 'center',
        formatter: function (value, row, index) {
            var page = $table.bootstrapTable("getPage");
            return page.pageSize * (page.pageNumber - 1) + index + 1;
        }
    }, {
        width: 200,
        field: 'create_time',
        title: '请求时间',
        align: 'center'
    }, {
        width: 120,
        field: 'module_name',
        title: '模块名称',
        align: 'center'
    }, {
        width: 120,
        field: 'ip',
        title: '访问ip',
        align: 'center'
    }, {
        field: 'params',
        title: '请求参数',
        align: 'center'
    }];


$table.bootstrapTable(bootstap_table_option);

////////////////////////////////////////////////////////////////////////////////


//不能命名为 search,会提示找不到,具体原因不明
function list_search() {
    $table.bootstrapTable('refresh');
}
