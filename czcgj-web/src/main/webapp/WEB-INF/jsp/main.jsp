<%@ page language="java" contentType="text/html; charset=utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>常州市户外广告倾覆预警平台</title>

    <jsp:include page="../common/header.jsp"/>

    <link rel="stylesheet" href="${ctx}/common/css/reset.css"/>
    <link rel="stylesheet" href="${ctx}/module/hpage/style/homepage.css"/>
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="${ctx}/common/bootstrap/css/bootstrap.min.css"/>
    <!-- Font Awesome -->
    <link href="${ctx}/common/font-awesome/css/font-awesome.css" rel="stylesheet"/>
    <!-- Theme style -->
    <link href="${ctx}/common/adminlte/dist/css/AdminLTE.css" rel="stylesheet"/>
    <link href="${ctx}/common/adminlte/dist/css/skins/_all-skins.css" rel="stylesheet"/>
    <link href="${ctx}/common/min/css/supershopui.common.min.css" rel="stylesheet"/>

    <!--[if lt IE 9]>
    <script src="${ctx}/common/js/html5shiv.min.js"></script>
    <script src="${ctx}/common/js/respond.min.js"></script>
    <![endif]-->

    <style type="text/css">
        * {
            margin: 0;
            padding: 0;

        }

        body, html {
            width: 100%;
            height: 100%;

        }

        body {
            overflow-y: hidden;
        }

        html {
            overflow: hidden;
        }
    </style>

</head>
<body class="hold-transition skin-blue sidebar-mini fixed">
<div class="wrapper">
    <!-- Main Header -->
    <header class="main-header">
        <!-- Logo -->
        <a href="${ctx}" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg">常州市户外广告倾覆预警平台</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">切换导航</span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="${ctx}/module/main/images/dd.png" class="user-image" alt="User Image">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="${ctx}/module/main/images/tou.png" class="img-circle" alt="User Image">
                                <p>
                                    ${user.username}
                                    <small>${user.company_name}</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <%--<div class="col-xs-6 text-center">--%>
                                        <%--<a onclick="showUserInfo()" style="cursor:pointer;">个人信息</a>--%>
                                    <%--</div>--%>
                                    <div class="col-xs-6 text-center">
                                        <a onclick="javascript:window.location.href='${ctx}/czcg'" style="cursor:pointer;">进入驾驶舱</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a onclick="editPwd()" style="cursor:pointer;">修改密码</a>
                                    </div>
                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <%--<div class="pull-left">--%>
                                <%--<a href="#" class="btn btn-default btn-flat">个人中心</a>--%>
                                <%--</div>--%>
                                <div class="pull-right" style="margin-right: 35%">
                                    <a href="${ctx}/user/logout" class="btn btn-default btn-flat">注销</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">

            </ul>
            <!-- /.sidebar-menu -->
        </section>

    </aside>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" id="content-wrapper">
        <div class="content-tabs">
            <!--style="margin-top: 1%"-->
            <button class="roll-nav roll-left tabLeft" onclick="scrollTabLeft()">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">

                <div class="page-tabs-content">

                </div>
            </nav>
            <button class="roll-nav roll-right tabRight" onclick="scrollTabRight()">
                <i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown tabClose" data-toggle="dropdown">
                    页签操作<i class="fa fa-caret-down"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li><a class="tabReload" href="javascript:refreshTab();">刷新当前</a></li>
                    <li><a class="tabCloseCurrent" href="javascript:closeCurrentTab();">关闭当前</a></li>
                    <li><a class="tabCloseAll" href="javascript:closeOtherTabs(true);">全部关闭</a></li>
                    <li><a class="tabCloseOther" href="javascript:closeOtherTabs();">除此之外全部关闭</a></li>
                </ul>
            </div>
            <button class="roll-nav roll-right fullscreen"><i class="fa fa-arrows-alt"></i></button>
        </div>
        <div class="content-iframe">
            <div class="tab-content " id="tab-content">
            </div>
        </div>
    </div>
</div>
<!-- Bootstrap 3.3.6 -->
<script src="${ctx}/common/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/common/min/js/supershopui.common.js"></script>

<script type="text/javascript">
    //    console.log("屏幕高度:" + window.screen.height + "屏幕宽度:" + window.screen.width + "页面可用高度:" + window.innerHeight);
    document.body.height = window.innerHeight;

    //    console.log(document.body.height);
    //----------------------------------------------------------------------------------
    $(function () {
//        addTabs(({id: '10008', title: '百度', close: false, url: 'http://www.baidu.com', isLocal: false}));
        App.fixIframeCotent();
        var menus = ${menuids};
        $('.sidebar-menu').sidebarMenu({data: menus});


    });

    function editPwd() {
        var index = layer.open(
            {
                type: 2,
                area: ['60%', '70%'],
                maxmin: true,
                title: '修改密码',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/member/editPwdJsp']
            });
    }

    function showUserInfo() {
        var index = layer.open(
            {
                type: 2,
                area: ['60%', '70%'],
                maxmin: true,
                title: '个人信息',
                skin: 'layui-layer-rim', // 加上边框
                content: [contextpath + '/member/showMember?int_id=' + ${user.int_id}]
            });


    }
</script>
</body>
</html>