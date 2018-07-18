<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>人员详情</title>

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
                <td class="labeltd" style="width:150px;"><label class="control-label">机构名称：</label></td>
                <td class="labeltd" style="text-align:left;"><label id="company_name"> ${user.company_name}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd" style="width:150px;"><label class="control-label">区域名称：</label></td>
                <td class="labeltd" style="text-align:left;"><label id="area_name"> ${user.area_name}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">用户账户：</td>
                <td class="labeltd" style="text-align:left;"><label id="account"> ${user.account}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">用户姓名：</td>
                <td class="labeltd" style="text-align:left;"><label id="username">${user.username}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">用户邮箱：</td>
                <td class="labeltd" style="text-align:left;"><label id="email">${user.email}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">用户性别：</td>
                <td class="labeltd" style="text-align:left;"><label id="sex">${user.sex}</label></td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">固定电话：</td>
                <td class="labeltd" style="text-align:left;"><label id="tel">${user.tel}</label></td>
            </tr>
            <%--<tr style="height:41px;">--%>
            <%--<td class="labeltd">数据权限：</td>--%>
            <%--<td class="labeltd" style="text-align:left;"><label id="data_auth_name">${user.data_auth_name}</label></td>--%>
            <%--</tr>--%>
            <tr style="height:41px;">
                <td class="labeltd">是否管理员：</td>
                <td class="labeltd" style="text-align:left;"><label id="is_admin"><c:if
                        test="${user.is_admin == 1}">是</c:if> <c:if test="${user.is_admin == 0}">否</c:if></label>
                </td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">可用状态：</td>
                <td class="labeltd" style="text-align:left;"><label id="is_enabled"><c:if
                        test="${user.is_enabled == 1}">可用</c:if> <c:if test="${user.is_enabled == 0}">不可用</c:if></label>
                </td>
            </tr>
            <tr style="height:41px;">
                <td class="labeltd">角色：</td>
                <td class="labeltd" style="text-align:left;"><label id="auth">${user.auth_names}</label></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
