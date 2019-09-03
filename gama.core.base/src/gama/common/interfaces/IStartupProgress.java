package gama.common.interfaces;

public interface IStartupProgress {

	String DEFAULT_MSG = "Loading GAMA...";

	void add(String task);

}
