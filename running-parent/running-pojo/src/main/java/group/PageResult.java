package group;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 * 
 * @author 
 *
 */
public class PageResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 总页数
	private long total;
	// 当前页数
	private List rows;

	public PageResult(long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}