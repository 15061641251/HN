$(function()
{
	loadtree();
})
function loadtree()
{
	$.ajax(
	{
		type : "post",
		url : getlocalhost() + '/dept/getDeptTree',
		success : function(response)
		{
			var settings =
			{
				view :
				{
					showLine : true
				},
				callback :
				{
					onClick : updateInfo,
					onRightClick : showMenu
				},
			};
			var nodes = eval(response);
			var zTree = $.fn.zTree.init($("#groupTree"), settings, nodes);
			var allNodes = zTree.getNodes();
			var firstNode = allNodes[0];
			zTree.expandNode(firstNode, true);
		}
	})
}

// 左击事件
var updateInfo = function(event, treeId, treeNode)
{
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	var nodes = treeObj.getNodes();
	var id = nodes[0].tId // 根节点id
	
	var sNodes = treeObj.getSelectedNodes();
	var sid = sNodes[0].tId // 所选择节点id
	if (sid == id)
	{
		$("#editDiv").hide();
		$("#addDiv").hide();
		$("#rMenu").hide();
	}
	else
	{
		var flag = treeNode.flag;
		var Id = treeNode.id;
		
		$("#editDiv").show();
		$("#addDiv").hide();
		$.ajax(
		{
			type : 'post',
			url : getlocalhost() + '/dept/getDeptDetail',
			data : "int_id=" + treeNode.id,
			success : function(m)
			{
				var returnCode = m.returnCode;
				if (returnCode == "0")
				{
					var info = m.result;
					
					$("#temp_id").val(info.int_id);
					$("#deptName").val(info.dept_name);
					if(info.dept_type == 1 ){
						$("#dept_type_1").click();
					}else if(info.dept_type == 2 ){
						$("#dept_type_2").click();
					}
					if(info.is_management == 0 ){
						$("#is_management_0").click();
					}else if(info.is_management == 1 ){
						$("#is_management_1").click();
					}
					$("#phone").val(info.tel);
					$("#sort").val(info.order_index);
				}
			}
		})
	}
	
};

function saveDeptDetail()
{
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	var Id = $("#temp_id").val();
	var dept_name = $.trim($("#deptName").val());
	var tel = $.trim($("#phone").val());
	
	if (isEmpty(dept_name))
	{
		layer.msg('部门名称不能为空!',
		{
			icon : 2
		});
		return false;
	}
	$.ajax(
	{
		type : "post",
		url : getlocalhost() + "/dept/editDept",
		data : "int_id=" + Id + "&dept_name=" + dept_name + "&tel=" + tel,
		success : function(m)
		{
			var returnCode = m.returnCode;
			if (returnCode == "0")
			{
				loadtree();
				layer.msg('修改成功!',
				{
					icon : 1
				});
			}
		}
	});
}

// 右击事件
var showMenu = function(event, treeId, treeNode)
{
    //debugger
	$("#rMenu").show();
	var Id = treeNode.id;
	
	var parentId = treeNode.parentid;
	
	var nodeName = treeNode.name;
	
	if (parentId == "-1" || parentId == "0")
	{
		$("#d_del").hide();
	}
	else
	{
		$("#d_del").show();
	}
	
	$("#ID").val(Id);
	$("#hideName").val(nodeName);
	
	$("#rMenu").css(
	{
		"top" : event.clientY + "px",
		"left" : event.clientX + "px"
	});
}

function addDeptEntity()
{
	$("#deptDetail").hide();
	$("#addDept").show();
	
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	var parentNode = treeObj.getNodeByParam("id", 1, null);
	
	var dept_name = $.trim($("#dept_Name").val());
	var tel = $("#dept_phone").val();
	
	if (isEmpty(dept_name))
	{
		layer.msg('部门名称不能为空!',
		{
			icon : 2
		});
		return false;
	}
	$.ajax(
	{
		type : "post",
		url : getlocalhost() + "/dept/addDept",
		data : "dept_name=" + dept_name + "&tel=" + tel + "&parentId=" + $("#ID").val(),
		success : function(m)
		{
			var returnCode = m.returnCode;
			if (returnCode == "0")
			{
				loadtree();
				cleanDate();
				hideForm();
				layer.msg('添加成功!',
				{
					icon : 1
				});
			}
		}
	});
}

function delDept()
{
	var IntId = $("#ID").val();
	var treeObj = $.fn.zTree.getZTreeObj("groupTree");
	var node = treeObj.getNodeByParam("id", IntId, null);
	var nodeName = $("#hideName").val();
	
	layer.confirm('确定删除(' + nodeName + ')吗?',
	{
		btn : [ '确定', '取消' ]
	}, function(index, layero)
	{
		// 确定的回调
		$.ajax(
		{
            url: getlocalhost() + "/dept/delDept",
            data: "int_id=" + IntId,
			success : function(m)
			{
				treeObj.removeNode(node);
				
				var returnCode = m.returnCode;
				if (returnCode == "0")
				{
					loadtree();
					hideForm();
					layer.msg('删除成功!',
					{
						icon : 1
					});
                } else {
                    loadtree();
                    hideForm();
                    layer.msg('删除失败!',
                        {
                            icon: 1
                        });
				}
			}
		})
	}, function(index)
	{
		// 取消的回调
	});
}

// 搜索功能
function searchNode()
{
	var str = $("#key").val();
	
	var zTree = $.fn.zTree.getZTreeObj("groupTree");
	// var ArrayNodes =
	// zTree.transformToArray(zTree.getNodes());//全部节点包括children
	var ArrayNodes = zTree.getNodes();// 所有根结点
	zTree.refresh();
	if ("" != str)
	{
		var nodes = zTree.getNodesByParamFuzzy("name", str);
		var searchNodes = "";
		
		for (var i = 0; i < nodes.length; i++)
		{
			searchNodes = nodes[i];
			zTree.selectNode(searchNodes, true);
		}
	}
	else
	{
		layer.msg('请输入关键字,再搜索!',
		{
			icon : 2
		});
		return false;
	}
}

function hiddenMenu(treeId, treeNode, clickFlag)
{
	$("#rMenu").hide();
};

function reShowMenu()
{
	$("#rMenu").show();
};
function hideForm()
{
	$("#addDiv").hide();
	$("#editDiv").hide();
}
function addDept()
{
	$("#editDiv").hide();
	$("#addDiv").show();
	$("#ID").val(1);
}
function showDeptAddForm()
{
	var IntId = $("#ID").val();
	$("#tempid").val(IntId);
	
	$("#editDiv").hide();
	$("#addDiv").show();
	$("#rMenu").hide();
	
	cleanDate();
};
// 清空表单数据
function cleanDate()
{
	$(':input', '#showDeptForm').not(':button, :submit, :reset, :hidden, :radio ').val('');
	$(':input', '#addDeptForm').not(':button, :submit, :reset, :hidden, :radio ').val('');
	
	$("#isregister").find("option[value='1']").attr("selected", "selected");
}
