import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;


public class PathTreeVisitor extends PathyBaseVisitor<Void>
{
	boolean debug;
	HashMap<String, PathyObject> worldDict;
	Deque<StackElement> stack;

	private boolean checkItemNode(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType() == PathyType.NODE;
	}

	private boolean checkItemAction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType() == PathyType.ACTION;
	}

	private boolean checkItemJunction(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType() == PathyType.JUNCT;
	}

	private boolean checkItemLink(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType() == PathyType.LINK;
	}

	private boolean checkItemEntity(String id)
	{
		return worldDict.containsKey(id) && worldDict.get(id).getType() == PathyType.ENTITY;
	}
	
	private boolean checkItemPlace(String id)
	{
		return worldDict.containsKey(id) && (worldDict.get(id).getType() == PathyType.JUNCT || worldDict.get(id).getType() == PathyType.NODE);
	}

	private HashSet<Node> getNodes()
	{
		HashSet<Node> set = new HashSet<Node>();

		for(Entry<String, PathyObject> e: worldDict.entrySet())
		{
			PathyObject item = e.getValue();
			if (item.getType() == PathyType.NODE)
			{
				set.add((Node) item);
			}
		}

		return set;
	}

	private HashSet<Link> getLinks()
	{
		HashSet<Link> set = new HashSet<Link>();

		for(Entry<String, PathyObject> e: worldDict.entrySet())
		{
			PathyObject item = e.getValue();
			if (item.getType() == PathyType.LINK)
			{
				set.add((Link) item);
			}
		}

		return set;
	}

	private HashSet<Entity> getEntities()
	{
		HashSet<Entity> set = new HashSet<Entity>();

		for(Entry<String, PathyObject> e: worldDict.entrySet())
		{
			PathyObject item = e.getValue();
			if (item.getType() == PathyType.ENTITY)
			{
				set.add((Entity) item);
			}
		}

		return set;
	}

	private HashSet<Junction> getJunctions()
	{
		HashSet<Junction> set = new HashSet<Junction>();

		for(Entry<String, PathyObject> e: worldDict.entrySet())
		{
			PathyObject item = e.getValue();
			if (item.getType() == PathyType.JUNCT)
			{
				set.add((Junction) item);
			}
		}

		return set;
	}

	private HashSet<Action> getActions()
	{
		HashSet<Action> set = new HashSet<Action>();

		for(Entry<String, PathyObject> e: worldDict.entrySet())
		{
			PathyObject item = e.getValue();
			if (item.getType() == PathyType.ACTION)
			{
				set.add((Action) item);
			}
		}

		return set;
	}

	private enum ErrorType{IDCONFLICT, IDNOTVALID, ENDPOINTERR}

	private String generateFeedback(ErrorType errtype, String id, String ptype, int par)
	{
		String ret = "";

		switch(errtype)
		{
		case IDCONFLICT:
			ret = "ERROR: \"" + id + "\" has already been used as an identifier.";
			break;
		case IDNOTVALID:
			ret = "ERROR: \"" + id + "\" is not a valid identifier for an existing " + ptype + ". PARAM " + Integer.toString(par) + ".";
			break;
		case ENDPOINTERR:
			ret = "ERROR: Endpoint error constructing \"" + id + "\".";
			break;
		}

		return ret;
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
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT,id, null, 0)); 
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
				Node param = (Node)worldDict.get(id);
				Entity newEnt = new Entity(id, param);
				worldDict.put(id, newEnt);
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, nodeid, "Node", 0));
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT,id, null, 0)); 
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
				Node param = (Node)worldDict.get(id);
				Entity newEnt = new Entity(id, param, energy);
				worldDict.put(id, newEnt);
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, nodeid, "Node", 0));
			}
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT, id, null, 0)); 
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
			PathyPlace placeA;
			PathyPlace placeB;
			boolean aCheck = checkItemPlace(aid);
			boolean bCheck = checkItemPlace(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aCheck && !bCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			
			if ((aCheck))
			{
				//parameter 0 is valid
				placeA = (PathyPlace)worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bCheck))
			{
				//parameter 1 is valid
				placeB = (PathyPlace)worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			Link newLink = new Link(id, placeA, placeB);
			worldDict.put(id, newLink);
		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT,id, null, 0)); 
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

			PathyPlace placeA;
			PathyPlace placeB;
			boolean aCheck = checkItemPlace(aid);
			boolean bCheck = checkItemPlace(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aCheck && !bCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			
			if ((aCheck))
			{
				//parameter 0 is valid
				placeA = (PathyPlace)worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bCheck))
			{
				//parameter 1 is valid
				placeB = (PathyPlace)worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			
			Link newLink = new Link(id, placeA, placeB, weight);

			worldDict.put(id, newLink);

		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT,id, null, 0)); 
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
		String id = ctx.idpar(0).getText();
		String aid = ctx.idpar(1).getText();
		String bid = ctx.idpar(2).getText();
		int weight = Integer.parseInt(ctx.intpar().getText());
		LinkDir dir = LinkDir.values()[Integer.parseInt(ctx.dirpar().getText())];
		if (!worldDict.containsKey(id))
		{

			PathyPlace placeA;
			PathyPlace placeB;
			boolean aCheck = checkItemPlace(aid);
			boolean bCheck = checkItemPlace(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aCheck && !bCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			
			if ((aCheck))
			{
				//parameter 0 is valid
				placeA = (PathyPlace)worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bCheck))
			{
				//parameter 1 is valid
				placeB = (PathyPlace)worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			Link newLink = new Link(id, placeA, placeB, weight, dir);

			worldDict.put(id, newLink);

		}
		else
		{
			//user tried to reuse a name
			throw new IllegalStateException(generateFeedback(ErrorType.IDCONFLICT,id, null, 0)); 
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

	public Void visitAssignAct(PathyParser.AssignActContext ctx)
	{
		String aid = ctx.idpar(0).getText();
		String nid = ctx.idpar(1).getText();

		boolean isact = checkItemAction(aid);
		boolean isnode = checkItemNode(nid);

		if (!isact && !isnode)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID,aid, "Action", 0)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID,nid, "Node", 1));
		}
		else if (!isact)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID,aid, "Action", 0));
		}
		else if (!isnode)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID,nid, "Node", 1));
		}

		//both checked out lets add it now
		Node n = (Node) worldDict.get(nid);
		Action a = (Action) worldDict.get(aid);

		n.addAction(a);
		return null;
	}

	public Void visitDelItem(PathyParser.DelItemContext ctx)
	{
		String id = ctx.idpar().getText();
		if (!worldDict.containsKey(id))
		{
			//Throws: if [ID] doesn't exist
			throw new IllegalStateException("ERROR: \"" + id + "\" does not exist."); 
		}

		PathyObject item = worldDict.get(id);
		PathyType itemtype = item.getType();

		if (itemtype == PathyType.NODE || itemtype == PathyType.JUNCT)
		{
			PathyPlace p = (PathyPlace)item;
			
			if (p.isConnected())
			{
				//Throws: if [ID] is a Node or Junction, and is attached to one or more Links
				throw new IllegalStateException("ERROR: \"" + id + "\" is an endpoint for one or more Links. Cannot remove \"" + id + "\" unless all Links are removed first."); 
			}
		}

		if (itemtype == PathyType.NODE)
		{
			HashSet<Entity> entities = getEntities();
			for (Entity e : entities)
			{
				if (e.getLocation() == (Node) item)
				{
					//Throws: if [ID] is a Node, and an Entity is currently located at [ID] 
					throw new IllegalStateException("ERROR: \"" + e.getID() + "\" is the current location of \"" + id 
							+ "\". Cannot remove \"" + id + "\" unless the Entity is moved first."); 
				}
			}

			Node n = (Node)item;
			if (!n.getActivities().isEmpty())
			{
				//Warning: shows a warning if [ID] is a Node, and had one or more Actions associated with it.
				System.out.println("WARNING: \"" + id + "\" had Action(s) assigned to it. Some actions may not be assigned to any Nodes as a result.");
			}

		}

		if (itemtype == PathyType.ACTION)
		{
			//Warning: shows a warning if [ID] is an Action, and was associated with one or more nodes.
			HashSet<Node> nodes = getNodes();
			boolean found = false;
			for (Node n : nodes)
			{
				if(!n.getActivities().isEmpty())
				{
					if (n.removeAction((Action) item))
					{
						//removeAction returns true is it was removed thus was there
						found = true;
					}
				}
			}
			if (found)
			{
				System.out.println("WARNING: \"" + id + "\" was assigned to one or more nodes. Some Nodes may not have any Actions assigned to them as a result.");
			}
		}
		
		if (itemtype == PathyType.LINK)
		{
			Link l = (Link)item;
			l.delink();
		}
		
		//now as the housekeeping has been taken care of we can finally delete the item.
		worldDict.remove(id);
		System.gc();
		return null;
	}

//flexi statements
	public Void visitSetLink2B(PathyParser.SetLink2BContext ctx)
	{
		String id = ctx.idpar().getText();
		if (checkItemLink(id))
		{
			Link l = (Link)worldDict.get(id);
			switch(ctx.op.getType()) {
			case PathyParser.SL2:
				l.setDirection(LinkDir.TWOWAY);
				break;
			case PathyParser.SLB:
				l.setDirection(LinkDir.BLOCKED);
				break;
			}
		}
		else
		{
			//parameter 0 is invalid
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
		}
		return null;
	}

	public Void visitSetVals(PathyParser.SetValsContext ctx)
	{
		String id = ctx.idpar().getText();
		int val = Integer.parseInt(ctx.intpar().getText());
		//the grammar won't catch negatives but check and fix it if it does happen.
		if (val < 0)
		{
			val = -val;
		}
		switch(ctx.op.getType()) {
		case PathyParser.SE:
			if (checkItemEntity(id))
			{
				Entity e = (Entity)worldDict.get(id);
				e.setEnergy(val);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Entity", 0));
			}
			break;
		case PathyParser.SW:
			if (checkItemLink(id))
			{
				Link l = (Link)worldDict.get(id);
				l.setWeight(val);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
			}
			break;
		}
		return null;
	}

	public Void visitMoveEnt(PathyParser.MoveEntContext ctx)
	{
		String eid = ctx.idpar(0).getText();
		String nid = ctx.idpar(1).getText();

		boolean isentity = checkItemEntity(eid);
		boolean isnode = checkItemNode(nid);
		if (!isentity && !isnode)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, eid, "Entity", 0)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, nid, "Node", 1));
		}
		else if (!isentity)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, eid, "Entity", 0));
		}
		else if (!isnode)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, nid, "Node", 1));
		}
		//both checked out lets add it now
		Node n = (Node) worldDict.get(nid);
		Entity e = (Entity) worldDict.get(eid);

		e.setLocation(n);

		return null;
	}

	public Void visitSetLink1W(PathyParser.SetLink1WContext ctx)
	{
		String lid = ctx.idpar(0).getText();
		String aid = ctx.idpar(1).getText();
		String bid = ctx.idpar(2).getText();
		boolean islink = checkItemEntity(lid);
		boolean isA = checkItemPlace(aid);
		boolean isB = checkItemPlace(bid);
		if (!islink && !isA && !isB)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, lid, "Link", 0)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 1)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 2));
		}
		else if (!islink && !isA)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, lid, "Link", 0)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 1));
		}
		else if (!islink && !isB)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, lid, "Link", 0)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 2));
		}
		else if (!isA && !isB)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 1)
					+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 2));
		}
		else if (!islink)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, lid, "Link", 0));
		}
		else if (!isA)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 1));
		}
		else if (!isB)
		{
			throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 2));
		}
		//two final checks - are the nodes on the Link, and are the nodes the same (just in same it slipped through)
		if (aid.equals(bid))
		{
			throw new RuntimeException("ERROR: Both endpoints refer to the same Node or Junction");
		}
		Link l = (Link)worldDict.get(lid);
		PathyPlace a = (PathyPlace)worldDict.get(aid);
		PathyPlace b = (PathyPlace)worldDict.get(bid);

		if (l.isEndpoint(a) && l.isEndpoint(b))
		{
			//all checks out lets do it now
			if (a == l.getA())
			{
				l.setDirection(LinkDir.ATOB);
			}
			else
			{
				l.setDirection(LinkDir.BTOA);
			}
		}
		else
		{
			if (!l.isEndpoint(a) && !l.isEndpoint(b))
			{
				//both weren't endpoints
				throw new RuntimeException("ERROR: The supplied endpoints, \"" + aid + "\"and \""+ bid +"\", were not found at either end of \""+ lid +"\".");
			}
			else if (!l.isEndpoint(a))
			{
				//a wasn't an endpoint
				throw new RuntimeException("ERROR: The supplied endpoint, \"" + aid + "\", was not found at either end of \""+ lid +"\". PARAM 1");
			}
			else
			{
				//b wasn't an endpoint
				throw new RuntimeException("ERROR: The supplied endpoint, \"" + bid + "\", was not found at either end of \""+ lid +"\". PARAM 2");
			}
		}
		return null;
	}

	public Void visitSetJunctDir(PathyParser.SetJunctDirContext ctx)
	{
		return null;
	}

//query statements
	public Void visitNoParamQuery(PathyParser.NoParamQueryContext ctx)
	{
		HashSet<? extends PathyObject> items = null;
		switch(ctx.op.getType()) {
		case PathyParser.FPN:
			items = getNodes();
			break;
		case PathyParser.FPA:
			items = getActions();
			break;
		case PathyParser.FPL:
			items = getLinks();
			break;
		case PathyParser.FPJ:
			items = getJunctions();
			break;
		case PathyParser.FPE:
			items = getEntities();
			break;
		}
		boolean first = true;

		System.out.print("{");
		for (PathyObject i : items)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				System.out.print(",");
			}
			System.out.print(i.getID());
		}
		System.out.println("}");
		return null;
	}

	@SuppressWarnings("unchecked")
	public Void visitOneParamQuery(PathyParser.OneParamQueryContext ctx)
	{
		String id = ctx.idpar().getText();
		switch(ctx.op.getType()) {
		case PathyParser.F1L:
			//Links
			if (checkItemLink(id))
			{
				Link l = (Link)worldDict.get(id);
				System.out.println("{" + l.getA().getID() + "," + l.getB().getID() + "}");
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
			}
			break;
		case PathyParser.F1D:
			//Direction
			if (checkItemLink(id))
			{
				Link l = (Link)worldDict.get(id);
				switch (l.getDirection())
				{
				case BLOCKED:
					System.out.println("{" + "BLOCKED" + "}");
					break;
				case TWOWAY:
					System.out.println("{" + "TWOWAY" + "}");
					break;
				case ATOB:
					System.out.println("{" + "ONEWAY" + "," + l.getA().getID() + "}");
					break;
				case BTOA:
					System.out.println("{" + "ONEWAY" + "," + l.getB().getID() + "}");
					break;
				}
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
			}
			break;
		case PathyParser.F1W:
			//Weight
			if (checkItemLink(id))
			{
				Link l = (Link)worldDict.get(id);
				System.out.println("{" + Integer.toString(l.getWeight()) + "}");
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
			}
			break;
		case PathyParser.F1A:
			//ActionsThere
			if (checkItemNode(id))
			{
				Node n = (Node)worldDict.get(id);
				HashSet<Action> actions = n.getActivities();
				
				System.out.print("{");

				boolean first = true;
				for(Action a : actions)
				{
					if (first)
					{
						first = false;
					}
					else
					{
						System.out.print(",");
					}
					System.out.print(a.getID());
				}
				
				System.out.println("}");
				
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Node", 0));
			}		
			break;
		case PathyParser.F1C:
			//Connectivity
			if (checkItemJunction(id))
			{
				Junction j = (Junction)worldDict.get(id);
				//i need the links as an indexed collection for what i need to do.
				ArrayList<Link> connections = new ArrayList<Link>(j.getConnections());
				int noConns = connections.size();
				//I also need a clone of the internals map as I'm going to be altering it
				//so i'm making sure I'm not altering the original
				HashMap<LinkPair, LinkDir> internals = (HashMap<LinkPair, LinkDir>) j.getInternalConnections().clone();
				int specified = internals.size();
				//Some checks to stop some fiddling around
				//Are there any connections at all?
				if (noConns < 2)
				{
					//there are not enough connections to form internal links, so print the minimum then return
					System.out.println("{}");
					return null;
				}
				//find the number of combinations possible in the connected links		
				int combinations = 0;
				for (int i = noConns - 1; i > 0; --i)
				{
					combinations += i;
				}
				
				//find out if all possible combinations are already specified
				//if not we have to put all the others in so we can iterate over the hashmap
				if (specified != combinations)
				{
					Link a = null;
					Link b = null;
					for (int ita = 0; ita < noConns; ++ita)
					{
						a = connections.get(ita);
						for (int itb = ita; itb < noConns; ++itb)
						{
							b = connections.get(itb);
							LinkPair lp = new LinkPair(a,b);
							if (!internals.containsKey(lp))
							{
								internals.put(lp, LinkDir.TWOWAY);
							}
						}
					}
				}
				specified = internals.size();
				if (specified == combinations)
				{
					System.out.print("{");
					Set<Entry<LinkPair, LinkDir>> internalSet = internals.entrySet();
					for (Entry<LinkPair, LinkDir> connect : internalSet)
					{
						LinkPair key = connect.getKey();
						LinkDir val = connect.getValue();
						System.out.print("["+ key.getA().getID() +","+ key.getB().getID() +",");
						switch (val)
						{
						case BLOCKED:
							System.out.print("BLOCKED");
							break;
						case TWOWAY:
							System.out.print("TWOWAY");
							break;
						case ATOB:
							System.out.print("ATOB");
							break;
						case BTOA:
							System.out.print("BTOA");
							break;
						}
						System.out.print("]");
					}
					System.out.println("}");
					//Example output: {[Link1,Link2,TWOWAY][Link1,Link3,TWOWAY][Link2,Link3,BLOCKED]}
				}
				else
				{
					//if we get here there's a bug somewhere as by now the hashmap should equal the combinations.
					throw new RuntimeException("CRITICAL: Connectivity - The number of combinations wasn't equal to the size of the internal connections");
				}
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Junction", 0));
			}				
			break;
		case PathyParser.F1I:
			//WhereIs
			if (checkItemEntity(id))
			{
				Entity e = (Entity)worldDict.get(id);
				System.out.println("{" + e.getLocation().getID() + "}");
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Link", 0));
			}
			break;
		case PathyParser.F1T:
			//LinkedTo
			if (checkItemPlace(id))
			{
				PathyPlace p = (PathyPlace)worldDict.get(id);
				HashSet<Link> conns = p.getConnections();

				System.out.print("{");
				boolean first = true;
				for(Link l : conns)
				{
					if (first)
					{
						first = false;
					}
					else
					{
						System.out.print(",");
					}
					//divine which end the node is on then print the other
					if (l.getA() == p)
					{
						System.out.print(l.getB().getID());
					}
					else
					{
						System.out.print(l.getA().getID());
					}
				}
				System.out.println("}");
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Node", 0));
			}
			break;
		case PathyParser.F1B:
			//ConnectedBy
			if (checkItemPlace(id))
			{
				PathyPlace p = (PathyPlace)worldDict.get(id);
				HashSet<Link> conns = p.getConnections();

				System.out.print("{");
				boolean first = true;
				for(Link l : conns)
				{
					if (first)
					{
						first = false;
					}
					else
					{
						System.out.print(",");
					}
					System.out.print(l.getID());
				}
				System.out.println("}");
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, id, "Node", 0));
			}
			break;
		case PathyParser.F1O:
			//TypeOf
			if (worldDict.containsKey(id))
			{
				PathyObject item = worldDict.get(id);
				switch(item.getType())
				{
				case NODE:
					System.out.println("{NODE}");
					break;
				case LINK:
					System.out.println("{LINK}");
					break;
				case JUNCT:
					System.out.println("{JUNCTION}");
					break;
				case ACTION:
					System.out.println("{ACTION}");
					break;
				case ENTITY:
					System.out.println("{ENTITY}");
					break;
				}
			}
			else
			{
				throw new RuntimeException("ERROR: \"" + id + "\" is not a valid identifier an object in the world");
			}
			break;
		}
		return null;
	}

	public Void visitTwoParamQuery(PathyParser.TwoParamQueryContext ctx) {
		String par1 = ctx.idpar(0).getText();
		String par2 = ctx.idpar(1).getText();
		boolean isNode1 = checkItemNode(par1);
		boolean isEntity1 = checkItemEntity(par1);
		boolean isNode2 = checkItemNode(par2);
		
		switch(ctx.op.getType())
		{
		case PathyParser.F2A:
		//PathTo
			if (isNode1 && isNode2)
			{
				Node nodeA = (Node)worldDict.get(par1);
				Node nodeB = (Node)worldDict.get(par2);
				//TODO: Path Finding Algorithm
			}
			else if (!isNode1 && !isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0) + System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}
			else if (!isNode1)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0));
			}
			else if (!isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}				
			break;
		case PathyParser.F2B:
		//CanMove
			if (isEntity1 && isNode2)
			{
				Entity nodeA = (Entity)worldDict.get(par1);
				Node nodeB = (Node)worldDict.get(par2);
				//TODO: Path Finding Algorithm
			}
			else if (!isEntity1 && !isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Entity", 0) + System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}
			else if (!isEntity1)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Entity", 0));
			}
			else if (!isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}				
			break;
		case PathyParser.F2C:
		//CanMoveWhy
			if (isEntity1 && isNode2)
			{
				Entity nodeA = (Entity)worldDict.get(par1);
				Node nodeB = (Node)worldDict.get(par2);
				//TODO: Path Finding Algorithm
			}
			else if (!isEntity1 && !isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Entity", 0) + System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}
			else if (!isEntity1)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Entity", 0));
			}
			else if (!isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}				
			break;
		case PathyParser.F2D:
		//EnergyReq
			if (isNode1 && isNode2)
			{
				Node nodeA = (Node)worldDict.get(par1);
				Node nodeB = (Node)worldDict.get(par2);
				//TODO: Path Finding Algorithm
			}
			else if (!isNode1 && !isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0) + System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}
			else if (!isNode1)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0));
			}
			else if (!isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}				
			break;
		case PathyParser.F2E:
		//SharedActions
			if (isNode1 && isNode2)
			{
				Node nodeA = (Node)worldDict.get(par1);
				Node nodeB = (Node)worldDict.get(par2);
				HashSet<Action> shared = new HashSet<Action>();
				HashSet<Action> actionsA = nodeA.getActivities();
				HashSet<Action> actionsB = nodeB.getActivities();
				
				for (Action a : actionsA)
				{
					if (actionsB.contains(a))
					{
						shared.add(a);
					}
				}

				System.out.print("{");

				boolean first = true;
				for(Action a : shared)
				{
					if (first)
					{
						first = false;
					}
					else
					{
						System.out.print(",");
					}
					System.out.print(a.getID());
				}
				
				System.out.println("}");
			}
			else if (!isNode1 && !isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0) + System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}
			else if (!isNode1)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par1, "Node", 0));
			}
			else if (!isNode2)
			{
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, par2, "Node", 1));
			}				
			break;
		}
		return null;
	}
}
