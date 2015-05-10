import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;


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
			PathyObject tempA;
			PathyObject tempB;
			boolean aNodeCheck = checkItemNode(aid);
			boolean bNodeCheck = checkItemNode(bid);
			boolean aJunctCheck = checkItemJunction(aid);
			boolean bJunctCheck = checkItemJunction(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aNodeCheck && !aJunctCheck
			&& !bNodeCheck && !bJunctCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			if ((aNodeCheck || aJunctCheck))
			{
				//parameter 0 is valid
				tempA = worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bNodeCheck || bJunctCheck))
			{
				//parameter 1 is valid
				tempB = worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			Link newLink;
			Node nodeA;
			Node nodeB;
			Junction junctA;
			Junction junctB;
			if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.NODE)
			{
				nodeA = (Node)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, nodeA, nodeB);
			}
			else if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.JUNCT)
			{
				nodeA = (Node)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, nodeA, junctB);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.NODE)
			{
				junctA = (Junction)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, junctA, nodeB);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.JUNCT)
			{
				junctA = (Junction)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, junctA, junctB);
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.ENDPOINTERR,id, null, 0));
			}
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
			PathyObject tempA;
			PathyObject tempB;
			boolean aNodeCheck = checkItemNode(aid);
			boolean bNodeCheck = checkItemNode(bid);
			boolean aJunctCheck = checkItemJunction(aid);
			boolean bJunctCheck = checkItemJunction(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aNodeCheck && !aJunctCheck
			&& !bNodeCheck && !bJunctCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			if ((aNodeCheck || aJunctCheck))
			{
				//parameter 0 is valid
				tempA = worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bNodeCheck || bJunctCheck))
			{
				//parameter 1 is valid
				tempB = worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			Link newLink;
			Node nodeA;
			Node nodeB;
			Junction junctA;
			Junction junctB;
			if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.NODE)
			{
				nodeA = (Node)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, nodeA, nodeB, weight);
			}
			else if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.JUNCT)
			{
				nodeA = (Node)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, nodeA, junctB, weight);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.NODE)
			{
				junctA = (Junction)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, junctA, nodeB, weight);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.JUNCT)
			{
				junctA = (Junction)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, junctA, junctB, weight);
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.ENDPOINTERR,id, null, 0));
			}

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
			PathyObject tempA;
			PathyObject tempB;
			boolean aNodeCheck = checkItemNode(aid);
			boolean bNodeCheck = checkItemNode(bid);
			boolean aJunctCheck = checkItemJunction(aid);
			boolean bJunctCheck = checkItemJunction(bid);
			//check if the parameters provided exist and are a nodes or junctions
			//there are a few checks due to Links taking different types for the same parameter signature
			if (!aNodeCheck && !aJunctCheck
			&& !bNodeCheck && !bJunctCheck)
			{
				//Both parameters are invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0)
						+ System.lineSeparator() + generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			if ((aNodeCheck || aJunctCheck))
			{
				//parameter 0 is valid
				tempA = worldDict.get(aid);
			}
			else
			{
				//parameter 0 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, aid, "Node or Junction", 0));
			}

			if ((bNodeCheck || bJunctCheck))
			{
				//parameter 1 is valid
				tempB = worldDict.get(bid);
			}
			else
			{
				//parameter 1 is invalid
				throw new RuntimeException(generateFeedback(ErrorType.IDNOTVALID, bid, "Node or Junction", 1));
			}
			Link newLink;
			Node nodeA;
			Node nodeB;
			Junction junctA;
			Junction junctB;
			if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.NODE)
			{
				nodeA = (Node)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, nodeA, nodeB, weight, dir);
			}
			else if (tempA.getType() == PathyType.NODE && tempB.getType() == PathyType.JUNCT)
			{
				nodeA = (Node)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, nodeA, junctB, weight, dir);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.NODE)
			{
				junctA = (Junction)tempA;
				nodeB = (Node)tempB;
				newLink = new Link(id, junctA, nodeB, weight, dir);
			}
			else if (tempA.getType() == PathyType.JUNCT && tempB.getType() == PathyType.JUNCT)
			{
				junctA = (Junction)tempA;
				junctB = (Junction)tempB;
				newLink = new Link(id, junctA, junctB, weight, dir);
			}
			else
			{
				throw new RuntimeException(generateFeedback(ErrorType.ENDPOINTERR,id, null, 0));
			}

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
			HashSet<Link> links = getLinks();
			for (Link l : links)
			{
				if (l.getA() == item || l.getB() == item)
				{
					//Throws: if [ID] is a Node or Junction, and is attached to one or more Links
					throw new IllegalStateException("ERROR: \"" + id + "\" is an endpoint for \"" + l.getID() 
							+ "\". Cannot remove \"" + id + "\" unless the Link is removed first."); 
				}
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
		boolean isA = checkItemNode(aid) || checkItemJunction(aid);
		boolean isB = checkItemNode(bid) || checkItemJunction(bid);
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
		PathyObject a = worldDict.get(lid);
		PathyObject b = worldDict.get(lid);

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
		for (PathyObject i : items)
		{
			System.out.println(i.getID());
		}
		return null;
	}

	public Void visitOneParamQuery()
	{
		return null;
	}

	public Void visitTwoParamQuery() {
		return null;
	}
}
