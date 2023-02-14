#pragma once
#include "Repo.h"
#include "Observer.h"
class Service: public Subject {
private:
	Repo& r;

public:
	
	Service(Repo& r);
	vector<Driver> getDriversServ();
	vector<Report> getReportsServ();
	void addReportServ(string& description, string& rep, pair<int, int> location, int valid);
	void addDriverServ(string& name, string& status, pair<int, int> location, int score);
	void readD();
	void readR();
	void deleteReportServ(string& descr);
};