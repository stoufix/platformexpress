import { User } from './user';
import { AssignedQuizOnlineInterviewId } from './assigned-quiz-online-interview-id';
import { Quiz } from './quiz';
import { OnlineInterview } from './online-interview';

export class AssignedQuizOnlineInterview {

    id: AssignedQuizOnlineInterviewId = new AssignedQuizOnlineInterviewId();
    assignedAt: Date;
    passingAt: Date;
    quiz: Quiz = new Quiz();
    /* Circular dependency detected */
    /* onlineInterview:OnlineInterview = new OnlineInterview(); */
    /*onlineInterview: OnlineInterview = new OnlineInterview();*/
    // onlineInterview: any;
    assignedBy: User = new User();

    constructor() { }
}
