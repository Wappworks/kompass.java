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
import java.util.Map;
import java.util.PriorityQueue;

import com.wappworks.common.ai.pathfinder.common.PathNode;
import com.wappworks.common.ai.pathfinder.common.PathNodeEvaluator;
import com.wappworks.common.ai.pathfinder.common.Pathfinder;

public class PathFinderAStar<T> implements Pathfinder<T>
{
	// Instance variables
	// ----------------------------------------
	
	// Public API
	// ----------------------------------------
	
	@Override
	public List<T> findPath(T nodeStart, T nodeEnd,
			PathNodeEvaluator<T> eval)
	{
		Map<T, NodeAStar<T>> pathNodeHeap = new Hashtable<T, NodeAStar<T>>();
		PriorityQueue<NodeAStar<T>> openHeap = new PriorityQueue<NodeAStar<T>>();
		
		NodeAStar<T> pathNodeStart  	= new NodeAStar<T>( nodeStart );
		NodeAStar<T> pathNodeEndBest 	= null;
		pathNodeStart.closed 			= true;
		pathNodeHeap.put( nodeStart, pathNodeStart );
		openHeap.add( pathNodeStart );

		while( openHeap.peek() != null )
		{
			NodeAStar<T> pathNodePrev = openHeap.poll();
			
			// TERMINATING CONDITION: Path is finished...
			if( eval.isPathFinished( pathNodePrev.node, nodeEnd) )
			{
				return( buildPath(pathNodePrev) );
			}
			
			List<T> neighbors = eval.getNeighbours( pathNodePrev.node, pathNodePrev.depth );
			if( neighbors == null )
			{
				continue;
			}
			
			for( T nodeCurr : neighbors )
			{
				// Get/Create the node's path node...
				NodeAStar<T> pathNodeCurr = pathNodeHeap.get( nodeCurr );
				if( pathNodeCurr == null )
				{
					pathNodeCurr = new NodeAStar<T>(nodeCurr);
					pathNodeHeap.put( nodeCurr, pathNodeCurr );
				}
				
				if( pathNodeCurr.closed )
				{
					continue;
				}
				
                // g is the "lowest" cost from the start to the current node...
				float g = pathNodePrev.g + eval.getCost( pathNodePrev.node, pathNodeCurr.node );
				
                // Check to see if the node should be closed...
				if( eval.isPathBlocked(pathNodeCurr.node, pathNodePrev.node, g) )
				{
					pathNodeCurr.closed = true;
					continue;
				}
				
				// Evaluate the node as necessary
				if( !pathNodeCurr.visited || g < pathNodeCurr.g )
				{
					if( pathNodeCurr.visited )
					{
						openHeap.remove( pathNodeCurr );
					}
					
					pathNodeCurr.visited = true;
					pathNodeCurr.depth = pathNodePrev.depth + 1;
					pathNodeCurr.parent = pathNodePrev;
					pathNodeCurr.h = eval.getHeuristicScore( pathNodeCurr.node, nodeEnd );
					pathNodeCurr.g = g;
					pathNodeCurr.f = pathNodeCurr.h + pathNodeCurr.g;
					
					openHeap.add( pathNodeCurr );

					if( pathNodeEndBest == null 														|| 
						pathNodeCurr.h < pathNodeEndBest.h 												|| 
						(pathNodeCurr.h == pathNodeEndBest.h) && (pathNodeCurr.f < pathNodeEndBest.f)		)
					{
						pathNodeEndBest = pathNodeCurr;
					}
				}
			}
		}
		
		// If we get here, we were not able to reach the destination, but we found the
		// best closest path to it...
		return( buildPath(pathNodeEndBest) );
	}

	@Override
	public List<PathNode<T>> findReachables(T nodeStart, PathNodeEvaluator<T> eval)
	{
		List<PathNode<T>> reachables = new ArrayList<PathNode<T>>();
		Map<T, NodeAStar<T>> pathNodeHeap = new Hashtable<T, NodeAStar<T>>();
		PriorityQueue<NodeAStar<T>> openHeap = new PriorityQueue<NodeAStar<T>>();
		
		NodeAStar<T> pathNodeStart = new NodeAStar<T>( nodeStart );
		pathNodeStart.closed = true;
		pathNodeHeap.put( nodeStart, pathNodeStart );
		openHeap.add( pathNodeStart );
		reachables.add( pathNodeStart );
		
		while( openHeap.peek() != null )
		{
			NodeAStar<T> pathNodePrev = openHeap.poll();
			
			List<T> neighbors = eval.getNeighbours( pathNodePrev.node, pathNodePrev.depth );
			for( T nodeCurr : neighbors )
			{
				// Get/Create the node's path node...
				NodeAStar<T> pathNodeCurr = pathNodeHeap.get( nodeCurr );
				if( pathNodeCurr == null )
				{
					pathNodeCurr = new NodeAStar<T>(nodeCurr);
					pathNodeHeap.put( nodeCurr, pathNodeCurr );
				}
				
				if( pathNodeCurr.closed )
				{
					continue;
				}
				
                // g is the "lowest" cost from the start to the current node...
				float g = pathNodePrev.g + eval.getCost( pathNodePrev.node, pathNodeCurr.node );
				
                // Check to see if the node should be closed...
				if( eval.isPathBlocked(pathNodeCurr.node, pathNodePrev.node, g) )
				{
					pathNodeCurr.closed = true;
					continue;
				}
				
				// Evaluate the node as necessary
				if( !pathNodeCurr.visited || g < pathNodeCurr.g )
				{
					if( !pathNodeCurr.visited )
					{
						reachables.add( pathNodeCurr );
					}
					
					pathNodeCurr.visited = true;
					pathNodeCurr.depth = pathNodePrev.depth + 1;
					pathNodeCurr.parent = pathNodePrev;
					pathNodeCurr.g = g;
					pathNodeCurr.f = pathNodeCurr.g;
					
					openHeap.add( pathNodeCurr );
				}
			}
		}
		
		// If we get here, we return an empty list...
		return( reachables );
	}
	
	private List<T> buildPath( NodeAStar<T> pathNodeEnd )
	{
		List<T> path = new ArrayList<T>();
		NodeAStar<T> pathNodeCurr = pathNodeEnd;
		while( pathNodeCurr != null )
		{
			path.add( pathNodeCurr.node );
			pathNodeCurr = pathNodeCurr.parent;
		}
		
		Collections.reverse( path );
		return( path );
	}
}
