#include "Service.h"
#include <fstream>


Service::Service(Repo& r):r(r)
{
	this->readD();
	this->readR();
}

vector<Driver> Service::getDriversServ()
{
	return this->r.getDrivers();
}

vector<Report> Service::getReportsServ()
{
	return this->r.getReports();
}

void Service::addReportServ(string& description, string& rep, pair<int, int> location, int valid)
{
	Report nr{ description,rep,location,valid };
	this->r.addReport(nr);
}

void Service::addDriverServ(string& name, string& status, pair<int, int> location, int score)
{
	Driver nd{ name,status,location,score };
	this->r.addDriver(nd);
}

void Service::readD()
{
	ifstream d;
	d.open("drivers.txt");
	string n, name, stat, lat, longi, sc;
	getline(d, n);
	int N = stoi(n);
	for (int i = 0; i < N; i++) {
		getline(d, name);
		getline(d, stat);
		getline(d, lat);
		getline(d, longi);
		getline(d, sc);
		pair<int, int> pp;
		pp.first = stoi(lat);
		pp.second = stoi(longi);
		this->addDriverServ(name, stat, pp, stoi(sc));
	}
	d.close();

}

void Service::readR()
{
	ifstream d;
	d.open("reports.txt");
	string n, descr,rep, lat, longi, calid;
	getline(d, n);
	int N = stoi(n);
	for (int i = 0; i < N; i++) {
		getline(d, descr);
		getline(d, rep);
		getline(d, lat);
		getline(d, longi);
		getline(d, calid);
		pair<int, int> pp;
		pp.first = stoi(lat);
		pp.second = stoi(longi);
		this->addReportServ(descr, rep, pp, stoi(calid));
	}
	d.close();
}

void Service::deleteReportServ(string& descr)
{
	int count = 0;
	for (auto& i : this->r.getReports()) {
		if (i.getDescr().compare(descr) == 0) {
			this->r.DeleteReport(count);
				break;
		}
		count++;
	}
}
