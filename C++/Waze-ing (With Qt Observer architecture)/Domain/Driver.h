#pragma once
#include <utility>
#include <string>
using namespace std;
class Driver {
private:
	string name, status;
	pair<int, int> currentLocation;
	int score;

public:
	Driver();
	Driver(string& name, string& status, pair<int, int> location, int score);
	int getLat();
	int getLong();
	int getScore();
	string getName();
	string getStatus();
	string toStr();
};