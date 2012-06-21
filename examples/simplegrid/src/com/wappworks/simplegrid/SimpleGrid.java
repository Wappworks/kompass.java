package com.wappworks.simplegrid;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.wappworks.common.ai.pathfinder.astar.PathFinderAStar;
import com.wappworks.common.ai.pathfinder.common.PathNodeEvaluator;
import com.wappworks.common.ai.pathfinder.common.Pathfinder;

@SuppressWarnings("serial")
public class SimpleGrid extends Canvas
{
	//	CONSTANTS
	// ----------------------------------

	//	MEMBER VARIABLES
	// ----------------------------------
	private Pathfinder<Point>	pathFinder;
	
	private int 				rowsNum;
	private int 				colsNum;
	private int[][] 			gridData;
	private Point[][] 			gridPoints;
	
	private Point				pathTargetStart;
	private Point				pathTargetEnd;
	private List<Point>			path;
	
	
	//	PUBLIC API
	// ----------------------------------
	public SimpleGrid( int inCanvasWidth, int inCanvasHeight, int inRowsNum, int inColsNum, int[][] inGridData )
	{
		super();
		
		pathFinder = new PathFinderAStar<Point>();
		
		rowsNum = inRowsNum;
		colsNum = inColsNum;
		gridData = inGridData;
		
		// Set up the grid points...
		gridPoints = new Point[ colsNum ][ rowsNum ];
		for( int row = 0; row < rowsNum; row++ )
		{
			for( int col = 0; col < colsNum; col++ )
			{
				gridPoints[ row ][ col ] = new Point( col, row );
			}
		}
		
		pathTargetStart = null;
		pathTargetEnd = null;
		path = null;

		// Set up the canvas size...
		setSize( inCanvasWidth, inCanvasHeight );
		
		// Attach the mouse listener...
		this.addMouseListener( new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent evt)
			{
				onMouseSelect( evt );
			}
		});
	}
	
	@Override
	public void paint( Graphics graphics )
	{
		Dimension 	canvasSize 	= this.getSize();
		int 		cellWidth	= canvasSize.width / colsNum;
		int 		cellHeight	= canvasSize.height / rowsNum;
		int			gridWidth	= cellWidth * colsNum;
		int			gridHeight	= cellHeight * rowsNum;
		int			offsetX		= ( canvasSize.width - gridWidth ) / 2; 
		int			offsetY		= ( canvasSize.height - gridHeight ) / 2;

		drawObstacles( graphics, offsetX, offsetY, cellWidth, cellHeight );
		drawPath( graphics, offsetX, offsetY, cellWidth, cellHeight );
		drawGrid( graphics, offsetX, offsetY, cellWidth, cellHeight );
	}
	
	private void onMouseSelect( MouseEvent evt )
	{
		// Do we have a path? If so, it's a clear operation...
		if( path != null )
		{
			pathTargetStart = null;
			pathTargetEnd = null;
			path = null;
			
			repaint();
			return;
		}
		
		Dimension 	canvasSize 	= this.getSize();
		int 		cellWidth	= canvasSize.width / colsNum;
		int 		cellHeight	= canvasSize.height / rowsNum;
		int			gridWidth	= cellWidth * colsNum;
		int			gridHeight	= cellHeight * rowsNum;
		int			offsetX		= ( canvasSize.width - gridWidth ) / 2; 
		int			offsetY		= ( canvasSize.height - gridHeight ) / 2;
		
		int cellX = (evt.getX() - offsetX) / cellWidth; 
		int cellY = (evt.getY() - offsetY) / cellHeight;
		
		// Invalid location? Ignore it...
		if( cellX < 0 || cellX >= colsNum || cellY < 0 || cellY >= rowsNum )
			return;
		
		// Are we setting up our target start? Make sure that the starting spot is valid...
		if( pathTargetStart == null )
		{
			if( gridData[ cellY ][ cellX ] != 0 )
				return;
			
			pathTargetStart = gridPoints[ cellY ][ cellX ];
			
			repaint();
			return;
		}

		// If we get here, we're setting up our end target...
		pathTargetEnd = gridPoints[ cellY ][ cellX ];
		buildPath( pathTargetStart, pathTargetEnd );
		
		repaint();
	}
	
	private void buildPath( Point start, Point end )
	{
		path = pathFinder.findPath(start, end, new PathNodeEvaluator<Point>()
		{
			@Override
			public float getCost(Point nodePrev, Point nodeCurr)
			{
				return getChebyshevDistance( nodePrev, nodeCurr );
			}

			@Override
			public float getHeuristicScore(Point nodeCurr, Point nodeDest)
			{
				return getChebyshevDistance( nodeCurr, nodeDest );
			}

			@Override
			public boolean isPathBlocked(Point nodeCurr, Point nodePrev, float nodeCurrCost)
			{
				return( gridData[ nodeCurr.y ][ nodeCurr.x ] != 0 );
			}

			@Override
			public boolean isPathFinished(Point nodeCurr, Point nodeDest)
			{
				return( nodeCurr.x == nodeDest.x && nodeCurr.y == nodeDest.y );
			}
			
			private float getChebyshevDistance( Point p1, Point p2 )
			{
				return Math.max( Math.abs(p2.x - p1.x), Math.abs(p2.y - p1.y) );
			}

			@Override
			public List<Point> getNeighbours(Point point, int pathDepth)
			{
				ArrayList<Point> neighbours = new ArrayList<Point>();
				
				if( (point.x - 1) >= 0 )
					neighbours.add( gridPoints[point.y][point.x - 1] );
				if( (point.x + 1) < colsNum )
					neighbours.add( gridPoints[point.y][point.x + 1] );
				if( (point.y - 1) >= 0 )
					neighbours.add( gridPoints[point.y - 1][point.x] );
				if( (point.y + 1) < rowsNum )
					neighbours.add( gridPoints[point.y + 1][point.x] );
				
				return neighbours;
			}
		});
	}
	
	private void drawObstacles( Graphics graphics, int offsetX, int offsetY, int cellWidth, int cellHeight )
	{
		for( int row = 0; row < rowsNum; row++ )
		{
			for( int col = 0; col < colsNum; col++ )
			{
				if( gridData[ row ][ col ] == 0 )
					continue;
				
				graphics.setColor( new Color(0, 0, 0) );
				graphics.fillRect( offsetX + (col * cellWidth), offsetY + (row * cellHeight), cellWidth, cellHeight );
			}
		}
	}

	private void drawPath( Graphics graphics, int offsetX, int offsetY, int cellWidth, int cellHeight )
	{
		if( path != null )
		{
			for( Point cell : path )
			{
				graphics.setColor( new Color(0, 0, 255) );
				graphics.fillRect( offsetX + (cell.x * cellWidth), offsetY + (cell.y * cellHeight), cellWidth, cellHeight );
			}
			
		}

		if( pathTargetStart != null )
		{
			graphics.setColor( new Color(0, 255, 0, 127) );
			graphics.fillRect( offsetX + (pathTargetStart.x * cellWidth), offsetY + (pathTargetStart.y * cellHeight), cellWidth, cellHeight );
		}

		if( pathTargetEnd != null )
		{
			graphics.setColor( new Color(255, 0, 0, 127) );
			graphics.fillRect( offsetX + (pathTargetEnd.x * cellWidth), offsetY + (pathTargetEnd.y * cellHeight), cellWidth, cellHeight );
		}
	}

	private void drawGrid( Graphics graphics, int offsetX, int offsetY, int cellWidth, int cellHeight )
	{
		int gridWidth	= cellWidth * colsNum;
		int gridHeight	= cellHeight * rowsNum;

		graphics.setColor( new Color(0, 0, 0) );
		
		// Draw the row separators
		for( int row = 0; row <= rowsNum; row++ )
		{
			int rowY = offsetY + (row * cellHeight);
			graphics.drawLine( offsetX, rowY, offsetX + gridWidth, rowY );
		}

		// Draw the column separators
		for( int col = 0; col <= colsNum; col++ )
		{
			int colX = offsetX + (col * cellWidth);
			graphics.drawLine( colX, offsetY, colX, offsetY + gridHeight );
		}
	}
}
