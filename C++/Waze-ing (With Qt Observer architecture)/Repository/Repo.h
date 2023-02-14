#pragma once
#include "Driver.h"
#include "Report.h"
#include <vector>
class Repo {

private:
	vector<Driver> drivers;
	vector<Report> reports;
public:
	Repo();
	vector<Driver> getDrivers();
	vector<Report> getReports();
	void addReport(Report& r);
	void addDriver(Driver& d);
	void DeleteReport(int c);

};