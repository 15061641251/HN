var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

var app = angular.module('CompanyAddApp', ['myHttpInterceptor']).config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push('myHttpInterceptor');

}]);

app.controller('companyAddController', ['$scope', '$http', function ($scope, $http) {

    vm = $scope;

    $scope.addCompany = function () {

        if (isEmpty($scope.company_name) || isEmpty($scope.address) || isEmpty($scope.person_name) || isEmpty($scope.person_phone)) {
            layer.msg('请填写完整', {icon: 5});
            return false;
        }

        layer.load(2);
        $http.post(contextpath + '/company/addCompanyPost', {

            "company_name": $scope.company_name,
            "address": $scope.address,
            "person_name": $scope.person_name,
            "person_phone": $scope.person_phone,
            "remark": $scope.remark

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
