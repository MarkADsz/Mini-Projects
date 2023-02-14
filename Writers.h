#pragma once
#include <string>
using namespace std;
class Writer {
private:
	string name, expertise;
public:
	Writer();
	Writer(string& name, string& expertise);
	string getName();
	string getEpertise();
	string toStr();

};