$.ajaxSetup({
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    complete: function (xhr, textStatus) {
        // session timeout
        if (xhr.status == 1000) {
            // 超时
            window.location.href = contextpath + "/timeout.jsp";
            return;
        }
        else if (xhr.status == 1001) {
            // 权限不足
            window.location.href = contextpath + "/denied.jsp";
        }
        else if (xhr.status == 404) {
            // 页面未找到
            window.location.href = contextpath + "/404/404.jsp";
        }
        else if (xhr.status == 500) {
            // 服务器错误
            window.location.href = contextpath + "/500.jsp";
        } else if (xhr.status == 1002) {
            //税务微官网权限不够
            window.location.href = contextpath + "/wx/tax_cadre/auth_error";
        } else if (xhr.status == 1003) {
            //税务微官网不是微信
            window.location.href = contextpath + "/wx/tax_cadre/equip_error";
        }
    }
});

/**
 * 获取当天日期
 * @returns {string}
 */
function getFormatCurrentDay() {
    var today = new Date(); // 获取今天日期

    var year = today.getFullYear();
    var mouth = today.getMonth() + 1;
    if (parseInt(mouth) < 10) {
        mouth = "0" + mouth;
    }
    var day = today.getDate();
    if (parseInt(day) < 10) {
        day = "0" + day;
    }

    return year + "-" + mouth + "-" + day;
}

/**
 * 获取指定天日期时间
 * @returns {string}
 */
function getFormatCurrentDayTime(date) {

    var year = date.getFullYear();
    var mouth = date.getMonth() + 1;
    if (parseInt(mouth) < 10) {
        mouth = "0" + mouth;
    }
    var day = date.getDate();
    if (parseInt(day) < 10) {
        day = "0" + day;
    }

    var hour = date.getHours();
    if (parseInt(hour) < 10) {
        hour = "0" + hour;
    }

    var minute = date.getMinutes();
    if (parseInt(minute) < 10) {
        minute = "0" + minute;
    }

    var seconds = date.getSeconds();
    if (parseInt(seconds) < 10) {
        seconds = "0" + seconds;
    }

    return year + "-" + mouth + "-" + day + " " + hour + ":" + minute + ":" + seconds;


}

/**
 * 获取指定日期
 * @returns string
 */
function getFormatDay(date) {

    var year = date.getFullYear();
    var mouth = date.getMonth() + 1;
    if (parseInt(mouth) < 10) {
        mouth = "0" + mouth;
    }

    var day = date.getDate();
    if (parseInt(day) < 10) {
        day = "0" + day;
    }

    return year + "-" + mouth + "-" + day;
}

/**
 * 日期比较，date2 > date1,返回true，否则返回false
 * @param date1
 * @param date2
 */
function checkTime(date1, date2) {
    var oDate1 = new Date(date1);
    var oDate2 = new Date(date2);
    return oDate1.getTime() < oDate2.getTime();
}

/**
 * 获取日期str
 * @returns {string}
 */
function getFormatDateTime(timeStr) {
    var today = new Date(timeStr); // 获取日期

    today.setHours(today.getHours() - 14);

    var year = today.getFullYear();
    var mouth = today.getMonth() + 1;
    if (parseInt(mouth) < 10) {
        mouth = "0" + mouth;
    }
    var day = today.getDate();
    if (parseInt(day) < 10) {
        day = "0" + day;
    }
    var week = today.getDay();
    var hour = today.getHours();
    if (parseInt(hour) < 10) {
        hour = "0" + hour;
    }
    var minute = today.getMinutes();
    if (parseInt(minute) < 10) {
        minute = "0" + minute;
    }
    var seconds = today.getSeconds();
    if (parseInt(seconds) < 10) {
        seconds = "0" + seconds;
    }
    return year + "-" + mouth + "-" + day + " " + hour + ":" + minute + ":" + seconds;
}

/**
 * 判断是否为数字(包括小数),但不包括负数
 * @param input
 * @returns {boolean}
 */
function isRate(val) {
    var re = /^[0-9]+.?[0-9]*$/;
    if (!re.test(val)) {
        return false;
    }
    return true;
}

/**
 * 判断是否正整数
 */
function isNumber(val) {
    var re = /^[1-9]+[0-9]*]*$/;
    if (!re.test(val)) {
        return false;
    }
    return true;
}

/**
 * 空返回true,
 *
 * @param str
 * @returns {Boolean}
 */
function isEmpty(str) {
    if (str == "undefined") {
        return true;
    }

    if (typeof (str) == "undefined") {
        return true;
    }
    if (str == null) {
        return true;
    }
    if (str == "") {
        return true;
    }

    if (!isNaN(str)) {
        return false;
    }

    if (str.trim() == "") {
        return true;
    }

    return false;
}

String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, '');
};

// 获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)
        return unescape(r[2]);
    return null;
}

function getQueryString2(key){
    var reg = new RegExp("(^|&)"+key+"=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(reg);
    return result?decodeURIComponent(result[2]):'';
}

Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
        "H+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    var week = {
        "0": "/u65e5",
        "1": "/u4e00",
        "2": "/u4e8c",
        "3": "/u4e09",
        "4": "/u56db",
        "5": "/u4e94",
        "6": "/u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" : "/u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

// 获取项目根路径
function getlocalhost() {
    // 获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
    var curWwwPath = window.document.location.href;
    // 获取主机地址之后的目录，如： myproj/view/my.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    // 获取主机地址，如： http://localhost:8083
    var localhostPaht = curWwwPath.substring(0, pos);
    // 获取带"/"的项目名，如：/myproj
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    // 得到了 http://localhost:8083/myproj
    var realPath = localhostPaht + projectName;
    return realPath;
}

/**
 * 验证手机号码,通过返回true,不通过返回false
 *
 * @param phone_num
 * @returns {Boolean}
 */
function checkPhone(phone_num) {
    var reg = /^1\d{10}$/;
    if (reg.test(phone_num)) {
        return true;
    }
    else {
        return false;
    }
}

String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
};

String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
};

/**
 bootstrap table默认options
 */
var bootstap_table_option = {

    method: 'get',
    striped: true,
    cache: false,

    pagination: true,
    sidePagination: "server",
    pageSize: 10,
    pageList: [10, 25, 50, 100],

    sortable: true,
    sortOrder: "asc",

    showExport: false,
    exportDataType: "all",
    exportTypes: ['csv', 'json', 'sql'],  //导出文件类型

    clickToSelect: true,

    headerOnly: true,
    resizable: true,
    cardView: false,

    search: false,
    strictSearch: true,

    showColumns: false,                  //是否显示所有的列
    showRefresh: false,                  //是否显示刷新按钮
    showToggle: false,                    //是否显示详细视图和列表视图的切换按钮

    paginationDetailHAlign: 'right',

    uniqueId: 'int_id',

    onLoadSuccess: function (response) {

    }, onLoadError: function () {  //加载失败时执行
        layer.msg("加载数据失败", {time: 1500, icon: 2});
    }, responseHandler: function (response) {
        //请求数据成功后，渲染表格前的方法
        if (response.returnCode == 1) {
            return {
                total: response.result.total,
                rows: response.result.list
            };
        } else {
            layer.msg(response.description);
        }
    },
}

var new_tab = function (href) {
    var a = document.createElement("a");
    a.setAttribute("href", href);
    a.setAttribute("target", "_blank");
    a.setAttribute("id", "camnpr");
    document.body.appendChild(a);
    a.click();

}

var setSelect2Val = function (id, value) {

    var select2 = $("#" + id);
    select2.val(value).trigger("change");
}