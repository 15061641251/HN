<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <jsp:include page="../../common/header.jsp"/>
    <script type="text/javascript" src="${ctx}/common/ztree3.5/jquery.ztree.all-3.5.js"></script>
    <link rel="stylesheet" href="${ctx}/common/ztree3.5/zTreeStyle.css?v=<%=System.currentTimeMillis()%>"
          type="text/css"/>
    <link rel="stylesheet" href="${ctx}/common/style/style.css?v=<%=System.currentTimeMillis()%>" type="text/css"/>

    <script type="text/javascript">
        var zTree;
        var ids = "";
        $(function () {
            ids = $("#ids").val();
            $.ajax(
                {
                    type: "post",
                    url: "${ctx}/dept/getDeptTree",
                    data: "selectIds=" + ids,
                    success: function (msg) {
                        var settings =
                            {
                                check: {
                                    enable: true
//                        chkStyle: "radio",
//                        radioType: "all"
                                },
                                callback: {
                                    onCheck: zTreeOnCheck
                                }
                            };
                        var nodes = eval('(' + msg + ')');

                        zTree = $.fn.zTree.init($("#groupTree"), settings, nodes);
                        var allNodes = zTree.getNodes();
                        var firstNode = allNodes[0];
                        zTree.expandNode(firstNode, true);
                    }
                });
        });

        function loadtreeNodeValue() {
            var nodes = zTree.getCheckedNodes(true);
            var ids = "";
            var names = "";
            if (nodes.length != 0) {
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].flag == "1") {
                        ids = ids + nodes[i].id + ",";
                        names = names + nodes[i].name + ",";
                    }
                }
                ids = ids.substring(0, getLength(ids) - 1);
                names = names.substring(0, getLength(names) - 1);
                $("#ids").val(ids);
                $("#names").val(names);
            }
            else {
                $("#ids").val("");
                $("#names").val("");
            }
        }

        //选中事件
        function zTreeOnCheck(event, treeId, treeNode) {
            var nodes = zTree.getCheckedNodes(true);
            var ids = "";
            var names = "";
            if (nodes.length != 0) {
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].flag == "dept") {
                        ids = ids + nodes[i].id + ",";
                        names = names + nodes[i].name + ",";
                    }
                }
                ids = ids.substring(0, getLength(ids) - 1);
                names = names.substring(0, getLength(names) - 1);

                $("#ids").val(ids);
                $("#names").val(names);
            }
            else {
                $("#ids").val("");
                $("#names").val("");
            }
        };

        //获取长度
        function getLength(str) {
            return str.replace(/[^\x00-\xff]/g, "aa").length;
        };

        function saveMember() {
            var nodes = zTree.getCheckedNodes(true);

            var ids = "";
            var names = "";
            if (nodes.length != 0) {
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].flag == 0) {
                        ids = ids + nodes[i].id + ",";
                        names = names + nodes[i].name + ",";
                    }
                }

                ids = ids.substr(0, ids.length - 1);
                names = names.substr(0, names.length - 1);

                $("#ids").val(ids);
                $("#names").val(names);
            }
            else {
                $("#ids").val("");
                $("#names").val("");
            }


            var date = ids + "<split>" + names;

            parent.setUserids(date);

            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        }

        function closeWin() {
            var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(index);
        }
    </script>
    <style type="text/css">
        .ztree li span.button.icon03_ico_docu {
            background: url(${ctx}/common/ztree3.5/img/people.gif);
        }

        .ztree li span.button.ico_docu {
            background: url(${ctx}/common/ztree3.5/img/folder_Close.gif);
        }

        #sure input {

        }

        #sure label {
            width: 25px;
        }
    </style>
</head>
<body>
<div style="width:100%;">
    <ul id="groupTree" class="ztree" style="overflow:auto;margin-buttom:50px;height:400px;"></ul>
    <br/> <input id="names" style="display:none;"/> <input id="ids" style="display:none;" value="${ids}"/> <br/>
</div>
<div id="sure" style="width:100%;height:40px;position:fixed;top:420px;text-align:center;z-index:999;">
    <input type="button" value="确定" class="ManagerButton" onclick="saveMember()">&nbsp;&nbsp;
    <input type="button" value="关闭" class="ManagerButtonNo" onclick="closeWin()"/>
</div>
</body>
</html>
