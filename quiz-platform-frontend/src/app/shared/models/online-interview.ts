import { Application } from './application';
import { AssignedQuizOnlineInterview } from './assigned-quiz-online-interview';

export class OnlineInterview {

    id: number;
    createdAt: Date;
    comments: String;
    status: String;
    assignedQuizzes: Array<AssignedQuizOnlineInterview> = [];
    application: Application = new Application();

    constructor() {
    }

}
