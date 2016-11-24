package com.infosys.westing.commerce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;
/**
 * 
 * <p>Description:页信息</p>
 * Created on 2014-8-22
 * @author  <a href="mailto: billy_ban@infosys.com">班点点</a>
 * @version 1.0
 */

public class PagerInfo<T> implements Serializable{

	private static final long serialVersionUID = -3080742314500190394L;

	public static final Long DEFAULT_PAGE_SIZE = 5L;
	
	/**
	 * 缁撴灉闆?
	 */
	private List<T> records = new ArrayList<T>();
	/**
	 * 璁板綍鎬绘暟
	 */
	private Long totalRecords = 0L;
	
	
	private Long totalPage = 0L;
	/**
	 * 褰撳墠椤?
	 */
	private Long currentPage = 1L;
	
	private int isCanUp = 0;
	private int isCanDown = 0;
	/**
	 * 姣忛〉璁板綍鏁?榛樿20鏉?
	 */
	private Long pageSize = DEFAULT_PAGE_SIZE;
	/**
	 * 鎺掑簭瀛楁鍚嶇О,澶氫釜瀛楁涓棿浣跨敤,鍒嗛殧
	 */
	private String orderProperty = "";
	/**
	 * 鎺掑簭鏂瑰紡asc鎴杁esc,澶氫釜瀛楁涓棿浣跨敤,鍒嗛殧
	 */
	private String order = "";
	/**
	 * 鏄惁璁＄畻鎬绘暟
	 */
	private boolean countTotal = true;
	
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public Long getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Long totalRecords) {
		this.totalRecords = totalRecords;
	}
	public Long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Long currentPage) {
		if(currentPage <= 0){
			this.currentPage = 1L;
		}else{
			this.currentPage = currentPage;
		}
		if(this.currentPage>1)setIsCanUp(1);
	}
	public Long getPageSize() {
		return pageSize;
	}
	public void setPageSize(Long pageSize) {
		if(pageSize <= 0){
			this.pageSize = DEFAULT_PAGE_SIZE;
		}else{
			this.pageSize = pageSize;
		}
	}
	public boolean isCountTotal() {
		return countTotal;
	}
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}
	/**
	 * 璁＄畻璁板綍鐨勬?椤垫暟
	 */
	public Long getTotalPages(){
		if(getTotalRecords() == 0){
			return 1L;
		}
		Long div = getTotalRecords()/getPageSize();
		Long sub = getTotalRecords()%getPageSize();
		if(sub == 0){
			return div;
		}else{
			return div + 1;
		}
	}
	/**
	 * 鏄惁璁剧疆浜嗘帓搴忓睘鎬?
	 * @return
	 */
	public boolean isOrderBySetted(){
		return StringUtils.isNotBlank(this.order) && StringUtils.isNotBlank(this.orderProperty);
	}
	/**
	 * 鏍规嵁褰撳墠椤佃幏鍙栬褰曞紑濮嬪彿
	 * @return
	 */
	public Long getFirstResult(){
		return (getCurrentPage() - 1) * getPageSize();
	}
	
	public Long getMaxResult(){
		return getCurrentPage() * getPageSize();
	}
	/**
	 * 鏍规嵁褰撳墠椤佃幏鍙栬褰曞紑濮嬪彿(for mysql)
	 * @return
	 */
	public Long getFirstResultExt(){
		Long firstPage = getFirstResult();
		return firstPage <= 0?0 : (firstPage-1);
	}
	
	public String getOrderProperty() {
		return orderProperty;
	}
	public void setOrderProperty(String orderProperty) {
		this.orderProperty = orderProperty;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		String lowcaseOrderDir = StringUtils.lowerCase(order);
		//妫?煡order瀛楃涓茬殑鍚堟硶鍊?
		String[] orderDirs = StringUtils.split(lowcaseOrderDir, ',');
		for (String orderDirStr : orderDirs) {
			if (!StringUtils.equals(Sort.DESC, orderDirStr) && !StringUtils.equals(Sort.ASC, orderDirStr)) {
				throw new IllegalArgumentException("" + orderDirStr + "");
			}
		}
		this.order = lowcaseOrderDir;
	}
	/**
	 * 鑾峰緱鎺掑簭鍙傛暟.
	 * @return
	 */
	public List<Sort> getSort() {
		String[] orderBys = StringUtils.split(this.orderProperty, ',');
		String[] orderDirs = StringUtils.split(this.order, ',');
		Validate.isTrue(orderBys.length == orderDirs.length, "");

		List<Sort> orders = new ArrayList<Sort>();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}
		return orders;
	}
	
	
	
	public Long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
		if(this.totalPage>this.currentPage)setIsCanDown(1);
	}
	
	

	public int getIsCanUp() {
		return isCanUp;
	}
	public void setIsCanUp(int isCanUp) {
		this.isCanUp = isCanUp;
	}
	public int getIsCanDown() {
		return isCanDown;
	}
	public void setIsCanDown(int isCanDown) {
		this.isCanDown = isCanDown;
	}
	/**
	 * 澶嶅埗pager鐨勫熀鏈俊鎭紝totalRecords,currentPage,pageSize,orderProperty,order,countTotal
	 * @param pager
	 * @return
	 */
	public static <X,M> PagerInfo<M> cloneFromPager(PagerInfo<X> pager){
		PagerInfo<M> result = new PagerInfo<M>();
		result.setCountTotal(pager.isCountTotal());
		result.setCurrentPage(pager.getCurrentPage());
		result.setOrder(pager.getOrder());
		result.setOrderProperty(pager.getOrderProperty());
		result.setPageSize(pager.getPageSize());
		result.setTotalRecords(pager.getTotalRecords());
		return result;
	}
	
	/**
	 * 澶嶅埗pager鐨勫熀鏈俊鎭紝totalRecords,currentPage,pageSize,orderProperty,order,countTotal,
	 * 閲嶆柊璁剧疆records锛宼otalRecords灞炴?
	 * @param pager
	 * @return
	 */
	public static <X> PagerInfo<X> cloneFromPager(PagerInfo<X> pager,long totalRecords,List<X> records){
		PagerInfo<X> result = cloneFromPager(pager);
		result.setTotalRecords(totalRecords);
		result.setRecords(records);
		return result;
	}
	
	public static class Sort {
		public static final String ASC = "asc";
		public static final String DESC = "desc";

		private final String property;
		private final String dir;

		public Sort(String property, String dir) {
			this.property = property;
			this.dir = dir;
		}

		public String getProperty() {
			return property;
		}

		public String getDir() {
			return dir;
		}
	}
}

	
	


