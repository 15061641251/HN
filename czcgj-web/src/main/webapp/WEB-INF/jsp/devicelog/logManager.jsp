<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>设备数据日志</title>

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
                            <td>告警状态</td>
                            <td>
                                <div class="pull-right search">
                                    <select id="status" name="status" style="width: 80px;">
                                        <option value="">全部</option>
                                        <option value="1">告警</option>
                                        <option value="0">正常</option>
                                    </select>
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
                        <a class="btn btn-default lr-add" onclick="importLog()"><i class="fa fa-plus"></i>&nbsp;补录</a>
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
<script src="${ctx}/module/devicelog/scripts/logManager.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="application/javascript">
    $(function () {
        $("#status").select2({
            minimumResultsForSearch: Infinity
        });
    })
</script>
</body>
</html>
