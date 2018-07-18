package com.sbq.tools;


import com.sbq.entity.MenusTreeObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 *
 */
public class MenusTreeUtil {

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param typeId
	 *            传入的父节点ID
	 * @return String
	 */
	public static List<MenusTreeObject> getChildTreeObjects(List<MenusTreeObject> list, int praentId) {
		List<MenusTreeObject> returnList = new ArrayList<MenusTreeObject>();
		for (Iterator<MenusTreeObject> iterator = list.iterator(); iterator.hasNext();) {
			MenusTreeObject t = (MenusTreeObject) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParent_id() == praentId) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 * 
	 * @author lanyuan Email: mmm333zzz520@163.com
	 * @date 2013-12-4 下午7:27:30
	 * @param list
	 * @param MenusTreeObject
	 */
	private static void recursionFn(List<MenusTreeObject> list, MenusTreeObject t) {
		List<MenusTreeObject> childList = getChildList(list, t);// 得到子节点列表
		t.setChildren(childList);
		for (MenusTreeObject tChild : childList) {
			if (hasChild(list, tChild)) {// 判断是否有子节点
				// returnList.add(TreeObject);
				Iterator<MenusTreeObject> it = childList.iterator();
				while (it.hasNext()) {
					MenusTreeObject n = (MenusTreeObject) it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	// 得到子节点列表
	private static List<MenusTreeObject> getChildList(List<MenusTreeObject> list, MenusTreeObject t) {

		List<MenusTreeObject> tlist = new ArrayList<MenusTreeObject>();
		Iterator<MenusTreeObject> it = list.iterator();
		while (it.hasNext()) {
			MenusTreeObject n = (MenusTreeObject) it.next();
			if (n.getParent_id() == t.getResources_id() && n.isIs_show()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	private static List<MenusTreeObject> returnList = new ArrayList<MenusTreeObject>();

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param typeId
	 *            传入的父节点ID
	 * @param prefix
	 *            子节点前缀
	 */
	public static List<MenusTreeObject> getChildTreeObjects(List<MenusTreeObject> list, int typeId, String prefix) {
		if (list == null)
			return null;
		for (Iterator<MenusTreeObject> iterator = list.iterator(); iterator.hasNext();) {
			MenusTreeObject node = (MenusTreeObject) iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (node.getParent_id() == typeId) {
				recursionFn(list, node, prefix);
			}
			// 二、遍历所有的父节点下的所有子节点
			/*
			 * if (node.getParentId()==0) { recursionFn(list, node); }
			 */
		}
		return returnList;
	}

	private static void recursionFn(List<MenusTreeObject> list, MenusTreeObject node, String p) {
		List<MenusTreeObject> childList = getChildList(list, node);// 得到子节点列表
		if (hasChild(list, node)) {// 判断是否有子节点
			returnList.add(node);
			Iterator<MenusTreeObject> it = childList.iterator();
			while (it.hasNext()) {
				MenusTreeObject n = (MenusTreeObject) it.next();
				n.setResources_name(p + n.getResources_name());
				n.setWeburl(p + n.getWeburl());
				n.setLogo(p + n.getLogo());
				recursionFn(list, n, p + p);
			}
		} else {
			returnList.add(node);
		}
	}

	// 判断是否有子节点
	private static boolean hasChild(List<MenusTreeObject> list, MenusTreeObject t) {
		return getChildList(list, t).size() > 0 ? true : false;
	}

	// 本地模拟数据测试
	public void main(String[] args) {
		/*
		 * long start = System.currentTimeMillis(); List<TreeObject>
		 * TreeObjectList = new ArrayList<TreeObject>();
		 * 
		 * TreeObjectUtil mt = new TreeObjectUtil(); List<TreeObject>
		 * ns=mt.getChildTreeObjects(TreeObjectList,0); for (TreeObject m : ns)
		 * { System.out.println(m.getName());
		 * System.out.println(m.getChildren()); } long end =
		 * System.currentTimeMillis(); System.out.println("用时:" + (end - start)
		 * + "ms");
		 */
	}

}
