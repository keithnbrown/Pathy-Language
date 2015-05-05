import java.util.Deque;
import java.util.HashMap;

public class PathTreeVisitor extends PathyBaseVisitor<Void>
{
	boolean debug;
	HashMap<String, PathyObject> worldDict;
	Deque<StackElement> stack;
	
	private boolean checkItemNode(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType().equals(PathyObject.NODE);
	}
	
	private boolean checkItemAction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType().equals(PathyObject.ACTION);
	}
	
	private boolean checkItemJunction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType().equals(PathyObject.JUNCT);
	}
	
	private boolean checkItemLink(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType().equals(PathyObject.LINK);
	}
	
	private boolean checkItemEntity(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType().equals(PathyObject.ENTITY);
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
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("ERROR: \"" + id + "\" has already been used as an identifier."); 
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
				Node param = (temp.type().equals(PathyObject.NODE) ? (Node)temp : null);
				if (param != null)
				{
					Entity newEnt = new Entity(id, param);
					worldDict.put(id, newEnt);
				}
				else
				{
					throw new RuntimeException("ERROR: Unable to retrieve Node \"" + nodeid + "\".");
				}
			}
			else
			{
				throw new RuntimeException("ERROR: \"" + nodeid + "\" is not an valid identifier for an existing Node. PARAM 0.");
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("ERROR: \"" + id + "\" has already been used as an identifier."); 
		}
		
		if (debug)
		{
			System.out.println(id);
			System.out.println(worldDict.get(id).toString());
			System.out.println();
			System.out.println(worldDict);
		}l
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
				Node param = (temp.type().equals(PathyObject.NODE) ? (Node)temp : null);
				if (param != null)
				{
					Entity newEnt = new Entity(id, param);
					worldDict.put(id, newEnt);
				}
				else
				{
					throw new RuntimeException("ERROR: Unable to retrieve Node \"" + nodeid + "\".");
				}
			}
			else
			{
				throw new RuntimeException("ERROR: \"" + nodeid + "\" is not an valid identifier for an existing Node. PARAM 0.");
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("ERROR: \"" + id + "\" has already been used as an identifier."); 
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

	public Void visitLinkDecDef(PathyParser.LinkDecDefContext ctx)
	{
		String id = ctx.idpar(0).getText();
		String aid = ctx.idpar(1).getText();
		String bid = ctx.idpar(2).getText();
		if (!worldDict.containsKey(id))
		{
			PathyObject tempA;
			PathyObject tempB;
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!checkItemNode(aid) && !checkItemJunction(aid)
			&& !checkItemNode(bid) && !checkItemJunction(bid))
			{
				//Both parameters are invalid
				String throwline = "ERROR: \"" + aid + "\" is not an valid identifier for an existing Node or Junction. PARAM 0.";
				throwline = throwline + System.lineSeparator() + "ERROR: \"" + bid + "\" is not an valid identifier for an existing Node or Junction. PARAM 1.";
				throw new RuntimeException(throwline);				
			}
			if ((checkItemNode(aid) || checkItemJunction(aid)))
			{
				//parameter 0 is valid
				tempA = worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException("ERROR: \"" + aid + "\" is not an valid identifier for an existing Node or Junction. PARAM 0.");
			}
			
			if ((checkItemNode(bid) || checkItemJunction(bid)))
			{
				//parameter 1 is valid
				tempB = worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException("ERROR: \"" + bid + "\" is not an valid identifier for an existing Node or Junction. PARAM 1.");
			}
			Link newLink;
			Node nodeA;
			Node nodeB;
			Junction junctA;
			Junction junctB;
			if (tempA.type().equals(PathyObject.NODE) && tempB.type().equals(PathyObject.NODE))
			{
				nodeA = (Node)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, nodeA, nodeB);
			}
			else if (tempA.type().equals(PathyObject.NODE) && tempB.type().equals(PathyObject.JUNCT))
			{
				nodeA = (Node)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, nodeA, junctB);				
			}			
			else if (tempA.type().equals(PathyObject.JUNCT) && tempB.type().equals(PathyObject.NODE))
			{
				junctA = (Junction)tempA;
				nodeB = (Node)tempB;	
				newLink = new Link(id, junctA, nodeB);			
			}
			else if (tempA.type().equals(PathyObject.JUNCT) && tempB.type().equals(PathyObject.JUNCT))
			{
				junctA = (Junction)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, junctA, junctB);						
			}
			else
			{
				throw new RuntimeException("ERROR: Endpoint error constructing \"" + id + "\".");
			}
			worldDict.put(id, newLink);
			
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("ERROR: \"" + id + "\" has already been used as an identifier."); 
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

	public Void visitLinkDecWeight(PathyParser.LinkDecWeightContext ctx)
	{
		String id = ctx.idpar(0).getText();
		String aid = ctx.idpar(1).getText();
		String bid = ctx.idpar(2).getText();
		int weight = Integer.parseInt(ctx.intpar().getText());
		if (!worldDict.containsKey(id))
		{
			PathyObject tempA;
			PathyObject tempB;
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!checkItemNode(aid) && !checkItemJunction(aid)
			&& !checkItemNode(bid) && !checkItemJunction(bid))
			{
				//Both parameters are invalid
				String throwline = "ERROR: \"" + aid + "\" is not an valid identifier for an existing Node or Junction. PARAM 0.";
				throwline = throwline + System.lineSeparator() + "ERROR: \"" + bid + "\" is not an valid identifier for an existing Node or Junction. PARAM 1.";
				throw new RuntimeException(throwline);				
			}
			if ((checkItemNode(aid) || checkItemJunction(aid)))
			{
				//parameter 0 is valid
				tempA = worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException("ERROR: \"" + aid + "\" is not an valid identifier for an existing Node or Junction. PARAM 0.");
			}
			
			if ((checkItemNode(bid) || checkItemJunction(bid)))
			{
				//parameter 1 is valid
				tempB = worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException("ERROR: \"" + bid + "\" is not an valid identifier for an existing Node or Junction. PARAM 1.");
			}
			Link newLink;
			Node nodeA;
			Node nodeB;
			Junction junctA;
			Junction junctB;
			if (tempA.type().equals(PathyObject.NODE) && tempB.type().equals(PathyObject.NODE))
			{
				nodeA = (Node)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, nodeA, nodeB, weight);
			}
			else if (tempA.type().equals(PathyObject.NODE) && tempB.type().equals(PathyObject.JUNCT))
			{
				nodeA = (Node)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, nodeA, junctB, weight);
			}			
			else if (tempA.type().equals(PathyObject.JUNCT) && tempB.type().equals(PathyObject.NODE))
			{
				junctA = (Junction)tempA;
				nodeB = (Node)tempB;	
				newLink = new Link(id, junctA, nodeB, weight);
			}
			else if (tempA.type().equals(PathyObject.JUNCT) && tempB.type().equals(PathyObject.JUNCT))
			{
				junctA = (Junction)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, junctA, junctB, weight);	
			}
			else
			{
				throw new RuntimeException("ERROR: Endpoint error constructing \"" + id + "\".");
			}
			
			worldDict.put(id, newLink);
			
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException("ERROR: \"" + id + "\" has already been used as an identifier."); 
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
