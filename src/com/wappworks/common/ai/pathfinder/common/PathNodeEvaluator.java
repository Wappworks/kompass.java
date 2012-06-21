/*
 * Common path node evaluator interface 
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

import java.util.List;

public interface PathNodeEvaluator<T>
{
	/*
	 * Determines if the path between two nodes is blocked
	 * 
	 * @param	nodeCurr		The node currently under evaluation
	 * @param	nodePrev		The previous node
	 * @param	nodeCurrCost	The cost of moving to the current node
	 * 
	 * @return					true if the path is blocked. false otherwise.
	 */
	boolean isPathBlocked(T nodeCurr, T nodePrev, float nodeCurrCost);
	
	/*
	 * Returns the cost of moving from the previous node to the current one
	 * 
	 * @param	nodeCurr		The node currently under evaluation
	 * @param	nodePrev		The previous node
	 * 
	 * @return					the cost of moving from nodePrev to nodeCurr
	 */
	float getCost(T nodePrev, T nodeCurr);

	/*
	 * Returns the heuristic cost of moving from the current node to the destination node
	 * 
	 * @param	nodeCurr		The node currently under evaluation
	 * @param	nodeDest		The destination node
	 * 
	 * @return					the cost of moving from nodeCurr to nodeDest
	 */
	float getHeuristicScore(T nodeCurr, T nodeDest);

	/*
	 * Determines if the path finding is complete
	 * 
	 * @param	nodeCurr		The node currently under evaluation
	 * @param	nodeDest		The destination node
	 * 
	 * @return					true if the path finding is complete. false otherwise.
	 */
	boolean isPathFinished(T nodeCurr, T nodeDest);

	/*
	 * Returns the target node's neighbors
	 * 
	 * @param	node			The target node
	 * @param	nodePathDepth	The path depth (so far) to the current node
	 * 
	 * @return					The list of node's neighbours
	 */
	List<T> getNeighbours( T node, int nodePathDepth );
}
