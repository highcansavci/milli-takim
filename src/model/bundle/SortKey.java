package model.bundle;

public enum SortKey {
	
	ASCENDING("Ascending"), DESCENDING("Descending");
	
	private String sortType;
	
	SortKey(String sortType) {
		this.setSortType(sortType);
	}

	public String getSortType() {
		return sortType;
	}

	private void setSortType(String sortType) {
		this.sortType = sortType;
	}
}
