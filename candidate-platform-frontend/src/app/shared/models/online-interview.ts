import { AssignedQuizOnlineInterview } from './assigned-quiz-online-interview';
import { OnlineInterviewQuizResult } from './online-interview-quiz-result';
import { Application } from './application';
export class OnlineInterview {
    id:number;
    createdAt:Date;
    comments:string;
    status:string;
    assignedQuizzes: Array<AssignedQuizOnlineInterview>=[];
    passedQuizzes: Array<OnlineInterviewQuizResult>=[];
    application:Application=new Application()
    constructor(){}
}
