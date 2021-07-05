import { OnlineInterview } from './online-interview';
import { Candidate } from './candidate';
export class Application {
    id:number;
    createdAt:Date;
    status:string;
    candidate:Candidate=new Candidate();
    onlineInterview:OnlineInterview=new OnlineInterview();
    constructor(){}
}