/*
 * A* path node
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.astar;

import com.wappworks.common.ai.pathfinder.common.PathNode;

public class NodeAStar<T> implements PathNode<T>
{
	public 	T 				node;
	
	public	int				depth;
	
	public	float			f;
	public	float			g;
	public	float			h;
	
	public	boolean			visited;
	public	boolean			closed;
	
	public	NodeAStar<T>	parent;
	
	public NodeAStar( T inNode )
	{
		node = inNode;
		reset();
	}
	
	public void reset()
	{
		depth = 0;
		f = 0;
		g = 0;
		h = 0;
		visited = false;
		closed = false;
		parent = null;
	}

	@Override
	public PathNode<T> getParent()	{	return parent;		}

	@Override
	public float getCost()			{	return g;			}
}
