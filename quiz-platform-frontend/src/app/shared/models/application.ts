import { Candidate } from './candidate';
import { OnlineInterview } from './online-interview';

export class Application {

    id: number;
    createdAt: Date;
    status: String;
    candidate: Candidate = new Candidate();
    /*onlineInterview: OnlineInterview = new OnlineInterview();*/

    constructor() { }

}
