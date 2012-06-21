package com.wappworks.common.ai.pathfinder.common;

public interface PathNode<T>
{
	PathNode<T> getParent();
	float		getCost();
}
