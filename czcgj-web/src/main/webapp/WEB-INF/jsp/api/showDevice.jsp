<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>设备详情</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link
            href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>"
            rel="stylesheet"/>

</head>
<body>
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;">
    <div>

    </div>
    <div class="main-div">
        <table>
            <tbody>
            <tr style="height:41px;">
                <td class="labeltd">设备名称：</td>
                <td class="labeltd" style="text-align:left;"><label id="device_name"></label></td>
                <td class="labeltd">设备编号：</td>
                <td class="labeltd" style="text-align:left;"><label id="device_id"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">授权信息：</td>
                <td class="labeltd" style="text-align:left;"><label id="auth_id"></label></td>
                <td class="labeltd">接入方式：</td>
                <td class="labeltd" style="text-align:left;"><label id="access_type"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">API-KEY：</td>
                <td class="labeltd" style="text-align:left;"><label id="api_key"></label></td>
                <td class="labeltd">API-URL：</td>
                <td class="labeltd" style="text-align:left;"><label id="api_url"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">正常上报周期(分)：</td>
                <td class="labeltd" style="text-align:left;"><label id="period"></label></td>
                <td class="labeltd">报警上报周期(分)：</td>
                <td class="labeltd" style="text-align:left;"><label id="warn_period"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">纬度：</td>
                <td class="labeltd" style="text-align:left;"><label id="lat"></label></td>
                <td class="labeltd">经度：</td>
                <td class="labeltd" style="text-align:left;"><label id="lng"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">地址：</td>
                <td class="labeltd" style="text-align:left;" colspan="3"><label id="address"></label></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="${ctx}/module/api/scripts/deviceDetail.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
