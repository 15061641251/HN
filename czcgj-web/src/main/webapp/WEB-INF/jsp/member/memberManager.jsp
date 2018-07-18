<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>常州市户外广告倾覆预警平台</title>
    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <link href="${ctx}/module/dept/style/manager.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>
    <jsp:include page="../../common/form.jsp"/>

    <style type="text/css">

        .bootstrap-table {
            font-size: 14px;
        }

        .divs {
            margin-top: 15px;
        }

        .TreeDiv {
            min-width: 250px;
        }
    </style>
</head>
<body style="overflow-y: hidden">
<div class="main-div">
    <div class="center-Panel" style="overflow-x: hidden">
        <div class="titlePanel">
            <div class="title-search">
                <table>
                    <tbody>
                    <tr>
                        <td>姓名</td>
                        <td>
                            <div class="pull-right search">
                                <input class="form-control" type="text" placeholder="搜索"
                                       id="user_name">
                            </div>
                        </td>
                        <td>机构名称</td>
                        <td>
                            <div class="pull-right search">
                                <input class="form-control" type="text" placeholder="搜索"
                                       id="company_name">
                            </div>
                        </td>
                        <td>区域名称</td>
                        <td>
                            <div class="pull-right search">
                                <input class="form-control" type="text" placeholder="搜索"
                                       id="area_name">
                            </div>
                        </td>
                        <td style="padding-left: 20px;">
                            <a class="btn btn-primary lr-search" onclick="javascript:list_search()">
                                <i class="fa fa-search"></i>&nbsp;查询</a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="toolbar">
                <div class="btn-group">
                    <a class="btn btn-default lr-add" onclick="addUser()"><i class="fa fa-plus"></i>&nbsp;新增</a>
                </div>
            </div>
        </div>
        <div>
            <table id="table">
            </table>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${ctx}/module/member/scripts/memberManager.js?v=<%=System.currentTimeMillis()%>"></script>

</body>
</html>