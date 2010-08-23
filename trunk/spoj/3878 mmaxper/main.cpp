#include <iostream>
using namespace std;

/* Use a dynamic programming algorithm.
   Each time we place a rectangle, there are two ways to place it: length
   wise and width wise. So when we place a rectangle, there are exactly
   4 combinations we need to care about. We can use the previous rectangle
   configurations to calculate the maximum perimeter if we put the current
   one one way, then the maximum if we put it the other way.
*/

int abs(int x)
{
  return x>0? x: -x;
}

int main()
{
  // Number of rectangles
  int rects;
  cin >> rects;

  // Array of rectangle dimensions
  int Q[rects][2];

  for(int i=0; i<rects; i++){
    cin >> Q[i][0];
    cin >> Q[i][1];
  }

  // Maximum for each of the two orientations
  int o1 = Q[0][1];
  int o2 = Q[0][0];

  // Heights for each of the two orientations
  int h1 = Q[0][0];
  int h2 = Q[0][1];

  for(int i=1; i<rects; i++){

    // New orientations (becomes orientations for next iteration)
    int n1=o1, n2=o1;

    // Try new rectangle
    n1 += abs(h1-Q[i][0]);
    n1 += Q[i][1];

    int n1_ = o2;
    n1_ += abs(h2-Q[i][0]);
    n1_ += Q[i][1];

    if(n1_ > n1) n1 = n1_;

    // Other way
    n2 += abs(h1-Q[i][1]);
    n2 += Q[i][0];

    int n2_ = o2;
    n2_ += abs(h2-Q[i][1]);
    n2_ += Q[i][0];

    if(n2_ > n2) n2 = n2_;

    // Update
    o1 = n1;
    o2 = n2;
    h1 = Q[i][0];
    h2 = Q[i][1];
  }

  cout << (o1>o2?o1:o2) << endl;

  return 0;
}
