
/*  This utility converts source code using tabs for indents, namely it converts all tabs into
    four spaces. Works best if tabs is used only, and not a mixture of tabs and spaces. Also
    does not work well when tabs are used for aligning instead of indenting.
*/

#include <fstream>
#include <vector>
#include <iostream>
using namespace std;

// Size of a tab
const int TAB = 4;

// Do the work on a file
void convertFile(const char* fname){

    // Open the file so it can be read.
    ifstream in;
    in.open(fname);

    // Error opening file?
    if(!in.is_open()){
        cout << "Error converting file: " << fname << endl;
        return;
    }

    // Set up a vector because we can't read and write to the
    // file at the same time.
    vector<char> contents;
    char next;

    while(true){
        // Read a character and process it
        next = in.get();

        // Stop at the end of file
        if(in.eof())
            break;

        // Found a tab?
        if(next == '\t'){
            for(int i=0; i<TAB; i++)
                contents.push_back(' ');
        } else contents.push_back(next);
    }

    // Write the file now
    ofstream out;
    out.open(fname);

    for(int i=0; i<contents.size(); i++)
        out.put(contents[i]);

    in.close();
    out.close();
}

int main(int argc, char *argv[]){

    // Process every file in the arguments list
    for(int i=1; i<argc; i++)
        convertFile(argv[i]);

    return 0;
}
