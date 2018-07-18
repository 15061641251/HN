package com.sbq.tools;


import com.sbq.entity.Resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 *
 */
public class ResourcesTreeUtil {

	/**
	 * 根据父节点的ID获取所有子节点
	 * 
	 * @param list
	 *            分类表
	 * @param typeId
	 *            传入的父节点ID
	 * @return String
	 */
	public static List<Resources> getChildTreeObjects(List<Resources> list, int praentId) {
		List<Resources> returnList = new ArrayList<Resources>();
		for (Iterator<Resources> iterator = list.iterator(); iterator.hasNext();) {
			Resources t = (Resources) iterator.next();
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
	 * @param Resources
	 */
	private static void recursionFn(List<Resources> list, Resources t) {
		List<Resources> childList = getChildList(list, t);// 得到子节点列表
		t.setChildren(childList);
		for (Resources tChild : childList) {
			if (hasChild(list, tChild)) {// 判断是否有子节点
				// returnList.add(TreeObject);
				Iterator<Resources> it = childList.iterator();
				while (it.hasNext()) {
					Resources n = (Resources) it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	// 得到子节点列表
	private static List<Resources> getChildList(List<Resources> list, Resources t) {

		List<Resources> tlist = new ArrayList<Resources>();
		Iterator<Resources> it = list.iterator();
		while (it.hasNext()) {
			Resources n = (Resources) it.next();
			if (n.getParent_id() == t.getInt_id() && n.isIs_show()) {
				tlist.add(n);
			}
		}
		return tlist;
	}

	private static List<Resources> returnList = new ArrayList<Resources>();

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
	public static List<Resources> getChildTreeObjects(List<Resources> list, int typeId, String prefix) {
		if (list == null)
			return null;
		for (Iterator<Resources> iterator = list.iterator(); iterator.hasNext();) {
			Resources node = (Resources) iterator.next();
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

	private static void recursionFn(List<Resources> list, Resources node, String p) {
		List<Resources> childList = getChildList(list, node);// 得到子节点列表
		if (hasChild(list, node)) {// 判断是否有子节点
			returnList.add(node);
			Iterator<Resources> it = childList.iterator();
			while (it.hasNext()) {
				Resources n = (Resources) it.next();
				n.setName(p + n.getName());
				n.setWeburl(p + n.getWeburl());
				n.setLogo(p + n.getLogo());
				n.setAuthority_id(n.getAuthority_id());
				n.setAuthority_name(p + n.getAuthority_name());
				n.setAuthority_description(p + n.getAuthority_description());
				recursionFn(list, n, p + p);
			}
		} else {
			returnList.add(node);
		}
	}

	// 判断是否有子节点
	private static boolean hasChild(List<Resources> list, Resources t) {
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
