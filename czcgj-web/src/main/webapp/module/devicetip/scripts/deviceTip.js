var app = angular.module('deviceTipApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('deviceTipAppController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    vm = $scope;

    var device_int_id = $location.search()["device_int_id"];

    $scope.tips = [];
    $scope.statuss = ["启用", "禁用"];

    layer.load(2);

    $http.get(contextpath + '/deviceTip/getDeviceTipList?device_int_id=' + device_int_id).success(function (response, data, header, config, status) {

        layer.closeAll('loading')
        if (response.returnCode == 1) {
            $scope.tips = response.resultList;

            for (var i = 0; i < $scope.tips.length; i++) {

                var tip = $scope.tips[i];
                var status = tip.status;
                if (status == 1) {
                    tip.show_status = '启用';
                } else if (status == 0) {
                    tip.show_status = '禁用';
                }
            }

        } else {
            layer.msg(response.description, {icon: 5});
        }

    }).error(function (response, data, header, config, status) {
        layer.closeAll('loading')
        layer.msg('出错了,请重试', {icon: 5});
    });

    $scope.addTip = function () {
        var new_tip = {
            'device_int_id': device_int_id,
            'status': '1',
            'show_status': '启用'
        };

        $scope.tips.push(new_tip);
    }

    $scope.saveTip = function () {

        for (var i = 0; i < $scope.tips.length; i++) {

            var tip = $scope.tips[i];
            var show_status = tip.show_status;
            if (show_status == '启用') {
                tip.status = 1;
            } else if (show_status == '禁用') {
                tip.status = 0;
            }

        }

        for (var i = 0; i < $scope.tips.length; i++) {
            var variable = $scope.tips[i];
            if (isEmpty(variable.phone_num)) {
                layer.msg('请填写完整', {icon: 5});
                return;
            }
        }

        var no_tip_minute = $("#no_tip_minute").val();

        if (!isNumber(no_tip_minute)) {
            layer.msg('请填写数值', {icon: 5});
            return;
        }

        layer.load(2);

        $.post(contextpath + "/deviceTip/saveDeviceTip", {
            device_int_id: device_int_id,
            no_tip_minute: no_tip_minute,
            tipList: JSON.stringify($scope.tips)

        }, function (response) {

            layer.closeAll('loading');
            var returnCode = response.returnCode;

            if (returnCode == 1) {

                layer.msg('保存成功', {
                    time: 2000,
                    icon: 6
                }, function () {
                    // parent.layer.close(index);
                });
                // parent.list_search();

            } else if (returnCode == 0) {

                layer.msg(data.description, {icon: 5});

            } else {

                layer.msg('出错了,请重试', {icon: 5});

            }
        });

    }

    $scope.delTip = function (tip) {
        for (var i = 0; i < $scope.tips.length; i++) {
            if ($scope.tips[i] == tip) {
                $scope.tips.splice(i, 1);
                break;
            }
        }
    }

}]);

