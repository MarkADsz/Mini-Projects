#pragma once
#include <string>
using namespace std;
class Idea {
private:
	string description, status, creator;
	int act;
public:
	Idea();
	Idea(string& desc, string& stat, string& cr,int act);
	string getDes();
	string getStat();
	string getCreat();
	int getAct();
	string toStr();
};
