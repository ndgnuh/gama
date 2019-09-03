package gama.common.interfaces;

public interface ConsumerWithPruning<T> {

	boolean process(T t);

}
