import java.util.Deque;
import java.util.HashMap;

public class PathTreeVisitor extends PathyBaseVisitor<Void>
{
	Hashmap<String, Node> nodeDict;
	Hashmap<String, Link> linkDict;
	Hashmap<String, Junction> junctionDict;
	Hashmap<String, Entity> entityDict;
	Hashmap<String, Action> actionDict;
	Deque<StackElement> stack;

	public PathTreeVisitor(Hashmap<String, Node> _nd, Hashmap<String, Link> _ld, Hashmap<String, Junction> _jd, Hashmap<String, Entity> _ed, Hashmap<String, Action> _ad)
	{
		super();
		nodeDict = _nd;
		linkDict = _ld;
		junctionDict = _jd;
		entityDict = _ed;
		actionDict = _ad;
	}
//declaration statements
	public Void visitSimpleDec(PathyParser.SimpleDecContext ctx)
	{
		String id = ctx.idpar().getText();
		//Check if ID exists
		switch(ctx.op.getType()) {
		case PathyParser.NODE:
			Node newNode = new Node();
			nodeDict.put(id, newNode);
			break;
		case PathyParser.JUNCT:
			Node newJunct = new Junction();
			junctionDict.put(id, newJunct);
			break;
		case PathyParser.ACT:
			Node newAct = new Action();
			actionDict.put(id, newAct);
			break;
		}

		System.out.println(id);
		
		return null;
	}

	public Void visitEntDec()
	{
		return null;
		
	}

	public Void visitLinkDecDef()
	{
		return null;
		
	}

	public Void visitLinkDecWeight()
	{
		return null;
		
	}

	public Void visitLinkDecBoth()
	{
		return null;
		
	}

	public Void visitAssignAct()
	{
		return null;
		
	}

	public Void visitDelItem()
	{
		return null;
		
	}

//flexi statements
	public Void visitSetLink2B()
	{
		return null;
		
	}

	public Void visitSetVals()
	{
return null;
		
	}
	
	public Void visitMoveEnt()
	{
return null;
		
	}

	public Void visitSetLink1W()
	{
return null;
		
	}

	public Void visitSetJunctDir()
	{
return null;
		
	}

//query statements
	public Void visitNoParamQuery()
	{
return null;
		
	}

	public Void visitOneParamQuery()
	{
return null;
		
	}

	public Void visitTwoParamQuery()
	{
return null;
		
	}
}
