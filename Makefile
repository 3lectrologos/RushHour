SRC = $(PWD)/src
BIN = $(PWD)/bin

SOURCE_FILES = \
	Algorithm \
	ArgParser \
	Astar \
	Bfs \
	Car \
	Heuristic \
	Point \
	Run \
	SimpleHeuristic \
	State \
	Test

vpath %.java $(PWD)/src
vpath %.class $(PWD)/bin

all: java bash input

bash: rush.sh test.sh

java: $(SOURCE_FILES:%=%.class)

rush.sh:
	printf "#%c/bin/bash\n \
	      time java -classpath $(BIN) -Xms2048m -Xmx2048m Run" ! \
	      > rush.sh
	chmod +x rush.sh

test.sh:
	printf "#%c/bin/bash\n \
	      time java -classpath $(BIN) -Xms2048m -Xmx2048m Test \
	      \"\044\061\" \"\044\062\"" ! \
	      > test.sh
	chmod +x test.sh
	
input:
	printf "4 6 6 (5,2) [(0,(1,2),(2,2)), (1,(0,0),(1,0)), (2,(0,1),(0,3)), (3,(0,4),(0,5)), (4,(3,1),(3,3)), (5,(5,0),(5,2)), (6,(4,4),(5,4)), (7,(2,5),(4,5))];" ! \
  > input

%.class: %.java
	javac -sourcepath $(SRC) -classpath $(BIN) -d $(BIN) $<

clean:
	rm -f $(BIN)/*.class
	rm -f rush.sh test.sh
	rm -f input
