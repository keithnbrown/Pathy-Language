all: grammar java

java: Action.java Entity.java Junction.java Link.java LinkDir.java LinkPair.java Node.java PathTreeVisitor.java Pathy.java Pathy.tokens PathyBaseListener.java PathyBaseVisitor.java PathyLexer.java PathyLexer.tokens PathyListener.java PathyObject.java PathyParser.java PathyPlace.java PathyType.java PathyVisitor.java
	javac *.java

grammar: Pathy.g4
	antlr4 Pathy.g4

.PHONY: clean

clean:
	rm -f PathyBaseListener.java PathyBaseVisitor.java PathyLexer.java PathyListener.java PathyParser.java PathyVisitor.java Pathy*.tokens *.class *~ demos/*~ .gitignore~
