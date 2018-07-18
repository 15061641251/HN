$(function () {

    var index = layer.load(2);

    $.get(contextpath + '/device/getDeviceDetail?deviceId=' + getQueryString("deviceId"), function (response) {

        layer.close(index);

        if (response.returnCode == 1) {

            var device_name = response.result.device_name;
            var device_id = response.result.device_id;
            var auth_id = response.result.auth_id;
            var access_type = response.result.access_type;
            var api_key = response.result.api_key;
            var api_url = response.result.api_url;
            var lat = response.result.lat;
            var lng = response.result.lng;
            var area_name = response.result.area_name;
            var address = response.result.address;
            var period = response.result.period;
            var warn_period = response.result.warn_period;
            var warn_angle = response.result.warn_angle;
            var serious_warn_period = response.result.serious_warn_period;
            var serious_warn_angle = response.result.serious_warn_angle;
            var no_tip_minute = response.result.no_tip_minute;
            var device_type = response.result.device_type;

            $("#device_name").html(device_name);
            $("#device_id").html(device_id);
            $("#auth_id").html(auth_id);
            $("#access_type").html(access_type);
            $("#api_key").html(api_key);
            $("#api_url").html(api_url);
            $("#lat").html(lat);
            $("#lng").html(lng);
            $("#area_name").html(area_name);
            $("#address").html(address);
            $("#period").html(period);
            $("#warn_period").html(warn_period);
            $("#warn_angle").html(warn_angle);
            $("#serious_warn_period").html(serious_warn_period);
            $("#serious_warn_angle").html(serious_warn_angle);
            $("#no_tip_minute").html(no_tip_minute);

            if(device_type == 1){
                $("#device_type").html("重庆设备");
            }else if(device_type == 2){
                $("#device_type").html("常州本地设备");
            }



        } else {
            layer.msg(response.description, {icon: 5});
        }
    });
})