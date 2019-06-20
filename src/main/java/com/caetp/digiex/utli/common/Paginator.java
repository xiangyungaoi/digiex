package com.caetp.digiex.utli.common;

public class Paginator {

	private int pageSize;
	private int rowCounts;
	private int offset;
	private int rows;

	public Paginator(int pageSize, int rowCounts) {
		super();
		this.pageSize = pageSize;
		this.rowCounts = rowCounts;

		if (this.rowCounts >= this.pageSize) {
			this.offset = this.rowCounts - this.pageSize;
			this.rows = this.pageSize;
		} else {
			this.offset = 0;
			this.rows = this.rowCounts;
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public int getOffset() {
		return offset;
	}

	public int getRows() {
		return rows;
	}

	public static class Builder {
		private int pageSize;
		private int rowCounts;

		public Builder setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder setRowCounts(int rowCounts) {
			this.rowCounts = rowCounts;
			return this;
		}

		public Paginator build() {
			return new Paginator(pageSize, rowCounts);
		}

	}
}
