var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('editAreaApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('editAreaController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    vm = $scope;

    $scope.editArea = function () {

        if (isEmpty($scope.company_id) || isEmpty($scope.leader_name) || isEmpty($scope.leader_phone) || isEmpty($scope.area_name)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/area/editAreaById', {

            int_id: $scope.int_id,
            company_id: $scope.company_id,
            leader_name: $scope.leader_name,
            leader_phone: $scope.leader_phone,
            area_name: $scope.area_name

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


    $http.get(contextpath + '/area/getAreaDetail?areaId=' + $location.search()["areaId"]).success(function (response, data, header, config, status) {

        $scope.int_id = response.result.int_id;
        $scope.area_name = response.result.area_name;
        $scope.leader_name = response.result.leader_name;
        $scope.leader_phone = response.result.leader_phone;
        $scope.company_id = response.result.company_id;

    });

}])
;