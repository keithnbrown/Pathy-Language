import java.util.Deque;
import java.util.HashMap;

public class PathTreeVisitor extends PathyBaseVisitor<Void>
{
	boolean debug;
	HashMap<String, PathyObject> worldDict;
	Deque<StackElement> stack;
	
	private boolean checkItemNode(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id) instanceof Node;
	}
	
	private boolean checkItemAction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id) instanceof Action;
	}
	
	private boolean checkItemJunction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id) instanceof Junction;
	}
	
	private boolean checkItemLink(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id) instanceof Link;
	}
	
	private boolean checkItemEntity(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id) instanceof Entity;
	}

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
		//check if id already exists
		if (!worldDict.containsKey(id))
		{
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
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("\"" + id + "\" has already been used as an identifier."); 
		}
		return null;
	}

	public Void visitEntDecDef(PathyParser.EntDecDefContext ctx)
	{
		String id = ctx.idpar(0).getText();
		String nodeid = ctx.idpar(1).getText();

		//check if id already exists
		if (!worldDict.containsKey(id))
		{
			//check if the parameter provided exists and is a node
			if (checkItemNode(nodeid))
			{
				PathyObject temp = worldDict.get(id);
				Node param = (temp instanceof Node ? (Node)temp : null);
				if (param != null)
				{
					Entity newEnt = new Entity(id, param);
					worldDict.put(id, newEnt);
				}
				else
				{
					throw new RuntimeException("Error retrieving Node \"" + nodeid + "\".");
				}
			}
			else
			{
				throw new RuntimeException("\"" + nodeid + "\" is not an valid identifier for an existing Node. PARAM 0.");
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("\"" + id + "\" has already been used as an identifier."); 
		}
		return null;
	}	

	public Void visitEntDecEnergy(PathyParser.EntDecEnergyContext ctx)
	{
		String id = ctx.idpar(0).getText();
		String nodeid = ctx.idpar(1).getText();
		int energy = Integer.parseInt(ctx.intpar().getText());
		//check if id already exists
		if (!worldDict.containsKey(id))
		{
			//check if the parameter provided exists and is a node
			if (checkItemNode(nodeid))
			{
				PathyObject temp = worldDict.get(nodeid);
				Node param = (temp instanceof Node ? (Node)temp : null);
				if (param != null)
				{
					Entity newEnt = new Entity(id, param);
					worldDict.put(id, newEnt);
				}
				else
				{
					throw new RuntimeException("Error retrieving Node \"" + nodeid + "\".");
				}
			}
			else
			{
				throw new RuntimeException("\"" + nodeid + "\" is not an valid identifier for an existing Node. PARAM 0.");
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("\"" + id + "\" has already been used as an identifier."); 
		}
		return null;
	}

	public Void visitLinkDecDef(PathyParser.LinkDecDefContext ctx)
	{
		String id = ctx.idpar(0).getText();
		String aid = ctx.idpar(1).getText();
		String bid = ctx.idpar(1).getText();
		if (!worldDict.containsKey(id))
		{
			PathyObject tempA;
			PathyObject tempB;
			//check if the parameters provided exist and are a nodes or junctions
			if ((checkItemNode(aid) || checkItemJunction(aid)))
			{
				tempA = worldDict.get(aid);
			}
			else
			{
				throw new RuntimeException("\"" + aid + "\" is not an valid identifier for an existing Node or Junction. PARAM 0.");
			}
			
			if ((checkItemNode(bid) || checkItemJunction(bid)))
			{
				tempB = worldDict.get(bid);
			}
			else
			{
				throw new RuntimeException("\"" + bid + "\" is not an valid identifier for an existing Node or Junction. PARAM 1.");
			}
			
			
			
			
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("\"" + id + "\" has already been used as an identifier."); 
		}
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
