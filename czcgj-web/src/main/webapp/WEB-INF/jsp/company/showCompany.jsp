<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>注册机构详情</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link
            href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>"
            rel="stylesheet"/>

</head>
<body>
<div id="showMember" class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;">
    <div>

    </div>
    <div class="main-div">
        <table>
            <tbody>
            <tr style="height:41px;">
                <td class="labeltd">机构名称：</td>
                <td class="labeltd" style="text-align:left;"><label id="company_name"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">注册地址：</td>
                <td class="labeltd" style="text-align:left;"><label id="address"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">创建时间：</td>
                <td class="labeltd" style="text-align:left;"><label id="create_time">
                </label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">联系人：</td>
                <td class="labeltd" style="text-align:left;"><label id="person_name"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">联系电话：</td>
                <td class="labeltd" style="text-align:left;"><label id="person_phone"></label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">备注：</td>
                <td class="labeltd" style="text-align:left;"><label id="remark"></label></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<script src="${ctx}/module/company/scripts/companyDetail.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
