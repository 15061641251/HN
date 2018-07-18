/**
 * 信息中心模板
 * 参数所需的js方法，集中管理
 * Created by ZhuYanBing on 2017/2/18.
 */

/**
 * 选择科室（部门）
 * @param item
 */
var changeDept = function () {
    var index = layer.open(
        {
            type: 2,
            area: ['500px', '500px'],
            title: '选择部门',
            content: [contextpath + '/dept/chooseDept2?ids=' + $("#deptIds").val()]
        });
}

/**
 * 呈现参数列表
 * @param paramList
 * @returns {string}
 */
var listParams = function (paramList) {
    var paramHtml = "";
    if (paramList.length > 0) {
        for (var i = 0; i < paramList.length; i++) {
            paramHtml += paramList[i].param_name + "&nbsp;&nbsp;" + paramList[i].type_value + "&nbsp;&nbsp;&nbsp;&nbsp;";
        }
    }
    return paramHtml;
}

var isEmptyParams = function (paramList) {
    if (paramList.length > 0) {
        for (var i = 0; i < paramList.length; i++) {
            var param = paramList[i];
            switch (param.param_type_id) {
                case 1:
                    if (isEmpty($("#deptIds").val())) {
                        layer.msg('请填写完整');
                        return true;
                    }
                    break;
                case 2:
                    if (isEmpty($("#param_Month").val())) {
                        layer.msg('请填写完整');
                        return true;
                    }
                    break;
            }
        }
    }
    return false;
}

/**
 * 将模板参数的值加入paramString,<<split>>分割
 * @param paramList
 * @returns {string}
 */
var completeParamList = function (paramList) {
    var paramString = "";
    if (paramList.length > 0) {
        for (var i = 0; i < paramList.length; i++) {
            var param = paramList[i];
            switch (param.param_type_id) {
                case 1:
                    if (isEmpty($("#deptIds").val())) {
                        layer.msg('请填写完整');
                    }
                    paramString += $("#deptIds").val();
                    break;
                case 2:
                    if (isEmpty($("#param_Month").val())) {
                        layer.msg('请填写完整');
                    }
                    paramString += $("#param_Month").val();
                    break;
            }
            if (i != paramList.length - 1) {
                paramString += "<<split>>";
            }
        }
    }
    return paramString;
}