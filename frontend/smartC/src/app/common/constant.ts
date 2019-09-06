export class Constant  {
	URL : string = 'http://139.155.5.200:8080/';
	// URL : string = 'http://169.254.66.232:8080/';
	// URL : string = 'http://100.6.10.171:8080/';
	// URL : string = 'http://localhost:8080/';
	// URL : string = 'http://100.6.10.171:8080/';
	WaitTime : number = 5000;
	RecordTypeMap = [{key: 1, value: 'fix', name: '维修'},
										{key: 2, value: 'oil', name: '加油'},
										{key: 3, value: 'violate', name: '违章'}]
}
