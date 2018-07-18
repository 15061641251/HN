<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>注册机构管理</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <jsp:include page="../../common/form.jsp"/>

</head>

<body>
<div>
    <div class="main-div">
        <div class="center-Panel" style="overflow-x: hidden">
            <div class="titlePanel">
                <div class="title-search">
                    <table>
                        <tbody>
                        <tr>
                            <td>注册机构名称</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control" type="text" placeholder="搜索" id="name"></div>
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
                        <a class="btn btn-default lr-add" onclick="addCompany()"><i class="fa fa-plus"></i>&nbsp;新增</a>
                    </div>
                </div>
            </div>
            <div>
                <table id="table">
                </table>
            </div>
        </div>
    </div>

</div>
<script src="${ctx}/module/company/scripts/companyManager.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
