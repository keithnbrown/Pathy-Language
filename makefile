all: grammar java

java: Action.java Entity.java Junction.java Link.java LinkDir.java LinkPair.java Node.java PathTreeVisitor.java Pathy.java Pathy.tokens PathyBaseListener.java PathyBaseVisitor.java PathyLexer.java PathyLexer.tokens PathyListener.java PathyObject.java PathyParser.java PathyPlace.java PathyType.java PathyVisitor.java
	javac *.java

grammar: Pathy.g4
	antlr4 Pathy.g4

demofile: demos/Demo1.pathy demos/Demo1a.pathy demos/Demo2.pathy demos/Demo3.pathy demos/Demo3a.pathy demos/Demo3b.pathy demos/Demo4.pathy demos/Demo5.pathy demos/Error1a.pathy demos/Error2a.pathy demos/Error2b.pathy demos/Error2c.pathy demos/Error3a.pathy demos/Error3b.pathy demos/Error4.pathy demos/Error4b.pathy demos/Demo5a.pathy
	echo "Demo1.pathy"  > demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo1.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo1a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo1a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo2.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo2.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo3.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo3.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo3a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo3a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo3b.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo3b.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo4.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo4.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo5.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo5.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Demo5a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Demo5a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error1a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error1a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error2a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error2a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error2b.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error2b.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error2c.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error2c.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error3a.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error3a.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error3b.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error3b.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error4.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error4.pathy  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	echo "Error4b.pathy"  >> demos/OutputExamples.txt
	echo "\n"  >> demos/OutputExamples.txt
	java Pathy demos/Error4b.pathy  >> demos/OutputExamples.txt


.PHONY: clean

clean:
	rm -f PathyBaseListener.java PathyBaseVisitor.java PathyLexer.java PathyListener.java PathyParser.java PathyVisitor.java Pathy*.tokens *.class *~ demos/*~ .gitignore~
