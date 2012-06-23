/*
 * A* path node
 * 
 * NOTE: This class has a natural ordering that is inconsistent
 * 		 with equals
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.astar;

import com.wappworks.common.ai.pathfinder.common.PathNode;

public class NodeAStar<T> implements PathNode<T>, Comparable<NodeAStar<?>>
{
	// Node cost fidelity is up to the 4th decimal place
	private static final float NODECOST_FIDELITY_MULTIPLIER = 10000;	
	
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
	
	@Override
	public T getNode()				{	return node;		}

	@Override
	public PathNode<T> getParent()	{	return parent;		}

	@Override
	public float getCost()			{	return g;			}

	@Override
	public int compareTo(NodeAStar<?> other )
	{
		// Difference is the value rounded up to the 4th decimal place...
		int diff = Math.round( (f - other.f) * NODECOST_FIDELITY_MULTIPLIER );
		if( diff == 0 )
		{
			diff = Math.round( (h - other.h) * NODECOST_FIDELITY_MULTIPLIER );
		}
		return diff;
	}

	@Override
	public boolean equals(Object other)
	{
		// The path nodes are equivalent if the child node is equal...
		if( other instanceof NodeAStar<?> )
		{
			return node.equals( ((NodeAStar<?>) other).node );
		}
		
		return false;
	}

	private void reset()
	{
		depth = 0;
		f = 0;
		g = 0;
		h = 0;
		visited = false;
		closed = false;
		parent = null;
	}
}
