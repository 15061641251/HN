var app = angular.module('deviceWarnRuleApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');
}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('deviceWarnRuleAppController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    vm = $scope;

    var device_int_id = $location.search()["device_int_id"];

    $scope.rules = [];
    $scope.show_types = ["<", "<=", "=", ">=", ">"];

    layer.load(2);

    $http.get(contextpath + '/deviceWarnRule/getDeviceWarnRuleList?device_int_id=' + device_int_id).success(function (response, data, header, config, status) {

        layer.closeAll('loading')
        if (response.returnCode == 1) {
            $scope.rules = response.resultList;

            for (var i = 0; i < $scope.rules.length; i++) {

                var rule = $scope.rules[i];
                var condition_type = rule.condition_type;
                if (condition_type == 1) {
                    rule.show_type = '<';
                } else if (condition_type == 2) {
                    rule.show_type = '<=';
                } else if (condition_type == 3) {
                    rule.show_type = '=';
                } else if (condition_type == 4) {
                    rule.show_type = '>=';
                } else if (condition_type == 5) {
                    rule.show_type = '>';
                }

            }

        } else {
            layer.msg(response.description, {icon: 5});
        }

    }).error(function (response, data, header, config, status) {
        layer.closeAll('loading')
        layer.msg('出错了,请重试', {icon: 5});
    });

    $scope.addRule = function () {
        var new_rule = {
            'device_int_id': device_int_id,
            'data_key': '',
            'condition_type': '',
            'data_value': ''
        };

        $scope.rules.push(new_rule);
    }

    $scope.saveRule = function () {

        for (var i = 0; i < $scope.rules.length; i++) {

            var rule = $scope.rules[i];
            var show_type = rule.show_type;
            if (show_type == '<') {
                rule.condition_type = 1;
            } else if (show_type == '<=') {
                rule.condition_type = 2;
            } else if (show_type == '=') {
                rule.condition_type = 3;
            } else if (show_type == '>=') {
                rule.condition_type = 4;
            } else if (show_type == '>') {
                rule.condition_type = 5;
            }

        }

        for (var i = 0; i < $scope.rules.length; i++) {
            var variable = $scope.rules[i];
            if (isEmpty(variable.data_key) || isEmpty(variable.condition_type) || isEmpty(variable.data_value)) {
                layer.msg('请填写完整', {icon: 5});
                return;
            }
        }


        layer.load(2);

        $.post(contextpath + "/deviceWarnRule/saveDeviceWarnRule", {
            device_int_id: device_int_id,
            ruleList: JSON.stringify($scope.rules)

        }, function (response) {

            layer.closeAll('loading');
            var returnCode = response.returnCode;

            if (returnCode == 1) {

                layer.msg('保存成功', {
                    time: 2000,
                    icon: 6
                }, function () {
                    parent.layer.close(index);
                });
                parent.list_search();

            } else if (returnCode == 0) {

                layer.msg(data.description, {icon: 5});

            } else {

                layer.msg('出错了,请重试', {icon: 5});

            }
        });

    }

    $scope.delRule = function (rule) {
        for (var i = 0; i < $scope.rules.length; i++) {
            if ($scope.rules[i] == rule) {
                $scope.rules.splice(i, 1);
                break;
            }
        }
    }

}]);

