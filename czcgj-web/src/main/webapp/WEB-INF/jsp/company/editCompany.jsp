<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html ng-app="editCompanyApp">
<head>
    <base href="/"/>
    <title>修改注册机构信息</title>

    <jsp:include page="../../common/header.jsp"/>
    <jsp:include page="../../common/angular.jsp"/>
    <jsp:include page="../../common/form.jsp"/>

    <link href="${ctx}/common/style/table.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

</head>
<body ng-controller="editCompanyController">
<div class="submenu"
     style="width:95%; padding-top: 10px;  padding-left:10px; padding-right:10px;padding-bottom:10px;overflow: auto;">
    <div class="main-div">
        <table>
            <tbody>
            <tr class="labeltr">
                <th colspan="4" style="text-align: left;"><span
                        style="margin-left: 10px; margin-top: 10px; font-size: 14px;">用户信息</span>
                </th>
            </tr>
            <tr style="height:41px;display: none" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>机构主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="int_id" ng-model="int_id" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>公司主键</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="company_id" ng-model="company_id" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>机构名称</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="company_name" ng-model="company_name" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>注册地址</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="address" ng-model="address" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>联系人</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="person_name" ng-model="person_name" class="form-control"/>
                </td>
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;<span
                        style="color:red">*</span>联系电话</label>
                </td>
                <td class="labeltd" style="text-align:left;"><input
                        id="person_phone" ng-model="person_phone" class="form-control"/>
                </td>
            </tr>
            <tr style="height:41px;" class="labeltr">
                <td class="labeltd"><label class="control-label">&nbsp;&nbsp;备注信息</label>
                </td>
                <td class="labeltd" style="text-align:left;" colspan="3">
                    <textarea class="form-control" style="height: 80px;" name="remark"
                              ng-model="remark"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
        <br/>
        <center>
            <input type="button" class="btn btn-large btn-primary" value="保  存"
                   ng-click="editCompany()"/>
        </center>
    </div>
</div>
<script type="text/javascript" src="${ctx }/common/scripts/angularjs/underscore.js"></script>
<script type="text/javascript"
        src="${ctx }/module/company/scripts/editCompany.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript"
        src="${ctx }/common/scripts/angularjs/angularFormPost.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>