#include "Repo.h"

Repo::Repo()
{
}

vector<Driver> Repo::getDrivers()
{
	return this->drivers;
}

vector<Report> Repo::getReports()
{
	return this->reports;
}

void Repo::addReport(Report& r)
{
	this->reports.push_back(r);
}

void Repo::addDriver(Driver& d)
{
	this->drivers.push_back(d);
}

void Repo::DeleteReport(int c)
{
	this->reports.erase(this->reports.begin() + c);
}
