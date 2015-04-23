import java.util.Deque;
import java.util.HashMap;

public class PathTreeVisitor extends PathyBaseVisitor<Void>
{
	boolean debug;
	HashMap<String, PathyObject> worldDict;
	Deque<StackElement> stack;

	public PathTreeVisitor(HashMap<String, PathyObject> _wd)
	{
		super();
		worldDict = _wd;
		debug = true;
	}
//declaration statements
	public Void visitSimpleDec(PathyParser.SimpleDecContext ctx)
	{
		String id = ctx.idpar().getText();
		//Check if ID exists
		switch(ctx.op.getType()) {
		case PathyParser.NODE:
			Node newNode = new Node(id);
			worldDict.put(id, newNode);
			break;
		case PathyParser.JUNCT:
			Junction newJunct = new Junction(id);
			worldDict.put(id, newJunct);
			break;
		case PathyParser.ACT:
			Action newAct = new Action(id);
			worldDict.put(id, newAct);
			break;
		}
		
		if (debug)
		{
			System.out.println(id);
			System.out.println(worldDict.get(id).toString());
			System.out.println();
			System.out.println(worldDict);
		}
		return null;
	}

	public Void visitEntDec(PathyParser.EntDecContext ctx)
	{
		return null;
		
	}

	public Void visitLinkDecDef(PathyParser.LinkDecDefContext ctx)
	{
		return null;
		
	}

	public Void visitLinkDecWeight(PathyParser.LinkDecWeightContext ctx)
	{
		return null;
		
	}

	public Void visitLinkDecBoth(PathyParser.LinkDecBothContext ctx)
	{
		return null;
		
	}

	public Void visitAssignAct(PathyParser.AssignActContext ctx)
	{
		return null;
		
	}

	public Void visitDelItem(PathyParser.DelItemContext ctx)
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
