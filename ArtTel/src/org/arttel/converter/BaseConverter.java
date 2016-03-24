package org.arttel.converter;

import org.arttel.entity.FinancialDocument;
import org.arttel.ui.ResultPage;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;

public abstract class BaseConverter<Model, Entity extends FinancialDocument<?>> { /*TODO zamienic FinancialDocument na UserSet w momencie
 																					u¿ycia tej klasy bazowej dla converterow encji nie-finansowych */

	public Model convert(final Entity entity) {
		return toModel().apply(entity);
	}

	public Entity convert(final Model model, final String userName) {
		final Entity entity = toEntity().apply(model);
		entity.setUser(userName);
		return entity;
	}

	public ResultPage<Model> convert(final ResultPage<Entity> entities) {
		final ImmutableList<Model> results =
				FluentIterable.from(entities.getRecords()).transform(toModel()).toList();
		return new ResultPage<Model>(results, entities.getPageNo(), entities.getPageCount());
	}

	protected abstract Function<Model, Entity> toEntity();

	protected abstract Function<Entity, Model> toModel();
}
