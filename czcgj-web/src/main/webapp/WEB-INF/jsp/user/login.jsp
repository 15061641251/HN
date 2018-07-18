<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <meta name="renderer" content="webkit"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>常州市户外广告倾覆预警平台</title>

    <jsp:include page="../../common/header.jsp"/>

    <link rel="stylesheet" href="${ctx}/login/thickbox.css" type="text/css"/>

    <script src="${ctx}/common/scripts/common/md5.js"></script>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-size: 12px;
            color: #676464
        }

        img {
            border: none;
        }

        ul, ol, li, dl, dt, dd {
            list-style: none;
        }

        .clear {
            clear: both;
        }

        a, a:link {
            text-decoration: none;
            color: #676464;
        }

        a:hover {
            /*color: #f3400c;*/
        }

        .fontStyle20 {
            font-size: 20px;
        }

        .fontStyle15 {
            font-size: 15px;
        }

        #topBar, #loginForm, #loginForm2, #bottomBar, #cpBar {
            width: 100%;
            min-width: 960px;
            _width: expression_r((documentElement . clientWidth < 960) ? "960px": "100%");
        }

        #topBar .center, #loginForm .center, #bottomBar .center, #cpBar .center {
            width: 960px;
            margin: 0 auto;
        }

        #topBar {
            height: 80px;
            background-color: #F5F4F2;
            border-top: 1px solid #E8E8E7
        }

        #topBar .logo, #topBar .systemname, #topBar .navbar {
            height: 80px;
        }

        #topBar {
            height: 80px;
            background-color: #F5F4F2;
        }

        #topBar .logo {
            width: 600px;
            float: left;
            cursor: pointer;
        }

        #topBar .navbar {
            float: right;
        }

        #topBar .navbar p {
            font-size: 14px;
            text-align: right;
            margin-top: 50px;
            color: #9c9b9a;
            margin-right: 5px;
        }

        #topBar .navbar p a {
            font-size: 14px;
        }

        #loginForm, #loginForm2, #loginForm .center {
            height: 430px;
        }

        /*#loginForm {background:url(file/common/login/login2013v1/bg_bg.jpg) repeat-x center;}*/
        /*#loginForm #loginForm2, #loginForm .center {background:transparent url(./login/index_bg.jpg) no-repeat center;}*/
        #form {
            margin-right: 5px;
            margin-top: 60px;
            width: 300px;
            float: right;
            background-color: #EDEFF1;
            -moz-box-shadow: 0 0 12px #000;
            -webkit-box-shadow: 0 0 12px #000;
            box-shadow: 0 0 12px #000; /*-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=90)";filter:alpha(opacity=80);-moz-opacity:0.9; -khtml-opacity: 0.9;opacity: 0.9;*/
        }

        #form .formTitle {
            background: url(../../login/login_title.png) repeat-x;
            font-size: 20px;
            height: 50px;
            text-align: center;
            line-height: 50px;
            text-align: center;
            font-family: '微软雅黑', '黑体';
            color: #000;
        }

        .loginTable {
            margin: 20px;
        }

        .loginTable td {
            height: 40px;
        }

        .loginTable td.h70 {
            height: 60px;
        }

        .loginTable .tip {
            height: 30px;
            text-align: center;
            color: red;
        }

        .loginTable .tip span {
            color: red;
        }

        .loginTable .inputText {
            height: 30px;
            border: 1px solid #cdcdcd;
            line-height: 30px;
            font-size: 16px;
            outline: none;
            text-indent: 5px;
        }

        .loginTable input.focus {
            border: 1px solid #FF8000;
        }

        .loginTable .inputTextLong {
            width: 180px;
        }

        .loginTable .inputTextShort {
            width: 78px;
        }

        a.submitbtn {
            display: block;
            height: 44px;
            width: 160px;
            color: #FFF;
        }

        a.submitbtn:hover {
            bdisplay: block;
            height: 44px;
            width: 160px;
            color: #FFF;
        }

        .qrcode {
            border: 2px solid #333 !important;
            background: #FFFFFF !important;
            padding: 4px !important;
        }

        #bottomBar {
            height: 80px;
            background-color: #F5F4F2;
            padding-top: 20px;
            border-bottom: 1px solid #E8E8E7
        }

        #bottomBar table {
            margin: 0 auto;
        }

        #bottomBar table a {
            line-height: 34px;
            font-size: 12px;
            display: block;
        }

        #cpBar {
            line-height: 70px;
            height: 70px;
            text-align: center;
        }

        #tooltip {
            position: absolute;
            border: 1px solid #333;
            background: #f7f5d1;
            padding: 2px 5px;
            color: #333;
            display: none;
            z-index: 999;
        }

        .authCodeImg {
            border: 1px solid #C5C5C5;
            padding: 4px;
            padding-bottom: 4px;
            background-color: #FFF;
        }

        .float_list_ul {
            margin: 0;
            padding: 1px;
            border: 1px solid #beceeb;
            background-color: #fff;
            font-size: 12px;
            list-style-type: none;
            -moz-box-shadow: 1px 1px 5px #8e8e8e;
            -webkit-box-shadow: 1px 1px 5px #8e8e8e;
            box-shadow: 1px 1px 5px #8e8e8e;
        }

        .float_list_a {
            display: block;
            width: 128px;
            text-decoration: none;
        }

        .float_list_a:hover {
            background-color: #c9d3e9;
            color: #333;
            text-decoration: none;
        }

        .float_list_ul li {
            line-height: 26px;
            border-top: 1px solid #f0f3f9;
            text-indent: 5px;
        }

        .float_list_ul li:first-child {
            border-top: 0;
        }

        .float_list_null {
            padding: 40px 20px;
            text-align: center;
        }

        .btn {
            color: #444;
        }

        td {
            text-align: center;
        }

        .login_input {
            border: 1px solid #ccc;
            width: 230px;
            height: 30px;
            padding-top: 6px;
            padding-left: 5px;
            color: #ada9aa;
        }

        .login_submit {
            background: url(../login/btn-login.png) no-repeat;
            width: 300px;
            height: 70px;
            border: none;
            color: #fff;
            font-weight: bold;
            padding-top: 10px;
            font-size: 16px;
            outline: none;
        }
    </style>

    <script type="text/javascript">
        function doResize() {
            var a = $("#headbar");
            var wh = $(window).height();
            if (wh > 680) {
                a.height((wh - 680) / 2);
            }
            else {
                a.height(0);
            }
        }

        var bgs1 = ['../login/index_bg2.jpg', '../login/index_bg2_1.jpg', '../login/index_bg2_3.jpg'];
        //var bgs2 = [ 'login/index_bg.jpg', 'login/index_bg_1.jpg', 'login/index_bg_2.jpg', 'login/index_bg_3.jpg' ];
        var bgs2 = ['../login/index_bg.png', '../login/index_bg_1.jpg', '../login/index_bg_3.png'];

        var storage = window.localStorage;

        $(function () {
            if (IETester() == '8.0' || IETester() == '7.0' || IETester() == '6.0' || IETester() == '5.0') {
                document.getElementById("IE").style.display = "inline";
            }
            else {
                loadUserName();
                doResize();

            }
            $("#iOST").mouseover(function () {
                $("#erweima").css(
                    {
                        position: "absolute",
                        left: $("#iOST").offset().left,
                        top: $("#iOST").offset().top - 20
                    })

                if ($("#erweima").is(":hidden")) {
                    $("#erweima").show();
                }

            });

            function IETester(userAgent) {
                var UA = userAgent || navigator.userAgent;
                if (/msie/i.test(UA)) {
                    return UA.match(/msie (\d+\.\d+)/i)[1];
                }
                else if (~UA.toLowerCase().indexOf('trident') && ~UA.indexOf('rv')) {
                    return UA.match(/rv:(\d+\.\d+)/)[1];
                }
                return false;
            }

            function loadUserName() {
                var username = storage.getItem("account");

                if (undefined != username && "" != username) {
                    document.getElementById("account").value = username;
                    $("input[type='checkbox']").attr("checked", true);
                }
                else {
                    $("input[type='checkbox']").attr("checked", false);
                }
            }

            $("#androidT").mouseover(function () {
                $("#erweima").css(
                    {
                        position: "absolute",
                        left: $("#androidT").offset().left,
                        top: $("#androidT").offset().top - 20
                    })

                $("#erweima").show();

            });
            $("#erweima").mouseout(function () {
                $("#erweima").hide();
            });

            $("#doc").mouseover(function () {
                $("#erweima").hide();
            })

            var random = Math.floor(Math.random() * bgs2.length);

            $("#loginForm2").css("background", "transparent url(" + bgs2[random] + ") no-repeat center");
            $("#loginForm").css("background", "transparent url(" + bgs1[random] + ") repeat-x center");

        })

        function login() {

            var username = document.getElementById("account").value;

            if ($("input[type='checkbox']").is(':checked')) {
                storage.setItem("account", username);
            }
            else {
                storage.setItem("account", "");
            }

            var password = document.getElementById("password").value;
            if (username == null || username == "") {
                document.getElementById("message").innerHTML = "请输入用户名！";
                return;
            }
            if (password == null || password == "") {
                document.getElementById("message").innerHTML = "请输入密码！";
                return;
            }
//            else {
//                document.getElementById("password").value = hex_md5(password);
//            }
            document.forms[0].submit();
        }

        function loginAuto() {
            if (event.keyCode == 13) {
                var button = document.getElementById("bsubmit"); //bsubmit 为botton按钮的id
                button.click();
                return false;
            }
        }

        function load() {
            var message = "${requestScope.message}";
            if (message != null && message != "") {
                document.getElementById("message").innerHTML = message;
            }
        }
    </script>

</head>
<body onload="load()" onkeypress="loginAuto()">
<div id="headbar" style="height: 0px;"></div>

<div id="topBar">
    <div class="center">
        <div class="logo">
            <img src="${ctx}/common/images/logo3.png" height="80" style="margin-left: -80px;">
        </div>
        <div class="clear"></div>
    </div>
</div>

<div id="loginForm">
    <div id="loginForm2">
        <div class="center" style="position: relative;">
            <div id="form" style="z-index: 2;position: relative;background-color:white;">
                <form action="${pageContext.request.contextPath}/user/loginCheck" method="post">
                    <table cellpadding="0" cellspacing="0" style="width:100%">
                        <tbody>
                        <tr>
                            <td height="80" style="background: url(${ctx}/login/login_title.png) repeat-x;">
                                <div
                                    style="font-size:20px;line-height: 50px;font-family: '微软雅黑', '黑体';text-align: center;color: #000;margin-bottom: 50px">
                                    登录系统
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="46"><input type="text" id="account" name="account" placeholder="用户名"
                                                   class="login_input"></td>
                        </tr>
                        <tr>
                            <td height="46" style="padding-top: 10px;"><input type="password" id="password"
                                                                              name="password" placeholder="密码"
                                                                              class="login_input"></td>
                        </tr>
                        <tr style="display: none">
                            <td height="46" style="padding-top: 10px;"><input type="text" id="redirect_url"
                                                                              name="redirect_url" value="${redirect_url}"
                                                                              class="login_input"></td>
                        </tr>
                        <tr style="height:20px">
                            <td>
                                <div style="float: left; margin-left: 35px;padding-top:8px;">
                                    <input style="margin-top:2px;display:block;height:12px;float:left;" id="remember"
                                           name="remember" type="checkbox"/> <span style="margin-top:3px;font-size:8px">记住用户名</span>
                                </div>
                                <div id="message"
                                     style="color: red; float: left; margin-left: 30px;margin-top:8px;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td height="80"><input type="button" id="bsubmit" class="login_submit" value="登  录"
                                                   onclick="login()"> &nbsp;&nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="IE" style="height: 100px;width: 300px;padding: 25px;display: none;">
                                    <p>您当前正在使用版本较低的IE浏览器 !!!</p><br/>
                                    <p>为了更好的使用体验,推荐您使用：</p><br/>
                                    <a style="color: red;" href="${ctx}/download/Chrome.exe">谷歌浏览器 、</a> <a
                                    style="color: red;" href="${ctx}/download/360cse.exe">360极速浏览器(极速模式)、</a><br/>
                                    <!-- <p>或者 升级IE到9.0以上版本！</p> -->
                                    <p>点击红色链接可直接下载</p>
                                </div>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                </form>
            </div>
            <div id="formShadow"
                 style="background-color:#000;z-index: 1;right: 14px;top:52px;width:0px;height:0px;position: absolute;"></div>

            <div class="clear"></div>
        </div>
    </div>
</div>
<div id="bottomBar">
    <%--<div class="center">--%>
        <%--<table border="0" align="center" cellpadding="0" cellspacing="0">--%>
            <%--<tbody>--%>
            <%--<tr>--%>
                <%--<td width="110" align="center" valign="middle"><a id="androidT" href="#" title="安卓客户端，扫描二维码在线安装"--%>
                                                                  <%--href="#"> <img src="${ctx}/login/android.jpg"--%>
                                                                                 <%--width="32" height="37"> <br>Android下载--%>
                <%--</a></td>--%>
                <%--<td width="110" align="center" valign="middle" id="doc"><a title="flash下载"--%>
                                                                           <%--href="${ctx}/download/flash.exe">--%>
                    <%--<img src="${ctx}/login/flash.jpg" width="32" height="37"> <br>flash下载--%>
                <%--</a></td>--%>
            <%--</tr>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</div>--%>
</div>
<div id="cpBar">
    <div class="center">
        <a>常州市户外广告倾覆预警平台</a>
    </div>
</div>

<div id="erweima" style="display:none;">
    <img src="${ctx}/login/download.png" style="width:100px;height:100px;"/>
</div>
<script>

    /* $(function() {


     layer.alert('请使用谷歌浏览器', {
     skin : 'layui-layer-molv' //样式类名
     ,
     closeBtn : 0
     });
     }); */
    $(".logo").click(function(){
        window.location.href = '${ctx}';
    })
</script>
</body>

</html>
