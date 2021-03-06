KOMPASS
==================
Kompass is a path finding framework library. It includes an implementation of the A-star algorithm.


FEATURES
------------------
- Templatized path finding base classes make it easy to customize the path finding requirements.
- A-star path finding algorithm included in the base library


REQUIREMENTS
------------------
This library will only work in a Java SE 6 or later environment.


VERSION HISTORY
------------------
### 0.1.1 Framework change

#### New features
- Added an adapter class for PathNodeEvaluator (PathNodeEvaluatorAdapter)

#### API changes
- PathNodeSet has been removed and its functions rolled into PathNodeEvaluator to allow custom neighbour searching
- PathFinderAStar constructor no longer takes any arguments.
- Added PathNode<T>::getNode() which returns the node wrapped by the path node instance. 


### 0.1.0 First release
Contains basic path finding framework and A-star algorithm


LICENSE
------------------
Released under the MIT license.

### MIT license

Copyright (C) 2011 Chris Khoo, Wappworks Studio 

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
associated documentation files (the "Software"), to deal in the Software without restriction, including 
without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the 
following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial 
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR 
THE USE OR OTHER DEALINGS IN THE SOFTWARE.

