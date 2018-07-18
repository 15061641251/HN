var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('editCompanyApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]).config(['$locationProvider', function ($locationProvider)// 配置路由模式
{
    $locationProvider.html5Mode(true);
}]);

var vm;
app.controller('editCompanyController', ['$scope', '$http', '$location', function ($scope, $http, $location) {

    vm = $scope;

    $scope.editCompany = function () {

        if (isEmpty($scope.company_name) || isEmpty($scope.address) || isEmpty($scope.person_name) || isEmpty($scope.person_phone)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/company/editCompanyById', {

            int_id: $scope.int_id,
            company_name: $scope.company_name,
            address: $scope.address,
            person_name: $scope.person_name,
            person_phone: $scope.person_phone,
            remark: $scope.remark

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


    $http.get(contextpath + '/company/getCompanyDetail?companyId=' + $location.search()["companyId"]).success(function (response, data, header, config, status) {

        $scope.int_id = response.result.int_id;
        $scope.company_name = response.result.company_name;
        $scope.address = response.result.address;
        $scope.person_name = response.result.person_name;
        $scope.person_phone = response.result.person_phone;
        $scope.remark = response.result.remark;

    });

}])
;