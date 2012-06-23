/*
 * Common path finder interface
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

import java.util.List;

public interface PathFinder<T>
{
	/*
	 * Finds a path from nodeStart to nodeEnd
	 * 
	 * @param nodeStart		The starting node
	 * @param nodeEnd		The destination node
	 * @param evaluator		The node evaluator
	 * 
	 * @return				A list containing the path from nodeStart to nodeEnd (including nodeStart and nodeEnd)
	 */
	List<T> findPath( T nodeStart, T nodeEnd, PathNodeEvaluator<T> evaluator );

	/*
	 * Finds all reachable nodes from nodeStart
	 * 
	 * @param nodeStart		The starting node
	 * @param evaluator		The node evaluator
	 * 
	 * @return				A list of reachable nodes from nodeStart (excluded)
	 */
	List<PathNode<T>> findReachables( T nodeStart, PathNodeEvaluator<T> eval );
}
