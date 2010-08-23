#include <iostream>
#include <string>
using namespace std;

// Edit distance between two character arrays using insertion,
// deletion, and substitution. Ignores case.
int editdist(string a, string b){

  // Use a dynamic programming algorithm.
  int dp[a.size()+1][b.size()+1];

  // Fill up first row and column
  for(int i=0; i<=a.size(); i++) dp[i][0] = i;
  for(int i=0; i<=b.size(); i++) dp[0][i] = i;

  // Fill up dp array
  for(int i=1; i<=a.size(); i++)
    for(int j=1; j<=b.size(); j++){

      bool same = a[i-1] == b[j-1];

      // Substitution
      int su = dp[i-1][j-1];
      if(!same) su++;

      // Insertion and deletion
      int in = dp[i-1][j]+1;
      int de = dp[i][j-1]+1;

      // Least of the three
      int ce = su;
      if(in < ce) ce = in;
      if(de < ce) ce = de;

      // Write to array
      dp[i][j] = ce;
    }

  return dp[a.size()][b.size()];
}

int main(){
  int cases;
  cin >> cases;

  for(int i=0; i<cases; i++){
    string a;
    string b;
    cin >> a;
    cin >> b;
    cout << editdist(a,b) << endl;
  }

  return 0;
}

