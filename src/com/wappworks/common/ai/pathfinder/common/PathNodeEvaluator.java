/*
 * Common path node evaluator interface 
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

public interface PathNodeEvaluator<T>
{
	boolean isClosed(T nodeCurr, T nodePrev, float nodeCurrCost);
	float getCost(T nodePrev, T nodeCurr);
	float getHeuristicScore(T nodeCurr, T nodeDest);
	boolean isPathFinished(T nodeCurr, T nodeDest);
}
