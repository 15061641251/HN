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
        width: 100,
        formatter: function (value, row, index) {
            //通过formatter可以自定义列显示的内容
            //value：当前field的值，即id
            //row：当前行的数据
            var result = '';
            var select = '<a onclick="selectCompany(' + row.int_id + ',\'' + row.company_name + '\')">选择</a>';
            result = select;
            return result;
        }
    }];


$table.bootstrapTable(bootstap_table_option);

////////////////////////////////////////////////////////////////////////////////


//不能命名为 search,会提示找不到,具体原因不明
function list_search() {
    $table.bootstrapTable('refresh');
}

var selectCompany = function (company_id, company_name) {

    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    parent.layer.close(index);

    parent.vm.selectCompany(company_id, company_name);
}