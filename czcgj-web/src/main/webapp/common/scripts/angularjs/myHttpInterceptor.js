angular.module('myHttpInterceptor', []).factory('myHttpInterceptor', [ '$rootScope', '$q', '$location', '$timeout', function($rootScope, $q, $location, $timeout)
{
	return{
		'request' : function(config)
		{
			config.headers['x-requested-with'] = 'XMLHttpRequest';
			return config || $q.when(config);
		},
		'requestError' : function(rejection)
		{
			return rejection;
		},
		'response' : function(response)
		{
			return response || $q.when(response);
		},
		'responseError' : function(response)
		{
			if (response.status === 1000)
			{
				// 超时
				window.location = contextpath + "/timeout.jsp";
				return false;
			}
			else if (response.status === 1001)
			{
				// 权限不足
				window.location = contextpath + "/denied.jsp";
				return false;
			}
			else if (response.status === 404)
			{
				window.location = contextpath + "/404/404.jsp";
				return false;
			}
			else if (response.status === 500)
			{
				window.location = contextpath + "/500.jsp";
				return false;
			}
			return $q.reject(response);
		}
	};
} ]);