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
	List<T> getNeighbours( T node, int nodePathDepth );
}
