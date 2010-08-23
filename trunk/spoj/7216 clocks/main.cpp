// Already did this problem.
// Copied from USACO folder, converted to C++

#include <iostream>
#include <cstdlib>
using namespace std;

int initial[] = {3,3,0,2,2,2,2,1,2};
int current[9];
int maxdepth;
int *solution;

/*
 * 0 1 2   A B C
 * 3 4 5   D E F
 * 6 7 8   G H I
 * */
int patterns[9][5] = {
  {0,1,3,4,-1},
  {0,1,2,-1,-1},
  {1,2,4,5,-1},
  {0,3,6,-1,-1},
  {1,3,4,5,7},
  {2,5,8,-1,-1},
  {3,4,6,7,-1},
  {6,7,8,-1,-1},
  {4,5,7,8,-1}
};

int used[9];

void adjust(int *a){
  if(*a==3)
    *a=0;
  else *a+=1;
}

void adjustback(int *a){
  if(*a==0)
    *a=3;
  else *a-=1;
}

// When called, current[][] should contain the position from which
// to begin the search.
bool dfs(int startfrom, int curdepth){
  bool all12 = true;

  // Is it already all 12?
  for(int i=0; i<9; i++)
    if(current[i]!=0){
      all12 = false;
      break;
    }

  if(all12){
    return true;
  }

  if(curdepth>=maxdepth)
    return false;

  for(int pat=startfrom; pat<9; pat++){
    if(used[pat]>=3)
      continue;

    //Adjust to, and back
    for(int i=0; i<5; i++){
      int c = patterns[pat][i];
      if(c != -1){
        adjust(&current[c]);
      }
    }

    int temp = solution[curdepth];
    used[pat]++;
    solution[curdepth]=pat+1;
    bool f = dfs(pat,curdepth+1);
    if(f) return true;
    solution[curdepth]=temp;
    used[pat]--;

    for(int i=0; i<5; i++){
      int c = patterns[pat][i];
      if(c != -1){
        adjustback(&current[c]);
      }
    }
  }

  return false;
}

int main() {

  for(int i=0; i<9; i++)
    cin >> initial[i];

  for(maxdepth=0;; maxdepth++){
    solution = (int*) calloc(maxdepth,4);
    for(int i=0; i<9; i++){
      current[i]=initial[i];
      used[i]=0;
    }

    bool f=dfs(0,0);
    if(f)
      break;
  }

  for(int i=0; i<maxdepth-1; i++)
    cout << solution[i] << " ";
  cout << solution[maxdepth-1];

  return 0;
}

