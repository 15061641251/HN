<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

    <title>常州市户外广告倾覆预警平台</title>
    <jsp:include page="../../common/header.jsp"/>

    <link href="${ctx}/module/dept/style/manager.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>
    <link href="${ctx}/common/ztree3.5/zTreeStyle.css?v=<%=System.currentTimeMillis()%>" rel="stylesheet"/>

    <jsp:include page="../../common/form.jsp"/>

    <style type="text/css">
        .divs {
            margin-top: 15px;
        }

        .TreeDiv {
            min-width: 150px;
        }

        td > label {
            float: right;
        }
    </style>
</head>
<body style="overflow-y: hidden">
<div class="wrap">
    <div class="submenu">
        <div class="main-div" id="maindiv1">
            <table id="tab">
                <tr id="divTr" valign="top">
                    <td id="tds">
                        <div id="TreeDiv" border="true" class="TreeDiv">
                            <div class="divs">
                                <input type="text" id="key" placeholder="输入部门" class="form-control"
                                       style="width: 150px;display: inline"/>
                                <a class="btn btn-primary lr-search" onclick="javascript:searchNode()">
                                    <i class="fa fa-search"></i>&nbsp;搜索</a>
                            </div>
                            <hr id="hrs">
                            <ul id="groupTree" class="ztree"></ul>
                        </div>
                    </td>
                    <td id="tdd">
                        <div class="main-div" id="addDiv" style="display: none;">
                            <form id="addDeptForm" name="showDetailForm">
                                <table class="tabinfo">
                                    <tbody>
                                    <tr class="trs">
                                        <td class="form_label">
                                            <label class="control-label">&nbsp;&nbsp;<span style="color: red;">*</span>组织名称:
                                            </label>
                                        </td>
                                        <td><input type="text" class="form-control" style="width: 200px;"
                                                   name="dept_Name" id="dept_Name" value="">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="form_label">
                                            <label class="control-label">&nbsp;&nbsp;电话号码:
                                            </label></td>
                                        <td>
                                            <input type="text" class="form-control" style="width: 200px;"
                                                   name="dept_phone" id="dept_phone" value="">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <br/>
                                <center>
                                    <input type="button" class="btn btn-primary" target="_blank" value="提交"
                                           onclick="addDeptEntity()">
                                </center>
                            </form>
                        </div>
                        <div class="main-div" id="editDiv" style="display: none;">
                            <form id="showDeptForm" name="showDetailForm">
                                <table class="tabinfo">
                                    <tbody>
                                    <tr style="height:44px;display:none;">
                                        <th width="100">部门标识:</th>
                                        <td><input type="text" class="text" style="height:30px;" name="temp_id"
                                                   id="temp_id" value=""></td>
                                    </tr>
                                    <tr class="trs">
                                        <td class="form_label">
                                            <label class="control-label">&nbsp;&nbsp;<span style="color: red;">*</span>组织名称:
                                            </label>
                                        </td>
                                        <td><input type="text" class="form-control" style="width: 200px;"
                                                   name="deptName" id="deptName" value=""></td>
                                    </tr>
                                    <tr class="trs">
                                        <td class="form_label">
                                            <label class="control-label">&nbsp;&nbsp;电话号码:
                                            </label>
                                        </td>
                                        <td><input type="text" class="form-control" style="width: 200px;" name="phone"
                                                   id="phone" value=""></td>
                                    </tr>
                                    <tr class="trs" style="display:none">
                                        <th class="thname">排&nbsp;&nbsp;&nbsp;&nbsp;序:</th>
                                        <td><input type="text" class="text" name="sort" id="sort" value=""></td>
                                    </tr>
                                    </tbody>
                                </table>
                                <br/>
                                <center>
                                    <input type="button" class="btn btn-primary" target="_blank" value="保存"
                                           onclick="saveDeptDetail()">
                                </center>
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
            <div id="rMenu" style="position: absolute; display:none;" onmouseout="hiddenMenu()"
                 onmouseover="reShowMenu()">
                <ul id="d_add" onclick="showDeptAddForm()">
                    <li>添加</li>
                </ul>
                <ul id="d_del" onclick="delDept()" style="display:none">
                    <li>删除</li>
                </ul>
            </div>
            <input id="ID" type="hidden"/> <input id="hideName" type="hidden"/>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${ctx }/common/ztree3.5/jquery.ztree.all-3.5.js?v=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" src="${ctx }/module/dept/scripts/manager.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>