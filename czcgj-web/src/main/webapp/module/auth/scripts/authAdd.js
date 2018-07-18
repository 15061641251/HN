var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
var app = angular.module('authAddApp', [ 'myHttpInterceptor' ]).config(
		[ '$httpProvider', function($httpProvider) {
			$httpProvider.interceptors.push('myHttpInterceptor');
		} ]);
app.controller('authAddController', [
		'$scope',
		'$http',
		'$timeout',
		function($scope, $http, $timeout) {

			$scope.resCheck = function(res, reslist) {
				// 如果有任何一个子节点被选中，则让上级节点也选中
				// 由于主菜单没有传过来，需要后台每次主动添加，这边只有两级
				reslist.is_checked = !!_.findWhere(reslist.children, {
					is_checked : true
				});
			};

			$scope.reslistCheck = function(reslist) {
				// 自动选中所有下级,取消的时候，子也取消
                if(reslist.is_checked == false){
                    res.is_checked = reslist.is_checked;
                }
			};

			$scope.postItems = [];
			$scope.addAuth = function($timeout) {
				// 找出所有已选中的复选框
				// 先找第二级的被选中的
				postItems = [];
				_.each($scope.resources, function(reslist) {
					if (reslist.is_checked) {
						postItems.push(reslist.int_id);
						_.each(reslist.children, function(res) {
							if (res.is_checked) {
								postItems.push(res.int_id);
							}
						})
					}

				});

				if (postItems.length == 0) {

					layer.msg('请选择权限');

				} else {

				    layer.load(2);

					$http.post(contextpath + '/auth/addAuthPost', {
                        auth_ids: postItems.join(","),
						name : $scope.name,
						description : $scope.description
					}).success(
							function(response, data, header, config, status,
									$timeout) {
                                layer.closeAll('loading');
								var success = response.success;
								if (success == 0) {
									layer.msg('错误的请求');
								} else if (success == 1) {
									// 关闭页面，刷新父界面
									layer.msg("添加成功", {
										time : 2000
									}, function() {

										parent.layer.close(index);
									})
									parent.list_search();

								}
							}).error(
							function(response, data, header, config, status) {
                                layer.closeAll('loading');
								layer.msg('出错了,请重试');
							});
				}
			};

			$http.get(contextpath + '/auth/getAllAuthReources').success(
					function(response, data, header, config, status) {
						$scope.resources = response;
					}).error(function(response, data, header, config, status) {
				layer.msg('出错了,请重试');
			});
		} ]);