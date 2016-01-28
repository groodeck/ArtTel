package org.arttel.ui;

import static org.arttel.util.Translator.parseInt;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class TableHeader{

	final PageInfo pageInfo;
	final Map<String, SortableColumn> columnsById;

	public TableHeader(final SortableColumn...sortableColumns) {
		columnsById = FluentIterable
				.from(Lists.newArrayList(sortableColumns))
				.uniqueIndex(new Function<SortableColumn, String>(){

					@Override
					public String apply(final SortableColumn input) {
						return input.getColumnKey();
					}

				});
		pageInfo = new PageInfo(getCurrentSortColumn().orNull());
	}

	public Map<String, SortableColumn> getColumns(){
		return columnsById;
	}

	public Optional<SortableColumn> getCurrentSortColumn() {
		return FluentIterable.from(columnsById.values()).firstMatch(
				new Predicate<SortableColumn>(){

					@Override
					public boolean apply(final SortableColumn input) {
						return input.getSortOrder() != null;
					}

				});
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public PageInfo updatePageNo(final HttpServletRequest request, final String newPageNo) {
		pageInfo.setPageNo(parseInt(newPageNo));
		return pageInfo;
	}

	public PageInfo updateSortColumn(final String sortColumnId) {

		final Optional<SortableColumn> currentSortColumnOpt = getCurrentSortColumn();
		SortOrder sortOrder = SortOrder.ASC;
		if(currentSortColumnOpt.isPresent()){
			final SortableColumn currentSortColumn = currentSortColumnOpt.get();
			if(currentSortColumn.getColumnKey().equals(sortColumnId)){
				sortOrder = currentSortColumn.getSortOrder().getOposite();
			}
			currentSortColumn.clear();
		}

		final SortableColumn newSortColumn = columnsById.get(sortColumnId);
		newSortColumn.setSortOrder(sortOrder);

		pageInfo.updateSort(newSortColumn);
		return pageInfo;
	}
}
