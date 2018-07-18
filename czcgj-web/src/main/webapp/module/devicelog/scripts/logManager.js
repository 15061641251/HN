var $table = $("#table");

bootstap_table_option.url = contextpath + '/deviceLog/getDeviceLogListByPage';
bootstap_table_option.queryParams = function (params) {
    var device_int_id = getQueryString('device_int_id');
    var log_start_time = $('#log_start_time').val();
    var log_end_time = $('#log_end_time').val();
    var status = $('#status').val();

    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        device_int_id: device_int_id,
        log_start_time: log_start_time,
        log_end_time: log_end_time,
        status: status
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
        width: 200,
        field: 'device_id',
        title: '设备ID',
        align: 'center'
    }, {
        width: 200,
        field: 'device_name',
        title: '设备名称',
        align: 'center'
    }, {
        width: 200,
        field: 'status',
        title: '告警状态',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.status == 1) {
                return "<span style=\"color:red\">告警</span>";
            }

        }
    }, {
        field: 'create_time',
        title: '调度时间',
        align: 'center'
    }, {
        field: 'log_time',
        title: '上报时间',
        align: 'center'
    }
];

bootstap_table_option.detailView = true;

bootstap_table_option.detailFormatter = function (index, row, element) {
    var result = '<table style="text-align: center;"><tr><th style="text-align: center;">数据流</th><th style="text-align: center;">数据值</th></tr>';

    var data_json = JSON.parse(row.data);
    for (var i = 0; i < data_json.length; i++) {
        var data = data_json[i];

        var key = data.id;
        var value = data.value;

        result += '<tr><td>' + key + '</td>' + '<td>' + value + '</td></tr>';

    }
    result += '</table>';
    return result;
}

$table.bootstrapTable(bootstap_table_option);

////////////////////////////////////////////////////////////////////////////////

var list_search = function () {
    $table.bootstrapTable('refresh');
}

//补录历史数据
var importLog = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['60%', '50%'],
            title: '补录数据',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/deviceLog/importLogHome?device_int_id=' + getQueryString('device_int_id')]
        });
}