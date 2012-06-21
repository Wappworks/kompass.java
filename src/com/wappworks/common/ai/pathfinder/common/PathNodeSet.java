/*
 * Common path node set interface
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

import java.util.List;

public interface PathNodeSet<T>
{
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
