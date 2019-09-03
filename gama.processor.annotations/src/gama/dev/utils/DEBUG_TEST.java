package gama.dev.utils;

public class DEBUG_TEST {

	public static void main(final String[] args) {
		new DEBUG_TEST().run();
	}

	public void run() {

		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			DEBUG.findCallingClassName();
		}
		DEBUG.LOG("Security manager : " + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			DEBUG.findCallingClassNameThreadTechnique();
		}
		DEBUG.LOG("Thread Stack trace : " + (System.currentTimeMillis() - start) + "ms");
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			DEBUG.findCallingClassNameStackWalkerTechnique();
		}
		DEBUG.LOG("StackWalker for each : " + (System.currentTimeMillis() - start) + "ms");

	}
}
