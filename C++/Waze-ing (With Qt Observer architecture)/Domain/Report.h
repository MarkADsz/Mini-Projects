#pragma once
#include <string>
#include <utility>
using namespace std;
class Report {

private:
	string description, reporter;
	pair<int, int> location;
	int validated;

public:
	Report();
	Report(string& description, string& rep, pair<int, int> location,int valid);
	string getDescr();
	string getRep();
	int getLat();
	int getLong();
	int getValid();
	string toStr();

};