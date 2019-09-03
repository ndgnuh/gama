package gama.util.list;

import java.util.List;

import com.google.common.collect.ForwardingList;

import gaml.types.IContainerType;
import gaml.types.IType;
import gaml.types.Types;

public class GamaListWrapper<E> extends ForwardingList<E> implements IList<E> {

	final List<E> wrapped;
	final IContainerType type;

	GamaListWrapper(final List<E> wrapped, final IType contents) {
		this.type = Types.LIST.of(contents);
		this.wrapped = wrapped;
	}

	@Override
	public IContainerType<?> getGamlType() {
		return type;
	}

	@Override
	protected List<E> delegate() {
		return wrapped;
	}

}
