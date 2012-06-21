/*
 * A* path finder
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;

import com.wappworks.common.ai.pathfinder.common.PathNode;
import com.wappworks.common.ai.pathfinder.common.PathNodeEvaluator;
import com.wappworks.common.ai.pathfinder.common.PathNodeSet;
import com.wappworks.common.ai.pathfinder.common.Pathfinder;

public class PathFinderAStar<T> implements Pathfinder<T>
{
	// Instance variables
	// ----------------------------------------
	private PathNodeSet<T>			nodeSet;
	
	// Public API
	// ----------------------------------------
	public PathFinderAStar( PathNodeSet<T> inNodeSet )
	{
		nodeSet = inNodeSet;
	}
	
	@Override
	public List<T> findPath(T nodeStart, T nodeEnd,
			PathNodeEvaluator<T> eval)
	{
		Hashtable<T, NodeAStar<T>> pathNodeHeap = new Hashtable<T, NodeAStar<T>>();
		TreeMap<Float, NodeAStar<T>> openHeap = new TreeMap<Float, NodeAStar<T>>();
		
		NodeAStar<T> pathNodeStart = new NodeAStar<T>( nodeStart );
		pathNodeStart.closed = true;
		pathNodeHeap.put( nodeStart, pathNodeStart );
		openHeap.put( pathNodeStart.f, pathNodeStart );

		while( openHeap.size() > 0 )
		{
			NodeAStar<T> pathNodePrev = openHeap.firstEntry().getValue();
			openHeap.remove( pathNodePrev );
			
			// TERMINATING CONDITION: Path is finished...
			if( eval.isPathFinished( pathNodePrev.node, nodeEnd) )
			{
				List<T> path = new ArrayList<T>();
				
				do
				{
					path.add( pathNodePrev.node );
					pathNodePrev = pathNodePrev.parent;
				}
				while( pathNodePrev != null );
				
				Collections.reverse( path );
				return( path );
			}
			
			List<T> neighbors = nodeSet.getNeighbours( pathNodePrev.node, pathNodePrev.depth );
			for( T nodeCurr : neighbors )
			{
				// Get/Create the node's path node...
				NodeAStar<T> pathNodeCurr = null;
				try
				{
					pathNodeCurr = pathNodeHeap.get( nodeCurr );
				}
				catch( NullPointerException e )	{}
				
				if( pathNodeCurr == null )
				{
					pathNodeCurr = new NodeAStar<T>(nodeCurr);
					pathNodeHeap.put( nodeCurr, pathNodeCurr );
				}
				
				if( pathNodeCurr.closed )
					continue;
				
                // g is the "lowest" cost from the start to the current node...
				float g = pathNodePrev.g + eval.getCost( pathNodePrev.node, pathNodeCurr.node );
				
                // Check to see if the node should be closed...
				if( eval.isClosed(pathNodeCurr.node, pathNodePrev.node, g) )
				{
					pathNodeCurr.closed = true;
					continue;
				}
				
				// Evaluate the node as necessary
				if( !pathNodeCurr.visited || g < pathNodeCurr.g )
				{
					if( pathNodeCurr.visited )
						openHeap.remove( pathNodeCurr );
					
					pathNodeCurr.visited = true;
					pathNodeCurr.depth = pathNodePrev.depth + 1;
					pathNodeCurr.parent = pathNodePrev;
					pathNodeCurr.h = eval.getHeuristicScore( pathNodeCurr.node, nodeEnd );
					pathNodeCurr.g = g;
					pathNodeCurr.f = pathNodeCurr.h + pathNodeCurr.g;
					
					openHeap.put( pathNodeCurr.f, pathNodeCurr );
				}
			}
		}
		
		// If we get here, we return an empty list...
		return( new ArrayList<T>() );
	}

	@Override
	public List<PathNode<T>> findReachables(T nodeStart, PathNodeEvaluator<T> eval)
	{
		List<PathNode<T>> reachables = new ArrayList<PathNode<T>>();
		Hashtable<T, NodeAStar<T>> pathNodeHeap = new Hashtable<T, NodeAStar<T>>();
		TreeMap<Float, NodeAStar<T>> openHeap = new TreeMap<Float, NodeAStar<T>>();
		
		NodeAStar<T> pathNodeStart = new NodeAStar<T>( nodeStart );
		pathNodeStart.closed = true;
		pathNodeHeap.put( nodeStart, pathNodeStart );
		openHeap.put( pathNodeStart.f, pathNodeStart );
		reachables.add( pathNodeStart );
		
		while( openHeap.size() > 0 )
		{
			NodeAStar<T> pathNodePrev = openHeap.firstEntry().getValue();
			openHeap.remove( pathNodePrev );
			
			List<T> neighbors = nodeSet.getNeighbours( pathNodePrev.node, pathNodePrev.depth );
			for( T nodeCurr : neighbors )
			{
				// Get/Create the node's path node...
				NodeAStar<T> pathNodeCurr = null;
				try
				{
					pathNodeCurr = pathNodeHeap.get( nodeCurr );
				}
				catch( NullPointerException e )	{}
				
				if( pathNodeCurr == null )
				{
					pathNodeCurr = new NodeAStar<T>(nodeCurr);
					pathNodeHeap.put( nodeCurr, pathNodeCurr );
				}
				
				if( pathNodeCurr.closed )
					continue;
				
                // g is the "lowest" cost from the start to the current node...
				float g = pathNodePrev.g + eval.getCost( pathNodePrev.node, pathNodeCurr.node );
				
                // Check to see if the node should be closed...
				if( eval.isClosed(pathNodeCurr.node, pathNodePrev.node, g) )
				{
					pathNodeCurr.closed = true;
					continue;
				}
				
				// Evaluate the node as necessary
				if( !pathNodeCurr.visited || g < pathNodeCurr.g )
				{
					if( !pathNodeCurr.visited )
						reachables.add( pathNodeCurr );
					
					pathNodeCurr.visited = true;
					pathNodeCurr.depth = pathNodePrev.depth + 1;
					pathNodeCurr.parent = pathNodePrev;
					pathNodeCurr.g = g;
					pathNodeCurr.f = pathNodeCurr.g;
					
					openHeap.put( pathNodeCurr.f, pathNodeCurr );
				}
			}
		}
		
		// If we get here, we return an empty list...
		return( reachables );
	}
}
