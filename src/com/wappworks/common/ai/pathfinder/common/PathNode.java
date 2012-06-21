package com.wappworks.common.ai.pathfinder.common;

public interface PathNode<T>
{
	/*
	 * Returns the path's previous node
	 * 
	 * @return					The current node's previous path node. null if there isn't one
	 */
	PathNode<T> getParent();

	/*
	 * Returns the node's total path cost
	 * 
	 * @return					The node's path cost
	 */
	float		getCost();
}
