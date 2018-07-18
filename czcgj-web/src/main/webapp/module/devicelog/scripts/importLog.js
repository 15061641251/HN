var importLog = function () {

    debugger;

    var device_int_id = getQueryString('device_int_id');
    var log_start_time = $("#log_start_time").val();
    var log_end_time = $("#log_end_time").val();

    if (isEmpty(log_start_time) || isEmpty(log_end_time)) {

        layer.msg('请选择时间', {icon: 5});
        return;
    }

    layer.load(2);
    
    $.get(contextpath + '/deviceLog/importLog', {
        "device_int_id": device_int_id,
        "log_start_time": log_start_time,
        "log_end_time": log_end_time
    }, function (data) {
        var returnCode = data.returnCode;
        layer.closeAll('loading');
        if (returnCode == 1) {
            layer.msg('补录成功', {icon: 6});
            parent.list_search();
        } else {
            layer.msg(data.description, {icon: 5});
        }
    });
}