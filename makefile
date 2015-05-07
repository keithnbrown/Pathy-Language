all: grammar java

java: Pathy.java PathyObject.java PathTreeVisitor.java PathyBaseListener.java PathyBaseVisitor.java PathyLexer.java PathyLexer.tokens PathyListener.java Pathy.tokens PathyParser.java PathyVisitor.java Node.java Link.java Junction.java Entity.java Action.java
	javac *.java

grammar: Pathy.g4
	antlr4 Pathy.g4

.PHONY: clean

clean:
	rm -f PathyBaseListener.java PathyBaseVisitor.java PathyParser.java PathyVisitor.java PathyLexer.java PathyListener.java Pathy*.tokens *.class *~ demos/*~ .gitignore~
