var $table = $("#table");

bootstap_table_option.url = contextpath + '/device/getDeviceListByPage';
bootstap_table_option.queryParams = function (params) {
    var device_name = $('#device_name').val();
    var device_id = $("#device_id").val();
    var company_name = $("#company_name").val();

    return {
        pageIndex: isEmpty(params.offset) ? 0 : (params.offset / params.limit) + 1,
        pageSize: isEmpty(params.limit) ? 0 : params.limit,
        device_name: device_name,
        device_id: device_id,
        company_name: company_name
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
        width: 120,
        field: 'company_name',
        title: '机构名称',
        align: 'center'
    }, {
        width: 120,
        field: 'area_name',
        title: '区域名称',
        align: 'center'
    }, {
        field: 'device_name',
        title: '设备名称',
        align: 'center'
    }, {
        field: 'create_time',
        title: '创建时间',
        align: 'center'
    }, {
        width: 110,
        field: 'device_id',
        title: '设备ID',
        align: 'center'
    }, {
        width: 80,
        field: 'access_type',
        title: '接入方式',
        align: 'center'
    }, {
        field: 'address',
        title: '安装地址',
        align: 'center'
    }, {
        width: 100,
        field: 'is_set_rule',
        title: '告警规则',
        align: 'center',
        formatter: function (value, row, index) {
            if (row.is_set_rule > 0) {
                return "<span>已设置</span>";
            } else {
                return "<span style=\"color:red\">未设置</span>";
            }

        }
    }, {
        width: 110,
        title: '任务调度状态',
        align: 'center',
        formatter: function (value, row, index) {
            //通过formatter可以自定义列显示的内容
            //value：当前field的值，即id
            //row：当前行的数据
            var result = '';

            if (row.status == 0) {
                result = '<span style="color:red">停止</span>';
            } else {
                result = '<span>正常</span>';
            }

            return result;
        }
    }, {
        title: '操作',
        align: 'center',
        width: 220,
        formatter: function (value, row, index) {
            //通过formatter可以自定义列显示的内容
            //value：当前field的值，即id
            //row：当前行的数据
            var result = '';

            var detail = '<a onclick="showDevice(' + row.int_id + ')">查看</a>';
            var edit = '<a onclick="editDevice(' + row.int_id + ')">修改</a>';
            var del = '<a onclick="delDevice(' + row.int_id + ')">删除</a>';

            var log = '<a onclick="deviceLog(' + row.int_id + ')">日志</a>';
            var task = '';
            if (row.status == 0) {
                task = '<a onclick="startTask(' + row.int_id + ')">启动任务</a>';
            } else {
                task = '<a onclick="stopTask(' + row.int_id + ')">停止任务</a>';
            }

            var rule = '<a onclick="rule(' + row.int_id + ')">规则设置</a>';

            var tip = '<a onclick="tip(' + row.int_id + ')">提醒设置</a>';

            result = detail + edit + del + log + task + rule + tip;
            return result;
        }
    }];


$table.bootstrapTable(bootstap_table_option);

////////////////////////////////////////////////////////////////////////////////


//不能命名为 search,会提示找不到,具体原因不明
function list_search() {
    $table.bootstrapTable('refresh');
}

var rule = function (device_int_id) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '设备规则设置',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/deviceWarnRule/manager?device_int_id=' + device_int_id]
        });
    layer.full(index);
}

var tip = function (device_int_id) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '提醒设置',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/deviceTip/manager?device_int_id=' + device_int_id]
        });
    layer.full(index);
}

//查看设置数据日志
var deviceLog = function (device_int_id) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '设备数据日志',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/deviceLog/manager?device_int_id=' + device_int_id]
        });
    layer.full(index);
}

//开始任务
var startTask = function (device_int_id) {
    layer.confirm('确定启用任务调用吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);

            $.post(contextpath + '/device/startTask', {
                "device_int_id": device_int_id
            }, function (data) {
                var returnCode = data.returnCode;
                layer.closeAll('loading');
                if (returnCode == 1) {
                    layer.msg('启动成功', {icon: 6});
                    list_search();
                } else {
                    layer.msg(data.description, {icon: 5});
                }
            });
        })
}

//停止任务
var stopTask = function (device_int_id) {
    layer.confirm('确定停止任务调用吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);

            $.post(contextpath + '/device/stopTask', {
                "device_int_id": device_int_id
            }, function (data) {
                var returnCode = data.returnCode;
                layer.closeAll('loading');
                if (returnCode == 1) {
                    layer.msg('停止成功', {icon: 6});
                    list_search();
                } else {
                    layer.msg(data.description, {icon: 5});
                }
            });
        })
}

var addDevice = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '新增设备',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/device/addDevice']
        });
    layer.full(index);
}

var showDevice = function (deviceId) {
    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: ['设备详情', 'text-align:center;font-size:16px;font-weight:bold;font-family:微软雅黑;'],
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/device/deviceDetail?deviceId=' + deviceId]
        });
    layer.full(index);
}

var editDevice = function (deviceId) {

    var index = layer.open(
        {
            type: 2,
            area: ['80%', '90%'],
            maxmin: true,
            title: '修改设备信息',
            skin: 'layui-layer-rim', // 加上边框
            content: [contextpath + '/device/editDevice?deviceId=' + deviceId]
        });
    layer.full(index);
}

var delDevice = function (deviceId) {
    layer.confirm('确定删除该设备吗？',
        {
            title: '提示',
            btn: ['确定', '取消']
        }, function () {
            // 确定删除
            layer.load(2);

            $.post(contextpath + '/device/delDevice', {
                "deviceId": deviceId
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