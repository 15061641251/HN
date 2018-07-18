var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('DeviceAddApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]);

$("#device_type").select2({minimumResultsForSearch: Infinity});

app.controller('deviceAddController', ['$scope', '$http', function ($scope, $http) {

    vm = $scope;

    $scope.$watch('device_id', function () {
        $scope.api_url = 'http://api.js.cmcconenet.com/devices/' + (isEmpty($scope.device_id) ? "" : $scope.device_id);
    });

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


    $scope.addDevice = function () {

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
        $http.post(contextpath + '/device/addDevicePost', {

            "device_name": $scope.device_name,
            "device_id": $scope.device_id,
            "auth_id": $scope.auth_id,
            "access_type": $scope.access_type,
            "api_key": $scope.api_key,
            "api_url": $scope.api_url,
            "period": $scope.period,
            "warn_period": $scope.warn_period,
            "warn_angle": $scope.warn_angle,
            "serious_warn_period": $scope.serious_warn_period,
            "serious_warn_angle": $scope.serious_warn_angle,
            "lat": $scope.lat,
            "lng": $scope.lng,
            "address": $scope.address,
            "device_type":$scope.device_type,
            no_tip_minute:$scope.no_tip_minute

        }).success(function (response, data, header, config, status) {

            layer.closeAll('loading');
            var returnCode = response.returnCode;
            if (returnCode == 0) {
                layer.msg(response.description, {icon: 5});
            }
            else if (returnCode == 1) {
                // 关闭页面，刷新父界面
                layer.msg("添加成功", {
                    time: 2000,
                    icon: 6
                }, function () {
                    parent.layer.close(index);
                })
                parent.list_search();
            }
        }).error(function (response, data, header, config, status) {
            layer.closeAll('loading');
            layer.msg('出错了,请重试', {icon: 5});
        });
    };

    $scope.selectArea = function (area_id, area_name) {
        $scope.area_id = area_id;
        $scope.area_name = area_name;

        $scope.$apply();
    }

}]);
