cd gama.processor.annotations
mvn clean install $1
cd - 
cd gama.processor.engine 
mvn clean install $1
cd - 
cd gama.build.parent
mvn clean install $1
cd -