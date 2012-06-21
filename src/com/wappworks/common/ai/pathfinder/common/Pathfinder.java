/*
 * Common path finder interface
 * 
 * Copyright 2011 Wappworks Studio
 * All rights reserved.
 */
package com.wappworks.common.ai.pathfinder.common;

import java.util.List;

public interface Pathfinder<T>
{
	List<T> findPath( T nodeStart, T nodeEnd, PathNodeEvaluator<T> evaluator );
	List<PathNode<T>> findReachables( T nodeStart, PathNodeEvaluator<T> eval );
}
