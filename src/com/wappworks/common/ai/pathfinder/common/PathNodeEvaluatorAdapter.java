/*
 * Path node evaluator adapter 
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

import java.util.List;

public abstract class PathNodeEvaluatorAdapter<T> implements
		PathNodeEvaluator<T>
{
	@Override
	public boolean isPathBlocked(T nodeCurr, T nodePrev, float nodeCurrCost)
	{
		return false;
	}

	@Override
	public float getCost(T nodePrev, T nodeCurr)
	{
		return 0;
	}

	@Override
	public float getHeuristicScore(T nodeCurr, T nodeDest)
	{
		return 0;
	}

	@Override
	public boolean isPathFinished(T nodeCurr, T nodeDest)
	{
		return( nodeCurr == nodeDest );
	}

	@Override
	public List<T> getNeighbours( T node, int nodePathDepth )
	{
		return( null );
	}
}
