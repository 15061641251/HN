var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('RegisterApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]);

app.controller('registerController', ['$scope', '$http', function ($scope, $http) {

    vm = $scope;

    $scope.register = function () {

        if (isEmpty($scope.company_name) || isEmpty($scope.address) || isEmpty($scope.person_name) || isEmpty($scope.person_phone)
            || isEmpty($scope.pwd) || isEmpty($scope.account)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/register/register', {

            "company_name": $scope.company_name,
            "address": $scope.address,
            "person_name": $scope.person_name,
            "person_phone": $scope.person_phone,
            "remark": $scope.remark,
            "account": $scope.account,
            "pwd": hex_md5($scope.pwd)

        }).success(function (response, data, header, config, status) {

            layer.closeAll('loading');
            var returnCode = response.returnCode;
            if (returnCode == 0) {
                layer.msg(response.description, {icon: 5});
            }
            else if (returnCode == 1) {
                // 关闭页面，刷新父界面
                layer.msg("注册成功", {
                    time: 2000,
                    icon: 6
                }, function () {
                    parent.layer.close(index);
                })
                parent.list_search();
            }else {
                layer.msg(response.description, {icon: 5});
            }
        }).error(function (response, data, header, config, status) {
            layer.closeAll('loading');
            layer.msg('出错了,请重试', {icon: 5});
        });
    };

}]);
