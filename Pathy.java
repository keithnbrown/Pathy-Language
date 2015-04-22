import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Deque;
import java.util.HashMap;

public class Pathy {
    public static void main(String[] args) throws Exception {
        String inputFile = null; 
        if(args.length > 0)
		inputFile = args[0];

        InputStream is = System.in;
        if (inputFile != null)
		is = new FileInputStream(inputFile);

        ANTLRInputStream input = new ANTLRInputStream(is);
        PathyLexer lexer = new PathyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PathyParser parser = new PathyParser(tokens);

        ParseTree tree = parser.r();
        System.out.println(tree.toStringTree(parser));

	Hashmap<String, Node> nodeDict = new Hashmap<String, Node>();
	Hashmap<String, Link> linkDict = new Hashmap<String, Link>();
	Hashmap<String, Junction> junctionDict = new Hashmap<String, Junction>();
	Hashmap<String, Entity> entityDict = new Hashmap<String, Entity>();
	Hashmap<String, Action> actionDict = new Hashmap<String, Action>();
	Deque<StackElement> stack;

	PathTreeVisitor eval = new PathTreeVisitor();
	System.out.println(eval.visit(tree));
    }
}
