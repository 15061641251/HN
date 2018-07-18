var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('editDeviceApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

$("#device_type").select2({
    minimumResultsForSearch: Infinity
});

var vm;
app.controller('editDeviceController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    vm = $scope;

    // 选择区域
    $scope.chooseArea = function () {
        var id = "";
        layer.open({
            type: 2,
            area: ['80%', '80%'],
            title: '选择区域',
            content: [contextpath + '/area/chooseOneArea']
        });
    };

    $scope.editDevice = function () {

        if (isEmpty($scope.device_name) || isEmpty($scope.device_id) || isEmpty($scope.auth_id) || isEmpty($scope.access_type)
            || isEmpty($scope.api_key) || isEmpty($scope.api_url) || isEmpty($scope.address) || isEmpty($scope.device_type)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }
        if (!isNumber($scope.period) || !isNumber($scope.warn_period)) {
            layer.msg('请填写正确数值', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/device/editDeviceById', {

            int_id: $scope.int_id,
            device_name: $scope.device_name,
            device_id: $scope.device_id,
            auth_id: $scope.auth_id,
            access_type: $scope.access_type,
            api_key: $scope.api_key,
            api_url: $scope.api_url,
            lat: $scope.lat,
            lng: $scope.lng,
            address: $scope.address,
            company_id: $scope.company_id,
            period: $scope.period,
            warn_period: $scope.warn_period,
            warn_angle: $scope.warn_angle,
            serious_warn_period: $scope.serious_warn_period,
            serious_warn_angle: $scope.serious_warn_angle,
            area_id: $scope.area_id,
            no_tip_minute:$scope.no_tip_minute,
            device_type:$scope.device_type

        }).success(function (response, data, header, config, status, $timeout) {

            layer.closeAll('loading');
            var returnCode = response.returnCode;
            if (returnCode == 0) {
                layer.msg(response.description, {icon: 5});
            }
            else if (returnCode == 1) {
                // 关闭页面，刷新父界面
                layer.msg('修改成功', {
                    time: 2000,
                    icon: 6
                }, function () {
                    parent.layer.close(index);
                });
                parent.list_search();
            }
        }).error(function (response, data, header, config, status) {
            layer.closeAll('loading');
            layer.msg('出错了,请重试', {icon: 5});
        });
    };


    $http.get(contextpath + '/device/getDeviceDetail?deviceId=' + $location.search()["deviceId"]).success(function (response, data, header, config, status) {

        $scope.int_id = response.result.int_id;
        $scope.device_name = response.result.device_name;
        $scope.device_id = response.result.device_id;
        $scope.auth_id = response.result.auth_id;
        $scope.access_type = response.result.access_type;
        $scope.api_key = response.result.api_key;
        $scope.api_url = response.result.api_url;
        $scope.lat = response.result.lat;
        $scope.lng = response.result.lng;
        $scope.address = response.result.address;
        $scope.company_id = response.result.company_id;
        $scope.period = response.result.period;
        $scope.warn_period = response.result.warn_period;
        $scope.warn_angle = response.result.warn_angle;
        $scope.serious_warn_period = response.result.serious_warn_period;
        $scope.serious_warn_angle = response.result.serious_warn_angle;
        $scope.area_id = response.result.area_id;
        $scope.area_name = response.result.area_name;
        $scope.no_tip_minute = response.result.no_tip_minute;
        $scope.device_type = response.result.device_type;

        setSelect2Val('device_type', $scope.device_type);


    });

    $scope.selectArea = function (area_id, area_name) {
        $scope.area_id = area_id;
        $scope.area_name = area_name;

        $scope.$apply();
    }

}])
;