<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>补录数据</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/bootstrap-table.jsp"/>

    <jsp:include page="../../common/form.jsp"/>

    <script language="javascript" type="text/javascript" src="${ctx}/common/DatePicker4.8/WdatePicker.js"></script>

    <style type="text/css">
        td > a {
            margin-left: 10px;
        }
    </style>
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
                            <td>上报时间</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control" type="text" placeholder="开始时间" id="log_start_time"
                                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"></div>
                            </td>
                            <td>至</td>
                            <td>
                                <div class="pull-right search">
                                    <input class="form-control" type="text" placeholder="结束时间" id="log_end_time"
                                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"></div>
                            </td>
                            <td style="padding-left: 20px;">
                                <a class="btn btn-primary lr-search" onclick="javascript:importLog()">
                                    <i class="fa fa-search"></i>&nbsp;补录</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${ctx}/module/devicelog/scripts/importLog.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
