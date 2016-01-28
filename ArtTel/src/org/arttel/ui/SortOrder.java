package org.arttel.ui;

public enum SortOrder {
	ASC("&#8593;", "ASC"), DESC("&#8595;", "DESC");

	private final String symbol;
	private final String clause;

	private SortOrder(final String symbol, final String clause){
		this.symbol = symbol;
		this.clause = clause;
	}

	public String getClause() {
		return clause;
	}

	public SortOrder getOposite(){
		if(this == ASC){
			return DESC;
		} else {
			return ASC;
		}
	}

	public String getSymbol(){
		return symbol;
	}
}
