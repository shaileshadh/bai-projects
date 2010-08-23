#include <iostream>
#include <string>
using namespace std;

// Output to cout, string s, t times.
void run(string s, int t){
  
  // Original string simply t times
  string s1 = s;
  for(int i=1; i<t; i++)
    s.append(s1);

  // Iterate with the substring function
  for(int i=0; i<s1.size(); i++){
    cout << s << endl;
    char c = s[0];
    s.erase(0,1);
    s.push_back(c);
  }
}

int main(){

  while(true){
    string s;
    int t;
    cin >> s;
    cin >> t;

    if(s == ".") break;
    run(s,t);
  }

  return 0;
}

