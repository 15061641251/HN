var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('AreaAddApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]);

app.controller('areaAddController', ['$scope', '$http', function ($scope, $http) {

    vm = $scope;

    $scope.addArea = function () {

        if (isEmpty($scope.area_name) || isEmpty($scope.leader_name) || isEmpty($scope.leader_phone)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/area/addAreaPost', {

            "area_name": $scope.area_name,
            "leader_name": $scope.leader_name,
            "leader_phone": $scope.leader_phone

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

}]);
