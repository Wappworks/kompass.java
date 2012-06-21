/*
 * A* path node
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.astar;

import com.wappworks.common.ai.pathfinder.common.PathNode;

public class NodeAStar<T> implements PathNode<T>, Comparable<NodeAStar<T>>
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

	@Override
	public int compareTo(NodeAStar<T> other )
	{
		float diff = f - other.f;
		if( diff == 0 )
		{
			diff = h - other.h;
		}
		
		if( diff < 0 )
			return -1;
		if( diff > 0 )
			return 1;
		
		return 0;
	}
}
