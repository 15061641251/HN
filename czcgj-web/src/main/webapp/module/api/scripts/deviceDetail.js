$(function () {

    var index = layer.load(2);

    $.get(contextpath + '/api//getDeviceDetailByDeviceId?deviceId=' + getQueryString("deviceId"), function (response) {

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
            var address = response.result.address;
            var period = response.result.period;
            var warn_period = response.result.warn_period;

            $("#device_name").html(device_name);
            $("#device_id").html(device_id);
            $("#auth_id").html(auth_id);
            $("#access_type").html(access_type);
            $("#api_key").html(api_key);
            $("#api_url").html(api_url);
            $("#lat").html(lat);
            $("#lng").html(lng);
            $("#address").html(address);
            $("#period").html(period);
            $("#warn_period").html(warn_period);


        } else {
            layer.msg(response.description, {icon: 5});
        }
    });
})