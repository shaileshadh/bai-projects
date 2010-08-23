#include <iostream>
#include <vector>
#include <string>
using namespace std;
typedef long long int ll;

ll gcd(ll a, ll b){
  while(b>0){
    ll t = b;
    b = a % b;
    a = t;
  }
  return a;
}

// Define the GCD and LCM functions for 2 arguments
ll lcm(ll a, ll b){
  return a*b / gcd(a,b);
}

// LCM for a vector
ll lcmv(vector<int> *v){
  ll c = v->at(0);
  for(int i=1; i<v->size(); i++)
    c = lcm(c,v->at(i));
  return c;
}

ll rcase(string s){

  // Numbers that can be and can't be divisible
  vector<int> yes;
  vector<int> no;

  // Parse string into vectors
  for(int i=0; i<s.size(); i++){
    if(s[i] == 'Y') yes.push_back(i+1);
    else no.push_back(i+1);
  }

  // No yes cases?
  if(yes.empty()) return -1;

  // Calculate lcm
  ll yl = lcmv(&yes);

  // Check that it's possible
  bool possible = true;
  for(int i=0; i<no.size(); i++)
    if(yl % no[i] == 0) possible = false;

  if(!possible) return -1;

  // Brute force it
  for(int i=1; ;i++){
    ll t = i*yl;

    // Test each one in the no vector
    bool f = true;
    for(int j=0; j<no.size(); j++){
      if(t % no[j] == 0){
        f = false;
        break;
      }
    }

    if(f)
      return t;
  }
}

int main(){

  while(true){

    // Read in as string and process
    string s;
    cin >> s;

    if(s == "*") break;

    cout << rcase(s) << endl;
  }

  return 0;
}
